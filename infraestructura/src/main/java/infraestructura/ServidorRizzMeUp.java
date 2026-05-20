package infraestructura;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import persistencia.AccesoDatos;
import persistencia.IAccesoDatos;

/**
 * Servidor TCP de RizzMeUp.
 *
 * Escucha en el puerto 9090, acepta clientes y hace broadcast
 * de eventos en tiempo real (nuevo usuario, match, mensaje).
 *
 * Se inicia automaticamente como hilo daemon cuando se crea
 * la primera instancia de ActualizarPersonas.
 *
 * @author USUARIO
 */
public class ServidorRizzMeUp {

    private final int puerto;
    private final ServerSocket serverSocket;
    private final List<ClienteHandler> clientes;
    private final IAccesoDatos bd;
    private final Gson gson;

    /**
     * Crea el servidor y ocupa el puerto.
     * Lanza IOException si el puerto ya esta en uso.
     *
     * @param puerto puerto TCP a escuchar
     * @throws IOException si el puerto ya esta ocupado
     */
    public ServidorRizzMeUp(int puerto) throws IOException {
        this.puerto = puerto;
        this.serverSocket = new ServerSocket(puerto);
        this.clientes = Collections.synchronizedList(new ArrayList<>());
        this.bd = new AccesoDatos();
        this.gson = buildGson();
        System.out.println("[Servidor] RizzMeUp listo en puerto " + puerto);
    }

    /**
     * Bucle principal: acepta clientes y lanza un hilo por cada uno.
     * Llamar en un hilo daemon separado.
     */
    public void iniciar() {
        System.out.println("[Servidor] Esperando clientes...");
        while (!serverSocket.isClosed()) {
            try {
                Socket socketCliente = serverSocket.accept();
                ClienteHandler handler = new ClienteHandler(socketCliente, this, bd, gson);
                clientes.add(handler);
                Thread hilo = new Thread(handler);
                hilo.setDaemon(true);
                hilo.setName("Handler-" + socketCliente.getRemoteSocketAddress());
                hilo.start();
            } catch (IOException e) {
                if (!serverSocket.isClosed()) {
                    System.err.println("[Servidor] Error aceptando cliente: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Envia un mensaje a todos los clientes conectados excepto el origen.
     *
     * @param mensajeJson JSON del mensaje a enviar
     * @param origen      el cliente que origino el evento (se excluye), puede ser null para enviar a todos
     */
    public void broadcast(String mensajeJson, ClienteHandler origen) {
        synchronized (clientes) {
            for (ClienteHandler cliente : clientes) {
                if (cliente != origen) {
                    cliente.enviar(mensajeJson);
                }
            }
        }
    }

    /**
     * Quita un cliente de la lista cuando se desconecta.
     */
    public void removerCliente(ClienteHandler handler) {
        clientes.remove(handler);
        System.out.println("[Servidor] Clientes activos: " + clientes.size());
    }

    /**
     * Gson con adaptadores para LocalDate y LocalDateTime.
     */
    public static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (src, t, ctx) -> ctx.serialize(src.toString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, t, ctx) -> LocalDate.parse(json.getAsString()))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (src, t, ctx) -> ctx.serialize(src.toString()))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, t, ctx) -> LocalDateTime.parse(json.getAsString()))
                .create();
    }

    /**
     * Permite ejecutar el servidor de forma independiente.
     */
    public static void main(String[] args) {
        try {
            ServidorRizzMeUp servidor = new ServidorRizzMeUp(9090);
            servidor.iniciar();
        } catch (IOException e) {
            System.err.println("Error fatal: No se pudo iniciar el servidor. ¿El puerto 9090 ya esta en uso?");
        }
    }
}

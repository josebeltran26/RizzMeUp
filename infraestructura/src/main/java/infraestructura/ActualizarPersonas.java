package infraestructura;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.LikeDTO;
import dto.MatchDTO;
import dto.MensajeDTO;
import dto.PreferenciasUsuarioDTO;
import dto.UsuarioDTO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Cliente TCP real que se comunica con ServidorRizzMeUp.
 *
 * Al instanciarse:
 *  1. Intenta iniciar el servidor en un hilo daemon (si el puerto esta libre).
 *     Si ya esta ocupado, otro proceso ya lo esta corriendo -> no pasa nada.
 *  2. Se conecta al servidor como cliente.
 *  3. Lanza un hilo de escucha que separa respuestas sincronas
 *     de broadcasts asincronos (nuevo usuario, match, mensaje).
 *
 * @author USUARIO
 */
public class ActualizarPersonas implements IActualizarPersonas {

    private static final String HOST = "localhost";
    private static final int PUERTO = 9090;
    private static final int TIMEOUT_MS = 5000;

    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;
    private final Gson gson;

    // Listeners para broadcasts en tiempo real
    private final List<Consumer<UsuarioDTO>> listenersNuevoUsuario = new CopyOnWriteArrayList<>();
    private final List<Consumer<UsuarioDTO>> listenersActualizado = new CopyOnWriteArrayList<>();
    private final List<Consumer<MatchDTO>> listenersNuevoMatch = new CopyOnWriteArrayList<>();
    private final List<Consumer<MensajeDTO>> listenersNuevoMensaje = new CopyOnWriteArrayList<>();

    // Cola FIFO para recibir respuestas sincronas (OK / ERROR / datos)
    private final BlockingQueue<MensajeTCP> colaRespuestas = new LinkedBlockingQueue<>();

    public ActualizarPersonas() {
        this.gson = ServidorRizzMeUp.buildGson(); // reutiliza el mismo Gson
        iniciarServidorSiNecesario();
        conectar();
    }

    // -------------------------------------------------------------------------
    // Inicio del servidor (daemon)
    // -------------------------------------------------------------------------

    private void iniciarServidorSiNecesario() {
        try {
            ServidorRizzMeUp servidor = new ServidorRizzMeUp(PUERTO);
            Thread hilo = new Thread(servidor::iniciar);
            hilo.setDaemon(true);
            hilo.setName("Servidor-RizzMeUp");
            hilo.start();
            Thread.sleep(600);
            System.out.println("[Cliente] Servidor iniciado en este proceso.");
        } catch (IOException e) {
            System.out.println("[Cliente] Servidor ya activo en otro proceso.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // -------------------------------------------------------------------------
    // Conexion al servidor
    // -------------------------------------------------------------------------

    private void conectar() {
        for (int i = 1; i <= 5; i++) {
            try {
                socket = new Socket(HOST, PUERTO);
                salida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
                entrada = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                iniciarHiloEscucha();
                System.out.println("[Cliente] Conectado al servidor " + HOST + ":" + PUERTO);
                return;
            } catch (IOException e) {
                System.out.println("[Cliente] Intento " + i + "/5 fallido...");
                try { Thread.sleep(400); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }
        throw new RuntimeException("No se pudo conectar al servidor en " + HOST + ":" + PUERTO);
    }

    private void iniciarHiloEscucha() {
        Thread hilo = new Thread(() -> {
            try {
                String linea;
                while ((linea = entrada.readLine()) != null) {
                    procesarEntrante(linea);
                }
            } catch (IOException e) {
                System.out.println("[Cliente] Conexion cerrada.");
            }
        });
        hilo.setDaemon(true);
        hilo.setName("RizzMeUp-Escucha");
        hilo.start();
    }

    // -------------------------------------------------------------------------
    // Procesador de mensajes entrantes
    // -------------------------------------------------------------------------

    private void procesarEntrante(String json) {
        try {
            MensajeTCP msg = gson.fromJson(json, MensajeTCP.class);
            switch (msg.getTipo()) {
                case "NUEVO_USUARIO" -> {
                    UsuarioDTO u = gson.fromJson(msg.getPayload(), UsuarioDTO.class);
                    listenersNuevoUsuario.forEach(l -> l.accept(u));
                }
                case "USUARIO_ACTUALIZADO" -> {
                    UsuarioDTO u = gson.fromJson(msg.getPayload(), UsuarioDTO.class);
                    listenersActualizado.forEach(l -> l.accept(u));
                }
                case "NUEVO_MATCH" -> {
                    MatchDTO m = gson.fromJson(msg.getPayload(), MatchDTO.class);
                    listenersNuevoMatch.forEach(l -> l.accept(m));
                }
                case "NUEVO_MENSAJE" -> {
                    MensajeDTO m = gson.fromJson(msg.getPayload(), MensajeDTO.class);
                    listenersNuevoMensaje.forEach(l -> l.accept(m));
                }
                // OK, ERROR, y cualquier respuesta sincrona → a la cola
                default -> colaRespuestas.put(msg);
            }
        } catch (Exception e) {
            System.err.println("[Cliente] Error procesando mensaje: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Envio sincrono: enviar + esperar respuesta
    // -------------------------------------------------------------------------

    private synchronized MensajeTCP enviarYEsperar(MensajeTCP msg) {
        salida.println(gson.toJson(msg));
        try {
            MensajeTCP resp = colaRespuestas.poll(TIMEOUT_MS, TimeUnit.MILLISECONDS);
            if (resp == null) throw new RuntimeException("Timeout esperando respuesta del servidor.");
            return resp;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrumpido esperando respuesta.", e);
        }
    }

    // =========================================================================
    // IActualizarPersonas - Usuario
    // =========================================================================

    @Override
    public Long registrarUsuario(UsuarioDTO usuario) {
        MensajeTCP resp = enviarYEsperar(new MensajeTCP("REGISTER", gson.toJson(usuario)));
        if ("OK".equals(resp.getTipo())) {
            return gson.fromJson(resp.getPayload(), UsuarioDTO.class).getId();
        }
        throw new RuntimeException("Error al registrar: " + resp.getPayload());
    }

    @Override
    public boolean actualizarUsuario(UsuarioDTO usuario) {
        return "OK".equals(enviarYEsperar(new MensajeTCP("UPDATE", gson.toJson(usuario))).getTipo());
    }

    @Override
    public boolean eliminarUsuario(Long usuarioId) {
        return "OK".equals(enviarYEsperar(new MensajeTCP("ELIMINAR", String.valueOf(usuarioId))).getTipo());
    }

    @Override
    public UsuarioDTO obtenerPerfilPorId(Long usuarioId) {
        MensajeTCP resp = enviarYEsperar(new MensajeTCP("GET_USUARIO", String.valueOf(usuarioId)));
        return "OK".equals(resp.getTipo()) ? gson.fromJson(resp.getPayload(), UsuarioDTO.class) : null;
    }

    @Override
    public UsuarioDTO obtenerPerfilPorCorreo(String correo) {
        MensajeTCP resp = enviarYEsperar(new MensajeTCP("GET_USUARIO_POR_CORREO", correo));
        return "OK".equals(resp.getTipo()) ? gson.fromJson(resp.getPayload(), UsuarioDTO.class) : null;
    }

    @Override
    public List<UsuarioDTO> obtenerTodosLosPerfiles() {
        MensajeTCP resp = enviarYEsperar(new MensajeTCP("GET_CANDIDATOS", "0"));
        if ("OK".equals(resp.getTipo())) {
            Type t = new TypeToken<List<UsuarioDTO>>() {}.getType();
            return gson.fromJson(resp.getPayload(), t);
        }
        return new ArrayList<>();
    }

    @Override
    public List<UsuarioDTO> obtenerCandidatos(Long usuarioId) {
        MensajeTCP resp = enviarYEsperar(new MensajeTCP("GET_CANDIDATOS", String.valueOf(usuarioId)));
        if ("OK".equals(resp.getTipo())) {
            Type t = new TypeToken<List<UsuarioDTO>>() {}.getType();
            return gson.fromJson(resp.getPayload(), t);
        }
        return new ArrayList<>();
    }

    // =========================================================================
    // Preferencias
    // =========================================================================

    @Override
    public void guardarPreferencias(PreferenciasUsuarioDTO preferencias) {
        enviarYEsperar(new MensajeTCP("GUARDAR_PREFERENCIAS", gson.toJson(preferencias)));
    }

    @Override
    public PreferenciasUsuarioDTO obtenerPreferencias(Long usuarioId) {
        MensajeTCP resp = enviarYEsperar(new MensajeTCP("GET_PREFERENCIAS", String.valueOf(usuarioId)));
        return "OK".equals(resp.getTipo()) ? gson.fromJson(resp.getPayload(), PreferenciasUsuarioDTO.class) : null;
    }

    // =========================================================================
    // Like
    // =========================================================================

    @Override
    public Long guardarLike(LikeDTO like) {
        MensajeTCP resp = enviarYEsperar(new MensajeTCP("LIKE", gson.toJson(like)));
        return "OK".equals(resp.getTipo()) ? Long.parseLong(resp.getPayload()) : null;
    }

    // =========================================================================
    // Match
    // =========================================================================

    @Override
    public List<MatchDTO> obtenerMatchesPorUsuario(Long usuarioId) {
        MensajeTCP resp = enviarYEsperar(new MensajeTCP("GET_MATCHES", String.valueOf(usuarioId)));
        if ("OK".equals(resp.getTipo())) {
            Type t = new TypeToken<List<MatchDTO>>() {}.getType();
            return gson.fromJson(resp.getPayload(), t);
        }
        return new ArrayList<>();
    }

    // =========================================================================
    // Mensaje
    // =========================================================================

    @Override
    public Long guardarMensaje(MensajeDTO mensaje) {
        MensajeTCP resp = enviarYEsperar(new MensajeTCP("GUARDAR_MENSAJE", gson.toJson(mensaje)));
        return "OK".equals(resp.getTipo()) ? Long.parseLong(resp.getPayload()) : null;
    }

    @Override
    public List<MensajeDTO> obtenerMensajesPorMatch(Long matchId) {
        MensajeTCP resp = enviarYEsperar(new MensajeTCP("GET_MENSAJES", String.valueOf(matchId)));
        if ("OK".equals(resp.getTipo())) {
            Type t = new TypeToken<List<MensajeDTO>>() {}.getType();
            return gson.fromJson(resp.getPayload(), t);
        }
        return new ArrayList<>();
    }

    // =========================================================================
    // Suscripciones en tiempo real
    // =========================================================================

    @Override
    public void suscribirNuevoUsuario(Consumer<UsuarioDTO> listener) {
        listenersNuevoUsuario.add(listener);
    }

    @Override
    public void suscribirUsuarioActualizado(Consumer<UsuarioDTO> listener) {
        listenersActualizado.add(listener);
    }

    @Override
    public void suscribirNuevoMatch(Consumer<MatchDTO> listener) {
        listenersNuevoMatch.add(listener);
    }

    @Override
    public void suscribirNuevoMensaje(Consumer<MensajeDTO> listener) {
        listenersNuevoMensaje.add(listener);
    }

    @Override
    public void desconectar() {
        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("[Cliente] Error al desconectar: " + e.getMessage());
        }
    }
}

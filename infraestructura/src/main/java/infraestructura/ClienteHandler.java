package infraestructura;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import persistencia.AccesoDatos;
import persistencia.IAccesoDatos;

/**
 * Maneja la conexion de un cliente TCP individual en el servidor.
 * Se ejecuta en su propio hilo por cada cliente conectado.
 *
 * @author USUARIO
 */
public class ClienteHandler implements Runnable {

    private final Socket socket;
    private final ServidorRizzMeUp servidor;
    private final IAccesoDatos bd;
    private final Gson gson;
    private PrintWriter salida;

    public ClienteHandler(Socket socket, ServidorRizzMeUp servidor, IAccesoDatos bd, Gson gson) {
        this.socket = socket;
        this.servidor = servidor;
        this.bd = bd;
        this.gson = gson;
    }

    @Override
    public void run() {
        System.out.println("[Servidor] Cliente conectado: " + socket.getRemoteSocketAddress());
        try (BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), "UTF-8"))) {
            salida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            String linea;
            while ((linea = entrada.readLine()) != null) {
                procesarMensaje(linea);
            }
        } catch (IOException e) {
            System.out.println("[Servidor] Cliente desconectado: " + socket.getRemoteSocketAddress());
        } finally {
            servidor.removerCliente(this);
        }
    }

    private void procesarMensaje(String json) {
        try {
            MensajeTCP msg = gson.fromJson(json, MensajeTCP.class);
            switch (msg.getTipo()) {

                case "REGISTER" -> {
                    UsuarioDTO usuario = gson.fromJson(msg.getPayload(), UsuarioDTO.class);
                    Long id = bd.guardarUsuario(usuario);
                    usuario.setId(id);
                    responder("OK", gson.toJson(usuario));
                    servidor.broadcast(gson.toJson(new MensajeTCP("NUEVO_USUARIO", gson.toJson(usuario))), this);
                }

                case "UPDATE" -> {
                    UsuarioDTO usuario = gson.fromJson(msg.getPayload(), UsuarioDTO.class);
                    bd.actualizarUsuario(usuario);
                    responder("OK", "actualizado");
                    servidor.broadcast(gson.toJson(new MensajeTCP("USUARIO_ACTUALIZADO", gson.toJson(usuario))), this);
                }

                case "ELIMINAR" -> {
                    Long id = Long.parseLong(msg.getPayload());
                    bd.eliminarUsuario(id);
                    responder("OK", "eliminado");
                }

                case "GET_USUARIO" -> {
                    Long id = Long.parseLong(msg.getPayload());
                    UsuarioDTO u = bd.obtenerUsuarioPorId(id);
                    responder("OK", gson.toJson(u));
                }

                case "GET_USUARIO_POR_CORREO" -> {
                    String correo = msg.getPayload();
                    UsuarioDTO u = bd.obtenerUsuarioPorCorreo(correo);
                    responder("OK", gson.toJson(u));
                }

                case "GET_CANDIDATOS" -> {
                    Long id = Long.parseLong(msg.getPayload());
                    List<UsuarioDTO> candidatos = id <= 0
                            ? bd.obtenerCandidatos(-1L)
                            : bd.obtenerCandidatos(id);
                    responder("OK", gson.toJson(candidatos));
                }

                case "GET_PREFERENCIAS" -> {
                    Long id = Long.parseLong(msg.getPayload());
                    PreferenciasUsuarioDTO p = bd.obtenerPreferencias(id);
                    responder("OK", gson.toJson(p));
                }

                case "GUARDAR_PREFERENCIAS" -> {
                    PreferenciasUsuarioDTO p = gson.fromJson(msg.getPayload(), PreferenciasUsuarioDTO.class);
                    bd.guardarPreferencias(p);
                    responder("OK", "preferencias guardadas");
                }

                case "LIKE" -> {
                    LikeDTO like = gson.fromJson(msg.getPayload(), LikeDTO.class);
                    Long likeId = bd.guardarLike(like);
                    if (like.isEsLike() && bd.existeLikeMutuo(like.getUsuarioOrigenId(), like.getUsuarioDestinoId())) {
                        MatchDTO match = new MatchDTO(null,
                                like.getUsuarioOrigenId(),
                                like.getUsuarioDestinoId(),
                                LocalDateTime.now(), true);
                        Long matchId = bd.guardarMatch(match);
                        match.setId(matchId);
                        servidor.broadcast(gson.toJson(new MensajeTCP("NUEVO_MATCH", gson.toJson(match))), null);
                    }
                    responder("OK", String.valueOf(likeId));
                }

                case "GET_MATCHES" -> {
                    Long id = Long.parseLong(msg.getPayload());
                    List<MatchDTO> matches = bd.obtenerMatchesPorUsuario(id);
                    responder("OK", gson.toJson(matches));
                }

                case "GUARDAR_MENSAJE" -> {
                    MensajeDTO mensaje = gson.fromJson(msg.getPayload(), MensajeDTO.class);
                    Long mensajeId = bd.guardarMensaje(mensaje);
                    mensaje.setId(mensajeId);
                    responder("OK", String.valueOf(mensajeId));
                    servidor.broadcast(gson.toJson(new MensajeTCP("NUEVO_MENSAJE", gson.toJson(mensaje))), this);
                }

                case "GET_MENSAJES" -> {
                    Long matchId = Long.parseLong(msg.getPayload());
                    List<MensajeDTO> mensajes = bd.obtenerMensajesPorMatch(matchId);
                    responder("OK", gson.toJson(mensajes));
                }

                default -> responder("ERROR", "Tipo desconocido: " + msg.getTipo());
            }
        } catch (Exception e) {
            System.err.println("[Servidor] Error: " + e.getMessage());
            responder("ERROR", e.getMessage() != null ? e.getMessage() : "Error interno");
        }
    }

    private void responder(String tipo, String payload) {
        if (salida != null) {
            salida.println(gson.toJson(new MensajeTCP(tipo, payload)));
        }
    }

    /**
     * Envia un mensaje push a este cliente (usado para broadcasts del servidor).
     */
    public void enviar(String mensajeJson) {
        if (salida != null) {
            salida.println(mensajeJson);
        }
    }
}

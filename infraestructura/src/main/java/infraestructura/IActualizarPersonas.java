package infraestructura;

import dto.LikeDTO;
import dto.MatchDTO;
import dto.MensajeDTO;
import dto.PreferenciasUsuarioDTO;
import dto.UsuarioDTO;
import java.util.List;
import java.util.function.Consumer;

/**
 * Interfaz de infraestructura para comunicarse con el servidor TCP.
 * Todas las operaciones van por socket al ServidorRizzMeUp,
 * que a su vez persiste en MongoDB.
 *
 * Los metodos suscribir* permiten recibir eventos en tiempo real
 * (nuevo usuario, match, etc.) sin hacer polling.
 *
 * @author USUARIO
 */
public interface IActualizarPersonas {

    // =========================================================================
    // Usuario
    // =========================================================================

    /**
     * Registra un nuevo usuario. El servidor lo guarda en MongoDB
     * y hace broadcast a todos los clientes conectados.
     *
     * @param usuario datos del nuevo usuario
     * @return ID asignado por el servidor
     */
    Long registrarUsuario(UsuarioDTO usuario);

    /**
     * Actualiza el perfil de un usuario existente.
     *
     * @param usuario datos actualizados
     * @return true si fue exitoso
     */
    boolean actualizarUsuario(UsuarioDTO usuario);

    /**
     * Desactiva un usuario (soft delete).
     *
     * @param usuarioId ID del usuario
     * @return true si fue exitoso
     */
    boolean eliminarUsuario(Long usuarioId);

    /**
     * Obtiene el perfil de un usuario por su ID.
     *
     * @param usuarioId ID del usuario
     * @return el UsuarioDTO o null si no existe
     */
    UsuarioDTO obtenerPerfilPorId(Long usuarioId);

    /**
     * Obtiene el perfil de un usuario por su correo electronico.
     *
     * @param correo correo del usuario
     * @return el UsuarioDTO o null si no existe
     */
    UsuarioDTO obtenerPerfilPorCorreo(String correo);

    /**
     * Obtiene todos los perfiles activos (sin filtrar por usuario).
     */
    List<UsuarioDTO> obtenerTodosLosPerfiles();

    /**
     * Obtiene los candidatos para el usuario (excluye ya vistos).
     *
     * @param usuarioId ID del usuario logueado
     * @return lista de candidatos potenciales
     */
    List<UsuarioDTO> obtenerCandidatos(Long usuarioId);

    // =========================================================================
    // Preferencias
    // =========================================================================

    void guardarPreferencias(PreferenciasUsuarioDTO preferencias);

    PreferenciasUsuarioDTO obtenerPreferencias(Long usuarioId);

    // =========================================================================
    // Like / Swipe
    // =========================================================================

    /**
     * Registra un like o dislike. El servidor detecta automaticamente
     * si hay match mutuo y hace broadcast de NUEVO_MATCH.
     *
     * @param like datos del swipe
     * @return ID del like guardado
     */
    Long guardarLike(LikeDTO like);

    // =========================================================================
    // Match
    // =========================================================================

    List<MatchDTO> obtenerMatchesPorUsuario(Long usuarioId);

    // =========================================================================
    // Mensaje
    // =========================================================================

    Long guardarMensaje(MensajeDTO mensaje);

    List<MensajeDTO> obtenerMensajesPorMatch(Long matchId);

    // =========================================================================
    // Suscripciones a eventos en tiempo real (push del servidor)
    // =========================================================================

    /**
     * Registra un listener que se llama cuando alguien se registra.
     * Util para actualizar la lista de swipe sin recargar.
     */
    void suscribirNuevoUsuario(Consumer<UsuarioDTO> listener);

    /**
     * Registra un listener que se llama cuando alguien actualiza su perfil.
     */
    void suscribirUsuarioActualizado(Consumer<UsuarioDTO> listener);

    /**
     * Registra un listener que se llama cuando hay un nuevo match.
     */
    void suscribirNuevoMatch(Consumer<MatchDTO> listener);

    /**
     * Registra un listener que se llama cuando llega un nuevo mensaje.
     */
    void suscribirNuevoMensaje(Consumer<MensajeDTO> listener);

    /**
     * Cierra la conexion con el servidor.
     */
    void desconectar();
}

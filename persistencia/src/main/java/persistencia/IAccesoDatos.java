package persistencia;

import dto.LikeDTO;
import dto.MatchDTO;
import dto.MensajeDTO;
import dto.PreferenciasUsuarioDTO;
import dto.UsuarioDTO;
import java.util.List;

/**
 * Interfaz de acceso a datos (Persistencia).
 * Define las operaciones CRUD para todas las entidades del sistema.
 * Las implementaciones concretas deciden si se usa base de datos local,
 * archivo, o cualquier otra estrategia de persistencia.
 *
 * @author USUARIO
 */
public interface IAccesoDatos {

    // =========================================================================
    // Usuario
    // =========================================================================

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param usuario el UsuarioDTO a guardar
     * @return el ID generado para el usuario
     */
    Long guardarUsuario(UsuarioDTO usuario);

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param usuario el UsuarioDTO con los datos actualizados
     */
    void actualizarUsuario(UsuarioDTO usuario);

    /**
     * Obtiene un usuario por su ID.
     *
     * @param usuarioId el ID del usuario
     * @return el UsuarioDTO o null si no existe
     */
    UsuarioDTO obtenerUsuarioPorId(Long usuarioId);

    /**
     * Obtiene un usuario por su correo electronico.
     *
     * @param correo el correo del usuario
     * @return el UsuarioDTO o null si no existe
     */
    UsuarioDTO obtenerUsuarioPorCorreo(String correo);

    /**
     * Obtiene todos los usuarios activos que no sean el usuario actual.
     *
     * @param usuarioId el ID del usuario actual (para excluirlo)
     * @return lista de candidatos potenciales
     */
    List<UsuarioDTO> obtenerCandidatos(Long usuarioId);

    /**
     * Elimina (desactiva) un usuario por su ID.
     *
     * @param usuarioId el ID del usuario a eliminar
     */
    void eliminarUsuario(Long usuarioId);

    // =========================================================================
    // PreferenciasUsuario
    // =========================================================================

    /**
     * Guarda o actualiza las preferencias de un usuario.
     *
     * @param preferencias el PreferenciasUsuarioDTO a guardar
     */
    void guardarPreferencias(PreferenciasUsuarioDTO preferencias);

    /**
     * Obtiene las preferencias de un usuario.
     *
     * @param usuarioId el ID del usuario
     * @return el PreferenciasUsuarioDTO o null si no tiene preferencias guardadas
     */
    PreferenciasUsuarioDTO obtenerPreferencias(Long usuarioId);

    // =========================================================================
    // Like (Swipe)
    // =========================================================================

    /**
     * Registra un like (Rizz Me Up) o dislike (Saltar) en la base de datos.
     *
     * @param like el LikeDTO con la accion del usuario
     * @return el ID generado para el like
     */
    Long guardarLike(LikeDTO like);

    /**
     * Verifica si el usuarioDestino ya le dio like al usuarioOrigen.
     * Se usa para detectar un match mutuo.
     *
     * @param usuarioOrigenId  el ID del usuario que acaba de dar like
     * @param usuarioDestinoId el ID del usuario que recibio el like
     * @return true si ya existe un like mutuo (match)
     */
    boolean existeLikeMutuo(Long usuarioOrigenId, Long usuarioDestinoId);

    /**
     * Obtiene los IDs de usuarios a los que el usuario ya les hizo swipe.
     * Se usa para no mostrarlos de nuevo en el swipe.
     *
     * @param usuarioId el ID del usuario
     * @return lista de IDs ya vistos
     */
    List<Long> obtenerYaVistos(Long usuarioId);

    // =========================================================================
    // Match
    // =========================================================================

    /**
     * Guarda un nuevo match entre dos usuarios.
     *
     * @param match el MatchDTO a guardar
     * @return el ID generado para el match
     */
    Long guardarMatch(MatchDTO match);

    /**
     * Obtiene todos los matches activos de un usuario.
     *
     * @param usuarioId el ID del usuario
     * @return lista de MatchDTO activos
     */
    List<MatchDTO> obtenerMatchesPorUsuario(Long usuarioId);

    /**
     * Verifica si existe un match activo entre dos usuarios.
     *
     * @param usuario1Id el ID del primer usuario
     * @param usuario2Id el ID del segundo usuario
     * @return el MatchDTO si existe, null si no hay match
     */
    MatchDTO obtenerMatchEntre(Long usuario1Id, Long usuario2Id);

    // =========================================================================
    // Mensaje
    // =========================================================================

    /**
     * Guarda un nuevo mensaje en la base de datos.
     *
     * @param mensaje el MensajeDTO a guardar
     * @return el ID generado para el mensaje
     */
    Long guardarMensaje(MensajeDTO mensaje);

    /**
     * Obtiene todos los mensajes de un match ordenados por fecha.
     *
     * @param matchId el ID del match (conversacion)
     * @return lista de MensajeDTO ordenados cronologicamente
     */
    List<MensajeDTO> obtenerMensajesPorMatch(Long matchId);

    /**
     * Marca como leidos todos los mensajes no leidos recibidos por el usuario
     * dentro de un match especifico.
     *
     * @param matchId      el ID del match
     * @param usuarioId    el ID del usuario que esta leyendo
     */
    void marcarMensajesComoLeidos(Long matchId, Long usuarioId);
}

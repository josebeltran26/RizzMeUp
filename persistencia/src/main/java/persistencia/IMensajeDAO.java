package persistencia;

import dto.MensajeDTO;
import java.util.List;

/**
 * DAO para la entidad Mensaje (chat).
 * Operaciones contra la coleccion "mensajes" en MongoDB.
 *
 * @author USUARIO
 */
public interface IMensajeDAO {

    /**
     * Inserta un nuevo mensaje y retorna el ID asignado.
     */
    Long insertar(MensajeDTO mensaje);

    /**
     * Retorna todos los mensajes de un match ordenados cronologicamente.
     */
    List<MensajeDTO> buscarPorMatch(Long matchId);

    /**
     * Marca como leidos todos los mensajes no leidos recibidos por un usuario
     * dentro de un match.
     */
    void marcarComoLeidos(Long matchId, Long destinatarioId);

    /**
     * Cuenta los mensajes no leidos que tiene el usuario en todos sus matches.
     */
    int contarNoLeidos(Long usuarioId);
}

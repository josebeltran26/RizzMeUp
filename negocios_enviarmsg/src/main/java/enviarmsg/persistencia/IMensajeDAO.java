package enviarmsg.persistencia;

import enviarmsg.negocio.dto.MensajeDTO;
import java.util.List;

/**
 * Interfaz de persistencia para el subsistema de enviarmsg.
 */
public interface IMensajeDAO {

    /**
     * Guarda el mensaje en la base de datos y le asigna un ID.
     */
    Long guardar(MensajeDTO mensaje);

    /**
     * Obtiene todos los mensajes de un match en orden cronologico.
     */
    List<MensajeDTO> obtenerMensajesPorMatch(Long matchId);

    /**
     * Marca todos los mensajes recibidos en un match como leidos.
     */
    void marcarComoLeidos(Long matchId, Long destinatarioId);
}

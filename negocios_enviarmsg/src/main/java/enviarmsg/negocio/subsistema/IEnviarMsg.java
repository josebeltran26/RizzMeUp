package enviarmsg.negocio.subsistema;

import enviarmsg.negocio.dto.MensajeDTO;
import java.util.List;

/**
 * Interfaz publica del subsistema EnviarMsg.
 * Sirve de fachada para interactuar con las funcionalidades de mensajeria.
 */
public interface IEnviarMsg {

    /**
     * Envia un mensaje validando sus datos, persistiendo en DB y
     * haciendo push en tiempo real por sockets.
     * 
     * @param mensaje DTO con la informacion del mensaje
     * @return ID unico asignado
     */
    Long enviarMensaje(MensajeDTO mensaje);

    /**
     * Recupera el historial de la conversacion ordenada cronologicamente.
     */
    List<MensajeDTO> obtenerMensajesPorMatch(Long matchId);

    /**
     * Marca todos los mensajes de una conversacion recibidos por el usuario como leidos.
     */
    void marcarComoLeidos(Long matchId, Long destinatarioId);
}

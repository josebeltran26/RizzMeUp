package enviarmsg.negocio.control;

import enviarmsg.negocio.bo.MensajeBO;
import enviarmsg.negocio.dto.MensajeDTO;
import enviarmsg.persistencia.IMensajeDAO;
import enviarmsg.persistencia.MensajeDAO;
import enviarmsg.infraestructura.INotificarMsg;
import enviarmsg.infraestructura.NotificarMsg;
import java.util.List;

/**
 * Controlador de negocio del subsistema EnviarMsg.
 * Orquesta la validacion, persistencia y notificaciones TCP en tiempo real.
 */
public class ControlEnviarMsg {

    private final MensajeBO mensajeBO;
    private final IMensajeDAO mensajeDAO;
    private final INotificarMsg notificarMsg;

    public ControlEnviarMsg() {
        this.mensajeBO = new MensajeBO();
        this.mensajeDAO = new MensajeDAO();
        this.notificarMsg = new NotificarMsg();
    }

    public ControlEnviarMsg(IMensajeDAO mensajeDAO, INotificarMsg notificarMsg) {
        this.mensajeBO = new MensajeBO();
        this.mensajeDAO = mensajeDAO;
        this.notificarMsg = notificarMsg;
    }

    /**
     * Envia un mensaje validandolo contra las reglas de negocio,
     * persistiendo en base de datos y notificando por sockets.
     * 
     * @param mensaje datos del mensaje
     * @return ID asignado al mensaje
     */
    public Long enviarMensaje(MensajeDTO mensaje) {
        // 1. Validar reglas de negocio
        mensajeBO.validarMensaje(mensaje);
        
        // 2. Persistir localmente en MongoDB
        Long idPersistido = mensajeDAO.guardar(mensaje);
        
        // 3. Notificar via sockets TCP en tiempo real al destinatario
        notificarMsg.notificar(mensaje);
        
        return idPersistido;
    }

    /**
     * Obtiene el historial de mensajes de un match de manera cronologica.
     */
    public List<MensajeDTO> obtenerMensajesPorMatch(Long matchId) {
        if (matchId == null || matchId <= 0) {
            throw new IllegalArgumentException("El ID del match debe ser valido.");
        }
        return mensajeDAO.obtenerMensajesPorMatch(matchId);
    }

    /**
     * Marca como leidos los mensajes recibidos.
     */
    public void marcarComoLeidos(Long matchId, Long destinatarioId) {
        if (matchId == null || destinatarioId == null) {
            throw new IllegalArgumentException("Los IDs de match y destinatario deben ser validos.");
        }
        mensajeDAO.marcarComoLeidos(matchId, destinatarioId);
    }
}

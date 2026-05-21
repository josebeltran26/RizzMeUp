package enviarmsg.negocio.subsistema;

import enviarmsg.negocio.control.ControlEnviarMsg;
import enviarmsg.negocio.dto.MensajeDTO;
import java.util.List;

/**
 * Fachada del subsistema EnviarMsg.
 * Implementa IEnviarMsg y delega las operaciones al controlador del negocio.
 */
public class EnviarMsgFacade implements IEnviarMsg {

    private final ControlEnviarMsg control;

    public EnviarMsgFacade() {
        this.control = new ControlEnviarMsg();
    }

    public EnviarMsgFacade(ControlEnviarMsg control) {
        this.control = control;
    }

    @Override
    public Long enviarMensaje(MensajeDTO mensaje) {
        return control.enviarMensaje(mensaje);
    }

    @Override
    public List<MensajeDTO> obtenerMensajesPorMatch(Long matchId) {
        return control.obtenerMensajesPorMatch(matchId);
    }

    @Override
    public void marcarComoLeidos(Long matchId, Long destinatarioId) {
        control.marcarComoLeidos(matchId, destinatarioId);
    }
}

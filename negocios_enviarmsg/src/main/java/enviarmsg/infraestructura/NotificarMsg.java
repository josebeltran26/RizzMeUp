package enviarmsg.infraestructura;

import enviarmsg.negocio.dto.MensajeDTO;
import infraestructura.ActualizarPersonas;
import infraestructura.IActualizarPersonas;

/**
 * Implementacion de INotificarMsg que utiliza la conexion de sockets TCP
 * del modulo global de infraestructura para notificar y transmitir los mensajes.
 */
public class NotificarMsg implements INotificarMsg {

    private final IActualizarPersonas infraTCP;

    /**
     * Constructor por defecto que inicializa una conexion de sockets TCP.
     */
    public NotificarMsg() {
        this.infraTCP = new ActualizarPersonas();
    }

    /**
     * Constructor que permite inyectar una instancia existente de IActualizarPersonas.
     * 
     * @param infraTCP la conexion TCP existente
     */
    public NotificarMsg(IActualizarPersonas infraTCP) {
        this.infraTCP = infraTCP;
    }

    @Override
    public Long notificar(MensajeDTO mensaje) {
        dto.MensajeDTO globalMsg = new dto.MensajeDTO(
                mensaje.getId(),
                mensaje.getMatchId(),
                mensaje.getRemitenteId(),
                mensaje.getDestinatarioId(),
                mensaje.getContenido(),
                mensaje.getFechaEnvio(),
                mensaje.isLeido()
        );
        
        Long id = infraTCP.guardarMensaje(globalMsg);
        mensaje.setId(id);
        return id;
    }
}

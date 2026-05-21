package enviarmsg.infraestructura;

import enviarmsg.negocio.dto.MensajeDTO;

/**
 * Interfaz para notificaciones de red e infraestructura de mensajeria.
 */
public interface INotificarMsg {

    /**
     * Envia/notifica un mensaje a traves de los sockets TCP para su transmision
     * en tiempo real al destinatario y almacenamiento centralizado.
     * 
     * @param mensaje el mensaje a transmitir
     * @return el ID generado por el servidor
     */
    Long notificar(MensajeDTO mensaje);
}

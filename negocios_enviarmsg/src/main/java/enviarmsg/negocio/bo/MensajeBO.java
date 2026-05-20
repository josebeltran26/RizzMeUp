package enviarmsg.negocio.bo;

import enviarmsg.negocio.dto.MensajeDTO;

/**
 * Business Object (BO) que valida las reglas de negocio de los mensajes.
 */
public class MensajeBO {

    /**
     * Valida un mensaje de acuerdo con las reglas del negocio de RizzMeUp.
     * 
     * @param mensaje el DTO del mensaje a validar
     * @throws IllegalArgumentException si alguna regla es violada
     */
    public void validarMensaje(MensajeDTO mensaje) {
        if (mensaje == null) {
            throw new IllegalArgumentException("El mensaje no puede ser nulo.");
        }
        if (mensaje.getMatchId() == null || mensaje.getMatchId() <= 0) {
            throw new IllegalArgumentException("El ID del match asociado debe ser valido.");
        }
        if (mensaje.getRemitenteId() == null || mensaje.getRemitenteId() <= 0) {
            throw new IllegalArgumentException("El ID del remitente debe ser valido.");
        }
        if (mensaje.getDestinatarioId() == null || mensaje.getDestinatarioId() <= 0) {
            throw new IllegalArgumentException("El ID del destinatario debe ser valido.");
        }
        
        String contenido = mensaje.getContenido();
        if (contenido == null || contenido.trim().isEmpty()) {
            throw new IllegalArgumentException("El contenido del mensaje no puede estar vacio.");
        }
        if (contenido.length() > 1000) {
            throw new IllegalArgumentException("El mensaje supera el limite de 1000 caracteres.");
        }
    }
}

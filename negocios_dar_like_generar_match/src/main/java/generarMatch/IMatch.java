package generarMatch;

import dto.MatchDTO;

/**
 * Interfaz de negocio para la gestión de Matches.
 * De acuerdo con el diseño de arquitectura y secuencia de StarUML.
 * 
 * @author Erik
 */
public interface IMatch {

    /**
     * Realiza e inserta el match en la base de datos si existe reciprocidad de likes.
     * 
     * @param idUsuario1 ID del primer usuario.
     * @param idUsuario2 ID del segundo usuario.
     * @return true si el match es exitoso, false en caso contrario.
     * @throws Exception en caso de error.
     */
    boolean realizarMatch(Long idUsuario1, Long idUsuario2) throws Exception;
    
    /**
     * Intenta generar el match validando reciprocidad y lanzando excepción si falla.
     */
    boolean generarMatch(Long idUsuario1, Long idUsuario2) throws Exception;
    
    /**
     * Guarda y registra un nuevo Match en el sistema.
     */
    Long crearMatch(MatchDTO match);

    /**
     * Valida si existe reciprocidad de likes mutuos (ambos se han dado like)
     * antes de consolidar el match.
     */
    boolean validarReciprocidad(Long usuario1Id, Long usuario2Id);
    
    /**
     * Envía una notificación de correo automático a ambos participantes
     * avisándoles del nuevo Match.
     */
    void enviarNotificacionMatch(MatchDTO match);
}

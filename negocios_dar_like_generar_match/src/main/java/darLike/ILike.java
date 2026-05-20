package darLike;

import dto.LikeDTO;

/**
 * Interfaz de negocio para la gestión de Likes (swipes).
 * De acuerdo con el diseño de arquitectura y secuencia de StarUML.
 * 
 * @author Erik
 */
public interface ILike {
    
    /**
     * Registra un like del emisor al receptor y retorna si es un like mutuo (match).
     * 
     * @param idEmisor ID del usuario que da el like.
     * @param idReceptor ID del usuario que recibe el like.
     * @return true si es recíproco, false en caso contrario.
     * @throws Exception en caso de error.
     */
    boolean darLike(Long idEmisor, Long idReceptor) throws Exception;

    /**
     * Registra un swipe (like o dislike) en el sistema.
     * 
     * @param like el DTO con la información del swipe.
     * @return el ID generado para el registro.
     */
    Long registrarLike(LikeDTO like);

    /**
     * Verifica si existe un like recíproco de la otra persona hacia el usuario origen.
     */
    boolean verificarReciprocidad(Long usuarioOrigenId, Long usuarioDestinoId);
}

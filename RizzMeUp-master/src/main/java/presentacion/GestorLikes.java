package presentacion;

import darLike.ILike;
import darLike.FLikes;
import dto.LikeDTO;

/**
 * Presenter / Controlador para gestionar las acciones y lógica de Likes (Swipes)
 * desde la interfaz de usuario, interactuando con la capa de negocio.
 * De acuerdo con el diseño de diseño de arquitectura del sistema.
 * 
 * @author Erik
 */
public class GestorLikes {
    
    private final ILike negocioLike;

    public GestorLikes() {
        this.negocioLike = new FLikes();
    }

    public GestorLikes(ILike negocioLike) {
        this.negocioLike = negocioLike;
    }

    /**
     * Llama al subsistema de negocios para registrar el Like.
     * Propaga cualquier excepción de acuerdo con el diagrama alterno de dar like.
     */
    public boolean darLike(Long idEmisor, Long idReceptor) throws Exception {
        return negocioLike.darLike(idEmisor, idReceptor);
    }

    /**
     * Registra un swipe (like o dislike) en el sistema.
     * 
     * @param like el DTO con la información del swipe.
     * @return el ID generado.
     */
    public Long registrarLike(LikeDTO like) {
        if (like == null) {
            System.err.println("[GestorLikes] El LikeDTO es nulo.");
            return null;
        }
        return negocioLike.registrarLike(like);
    }

    /**
     * Verifica si existe un like mutuo (reciprocidad) entre los usuarios.
     * 
     * @param usuarioOrigenId ID del usuario actual.
     * @param usuarioDestinoId ID del candidato.
     * @return true si ya existe interés recíproco.
     */
    public boolean verificarReciprocidad(Long usuarioOrigenId, Long usuarioDestinoId) {
        if (usuarioOrigenId == null || usuarioDestinoId == null) {
            System.err.println("[GestorLikes] Los IDs de usuario no pueden ser nulos para verificar reciprocidad.");
            return false;
        }
        // Llamamos al negocio para verificar si el usuarioDestino ya le dio like al usuarioOrigen
        return negocioLike.verificarReciprocidad(usuarioOrigenId, usuarioDestinoId);
    }
}

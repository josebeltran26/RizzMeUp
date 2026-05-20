package darLike;

import dto.LikeDTO;
import infraestructura.ActualizarPersonas;
import infraestructura.IActualizarPersonas;
import persistencia.ILikeDAO;
import persistencia.LikeDAO;
import java.time.LocalDateTime;

/**
 * Clase control GestorLikes (dentro de negocios/darLike) encargada de interactuar
 * con la persistencia y la infraestructura para registrar likes y verificar reciprocidad.
 * 
 * @author Erik
 */
public class GestorLikes {
    
    private final IActualizarPersonas infra;
    private final ILikeDAO likeDAO;

    public GestorLikes() {
        this.infra = new ActualizarPersonas();
        this.likeDAO = new LikeDAO();
    }

    public GestorLikes(IActualizarPersonas infra, ILikeDAO likeDAO) {
        this.infra = infra;
        this.likeDAO = likeDAO;
    }

    /**
     * Lógica principal para dar un Like.
     * 
     * @param idEmisor ID del usuario emisor.
     * @param idReceptor ID del usuario receptor.
     * @return true si el receptor ya le dio like al emisor previamente, false en caso contrario.
     * @throws Exception si ocurre algún error.
     */
    public boolean darLike(Long idEmisor, Long idReceptor) throws Exception {
        if (idEmisor == null || idReceptor == null) {
            throw new Exception("Los IDs de emisor y receptor no pueden ser nulos.");
        }
        
        // Creamos y guardamos el DTO de Like
        LikeDTO like = new LikeDTO(null, idEmisor, idReceptor, true, LocalDateTime.now());
        registrarLike(like);
        
        // Verificamos si existe reciprocidad
        return verificarReciprocidad(idEmisor, idReceptor);
    }

    /**
     * Registra el like en el sistema (vía TCP o BD).
     */
    public Long registrarLike(LikeDTO like) {
        try {
            return infra.guardarLike(like);
        } catch (Exception e) {
            System.err.println("[GestorLikes Negocio] Error al registrar like por TCP, usando BD: " + e.getMessage());
            return likeDAO.insertar(like);
        }
    }

    /**
     * Verifica si el receptor ya le dio like al emisor.
     */
    public boolean verificarReciprocidad(Long usuarioOrigenId, Long usuarioDestinoId) {
        try {
            return likeDAO.existeLikeMutuo(usuarioOrigenId, usuarioDestinoId);
        } catch (Exception e) {
            System.err.println("[GestorLikes Negocio] Error al verificar reciprocidad: " + e.getMessage());
            return false;
        }
    }
}

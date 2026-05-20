package darLike;

import dto.LikeDTO;

/**
 * Facade FLikes que implementa la interfaz ILike.
 * Delega la lógica de negocio a la clase control GestorLikes.
 * Según el diseño de arquitectura y secuencia de StarUML.
 * 
 * @author Erik
 */
public class FLikes implements ILike {
    
    private final GestorLikes gestorLikes;

    public FLikes() {
        this.gestorLikes = new GestorLikes();
    }

    public FLikes(GestorLikes gestorLikes) {
        this.gestorLikes = gestorLikes;
    }

    @Override
    public boolean darLike(Long idEmisor, Long idReceptor) throws Exception {
        return gestorLikes.darLike(idEmisor, idReceptor);
    }

    @Override
    public Long registrarLike(LikeDTO like) {
        return gestorLikes.registrarLike(like);
    }

    @Override
    public boolean verificarReciprocidad(Long usuarioOrigenId, Long usuarioDestinoId) {
        return gestorLikes.verificarReciprocidad(usuarioOrigenId, usuarioDestinoId);
    }
}

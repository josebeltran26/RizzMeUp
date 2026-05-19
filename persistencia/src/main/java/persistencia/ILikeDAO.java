package persistencia;

import dto.LikeDTO;
import java.util.List;

/**
 * DAO para la entidad Like (swipe).
 * Operaciones contra la coleccion "likes" en MongoDB.
 *
 * @author USUARIO
 */
public interface ILikeDAO {

    /**
     * Registra un swipe (Rizz Me Up o Saltar) y retorna el ID asignado.
     */
    Long insertar(LikeDTO like);

    /**
     * Verifica si usuarioB ya le dio like a usuarioA (para detectar match mutuo).
     */
    boolean existeLikeMutuo(Long usuarioOrigenId, Long usuarioDestinoId);

    /**
     * Retorna los IDs de usuarios a los que el usuario ya les hizo swipe
     * (para no mostrarlos de nuevo).
     */
    List<Long> obtenerIdsYaVistos(Long usuarioId);

    /**
     * Verifica si el usuario ya hizo swipe a un candidato especifico.
     */
    boolean yaHizoSwipe(Long usuarioOrigenId, Long usuarioDestinoId);
}

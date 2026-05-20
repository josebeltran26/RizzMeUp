package persistencia;

import dto.MatchDTO;
import java.util.List;

/**
 * DAO para la entidad Match.
 * Operaciones contra la coleccion "matches" en MongoDB.
 *
 * @author USUARIO
 */
public interface IMatchDAO {

    /**
     * Inserta un nuevo match y retorna el ID asignado.
     */
    Long insertar(MatchDTO match);

    /**
     * Retorna todos los matches activos de un usuario.
     */
    List<MatchDTO> buscarPorUsuario(Long usuarioId);

    /**
     * Busca el match entre dos usuarios especificos (si existe).
     * @return MatchDTO o null si no hay match entre ellos
     */
    MatchDTO buscarEntre(Long usuario1Id, Long usuario2Id);

    /**
     * Desactiva un match (cuando los usuarios se desemparejan).
     */
    void desactivar(Long matchId);
}

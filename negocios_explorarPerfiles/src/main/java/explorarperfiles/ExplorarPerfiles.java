package explorarperfiles;

import dto.UsuarioDTO;
import dto.LikeDTO;
import java.util.ArrayList;
import java.util.List;
import persistencia.AccesoDatos;
import persistencia.IAccesoDatos;

/**
 * Implementacion real de IExplorarPerfiles que consulta MongoDB.
 *
 * @author Roger Jr
 */
public class ExplorarPerfiles implements IExplorarPerfiles {

    private final IAccesoDatos bd;

    public ExplorarPerfiles() {
        this.bd = new AccesoDatos();
    }

    @Override
    public List<UsuarioDTO> obtenerCandidatos(Long usuarioId) {
        try {
            List<UsuarioDTO> todos = bd.obtenerCandidatos(usuarioId);
            if (todos == null) return new ArrayList<>();
            // Excluir los que ya recibieron swipe
            List<Long> yaVistos = bd.obtenerYaVistos(usuarioId);
            List<UsuarioDTO> candidatos = new ArrayList<>();
            for (UsuarioDTO u : todos) {
                if (yaVistos == null || !yaVistos.contains(u.getId())) {
                    candidatos.add(u);
                }
            }
            return candidatos;
        } catch (Exception e) {
            System.err.println("[ExplorarPerfiles] Error al obtener candidatos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<UsuarioDTO> obtenerCandidatosFiltrados(Long usuarioId) {
        // Reutiliza obtenerCandidatos con el filtro de ya vistos
        return obtenerCandidatos(usuarioId);
    }

    @Override
    public void registrarLike(LikeDTO like) {
        try {
            bd.guardarLike(like);
        } catch (Exception e) {
            // Ignorar duplicados (ya le dio swipe)
            System.err.println("[ExplorarPerfiles] Like duplicado o error: " + e.getMessage());
        }
    }

    @Override
    public UsuarioDTO obtenerPerfilPorId(Long usuarioId) {
        try {
            return bd.obtenerUsuarioPorId(usuarioId);
        } catch (Exception e) {
            System.err.println("[ExplorarPerfiles] Error al obtener perfil: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void resetearSwipes(Long usuarioId) {
        try {
            bd.limpiarSwipes(usuarioId);
            System.out.println("[ExplorarPerfiles] Swipes reseteados para usuario " + usuarioId);
        } catch (Exception e) {
            System.err.println("[ExplorarPerfiles] Error al resetear swipes: " + e.getMessage());
        }
    }
}

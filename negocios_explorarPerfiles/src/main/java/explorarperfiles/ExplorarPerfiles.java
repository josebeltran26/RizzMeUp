package explorarperfiles;

import dto.UsuarioDTO;
import dto.LikeDTO;
import java.util.ArrayList;
import java.util.List;
import persistencia.ILikeDAO;
import persistencia.IUsuarioDAO;
import persistencia.LikeDAO;
import persistencia.UsuarioDAO;

/**
 * Implementación de negocio para explorar perfiles.
 * Conectada directamente a la base de datos de MongoDB mediante los DAOs reales.
 * 
 * @author Roger Jr / Erik
 */
public class ExplorarPerfiles implements IExplorarPerfiles {
    
    private final IUsuarioDAO usuarioDAO;
    private final ILikeDAO likeDAO;

    public ExplorarPerfiles() {
        this.usuarioDAO = new UsuarioDAO();
        this.likeDAO = new LikeDAO();
    }

    public ExplorarPerfiles(IUsuarioDAO usuarioDAO, ILikeDAO likeDAO) {
        this.usuarioDAO = usuarioDAO;
        this.likeDAO = likeDAO;
    }

    @Override
    public List<UsuarioDTO> obtenerCandidatos(Long usuarioId) {
        try {
            if (usuarioId == null) {
                return new ArrayList<>();
            }
            // 1. Obtener la lista de IDs de usuarios a los que ya les hizo swipe (like / skip)
            List<Long> yaVistos = likeDAO.obtenerIdsYaVistos(usuarioId);
            if (yaVistos == null) {
                yaVistos = new ArrayList<>();
            }
            // 2. Traer candidatos de MongoDB excluyendo al usuario actual y los ya vistos
            List<UsuarioDTO> candidatos = usuarioDAO.obtenerCandidatos(usuarioId, yaVistos);
            if (candidatos == null || candidatos.isEmpty()) {
                return new ArrayList<>();
            }

            // 3. Separar candidatos que ya dieron like (Rizz Me Up) de los que no
            List<Long> meDieronLike = likeDAO.obtenerIdsQueMeDieronLike(usuarioId);
            if (meDieronLike == null) {
                meDieronLike = new ArrayList<>();
            }

            List<UsuarioDTO> conRizz = new ArrayList<>();
            List<UsuarioDTO> sinRizz = new ArrayList<>();

            for (UsuarioDTO c : candidatos) {
                if (meDieronLike.contains(c.getId())) {
                    conRizz.add(c);
                } else {
                    sinRizz.add(c);
                }
            }

            // Unir ambas listas poniendo primero los que ya dieron like
            List<UsuarioDTO> resultadoOrdenado = new ArrayList<>();
            resultadoOrdenado.addAll(conRizz);
            resultadoOrdenado.addAll(sinRizz);

            return resultadoOrdenado;
        } catch (Exception e) {
            System.err.println("[ExplorarPerfiles] Error al obtener candidatos de la base de datos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<UsuarioDTO> obtenerCandidatosFiltrados(Long usuarioId) {
        // Retorna candidatos reales que no han sido vistos aún
        return obtenerCandidatos(usuarioId);
    }

    @Override
    public void registrarLike(LikeDTO like) {
        try {
            if (like != null) {
                likeDAO.insertar(like);
            }
        } catch (Exception e) {
            System.err.println("[ExplorarPerfiles] Error al registrar el like en la BD: " + e.getMessage());
        }
    }

    @Override
    public UsuarioDTO obtenerPerfilPorId(Long usuarioId) {
        try {
            if (usuarioId == null) {
                return null;
            }
            return usuarioDAO.buscarPorId(usuarioId);
        } catch (Exception e) {
            System.err.println("[ExplorarPerfiles] Error al obtener perfil de la BD por ID: " + e.getMessage());
            return null;
        }
    }
}

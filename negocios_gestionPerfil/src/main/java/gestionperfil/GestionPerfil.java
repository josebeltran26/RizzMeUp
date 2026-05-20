package gestionperfil;

import dto.UsuarioDTO;
import infraestructura.ActualizarPersonas;
import infraestructura.IActualizarPersonas;

/**
 * Implementacion real de IGestionPerfil conectandose a la infraestructura TCP.
 *
 * @author Roger Jr
 */
public class GestionPerfil implements IGestionPerfil {

    private final IActualizarPersonas infra;

    public GestionPerfil() {
        this.infra = new ActualizarPersonas();
    }

    @Override
    public UsuarioDTO obtenerPerfil(Long usuarioId) {
        try {
            return infra.obtenerPerfilPorId(usuarioId);
        } catch (Exception e) {
            System.err.println("[GestionPerfil] Error al obtener perfil: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void actualizarPerfil(UsuarioDTO perfilActualizado) {
        try {
            boolean exito = infra.actualizarUsuario(perfilActualizado);
            if (exito) {
                System.out.println("[GestionPerfil] Perfil de " + perfilActualizado.getNombre() + " guardado con exito.");
            } else {
                System.err.println("[GestionPerfil] Falló al actualizar perfil en el servidor.");
            }
        } catch (Exception e) {
            System.err.println("[GestionPerfil] Error al actualizar perfil: " + e.getMessage());
        }
    }
}

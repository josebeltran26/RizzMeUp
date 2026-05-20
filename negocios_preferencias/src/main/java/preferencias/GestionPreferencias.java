package preferencias;

import dto.PreferenciasUsuarioDTO;
import persistencia.IPreferenciasDAO;
import persistencia.PreferenciasDAO;

/**
 * Lógica de negocio para administrar las preferencias de búsqueda.
 * Conectada directamente a la base de datos de MongoDB.
 * 
 * @author Roger Jr / Erik
 */
public class GestionPreferencias implements IPreferencias {

    private final IPreferenciasDAO preferenciasDAO;

    public GestionPreferencias() {
        this.preferenciasDAO = new PreferenciasDAO();
    }

    public GestionPreferencias(IPreferenciasDAO preferenciasDAO) {
        this.preferenciasDAO = preferenciasDAO;
    }

    @Override
    public PreferenciasUsuarioDTO obtenerPreferencias(Long usuarioId) {
        try {
            if (usuarioId == null) {
                return null;
            }
            PreferenciasUsuarioDTO prefs = preferenciasDAO.buscarPorUsuario(usuarioId);
            // Si el usuario no tiene preferencias guardadas aún, se le crean unas básicas por defecto
            if (prefs == null) {
                prefs = new PreferenciasUsuarioDTO();
                prefs.setUsuarioId(usuarioId);
                prefs.setEdadMinima(18);
                prefs.setEdadMaxima(25);
                prefs.setGeneroBuscado("Femenino");
                prefs.setCiudadPreferida("Ciudad Obregón");
                prefs.setInteresEstudios(true);
            }
            return prefs;
        } catch (Exception e) {
            System.err.println("[GestionPreferencias] Error al obtener preferencias de la BD: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void guardarPreferencias(PreferenciasUsuarioDTO preferencias) {
        try {
            if (preferencias != null) {
                preferenciasDAO.guardarOActualizar(preferencias);
                System.out.println("[GestionPreferencias] Preferencias guardadas exitosamente en MongoDB.");
            }
        } catch (Exception e) {
            System.err.println("[GestionPreferencias] Error al guardar preferencias en la BD: " + e.getMessage());
        }
    }
}

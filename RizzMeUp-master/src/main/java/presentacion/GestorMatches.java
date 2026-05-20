package presentacion;

import dto.MatchDTO;
import dto.UsuarioDTO;
import generarMatch.IMatch;
import generarMatch.FMatches;
import javax.swing.SwingUtilities;

/**
 * Presenter / Controlador para la gestión y flujo de Matches en la interfaz.
 * De acuerdo con el diseño de arquitectura del sistema.
 * 
 * @author Erik
 */
public class GestorMatches {
    
    private final IMatch negocioMatch;

    public GestorMatches() {
        this.negocioMatch = new FMatches();
    }

    public GestorMatches(IMatch negocioMatch) {
        this.negocioMatch = negocioMatch;
    }

    /**
     * Intenta generar un Match llamando al subsistema de negocios.
     * Propaga una excepción en caso de no existir reciprocidad (flujo alterno).
     */
    public boolean generarMatch(Long usuario1Id, Long usuario2Id) throws Exception {
        return negocioMatch.generarMatch(usuario1Id, usuario2Id);
    }

    /**
     * Valida si existe coincidencia mutua de likes antes de consolidar el match.
     * 
     * @param usuarioOrigenId ID del primer usuario.
     * @param usuarioDestinoId ID del segundo usuario.
     * @return true si es recíproco.
     */
    public boolean validarReciprocidad(Long usuarioOrigenId, Long usuarioDestinoId) {
        if (usuarioOrigenId == null || usuarioDestinoId == null) {
            return false;
        }
        return negocioMatch.validarReciprocidad(usuarioOrigenId, usuarioDestinoId);
    }

    /**
     * Registra un nuevo Match en el sistema.
     * 
     * @param match el MatchDTO.
     * @return el ID asignado.
     */
    public Long crearMatch(MatchDTO match) {
        if (match == null) {
            return null;
        }
        return negocioMatch.crearMatch(match);
    }

    /**
     * Despliega la pantalla de celebración del Match ("¡RIZZ!") interrumpiendo el flujo.
     * 
     * @param match el MatchDTO generado.
     * @param usuarioLogueado datos del usuario principal.
     * @param candidato datos del usuario con quien hizo match.
     */
    public void desplegarPantallaMatch(MatchDTO match, UsuarioDTO usuarioLogueado, UsuarioDTO candidato) {
        SwingUtilities.invokeLater(() -> {
            FrmMatch frame = new FrmMatch(match, usuarioLogueado, candidato);
            frame.setVisible(true);
        });
    }
}

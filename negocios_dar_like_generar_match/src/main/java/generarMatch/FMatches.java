package generarMatch;

import dto.MatchDTO;

/**
 * Facade FMatches que implementa la interfaz IMatch.
 * Delega la lógica de negocio a la clase control GestorMatch.
 * Según el diseño de arquitectura y secuencia de StarUML.
 * 
 * @author Erik
 */
public class FMatches implements IMatch {
    
    private final GestorMatch gestorMatch;

    public FMatches() {
        this.gestorMatch = new GestorMatch();
    }

    public FMatches(GestorMatch gestorMatch) {
        this.gestorMatch = gestorMatch;
    }

    @Override
    public boolean realizarMatch(Long idUsuario1, Long idUsuario2) throws Exception {
        return gestorMatch.realizarMatch(idUsuario1, idUsuario2);
    }

    @Override
    public boolean generarMatch(Long idUsuario1, Long idUsuario2) throws Exception {
        return gestorMatch.generarMatch(idUsuario1, idUsuario2);
    }

    @Override
    public Long crearMatch(MatchDTO match) {
        return gestorMatch.crearMatch(match);
    }

    @Override
    public boolean validarReciprocidad(Long usuario1Id, Long usuario2Id) {
        return gestorMatch.validarReciprocidad(usuario1Id, usuario2Id);
    }

    @Override
    public void enviarNotificacionMatch(MatchDTO match) {
        gestorMatch.enviarNotificacionMatch(match);
    }
}

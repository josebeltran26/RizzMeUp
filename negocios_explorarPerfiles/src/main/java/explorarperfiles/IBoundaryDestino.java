package explorarperfiles;

import dto.MatchDTO;
import dto.UsuarioDTO;

/**
 * Boundary del flujo de Explorar Perfiles (Destino).
 *
 * Interfaz que la capa de presentacion (panel Swing de swipe) implementa
 * para recibir eventos del ControlDestino.
 *
 * El panel implementa esta interfaz y se registra en el controlador.
 * El controlador llama a estos metodos cuando hay novedades.
 *
 * @author USUARIO
 */
public interface IBoundaryDestino {

    /**
     * Hay un nuevo candidato para mostrar en la tarjeta de swipe.
     * @param candidato datos del perfil a mostrar
     */
    void mostrarCandidato(UsuarioDTO candidato);

    /**
     * No quedan mas candidatos disponibles para este usuario.
     * La UI debe mostrar la pantalla de "vuelve mas tarde".
     */
    void sinMasCandidatos();

    /**
     * Se produjo un match con otro usuario.
     * La UI debe mostrar la pantalla de felicitacion.
     *
     * @param match   datos del match
     * @param otro    perfil del usuario con quien hizo match
     */
    void notificarMatch(MatchDTO match, UsuarioDTO otro);

    /**
     * Un nuevo usuario se registro en el sistema (push en tiempo real).
     * Permite actualizar la lista de candidatos sin recargar manualmente.
     * @param nuevoUsuario perfil del recien registrado
     */
    void nuevoCandidatoDisponible(UsuarioDTO nuevoUsuario);

    /**
     * Ocurrio un error al cargar candidatos o registrar un swipe.
     * @param mensaje descripcion del error
     */
    void mostrarError(String mensaje);
}

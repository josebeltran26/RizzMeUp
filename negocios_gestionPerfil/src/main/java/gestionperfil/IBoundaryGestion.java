package gestionperfil;

import dto.UsuarioDTO;

/**
 * Boundary de Gestion de Perfil.
 *
 * Interfaz que la capa de presentacion (panel Swing) debe implementar
 * para recibir los resultados del ControlGestion.
 *
 * El patron ECB (Entity-Control-Boundary) desacopla el control de la UI:
 * el controlador llama a estos metodos cuando una operacion termina,
 * sin saber nada del componente visual concreto.
 *
 * @author USUARIO
 */
public interface IBoundaryGestion {

    /**
     * El perfil fue obtenido correctamente desde la infraestructura.
     * @param perfil datos del usuario a mostrar en la UI
     */
    void mostrarPerfil(UsuarioDTO perfil);

    /**
     * El registro fue exitoso.
     * @param idAsignado ID generado por el servidor
     */
    void registroExitoso(Long idAsignado);

    /**
     * La actualizacion del perfil fue exitosa.
     */
    void actualizacionExitosa();

    /**
     * La cuenta fue eliminada exitosamente.
     */
    void eliminacionExitosa();

    /**
     * Ocurrio un error en alguna operacion de gestion.
     * @param mensaje descripcion del error para mostrar al usuario
     */
    void mostrarError(String mensaje);
}

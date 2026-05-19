package persistencia;

import dto.PreferenciasUsuarioDTO;

/**
 * DAO para la entidad PreferenciasUsuario.
 * Operaciones contra la coleccion "preferencias" en MongoDB.
 *
 * @author USUARIO
 */
public interface IPreferenciasDAO {

    /**
     * Guarda o actualiza las preferencias de busqueda del usuario.
     * Si ya existen preferencias para ese usuario, las reemplaza.
     */
    void guardarOActualizar(PreferenciasUsuarioDTO preferencias);

    /**
     * Retorna las preferencias de un usuario.
     * @return PreferenciasUsuarioDTO o null si no ha configurado preferencias
     */
    PreferenciasUsuarioDTO buscarPorUsuario(Long usuarioId);
}

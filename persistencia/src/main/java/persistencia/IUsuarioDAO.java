package persistencia;

import dto.UsuarioDTO;
import java.util.List;

/**
 * Interfaz para el Data Access Object de Usuario.
 * Define las operaciones permitidas sobre la coleccion de usuarios en MongoDB.
 *
 * @author USUARIO
 */
public interface IUsuarioDAO {

    /**
     * Inserta un nuevo usuario en la base de datos.
     * Genera un ID autoincremental de forma atomica.
     *
     * @param u el usuario a insertar
     * @return el ID asignado
     */
    Long insertar(UsuarioDTO u);

    /**
     * Actualiza el documento completo de un usuario existente.
     *
     * @param u el usuario con los datos actualizados (requiere ID)
     */
    void actualizar(UsuarioDTO u);

    /**
     * Busca un usuario por su ID unico.
     *
     * @param id el ID del usuario
     * @return el UsuarioDTO, o null si no se encuentra
     */
    UsuarioDTO buscarPorId(Long id);

    /**
     * Busca un usuario por su correo electronico.
     *
     * @param correo el correo del usuario
     * @return el UsuarioDTO, o null si no se encuentra
     */
    UsuarioDTO buscarPorCorreo(String correo);

    /**
     * Obtiene una lista de candidatos potenciales para el flujo de swipe.
     * Filtra al propio usuario y a los usuarios que ya ha visto (yaVistos).
     *
     * @param usuarioId ID del usuario que solicita
     * @param yaVistos lista de IDs a excluir (ya likeados/saltados)
     * @return lista de usuarios candidatos
     */
    List<UsuarioDTO> obtenerCandidatos(Long usuarioId, List<Long> yaVistos);

    /**
     * Desactiva lógicamente un usuario en lugar de borrarlo fisicamente.
     *
     * @param id ID del usuario a desactivar
     */
    void desactivar(Long id);

    /**
     * Verifica de forma rapida si un correo ya esta registrado.
     *
     * @param correo correo a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existeCorreo(String correo);

}

package gestionperfil;

import dto.UsuarioDTO;

/**
 * Contrato del Business Object de Usuario.
 * Define las reglas de negocio y validaciones del dominio.
 *
 * @author USUARIO
 */
public interface IUsuarioBO {

    /**
     * Valida todos los campos obligatorios del usuario.
     * @return null si es valido, o un mensaje de error descriptivo
     */
    String validar();

    /**
     * Valida solo el correo electronico.
     */
    boolean correoValido();

    /**
     * Valida la contrasena (minimo 6 caracteres).
     */
    boolean contrasenaValida();

    /**
     * Valida que la edad sea mayor o igual a 18.
     */
    boolean edadValida();

    /**
     * Retorna el DTO subyacente con los datos del usuario.
     */
    UsuarioDTO getDTO();

    /**
     * Autentica la contrasena del usuario (comparacion directa).
     * @param contrasena la contrasena a verificar
     * @return true si coincide
     */
    boolean autenticar(String contrasena);
}

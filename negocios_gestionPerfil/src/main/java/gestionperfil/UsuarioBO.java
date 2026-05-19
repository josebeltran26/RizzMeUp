package gestionperfil;

import dto.UsuarioDTO;
import java.time.LocalDateTime;

/**
 * Business Object de Usuario.
 * Encapsula un UsuarioDTO y aplica las reglas de negocio del dominio.
 *
 * @author USUARIO
 */
public class UsuarioBO implements IUsuarioBO {

    private final UsuarioDTO dto;

    /**
     * Crea un BO a partir de un DTO existente.
     */
    public UsuarioBO(UsuarioDTO dto) {
        this.dto = dto;
    }

    /**
     * Factory method: crea un nuevo usuario con la fecha de registro actual.
     */
    public static UsuarioBO nuevo(String nombre, String correo, String contrasena,
            int edad, String genero, String ciudad) {
        UsuarioDTO u = new UsuarioDTO();
        u.setNombre(nombre);
        u.setCorreo(correo);
        u.setContrasena(contrasena);
        u.setEdad(edad);
        u.setGenero(genero);
        u.setCiudad(ciudad);
        u.setFechaRegistro(LocalDateTime.now());
        u.setActivo(true);
        return new UsuarioBO(u);
    }

    // =========================================================================
    // IUsuarioBO
    // =========================================================================

    @Override
    public String validar() {
        if (dto.getNombre() == null || dto.getNombre().isBlank())
            return "El nombre es obligatorio.";
        if (!correoValido())
            return "El correo no tiene un formato valido.";
        if (!contrasenaValida())
            return "La contrasena debe tener al menos 6 caracteres.";
        if (!edadValida())
            return "Debes ser mayor de 18 años para registrarte.";
        if (dto.getGenero() == null || dto.getGenero().isBlank())
            return "El genero es obligatorio.";
        return null; // sin errores
    }

    @Override
    public boolean correoValido() {
        if (dto.getCorreo() == null) return false;
        // Formato basico: debe tener @ y al menos un punto despues
        return dto.getCorreo().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }

    @Override
    public boolean contrasenaValida() {
        return dto.getContrasena() != null && dto.getContrasena().length() >= 6;
    }

    @Override
    public boolean edadValida() {
        return dto.getEdad() >= 18;
    }

    @Override
    public UsuarioDTO getDTO() {
        return dto;
    }

    @Override
    public boolean autenticar(String contrasena) {
        return dto.getContrasena() != null && dto.getContrasena().equals(contrasena);
    }
}

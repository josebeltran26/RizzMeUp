package presentacion;

import dto.UsuarioDTO;

/**
 * Singleton que guarda el usuario logueado en la sesion activa.
 * Se inicializa al hacer login o registro y se limpia al cerrar sesion.
 *
 * @author Erik
 */
public class SesionUsuario {

    private static SesionUsuario instancia;

    private Long usuarioId;
    private String nombre;
    private String correo;

    private SesionUsuario() {}

    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    public void iniciarSesion(UsuarioDTO usuario) {
        this.usuarioId = usuario.getId();
        this.nombre    = usuario.getNombre();
        this.correo    = usuario.getCorreo();
        System.out.println("[Sesion] Usuario logueado: " + nombre + " (ID=" + usuarioId + ")");
    }

    public void cerrarSesion() {
        this.usuarioId = null;
        this.nombre    = null;
        this.correo    = null;
    }

    public Long getUsuarioId() { return usuarioId; }
    public String getNombre()  { return nombre; }
    public String getCorreo()  { return correo; }

    public boolean estaLogueado() {
        return usuarioId != null;
    }
}

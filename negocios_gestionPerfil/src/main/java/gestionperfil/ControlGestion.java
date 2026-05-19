package gestionperfil;

import dto.LikeDTO;
import dto.MatchDTO;
import dto.UsuarioDTO;
import explorarperfiles.IBoundaryDestino;
import explorarperfiles.LikeBO;
import infraestructura.IActualizarPersonas;
import java.util.ArrayList;
import java.util.List;

/**
 * Control principal de RizzMeUp (patron ECB — un solo Control).
 *
 * Centraliza TODA la logica de orquestacion de la aplicacion:
 *   - Gestion de perfiles (registro, login, actualizar, eliminar)
 *   - Flujo de swipe (candidatos, like, saltar, matches)
 *   - Eventos en tiempo real (nuevos usuarios, nuevos matches)
 *
 * Se conecta con los 2 subsistemas del sistema:
 *   [1] Infraestructura (IActualizarPersonas) — cliente TCP → servidor
 *   [2] Persistencia (MongoDB) — a traves del servidor via TCP
 *
 * Las boundaries desacoplan el control de la UI:
 *   - IBoundaryGestion : panel de perfil / registro
 *   - IBoundaryDestino : panel de swipe / matches
 *
 * Uso desde la presentacion:
 * <pre>
 *   IActualizarPersonas infra = new ActualizarPersonas();
 *   ControlGestion ctrl = new ControlGestion(infra);
 *   ctrl.setBoundaryGestion(panelPerfil);   // implementa IBoundaryGestion
 *   ctrl.setBoundaryDestino(panelSwipe);    // implementa IBoundaryDestino
 *   ctrl.registrarUsuario(dto);
 *   ctrl.iniciarSwipe(usuarioId);
 * </pre>
 *
 * @author USUARIO
 */
public class ControlGestion {

    // -------------------------------------------------------------------------
    // Subsistemas
    // -------------------------------------------------------------------------

    /** Subsistema 1: infraestructura TCP (cliente ↔ servidor ↔ MongoDB) */
    private final IActualizarPersonas infraestructura;

    // -------------------------------------------------------------------------
    // Boundaries (una por cada panel de la UI)
    // -------------------------------------------------------------------------

    private IBoundaryGestion boundaryGestion;
    private IBoundaryDestino boundaryDestino;

    // -------------------------------------------------------------------------
    // Estado interno del flujo de swipe
    // -------------------------------------------------------------------------

    private Long usuarioActualId;
    private List<UsuarioDTO> candidatos = new ArrayList<>();
    private int indiceActual = 0;

    // =========================================================================
    // Constructor
    // =========================================================================

    /**
     * @param infraestructura subsistema de infraestructura (TCP client)
     */
    public ControlGestion(IActualizarPersonas infraestructura) {
        this.infraestructura = infraestructura;

        // Subsistema 2 (push en tiempo real): suscribirse a eventos del servidor
        infraestructura.suscribirNuevoUsuario(this::onNuevoUsuario);
        infraestructura.suscribirNuevoMatch(this::onNuevoMatch);
    }

    // =========================================================================
    // Configuracion de boundaries
    // =========================================================================

    public void setBoundaryGestion(IBoundaryGestion boundary) {
        this.boundaryGestion = boundary;
    }

    public void setBoundaryDestino(IBoundaryDestino boundary) {
        this.boundaryDestino = boundary;
    }

    // =========================================================================
    // GESTION DE PERFILES
    // =========================================================================

    /**
     * Registra un nuevo usuario. Valida con UsuarioBO antes de enviar.
     */
    public void registrarUsuario(UsuarioDTO usuario) {
        UsuarioBO bo = new UsuarioBO(usuario);
        String error = bo.validar();
        if (error != null) { notificarErrorGestion(error); return; }

        try {
            Long id = infraestructura.registrarUsuario(usuario);
            usuario.setId(id);
            this.usuarioActualId = id;
            if (boundaryGestion != null) boundaryGestion.registroExitoso(id);
        } catch (Exception e) {
            notificarErrorGestion("Error al registrar: " + e.getMessage());
        }
    }

    /**
     * Inicia sesion verificando correo y contrasena.
     * Si es exitoso, notifica a la boundary y guarda el ID localmente.
     *
     * @param correo correo del usuario
     * @param contrasena contrasena sin encriptar (por ahora)
     */
    public void iniciarSesion(String correo, String contrasena) {
        try {
            UsuarioDTO perfil = infraestructura.obtenerPerfilPorCorreo(correo);
            if (perfil == null) {
                notificarErrorGestion("No se encontro un usuario con ese correo.");
                return;
            }
            
            UsuarioBO bo = new UsuarioBO(perfil);
            if (!bo.autenticar(contrasena)) {
                notificarErrorGestion("Contrasena incorrecta.");
                return;
            }
            
            this.usuarioActualId = perfil.getId();
            // Notificamos que el registro/login fue exitoso pasando el ID
            if (boundaryGestion != null) boundaryGestion.registroExitoso(perfil.getId());
            
        } catch (Exception e) {
            notificarErrorGestion("Error al iniciar sesion: " + e.getMessage());
        }
    }

    /**
     * Obtiene y muestra el perfil de un usuario.
     */
    public void obtenerPerfil(Long usuarioId) {
        try {
            UsuarioDTO perfil = infraestructura.obtenerPerfilPorId(usuarioId);
            if (perfil == null) {
                notificarErrorGestion("No se encontro el perfil con ID: " + usuarioId);
                return;
            }
            if (boundaryGestion != null) boundaryGestion.mostrarPerfil(perfil);
        } catch (Exception e) {
            notificarErrorGestion("Error al obtener perfil: " + e.getMessage());
        }
    }

    /**
     * Actualiza el perfil. Valida con UsuarioBO antes de enviar.
     */
    public void actualizarPerfil(UsuarioDTO usuario) {
        UsuarioBO bo = new UsuarioBO(usuario);
        String error = bo.validar();
        if (error != null) { notificarErrorGestion(error); return; }

        try {
            boolean ok = infraestructura.actualizarUsuario(usuario);
            if (ok) {
                if (boundaryGestion != null) boundaryGestion.actualizacionExitosa();
            } else {
                notificarErrorGestion("El servidor no pudo actualizar el perfil.");
            }
        } catch (Exception e) {
            notificarErrorGestion("Error al actualizar: " + e.getMessage());
        }
    }

    /**
     * Elimina (desactiva) la cuenta de un usuario.
     */
    public void eliminarPerfil(Long usuarioId) {
        try {
            boolean ok = infraestructura.eliminarUsuario(usuarioId);
            if (ok) {
                if (boundaryGestion != null) boundaryGestion.eliminacionExitosa();
            } else {
                notificarErrorGestion("No se pudo eliminar el perfil.");
            }
        } catch (Exception e) {
            notificarErrorGestion("Error al eliminar: " + e.getMessage());
        }
    }

    // =========================================================================
    // FLUJO DE SWIPE (EXPLORAR PERFILES)
    // =========================================================================

    /**
     * Inicia el flujo de swipe para el usuario dado.
     * Carga candidatos y muestra el primero.
     *
     * @param usuarioId ID del usuario logueado
     */
    public void iniciarSwipe(Long usuarioId) {
        this.usuarioActualId = usuarioId;
        this.indiceActual = 0;
        cargarCandidatos();
    }

    /**
     * Recarga la lista de candidatos desde el servidor.
     */
    public void cargarCandidatos() {
        try {
            candidatos = infraestructura.obtenerCandidatos(usuarioActualId);
            indiceActual = 0;
            mostrarSiguienteCandidato();
        } catch (Exception e) {
            notificarErrorDestino("Error al cargar candidatos: " + e.getMessage());
        }
    }

    /**
     * El usuario dio "Rizz Me Up" al candidato actual.
     */
    public void darLike() {
        if (!hayCandidatoActual()) return;
        UsuarioDTO candidato = candidatos.get(indiceActual);

        LikeBO bo = LikeBO.rizzMeUp(usuarioActualId, candidato.getId());
        String error = bo.validar();
        if (error != null) { notificarErrorDestino(error); return; }

        try {
            infraestructura.guardarLike(bo.getDTO());
        } catch (Exception e) {
            notificarErrorDestino("Error al registrar like: " + e.getMessage());
        }

        indiceActual++;
        mostrarSiguienteCandidato();
    }

    /**
     * El usuario presiono "Saltar" en el candidato actual.
     */
    public void saltar() {
        if (!hayCandidatoActual()) return;
        UsuarioDTO candidato = candidatos.get(indiceActual);

        LikeBO bo = LikeBO.saltar(usuarioActualId, candidato.getId());
        try {
            infraestructura.guardarLike(bo.getDTO());
        } catch (Exception e) {
            notificarErrorDestino("Error al registrar saltar: " + e.getMessage());
        }

        indiceActual++;
        mostrarSiguienteCandidato();
    }

    /**
     * Obtiene los matches activos del usuario actual.
     */
    public List<MatchDTO> obtenerMisMatches() {
        try {
            return infraestructura.obtenerMatchesPorUsuario(usuarioActualId);
        } catch (Exception e) {
            notificarErrorDestino("Error al obtener matches: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // =========================================================================
    // EVENTOS EN TIEMPO REAL (push del servidor)
    // =========================================================================

    private void onNuevoUsuario(UsuarioDTO nuevoUsuario) {
        if (usuarioActualId != null && !nuevoUsuario.getId().equals(usuarioActualId)) {
            candidatos.add(nuevoUsuario);
            if (boundaryDestino != null) boundaryDestino.nuevoCandidatoDisponible(nuevoUsuario);
        }
    }

    private void onNuevoMatch(MatchDTO match) {
        if (usuarioActualId == null) return;
        boolean involucrado = usuarioActualId.equals(match.getUsuario1Id())
                || usuarioActualId.equals(match.getUsuario2Id());
        if (!involucrado) return;

        Long otroId = usuarioActualId.equals(match.getUsuario1Id())
                ? match.getUsuario2Id() : match.getUsuario1Id();
        try {
            UsuarioDTO otro = infraestructura.obtenerPerfilPorId(otroId);
            if (boundaryDestino != null) boundaryDestino.notificarMatch(match, otro);
        } catch (Exception e) {
            notificarErrorDestino("Error al obtener perfil del match: " + e.getMessage());
        }
    }

    // =========================================================================
    // Privado
    // =========================================================================

    private boolean hayCandidatoActual() {
        return candidatos != null && indiceActual < candidatos.size();
    }

    private void mostrarSiguienteCandidato() {
        if (hayCandidatoActual()) {
            if (boundaryDestino != null) boundaryDestino.mostrarCandidato(candidatos.get(indiceActual));
        } else {
            if (boundaryDestino != null) boundaryDestino.sinMasCandidatos();
        }
    }

    private void notificarErrorGestion(String msg) {
        System.err.println("[ControlGestion] " + msg);
        if (boundaryGestion != null) boundaryGestion.mostrarError(msg);
    }

    private void notificarErrorDestino(String msg) {
        System.err.println("[ControlGestion-Swipe] " + msg);
        if (boundaryDestino != null) boundaryDestino.mostrarError(msg);
    }
}

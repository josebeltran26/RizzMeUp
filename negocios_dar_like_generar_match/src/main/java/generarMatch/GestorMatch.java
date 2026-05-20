package generarMatch;

import dto.MatchDTO;
import dto.UsuarioDTO;
import persistencia.ILikeDAO;
import persistencia.IMatchDAO;
import persistencia.IUsuarioDAO;
import persistencia.LikeDAO;
import persistencia.MatchDAO;
import persistencia.UsuarioDAO;
import java.time.LocalDateTime;

/**
 * Clase control GestorMatch (dentro de negocios/generarMatch) encargada de
 * validar la reciprocidad de likes, registrar matches en la base de datos
 * y simular el envío de notificaciones de correo.
 * 
 * @author Erik
 */
public class GestorMatch {
    
    private final IMatchDAO matchDAO;
    private final ILikeDAO likeDAO;
    private final IUsuarioDAO usuarioDAO;

    public GestorMatch() {
        this.matchDAO = new MatchDAO();
        this.likeDAO = new LikeDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public GestorMatch(IMatchDAO matchDAO, ILikeDAO likeDAO, IUsuarioDAO usuarioDAO) {
        this.matchDAO = matchDAO;
        this.likeDAO = likeDAO;
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Lógica principal para realizar un Match.
     * 
     * @param idUsuario1 ID del primer usuario.
     * @param idUsuario2 ID del segundo usuario.
     * @return true si la operación se completó con éxito, false si no es mutuo.
     * @throws Exception si hay algún problema.
     */
    public boolean realizarMatch(Long idUsuario1, Long idUsuario2) throws Exception {
        return generarMatch(idUsuario1, idUsuario2);
    }

    public boolean generarMatch(Long idUsuario1, Long idUsuario2) throws Exception {
        if (idUsuario1 == null || idUsuario2 == null) {
            throw new Exception("Los IDs de los usuarios para realizar el match no pueden ser nulos.");
        }
        
        // 1. Validar reciprocidad de likes
        boolean esReciproco = validarReciprocidad(idUsuario1, idUsuario2);
        if (!esReciproco) {
            throw new Exception("No existe reciprocidad de likes entre los usuarios.");
        }

        // 2. Crear y persistir el Match
        MatchDTO match = new MatchDTO(null, idUsuario1, idUsuario2, LocalDateTime.now(), true);
        crearMatch(match);
        
        return true;
    }

    /**
     * Valida si existe interés mutuo (ambos usuarios se dieron like).
     */
    public boolean validarReciprocidad(Long usuario1Id, Long usuario2Id) {
        try {
            boolean like1a2 = likeDAO.existeLikeMutuo(usuario2Id, usuario1Id);
            boolean like2a1 = likeDAO.existeLikeMutuo(usuario1Id, usuario2Id);
            return like1a2 && like2a1;
        } catch (Exception e) {
            System.err.println("[GestorMatch Negocio] Error al validar reciprocidad: " + e.getMessage());
            return false;
        }
    }

    /**
     * Registra el Match en la base de datos y simula la notificación por correo.
     */
    public Long crearMatch(MatchDTO match) {
        try {
            Long matchId = matchDAO.insertar(match);
            match.setId(matchId);
            
            // Simular notificaciones por correo
            enviarNotificacionMatch(match);
            
            return matchId;
        } catch (Exception e) {
            System.err.println("[GestorMatch Negocio] Error al guardar match en BD: " + e.getMessage());
            return null;
        }
    }

    /**
     * Simula el envío de una notificación automática de match por correo.
     */
    public void enviarNotificacionMatch(MatchDTO match) {
        try {
            UsuarioDTO u1 = usuarioDAO.buscarPorId(match.getUsuario1Id());
            UsuarioDTO u2 = usuarioDAO.buscarPorId(match.getUsuario2Id());
            
            String correo1 = u1 != null ? u1.getCorreo() : "usuario1@rizzmeup.com";
            String correo2 = u2 != null ? u2.getCorreo() : "usuario2@rizzmeup.com";
            String nombre1 = u1 != null ? u1.getNombre() : "Usuario 1";
            String nombre2 = u2 != null ? u2.getNombre() : "Usuario 2";
            
            System.out.println("======================================================================");
            System.out.println("                     [SISTEMA DE CORREO AUTOMÁTICO]                   ");
            System.out.println("======================================================================");
            System.out.println("Para: " + correo1);
            System.out.println("Asunto: ¡Hiciste match con " + nombre2 + "! 🔥");
            System.out.println("Hola " + nombre1 + ", ¡felicidades! Tú y " + nombre2 + " se han dado Rizz mutuamente.");
            System.out.println("Ya pueden empezar a chatear en RizzMeUp.");
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Para: " + correo2);
            System.out.println("Asunto: ¡Hiciste match con " + nombre1 + "! 🔥");
            System.out.println("Hola " + nombre2 + ", ¡felicidades! Tú y " + nombre1 + " se han dado Rizz mutuamente.");
            System.out.println("Ya pueden empezar a chatear en RizzMeUp.");
            System.out.println("======================================================================");
        } catch (Exception e) {
            System.err.println("[GestorMatch Negocio] Error al enviar correos: " + e.getMessage());
        }
    }

    /**
     * Firma de método requerida por el diagrama de clases de StarUML.
     * En una implementación desacoplada, este método sirve como punto de enlace
     * para que la presentación o capas externas disparen la UI.
     */
    public void desplegarPantallaMatch() {
        System.out.println("[GestorMatch Negocio] Solicitud de despliegue de pantalla de match.");
    }
}

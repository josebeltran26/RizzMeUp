package enviarmsg.presentacion;

import dto.UsuarioDTO;
import enviarmsg.negocio.dto.MatchDTO;
import enviarmsg.negocio.dto.MensajeDTO;
import enviarmsg.negocio.subsistema.EnviarMsgFacade;
import enviarmsg.negocio.subsistema.IEnviarMsg;
import infraestructura.ActualizarPersonas;
import infraestructura.IActualizarPersonas;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Mediador de presentacion (Controlador de UI) para la mensajeria.
 * Conecta los paneles Swing con el subsistema EnviarMsg y gestiona los sockets TCP en tiempo real.
 */
public class ControlChat {

    private final IEnviarMsg subsistemaNegocio;
    private final IActualizarPersonas infraTCP;
    private final Long usuarioLogueadoId;
    
    private PnlMensajeria vistaPrincipal;
    private MatchDTO matchSeleccionado;

    public ControlChat(Long usuarioLogueadoId) {
        this.usuarioLogueadoId = usuarioLogueadoId;
        this.subsistemaNegocio = new EnviarMsgFacade();
        this.infraTCP = new ActualizarPersonas();
        
        // Registrar suscripcion para recibir mensajes en tiempo real
        this.infraTCP.suscribirNuevoMensaje(new Consumer<dto.MensajeDTO>() {
            @Override
            public void accept(dto.MensajeDTO m) {
                recibirMensajeTiempoReal(m);
            }
        });
    }

    public void setVista(PnlMensajeria vista) {
        this.vistaPrincipal = vista;
    }

    public Long getUsuarioLogueadoId() {
        return usuarioLogueadoId;
    }

    /**
     * Carga todos los matches del usuario actual.
     */
    public List<MatchDTO> cargarMatches() {
        List<dto.MatchDTO> listGlobal = infraTCP.obtenerMatchesPorUsuario(usuarioLogueadoId);
        List<MatchDTO> listLocal = new ArrayList<>();
        
        for (dto.MatchDTO m : listGlobal) {
            listLocal.add(new MatchDTO(
                    m.getId(),
                    m.getUsuario1Id(),
                    m.getUsuario2Id(),
                    m.getFechaMatch(),
                    m.isActivo()
            ));
        }
        return listLocal;
    }

    /**
     * Obtiene el perfil de un usuario dado su ID.
     */
    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        return infraTCP.obtenerPerfilPorId(id);
    }

    /**
     * Obtiene todos los mensajes de un match.
     */
    public List<MensajeDTO> obtenerMensajesPorMatch(Long matchId) {
        return subsistemaNegocio.obtenerMensajesPorMatch(matchId);
    }

    /**
     * Selecciona un match y carga su conversacion.
     */
    public void seleccionarMatch(MatchDTO match) {
        this.matchSeleccionado = match;
        if (match != null) {
            // Cargar mensajes cronologicamente
            List<MensajeDTO> mensajes = subsistemaNegocio.obtenerMensajesPorMatch(match.getId());
            
            // Marcar mensajes como leidos
            subsistemaNegocio.marcarComoLeidos(match.getId(), usuarioLogueadoId);
            
            // Actualizar panel de chat derecho
            if (vistaPrincipal != null) {
                vistaPrincipal.mostrarConversacion(match, mensajes);
            }
        }
    }

    /**
     * Envia un nuevo mensaje de texto.
     */
    public void enviarMensaje(String texto) {
        if (matchSeleccionado == null || texto == null || texto.trim().isEmpty()) {
            return;
        }
        
        Long destinatarioId = matchSeleccionado.getUsuario1Id().equals(usuarioLogueadoId) 
                ? matchSeleccionado.getUsuario2Id() 
                : matchSeleccionado.getUsuario1Id();

        MensajeDTO nuevoMsg = new MensajeDTO();
        nuevoMsg.setMatchId(matchSeleccionado.getId());
        nuevoMsg.setRemitenteId(usuarioLogueadoId);
        nuevoMsg.setDestinatarioId(destinatarioId);
        nuevoMsg.setContenido(texto);
        nuevoMsg.setFechaEnvio(LocalDateTime.now());
        nuevoMsg.setLeido(false);

        // Enviar al subsistema (valida, guarda y transmite)
        subsistemaNegocio.enviarMensaje(nuevoMsg);

        // Actualizar UI del chat agregando el mensaje enviado
        if (vistaPrincipal != null) {
            vistaPrincipal.agregarMensajePantalla(nuevoMsg);
        }
    }

    /**
     * Procesa los mensajes recibidos en tiempo real por el socket TCP.
     */
    private void recibirMensajeTiempoReal(dto.MensajeDTO m) {
        // Mapear a DTO local
        MensajeDTO localMsg = new MensajeDTO(
                m.getId(),
                m.getMatchId(),
                m.getRemitenteId(),
                m.getDestinatarioId(),
                m.getContenido(),
                m.getFechaEnvio(),
                m.isLeido()
        );

        // Ignorar los ecos de nuestros propios mensajes (ya se agregaron a la UI localmente al enviar)
        if (localMsg.getRemitenteId().equals(usuarioLogueadoId)) {
            return;
        }

        java.awt.EventQueue.invokeLater(() -> {
            // Si el mensaje es para la conversacion activa actual, la actualizamos
            if (matchSeleccionado != null && matchSeleccionado.getId().equals(localMsg.getMatchId())) {
                // Marcar como leido
                subsistemaNegocio.marcarComoLeidos(matchSeleccionado.getId(), usuarioLogueadoId);
                
                if (vistaPrincipal != null) {
                    vistaPrincipal.agregarMensajePantalla(localMsg);
                }
            } else {
                // Si no esta activa, simplemente refrescamos la lista de chats para actualizar el ultimo mensaje
                if (vistaPrincipal != null) {
                    vistaPrincipal.refrescarListaChats();
                }
            }
        });
    }
}

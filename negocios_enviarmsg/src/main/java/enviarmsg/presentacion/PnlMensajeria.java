package enviarmsg.presentacion;

import enviarmsg.negocio.dto.MatchDTO;
import enviarmsg.negocio.dto.MensajeDTO;
import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * Panel contenedor principal de Mensajeria (Split Pane).
 * Une la JPListaChats (izquierda) y el JPChat (derecha).
 * Si no hay matches, despliega una vista premium de "Sin matches" con estilo RizzMeUp.
 */
public class PnlMensajeria extends JPanel {

    private final ControlChat controlador;
    private JPListaChats pnlLista;
    private JPChat pnlChat;
    private JSplitPane splitPane;
    private JPanel pnlVacio; // Vista cuando no hay matches o no se ha seleccionado nada

    public PnlMensajeria() {
        // Por defecto el usuario logueadoId es 1L
        this(1L);
    }

    public PnlMensajeria(Long usuarioLogueadoId) {
        this.controlador = new ControlChat(usuarioLogueadoId);
        this.controlador.setVista(this);

        initComponents();
        evaluarEstadoMatches();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));
        setPreferredSize(new Dimension(860, 770));

        pnlLista = new JPListaChats(controlador);
        pnlChat = new JPChat(controlador);

        // Vista Vacía Premium
        pnlVacio = crearPanelVacio();

        // SplitPane para dividir la lista (izquierda) y el chat (derecha)
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLista, pnlVacio);
        splitPane.setDividerLocation(300);
        splitPane.setDividerSize(5);
        splitPane.setBorder(null);
        splitPane.setEnabled(false); // Desactivar movimiento del divisor para mantener layout limpio

        add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Muestra la conversacion activa en el panel derecho.
     */
    public void mostrarConversacion(MatchDTO match, List<MensajeDTO> mensajes) {
        splitPane.setRightComponent(pnlChat);
        pnlChat.cargarMatch(match, mensajes);
        splitPane.revalidate();
        splitPane.repaint();
    }

    /**
     * Agrega un mensaje a la pantalla actual de chat.
     */
    public void agregarMensajePantalla(MensajeDTO mensaje) {
        pnlChat.agregarMensaje(mensaje);
        
        // Refrescar tambien la barra de matches para actualizar el ultimo mensaje
        pnlLista.recargarLista();
    }

    /**
     * Refresca la barra lateral izquierda.
     */
    public void refrescarListaChats() {
        pnlLista.recargarLista();
        evaluarEstadoMatches();
    }

    /**
     * Evalua si hay matches. Si no los hay, muestra el panel vacio de error.
     */
    private void evaluarEstadoMatches() {
        if (pnlLista.getListModel().isEmpty()) {
            splitPane.setRightComponent(pnlVacio);
            // Personalizar texto vacio para indicar que no hay matches aun
            lblVacioTitulo.setText("¡Aún no tienes matches!");
            lblVacioSub.setText("Ve a SWIPE y conecta con personas increibles primero.");
        } else {
            // Si hay matches pero no hay nada seleccionado, mostrar instrucciones de seleccion
            if (splitPane.getRightComponent() == pnlVacio) {
                lblVacioTitulo.setText("¡Es hora de chatear!");
                lblVacioSub.setText("Selecciona una conversacion de la izquierda para comenzar.");
            }
        }
        splitPane.revalidate();
        splitPane.repaint();
    }

    // Componentes del panel vacio
    private JLabel lblVacioTitulo;
    private JLabel lblVacioSub;

    /**
     * Crea un panel con estetica premium que se muestra en ausencia de chats activos
     * o seleccionados.
     */
    private JPanel crearPanelVacio() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 245, 249)); // Fondo rosa ultra suave
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // Icono gigante decorativo (Nube con Corazon en texto)
        JLabel lblIcono = new JLabel("💬💖");
        lblIcono.setFont(new Font("Segoe UI", Font.PLAIN, 72));
        panel.add(lblIcono, gbc);

        // Titulo
        gbc.gridy = 1;
        lblVacioTitulo = new JLabel("¡Es hora de chatear!");
        lblVacioTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblVacioTitulo.setForeground(new Color(102, 0, 51));
        panel.add(lblVacioTitulo, gbc);

        // Subtitulo
        gbc.gridy = 2;
        lblVacioSub = new JLabel("Selecciona una conversacion de la izquierda para comenzar.");
        lblVacioSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblVacioSub.setForeground(new Color(110, 80, 95));
        panel.add(lblVacioSub, gbc);

        return panel;
    }
}

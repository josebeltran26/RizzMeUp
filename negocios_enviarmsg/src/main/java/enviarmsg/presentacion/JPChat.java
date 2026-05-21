package enviarmsg.presentacion;

import dto.UsuarioDTO;
import enviarmsg.negocio.dto.MatchDTO;
import enviarmsg.negocio.dto.MensajeDTO;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Panel de chat interactivo premium.
 * Renderiza la cabecera del match, burbujas redondeadas de chat (izquierda/derecha),
 * y la barra de entrada de texto inferior.
 */
public class JPChat extends JPanel {

    private final ControlChat controlador;
    private MatchDTO matchActual;
    private UsuarioDTO partner;

    private JPanel pnlChatArea;
    private JScrollPane scrollChat;
    private JTextField txtMensaje;
    private JButton btnEnviar;
    private JLabel lblHeaderNombre;
    private JLabel lblHeaderAvatar;

    public JPChat(ControlChat controlador) {
        this.controlador = controlador;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));

        // 1. Cabecera (Header)
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(255, 213, 233));
        pnlHeader.setLayout(new BorderLayout());
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(238, 197, 217)));
        pnlHeader.setPreferredSize(new Dimension(860, 65));

        JPanel pnlHeaderLeft = new JPanel();
        pnlHeaderLeft.setOpaque(false);
        pnlHeaderLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));

        lblHeaderAvatar = new JLabel();
        lblHeaderAvatar.setPreferredSize(new Dimension(45, 45));
        pnlHeaderLeft.add(lblHeaderAvatar);

        lblHeaderNombre = new JLabel("Selecciona un match para chatear");
        lblHeaderNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHeaderNombre.setForeground(new Color(102, 0, 51));
        pnlHeaderLeft.add(lblHeaderNombre);

        pnlHeader.add(pnlHeaderLeft, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        // 2. Area de Mensajes (Scrollable)
        pnlChatArea = new JPanel();
        pnlChatArea.setBackground(new Color(255, 245, 249)); // Fondo rosa ultra suave
        pnlChatArea.setLayout(new BoxLayout(pnlChatArea, BoxLayout.Y_AXIS));

        scrollChat = new JScrollPane(pnlChatArea);
        scrollChat.setBorder(null);
        scrollChat.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollChat, BorderLayout.CENTER);

        // 3. Barra inferior de Entrada
        JPanel pnlInputBar = new JPanel();
        pnlInputBar.setBackground(new Color(255, 213, 233));
        pnlInputBar.setLayout(new BorderLayout(10, 10));
        pnlInputBar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        txtMensaje = new JTextField();
        txtMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMensaje.setBackground(Color.WHITE);
        txtMensaje.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(238, 197, 217), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Placeholder
        txtMensaje.setText("Escribe un mensaje de Rizz...");
        txtMensaje.setForeground(Color.GRAY);
        txtMensaje.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtMensaje.getText().equals("Escribe un mensaje de Rizz...")) {
                    txtMensaje.setText("");
                    txtMensaje.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtMensaje.getText().isEmpty()) {
                    txtMensaje.setText("Escribe un mensaje de Rizz...");
                    txtMensaje.setForeground(Color.GRAY);
                }
            }
        });

        // Enviar con Enter
        txtMensaje.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enviarMensaje();
                }
            }
        });

        btnEnviar = new JButton("Enviar");
        btnEnviar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEnviar.setBackground(new Color(255, 51, 153));
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFocusPainted(false);
        btnEnviar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnEnviar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEnviar.addActionListener(e -> enviarMensaje());

        pnlInputBar.add(txtMensaje, BorderLayout.CENTER);
        pnlInputBar.add(btnEnviar, BorderLayout.EAST);
        add(pnlInputBar, BorderLayout.SOUTH);

        // Desactivar entrada al inicio
        txtMensaje.setEnabled(false);
        btnEnviar.setEnabled(false);
    }

    /**
     * Carga y renderiza el historial de mensajes de la conversacion seleccionada.
     */
    public void cargarMatch(MatchDTO match, List<MensajeDTO> mensajes) {
        this.matchActual = match;
        
        // Obtener el partner
        Long otroId = match.getUsuario1Id().equals(controlador.getUsuarioLogueadoId()) 
                ? match.getUsuario2Id() 
                : match.getUsuario1Id();
        
        this.partner = controlador.obtenerUsuarioPorId(otroId);

        // Actualizar header
        if (partner != null) {
            lblHeaderNombre.setText(partner.getNombre());
            if (partner.getFotoPerfilBase64() != null && !partner.getFotoPerfilBase64().isEmpty()) {
                lblHeaderAvatar.setIcon(new ImageIcon(crearImagenCircular(partner.getFotoPerfilBase64(), 45)));
            } else {
                lblHeaderAvatar.setIcon(new ImageIcon(crearAvatarPorDefecto(45, partner.getNombre())));
            }
        } else {
            lblHeaderNombre.setText("Usuario " + otroId);
            lblHeaderAvatar.setIcon(new ImageIcon(crearAvatarPorDefecto(45, "?")));
        }

        // Habilitar campos
        txtMensaje.setEnabled(true);
        btnEnviar.setEnabled(true);
        if (txtMensaje.getText().isEmpty() || txtMensaje.getText().equals("Escribe un mensaje de Rizz...")) {
            txtMensaje.setText("Escribe un mensaje de Rizz...");
            txtMensaje.setForeground(Color.GRAY);
        }

        // Pintar mensajes
        pnlChatArea.removeAll();
        // Espaciado inicial
        pnlChatArea.add(Box.createRigidArea(new Dimension(0, 15)));

        if (mensajes != null) {
            for (MensajeDTO msg : mensajes) {
                pnlChatArea.add(crearFilaMensaje(msg));
                pnlChatArea.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        pnlChatArea.revalidate();
        pnlChatArea.repaint();
        scrollAlFinal();
    }

    /**
     * Agrega un mensaje a la pantalla en caliente sin recargar toda la conversacion.
     */
    public void agregarMensaje(MensajeDTO mensaje) {
        pnlChatArea.add(crearFilaMensaje(mensaje));
        pnlChatArea.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlChatArea.revalidate();
        pnlChatArea.repaint();
        scrollAlFinal();
    }

    private void enviarMensaje() {
        String msgText = txtMensaje.getText().trim();
        if (msgText.isEmpty() || msgText.equals("Escribe un mensaje de Rizz...")) {
            return;
        }
        controlador.enviarMensaje(msgText);
        txtMensaje.setText("");
        txtMensaje.requestFocus();
    }

    private void scrollAlFinal() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollChat.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    /**
     * Crea un panel fila que alinea el mensaje a la izquierda o derecha.
     */
    private JPanel crearFilaMensaje(MensajeDTO msg) {
        JPanel pnlFila = new JPanel();
        pnlFila.setOpaque(false);
        
        boolean esPropio = msg.getRemitenteId().equals(controlador.getUsuarioLogueadoId());
        pnlFila.setLayout(new FlowLayout(esPropio ? FlowLayout.RIGHT : FlowLayout.LEFT, 15, 0));

        // Burbuja de Chat
        BurbujaChat burbuja = new BurbujaChat(msg.getContenido(), esPropio);
        pnlFila.add(burbuja);

        // Forzar ancho maximo de fila
        pnlFila.setMaximumSize(new Dimension(Short.MAX_VALUE, burbuja.getPreferredSize().height));
        return pnlFila;
    }

    /**
     * Componente Swing customizado que dibuja la burbuja de chat redondeada.
     */
    private static class BurbujaChat extends JPanel {
        private final String texto;
        private final boolean esPropio;

        public BurbujaChat(String texto, boolean esPropio) {
            this.texto = texto;
            this.esPropio = esPropio;
            
            setOpaque(false);
            setLayout(new BorderLayout());
            
            JTextArea areaTexto = new JTextArea(texto);
            areaTexto.setLineWrap(true);
            areaTexto.setWrapStyleWord(true);
            areaTexto.setEditable(false);
            areaTexto.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            areaTexto.setOpaque(false);
            areaTexto.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

            if (esPropio) {
                areaTexto.setForeground(Color.WHITE);
            } else {
                areaTexto.setForeground(new Color(73, 30, 50));
            }

            // Limitar ancho maximo de la burbuja
            int maxBubbleWidth = 400;
            areaTexto.setSize(new Dimension(maxBubbleWidth, Short.MAX_VALUE));
            Dimension d = areaTexto.getPreferredSize();
            if (d.width > maxBubbleWidth) {
                d.width = maxBubbleWidth;
                areaTexto.setPreferredSize(d);
            }

            add(areaTexto, BorderLayout.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (esPropio) {
                // Magenta/Rosa fuerte
                g2.setColor(new Color(255, 51, 153));
            } else {
                // Rosa claro suave
                g2.setColor(new Color(255, 213, 233));
            }
            // Dibujar rectangulo redondeado
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // =========================================================================
    // Utilidades de Imagenes
    // =========================================================================

    private Image crearImagenCircular(String base64, int diametro) {
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            BufferedImage src = ImageIO.read(new ByteArrayInputStream(bytes));
            if (src == null) {
                return crearAvatarPorDefecto(diametro, "?");
            }
            
            BufferedImage formatted = new BufferedImage(diametro, diametro, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = formatted.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setClip(new Ellipse2D.Float(0, 0, diametro, diametro));
            g2.drawImage(src, 0, 0, diametro, diametro, null);
            g2.dispose();
            return formatted;
        } catch (Exception e) {
            return crearAvatarPorDefecto(diametro, "?");
        }
    }

    private Image crearAvatarPorDefecto(int diametro, String nombre) {
        BufferedImage img = new BufferedImage(diametro, diametro, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(255, 51, 153));
        g2.fill(new Ellipse2D.Float(0, 0, diametro, diametro));

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
        String inicial = nombre != null && !nombre.isEmpty() ? nombre.substring(0, 1).toUpperCase() : "?";
        FontMetrics fm = g2.getFontMetrics();
        int x = (diametro - fm.stringWidth(inicial)) / 2;
        int y = ((diametro - fm.getHeight()) / 2) + fm.getAscent();
        g2.drawString(inicial, x, y);
        g2.dispose();

        return img;
    }
}

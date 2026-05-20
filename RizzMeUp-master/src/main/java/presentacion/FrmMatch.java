package presentacion;

import dto.MatchDTO;
import dto.UsuarioDTO;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Pantalla de celebración de Match ("¡RIZZ!").
 * Diseñada siguiendo directrices estéticas premium y modernas,
 * con fondos degradados, avatares circulares y micro-animaciones interactivas.
 * 
 * @author Erik
 */
public class FrmMatch extends JFrame {

    private final MatchDTO match;
    private final UsuarioDTO usuarioLogueado;
    private final UsuarioDTO candidato;

    public FrmMatch(MatchDTO match, UsuarioDTO usuarioLogueado, UsuarioDTO candidato) {
        this.match = match;
        this.usuarioLogueado = usuarioLogueado;
        this.candidato = candidato;

        initComponents();
    }

    private void initComponents() {
        setTitle("¡RIZZ MATCH! - RizzMeUp");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(860, 600));
        setResizable(false);

        // Panel con Fondo Degradado Premium (Rosa Vibrante a Púrpura Oscuro)
        JPanel pnlFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 51, 153), 0, getHeight(), new Color(47, 21, 37));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Dibujar corazones de fondo sutiles
                g2d.setColor(new Color(255, 255, 255, 20));
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 48));
                g2d.drawString("♥", 100, 150);
                g2d.drawString("♥", 750, 450);
                g2d.drawString("♥", 200, 480);
                g2d.drawString("♥", 680, 120);
            }
        };
        pnlFondo.setLayout(new BorderLayout());
        setContentPane(pnlFondo);

        // Contenedor principal con BoxLayout vertical
        JPanel pnlContenido = new JPanel();
        pnlContenido.setOpaque(false);
        pnlContenido.setLayout(new BoxLayout(pnlContenido, BoxLayout.Y_AXIS));
        pnlContenido.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));
        pnlFondo.add(pnlContenido, BorderLayout.CENTER);

        // 1. TÍTULO DE CELEBRACIÓN DE MATCH ("¡RIZZ!")
        JLabel lblTitulo = new JLabel("¡RIZZ!", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Outfit", Font.BOLD, 72));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("¡Hay química! Se han dado Rizz mutuamente.", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSubtitulo.setForeground(new Color(255, 213, 233));
        lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

        pnlContenido.add(lblTitulo);
        pnlContenido.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlContenido.add(lblSubtitulo);
        pnlContenido.add(Box.createRigidArea(new Dimension(0, 40)));

        // 2. AVATARES CIRCULARES LADO A LADO
        JPanel pnlAvatares = new JPanel();
        pnlAvatares.setOpaque(false);
        pnlAvatares.setLayout(new BoxLayout(pnlAvatares, BoxLayout.X_AXIS));
        pnlAvatares.setAlignmentX(CENTER_ALIGNMENT);

        // Avatar 1: Usuario Logueado
        JPanel pnlUser1 = crearContenedorAvatar(usuarioLogueado, "Tú");
        // Avatar 2: Candidato
        JPanel pnlUser2 = crearContenedorAvatar(candidato, candidato.getNombre());

        pnlAvatares.add(pnlUser1);
        pnlAvatares.add(Box.createRigidArea(new Dimension(60, 0))); // Espacio entre círculos
        pnlAvatares.add(pnlUser2);

        pnlContenido.add(pnlAvatares);
        pnlContenido.add(Box.createRigidArea(new Dimension(0, 50)));

        // 3. BOTÓN DE INTERACCIÓN "¡CHATEAR!"
        JButton btnChatear = new JButton("¡CHATEAR!") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2d.setColor(new Color(204, 0, 102));
                } else if (getModel().isRollover()) {
                    g2d.setColor(new Color(255, 77, 166));
                } else {
                    g2d.setColor(new Color(255, 51, 153));
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };
        btnChatear.setFont(new Font("Segoe UI", Font.BOLD, 22));
        btnChatear.setForeground(Color.WHITE);
        btnChatear.setContentAreaFilled(false);
        btnChatear.setBorderPainted(false);
        btnChatear.setFocusPainted(false);
        btnChatear.setOpaque(false);
        btnChatear.setPreferredSize(new Dimension(220, 60));
        btnChatear.setMaximumSize(new Dimension(220, 60));
        btnChatear.setMinimumSize(new Dimension(220, 60));
        btnChatear.setAlignmentX(CENTER_ALIGNMENT);

        // Efectos de Micro-Animación Hover
        btnChatear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnChatear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
        });

        btnChatear.addActionListener(e -> {
            // Cerramos la pantalla al hacer clic
            dispose();
        });

        pnlContenido.add(btnChatear);

        pack();
        setLocationRelativeTo(null); // Centrar en pantalla
    }

    /**
     * Crea un panel con foto circular y el nombre debajo.
     */
    private JPanel crearContenedorAvatar(UsuarioDTO usuario, String etiquetaNombre) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Cargar y procesar la foto circular
        Image imgOriginal = null;
        String base64Image = usuario.getFotoPerfilBase64();
        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                imgOriginal = ImageIO.read(bis);
            } catch (Exception e) {
                System.out.println("Error decodificando imagen para FrmMatch: " + e.getMessage());
            }
        }

        BufferedImage circularImage;
        if (imgOriginal != null) {
            circularImage = crearFotoCircular(imgOriginal, 180);
        } else {
            // Imagen por defecto si no tiene foto
            BufferedImage defaultImg = new BufferedImage(180, 180, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = defaultImg.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 213, 233));
            g2.fillOval(0, 0, 180, 180);
            g2.setColor(new Color(102, 0, 51));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 48));
            g2.drawString(usuario.getNombre().substring(0, 1), 75, 110);
            g2.dispose();
            circularImage = defaultImg;
        }

        JLabel lblAvatar = new JLabel(new ImageIcon(circularImage));
        lblAvatar.setAlignmentX(CENTER_ALIGNMENT);
        
        // Bordes elegantes para resaltar las fotos
        lblAvatar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel lblNombre = new JLabel(etiquetaNombre, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(lblAvatar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblNombre);

        return panel;
    }

    /**
     * Recorta una imagen para darle forma circular perfecta.
     */
    private BufferedImage crearFotoCircular(Image image, int diameter) {
        BufferedImage buffer = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Creamos la forma circular de máscara
        Ellipse2D.Double area = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2.setClip(area);
        
        // Dibujamos la imagen original ajustándola al diámetro
        g2.drawImage(image, 0, 0, diameter, diameter, null);
        
        // Añadir un borde blanco estilizado alrededor del círculo
        g2.setClip(null);
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(Color.WHITE);
        g2.setStroke(new java.awt.BasicStroke(4f));
        g2.drawOval(2, 2, diameter - 4, diameter - 4);
        
        g2.dispose();
        return buffer;
    }
}

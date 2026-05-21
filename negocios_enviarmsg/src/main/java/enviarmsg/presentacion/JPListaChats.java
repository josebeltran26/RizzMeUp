package enviarmsg.presentacion;

import dto.UsuarioDTO;
import enviarmsg.negocio.dto.MatchDTO;
import enviarmsg.negocio.dto.MensajeDTO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Panel lateral izquierdo que muestra la lista de matches activos (conversaciones).
 * Diseñado con una estetica rosa premium, avatares circulares y bordes suaves.
 */
public class JPListaChats extends JPanel {

    private final ControlChat controlador;
    private JList<MatchDTO> lstMatches;
    private DefaultListModel<MatchDTO> listModel;
    private final Map<Long, UsuarioDTO> cachePerfiles;

    public JPListaChats(ControlChat controlador) {
        this.controlador = controlador;
        this.cachePerfiles = new HashMap<>();
        
        initComponents();
        recargarLista();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 213, 233));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(238, 197, 217)));

        // Header del panel lateral
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(255, 213, 233));
        pnlHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(238, 197, 217)));

        JLabel lblTitulo = new JLabel("Tus Conversaciones");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(102, 0, 51));
        pnlHeader.add(lblTitulo);
        add(pnlHeader, BorderLayout.NORTH);

        // Lista de chats
        listModel = new DefaultListModel<>();
        lstMatches = new JList<>(listModel);
        lstMatches.setBackground(new Color(255, 213, 233));
        lstMatches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstMatches.setCellRenderer(new MatchListCellRenderer());
        
        // Listener de seleccion
        lstMatches.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                MatchDTO match = lstMatches.getSelectedValue();
                if (match != null) {
                    controlador.seleccionarMatch(match);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(lstMatches);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(255, 213, 233));
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Carga o recarga todos los matches desde el controlador.
     */
    public void recargarLista() {
        listModel.clear();
        List<MatchDTO> matches = controlador.cargarMatches();
        
        if (matches != null) {
            for (MatchDTO m : matches) {
                // Pre-cargar perfiles en cache de forma sincrona ligera
                Long otroId = m.getUsuario1Id().equals(controlador.getUsuarioLogueadoId()) 
                        ? m.getUsuario2Id() 
                        : m.getUsuario1Id();
                
                if (!cachePerfiles.containsKey(otroId)) {
                    UsuarioDTO perfil = controlador.obtenerUsuarioPorId(otroId);
                    if (perfil != null) {
                        cachePerfiles.put(otroId, perfil);
                    }
                }
                listModel.addElement(m);
            }
        }
        
        // Si no hay conversaciones, notificar a la vista principal
        if (listModel.isEmpty() && controlador != null) {
            // Se maneja a traves de PnlMensajeria
        }
    }

    /**
     * Obtiene el modelo de datos de la lista de matches.
     */
    public DefaultListModel<MatchDTO> getListModel() {
        return listModel;
    }

    /**
     * Renderizador premium personalizado para los elementos de la lista de matches.
     */
    private class MatchListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            
            MatchDTO m = (MatchDTO) value;
            Long otroId = m.getUsuario1Id().equals(controlador.getUsuarioLogueadoId()) 
                    ? m.getUsuario2Id() 
                    : m.getUsuario1Id();
            
            UsuarioDTO partner = cachePerfiles.get(otroId);
            
            JPanel pnlItem = new JPanel();
            pnlItem.setLayout(new BorderLayout(12, 0));
            pnlItem.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            if (isSelected) {
                pnlItem.setBackground(new Color(255, 169, 217)); // Rosa mas oscuro al seleccionar
            } else {
                pnlItem.setBackground(new Color(255, 213, 233)); // Fondo base
            }

            // Imagen Circular
            JLabel lblAvatar = new JLabel();
            lblAvatar.setPreferredSize(new Dimension(50, 50));
            
            if (partner != null && partner.getFotoPerfilBase64() != null && !partner.getFotoPerfilBase64().isEmpty()) {
                lblAvatar.setIcon(new ImageIcon(crearImagenCircular(partner.getFotoPerfilBase64(), 50)));
            } else {
                lblAvatar.setIcon(new ImageIcon(crearAvatarPorDefecto(50, partner != null ? partner.getNombre() : "?")));
            }
            pnlItem.add(lblAvatar, BorderLayout.WEST);

            // Contenedor texto (Nombre + Ultimo mensaje)
            JPanel pnlTexto = new JPanel();
            pnlTexto.setLayout(new GridLayout(2, 1, 2, 2));
            pnlTexto.setOpaque(false);

            JLabel lblNombre = new JLabel(partner != null ? partner.getNombre() : "Usuario " + otroId);
            lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 15));
            lblNombre.setForeground(new Color(73, 30, 50));
            pnlTexto.add(lblNombre);

            // Ultimo mensaje mock/real
            String ultimoMsg = "¡Hazle Rizz y enviale un mensaje!";
            try {
                // Obtener el ultimo mensaje real persistido
                List<MensajeDTO> msgList = controlador.obtenerMensajesPorMatch(m.getId());
                if (msgList != null && !msgList.isEmpty()) {
                    MensajeDTO uMsg = msgList.get(msgList.size() - 1);
                    ultimoMsg = uMsg.getContenido();
                    if (ultimoMsg.length() > 22) {
                        ultimoMsg = ultimoMsg.substring(0, 19) + "...";
                    }
                }
            } catch (Exception ex) {
                // Fallback silencioso
            }

            JLabel lblUltimoMsg = new JLabel(ultimoMsg);
            lblUltimoMsg.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblUltimoMsg.setForeground(new Color(110, 80, 95));
            pnlTexto.add(lblUltimoMsg);

            pnlItem.add(pnlTexto, BorderLayout.CENTER);
            return pnlItem;
        }

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
                
                // Recortar en forma de circulo
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

            // Fondo circular rosa fuerte
            g2.setColor(new Color(255, 51, 153));
            g2.fill(new Ellipse2D.Float(0, 0, diametro, diametro));

            // Primera letra del nombre al centro
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
            String inicial = nombre != null && !nombre.isEmpty() ? nombre.substring(0, 1).toUpperCase() : "?";
            FontMetrics fm = g2.getFontMetrics();
            int x = (diametro - fm.stringWidth(inicial)) / 2;
            int y = ((diametro - fm.getHeight()) / 2) + fm.getAscent();
            g2.drawString(inicial, x, y);
            g2.dispose();

            return img;
        }
    }
}

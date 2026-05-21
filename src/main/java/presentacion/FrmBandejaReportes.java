/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package presentacion;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import negocio.dto.ReporteDetalleDTO;
import negocio.subsistema.ControlGestionReportes;

/**
 *
 * @author Roger Jr
 */
public class FrmBandejaReportes extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(
            FrmBandejaReportes.class.getName());

    private static final Color COLOR_FONDO = new Color(255, 242, 248);
    private static final Color COLOR_MENU = new Color(255, 204, 224);
    private static final Color COLOR_ITEM = new Color(255, 214, 230);
    private static final Color COLOR_ITEM_HOVER = new Color(252, 199, 220);
    private static final Color COLOR_TEXTO = new Color(82, 42, 63);
    private static final Color COLOR_TEXTO_SUAVE = new Color(150, 103, 126);
    private static final Color COLOR_AVATAR = new Color(245, 250, 255);
    private static final Color COLOR_AZUL = new Color(66, 135, 204);

    private static final int ANCHO_MENU = 165;
    private static final int ANCHO_TOTAL = 860;
    private static final int ALTO_TOTAL = 770;

    private JPanel pnlMenu;
    private JPanel pnlLista;
    private JScrollPane scrollLista;
    private JLabel lblSinReportes;

    public FrmBandejaReportes() {
        initVista();
        cargarReportes();
    }

    private void initVista() {
        setBackground(COLOR_FONDO);
        setLayout(null);
        setPreferredSize(new Dimension(ANCHO_TOTAL, ALTO_TOTAL));

        crearMenuLateral();
        crearContenido();
    }

    private void crearMenuLateral() {
        pnlMenu = new JPanel();
        pnlMenu.setBackground(COLOR_MENU);
        pnlMenu.setLayout(null);
        pnlMenu.setBounds(0, 0, ANCHO_MENU, ALTO_TOTAL);
        add(pnlMenu);

        // Espacio para el logo de Rizz Me Up

        JLabel lblLogoTexto = new JLabel("Rizz Me Up!");
        lblLogoTexto.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblLogoTexto.setForeground(new Color(190, 50, 105));
        lblLogoTexto.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogoTexto.setBounds(15, 82, 135, 28);
        pnlMenu.add(lblLogoTexto);

        JPanel indicador = new JPanel();
        indicador.setBackground(COLOR_TEXTO);
        indicador.setBounds(0, 160, 6, 34);
        pnlMenu.add(indicador);

        JLabel lblEvaluar = new JLabel(
                "<html><div>▣&nbsp;&nbsp;Evaluar<br>&nbsp;&nbsp;&nbsp;&nbsp;Reportes</div></html>");
        lblEvaluar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblEvaluar.setForeground(COLOR_TEXTO);
        lblEvaluar.setBounds(18, 150, 130, 55);
        pnlMenu.add(lblEvaluar);


        AvatarPanel avatarAdmin = new AvatarPanel();
        avatarAdmin.setBounds(18, 690, 38, 38);
        pnlMenu.add(avatarAdmin);

        JLabel lblAdmin = new JLabel("Administrador");
        lblAdmin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblAdmin.setForeground(COLOR_TEXTO);
        lblAdmin.setBounds(55, 697, 105, 25);
        pnlMenu.add(lblAdmin);
        
    }

    private void crearContenido() {
        pnlLista = new JPanel();
        pnlLista.setOpaque(false);
        pnlLista.setLayout(new BoxLayout(pnlLista, BoxLayout.Y_AXIS));
        pnlLista.setBorder(BorderFactory.createEmptyBorder(16, 20, 10, 10));

        scrollLista = new JScrollPane(pnlLista);
        scrollLista.setBorder(BorderFactory.createEmptyBorder());
        scrollLista.setOpaque(false);
        scrollLista.getViewport().setOpaque(false);
        scrollLista.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLista.getVerticalScrollBar().setUnitIncrement(16);
        scrollLista.setBounds(ANCHO_MENU, 0, ANCHO_TOTAL - ANCHO_MENU, ALTO_TOTAL);
        add(scrollLista);

        lblSinReportes = new JLabel(
                "<html><div style='text-align:center;'>"
                + "Por el momento no existen<br>reportes a evaluar"
                + "</div></html>");
        lblSinReportes.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblSinReportes.setForeground(COLOR_TEXTO);
        lblSinReportes.setHorizontalAlignment(SwingConstants.CENTER);
        lblSinReportes.setBounds(ANCHO_MENU, 315, ANCHO_TOTAL - ANCHO_MENU, 100);
        lblSinReportes.setVisible(false);
        add(lblSinReportes);
    }

    private void cargarReportes() {
        try {
            List<ReporteDetalleDTO> reportes = ControlGestionReportes
                    .getInstancia()
                    .obtenerPendientes();

            pnlLista.removeAll();

            if (reportes == null || reportes.isEmpty()) {
                scrollLista.setVisible(false);
                lblSinReportes.setVisible(true);
                revalidate();
                repaint();
                return;
            }

            for (ReporteDetalleDTO reporte : reportes) {
                pnlLista.add(crearFilaReporte(reporte));
                pnlLista.add(Box.createVerticalStrut(10));
            }

            scrollLista.setVisible(true);
            lblSinReportes.setVisible(false);
            pnlLista.revalidate();
            pnlLista.repaint();

        } catch (Exception e) {
            logger.severe("error al cargar reportes: " + e.getMessage());
        }
    }

    private JPanel crearFilaReporte(ReporteDetalleDTO reporte) {
        int anchoFila = ANCHO_TOTAL - ANCHO_MENU - 40;

        RoundedPanel fila = new RoundedPanel(12);
        fila.setBackground(COLOR_ITEM);
        fila.setLayout(null);
        fila.setPreferredSize(new Dimension(anchoFila, 82));
        fila.setMinimumSize(new Dimension(anchoFila, 82));
        fila.setMaximumSize(new Dimension(anchoFila, 82));
        fila.setCursor(new Cursor(Cursor.HAND_CURSOR));

        AvatarPanel avatar = new AvatarPanel();
        avatar.setBounds(16, 14, 48, 48);
        fila.add(avatar);

        String motivo = reporte.getMotivo() != null
                ? reporte.getMotivo()
                : "Sin motivo";

        String tituloTexto = "<html><body style='width:520px'>"
                + "Reporte #"
                + reporte.getId()
                + ": "
                + motivo
                + "</body></html>";

        JLabel lblTitulo = new JLabel(tituloTexto);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setBounds(78, 10, anchoFila - 220, 38);
        fila.add(lblTitulo);

        String nombreReportado = reporte.getReportado() != null
                ? reporte.getReportado().getNombre()
                : "Usuario desconocido";

        JLabel lblNombre = new JLabel(nombreReportado);
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblNombre.setForeground(COLOR_TEXTO_SUAVE);
        lblNombre.setBounds(78, 53, anchoFila - 220, 20);
        fila.add(lblNombre);

        String hora = reporte.getFecha() != null
                ? new SimpleDateFormat("hh:mm a").format(reporte.getFecha())
                : "--:--";

        JLabel lblHora = new JLabel(hora);
        lblHora.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblHora.setForeground(COLOR_TEXTO_SUAVE);
        lblHora.setHorizontalAlignment(SwingConstants.RIGHT);
        lblHora.setBounds(anchoFila - 135, 53, 110, 18);
        fila.add(lblHora);

        fila.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                fila.setBackground(COLOR_ITEM_HOVER);
                fila.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                fila.setBackground(COLOR_ITEM);
                fila.repaint();
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                abrirDetalle(reporte.getId());
            }
        });

        return fila;
    }

    private void abrirDetalle(String idReporte) {
        try {
            Container padre = getParent();

            while (padre != null && !(padre instanceof IContenedorPrincipal)) {
                padre = padre.getParent();
            }

            if (padre instanceof IContenedorPrincipal) {
                ((IContenedorPrincipal) padre).mostrarPanel(
                        new FrmDetalleReporte(idReporte));
            }

        } catch (Exception e) {
            logger.severe("error al abrir detalle: " + e.getMessage());
        }
    }

    private static class RoundedPanel extends JPanel {

        private final int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(220, 170, 195, 70));
            g2.fillRoundRect(3, 4, getWidth() - 6, getHeight() - 6,
                    radius, radius);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 7, getHeight() - 8,
                    radius, radius);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class AvatarPanel extends JPanel {

        public AvatarPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(getWidth(), getHeight());

            g2.setColor(COLOR_AVATAR);
            g2.fillOval(0, 0, size - 1, size - 1);

            g2.setColor(COLOR_AZUL);
            g2.fillOval(size / 2 - 7, 8, 14, 14);

            g2.setColor(new Color(45, 105, 170));
            g2.fillArc(8, 22, size - 16, 24, 0, 180);

            g2.setColor(new Color(230, 238, 248));
            g2.fillPolygon(
                    new int[]{size / 2, size / 2 - 8, size / 2 + 8},
                    new int[]{28, size - 3, size - 3},
                    3
            );

            g2.dispose();
        }
    }

   


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

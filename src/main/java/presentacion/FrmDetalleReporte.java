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
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import negocio.dto.ReporteDetalleDTO;
import negocio.dto.UsuarioDTO;
import negocio.subsistema.ControlGestionReportes;

/**
 *
 * @author Roger Jr
 */
public class FrmDetalleReporte extends javax.swing.JPanel 
{


    private static final Logger logger = Logger.getLogger(
            FrmDetalleReporte.class.getName());

    private static final Color COLOR_FONDO = new Color(255, 242, 248);
    private static final Color COLOR_TEXTO = new Color(91, 45, 68);
    private static final Color COLOR_TEXTO_SUAVE = new Color(145, 86, 115);
    private static final Color COLOR_TARJETA = new Color(246, 103, 151);
    private static final Color COLOR_TARJETA_CLARA = new Color(255, 214, 231);
    private static final Color COLOR_BTN_ACCION = new Color(106, 45, 70);
    private static final Color COLOR_BTN_HOVER = new Color(145, 58, 94);
    
    private static final Color COLOR_MENU = new Color(255, 204, 224);

    private static final int ANCHO_MENU = 165;
    private static final int ANCHO_TOTAL = 860;
    private static final int ALTO_TOTAL = 770;

    private JPanel pnlMenu;

    private final String idReporte;
    private ReporteDetalleDTO detalleActual;

    private JLabel lblTitulo;
    private JLabel lblNombreReportante;
    private JLabel lblNombreReportado;
    private JLabel lblProfesionReportado;
    private JLabel lblBioReportado;
    private JLabel lblTipoReporte;

    private JButton btnVolver;
    private JButton btnAceptarFoto;
    private JButton btnFotoAlterada;
    private JButton btnOcultarFoto;
    private JButton btnDarAviso;
    private JButton btnSaberComportamiento;

    public FrmDetalleReporte(String idReporte) {
        this.idReporte = idReporte;
        initVista();
        cargarDetalle();
    }

    private void initVista() {
        setBackground(COLOR_FONDO);
        setLayout(null);
        setPreferredSize(new Dimension(ANCHO_TOTAL, ALTO_TOTAL));

        crearMenuLateral();

        btnVolver = new JButton("←");
        btnVolver.setBounds(180, 18, 42, 34);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 22));
        btnVolver.setForeground(COLOR_TEXTO);
        btnVolver.setBackground(COLOR_FONDO);
        btnVolver.setBorderPainted(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnVolver);

        lblTitulo = new JLabel(
                "<html><div style='text-align:center;'>Detalles del<br>Reporte</div></html>");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(ANCHO_MENU, 34, ANCHO_TOTAL - ANCHO_MENU, 70);
        add(lblTitulo);

        JPanel pnlReportante = crearTarjetaReportante();
        pnlReportante.setBounds(230, 170, 220, 230);
        add(pnlReportante);

        JPanel pnlAcciones = crearPanelAcciones();
        pnlAcciones.setBounds(480, 205, 145, 170);
        add(pnlAcciones);

        JPanel pnlReportado = crearTarjetaReportado();
        pnlReportado.setBounds(650, 165, 180, 280);
        add(pnlReportado);

        initEventos();
    }
    
    private void crearMenuLateral() {
        pnlMenu = new JPanel();
        pnlMenu.setBackground(COLOR_MENU);
        pnlMenu.setLayout(null);
        pnlMenu.setBounds(0, 0, ANCHO_MENU, ALTO_TOTAL);
        add(pnlMenu);

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

    private JPanel crearTarjetaReportante() {
        RoundedPanel pnl = new RoundedPanel(4);
        pnl.setBackground(COLOR_TARJETA);
        pnl.setLayout(null);

        DecorativeCircle c1 = new DecorativeCircle(new Color(255, 127, 170, 120));
        c1.setBounds(-45, -35, 150, 150);
        pnl.add(c1);

        DecorativeCircle c2 = new DecorativeCircle(new Color(255, 180, 205, 110));
        c2.setBounds(110, 20, 150, 150);
        pnl.add(c2);

        DecorativeCircle c3 = new DecorativeCircle(new Color(230, 73, 126, 100));
        c3.setBounds(95, 105, 150, 150);
        pnl.add(c3);

        AvatarPanel avatarReportante = new AvatarPanel();
        avatarReportante.setBounds(42, 42, 46, 46);
        pnl.add(avatarReportante);

        lblNombreReportante = new JLabel("cargando...");
        lblNombreReportante.setFont(new Font("Segoe UI", Font.BOLD, 9));
        lblNombreReportante.setForeground(Color.WHITE);
        lblNombreReportante.setBounds(94, 48, 100, 18);
        pnl.add(lblNombreReportante);

        JLabel lblRol = new JLabel("Reportó el caso");
        lblRol.setFont(new Font("Segoe UI", Font.BOLD, 8));
        lblRol.setForeground(new Color(80, 34, 54));
        lblRol.setBounds(42, 92, 100, 16);
        pnl.add(lblRol);

        AvatarPanel avatarReportado = new AvatarPanel();
        avatarReportado.setBounds(120, 112, 46, 46);
        pnl.add(avatarReportado);

        lblTipoReporte = new JLabel(
                "<html><b>Tipo de reporte:</b><br>--</html>");
        lblTipoReporte.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTipoReporte.setForeground(new Color(45, 20, 32));
        lblTipoReporte.setBounds(30, 160, 165, 48);
        pnl.add(lblTipoReporte);

        return pnl;
    }

    private JPanel crearPanelAcciones() {
        JPanel pnl = new JPanel();
        pnl.setOpaque(false);
        pnl.setLayout(null);

        btnAceptarFoto = crearBotonAccion("Aprobar foto");
        btnFotoAlterada = crearBotonAccion("Editar advertencia");
        btnOcultarFoto = crearBotonAccion("Ocultar foto y aviso");
        btnDarAviso = crearBotonAccion("Avisar usuario");
        btnSaberComportamiento = crearBotonAccion("Revisar comportamiento");

        btnAceptarFoto.setBounds(0, 0, 145, 24);
        btnFotoAlterada.setBounds(0, 34, 145, 24);
        btnOcultarFoto.setBounds(0, 68, 145, 24);
        btnDarAviso.setBounds(0, 102, 145, 24);
        btnSaberComportamiento.setBounds(0, 136, 145, 24);

        pnl.add(btnAceptarFoto);
        pnl.add(btnFotoAlterada);
        pnl.add(btnOcultarFoto);
        pnl.add(btnDarAviso);
        pnl.add(btnSaberComportamiento);

        return pnl;
    }

    private JPanel crearTarjetaReportado() {
        JPanel pnl = new JPanel();
        pnl.setOpaque(false);
        pnl.setLayout(null);

        JLabel lblTituloFoto = new JLabel("Foto Reportada");
        lblTituloFoto.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTituloFoto.setForeground(COLOR_TEXTO);
        lblTituloFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloFoto.setBounds(0, 0, 180, 20);
        pnl.add(lblTituloFoto);

        FacePanel foto = new FacePanel();
        foto.setBounds(20, 25, 140, 145);
        pnl.add(foto);

        RoundedPanel info = new RoundedPanel(2);
        info.setBackground(COLOR_TARJETA_CLARA);
        info.setLayout(null);
        info.setBounds(20, 165, 140, 78);
        pnl.add(info);

        lblNombreReportado = new JLabel("cargando...");
        lblNombreReportado.setFont(new Font("Segoe UI", Font.BOLD, 9));
        lblNombreReportado.setForeground(COLOR_TEXTO);
        lblNombreReportado.setBounds(8, 6, 124, 14);
        info.add(lblNombreReportado);

        lblProfesionReportado = new JLabel("");
        lblProfesionReportado.setFont(new Font("Segoe UI", Font.PLAIN, 8));
        lblProfesionReportado.setForeground(COLOR_TEXTO);
        lblProfesionReportado.setBounds(8, 21, 124, 12);
        info.add(lblProfesionReportado);

        lblBioReportado = new JLabel("");
        lblBioReportado.setFont(new Font("Segoe UI", Font.PLAIN, 8));
        lblBioReportado.setForeground(COLOR_TEXTO_SUAVE);
        lblBioReportado.setBounds(8, 34, 124, 28);
        info.add(lblBioReportado);

        JLabel lblRocks = new JLabel("✕ Estrellitas    / Rock");
        lblRocks.setFont(new Font("Segoe UI", Font.BOLD, 8));
        lblRocks.setForeground(new Color(180, 53, 93));
        lblRocks.setBounds(8, 59, 124, 14);
        info.add(lblRocks);

        JLabel lblInfoUsuario = new JLabel(
                "<html><div style='text-align:center;'>Informacion del<br>Usuario</div></html>");
        lblInfoUsuario.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblInfoUsuario.setForeground(COLOR_TEXTO);
        lblInfoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblInfoUsuario.setBounds(0, 248, 180, 32);
        pnl.add(lblInfoUsuario);

        return pnl;
    }

    private void initEventos() {
        btnVolver.addActionListener(e -> volverBandeja());

        btnAceptarFoto.addActionListener(e -> procesarAccion("ACEPTAR"));
        btnFotoAlterada.addActionListener(e -> procesarAccion("FOTO_ALTERADA"));
        btnOcultarFoto.addActionListener(e -> procesarAccion("OCULTAR_FOTO"));
        btnDarAviso.addActionListener(e -> procesarAccion("AVISO"));
        btnSaberComportamiento.addActionListener(
                e -> procesarAccion("COMPORTAMIENTO"));
    }

    private void cargarDetalle() {
        try {
            detalleActual = ControlGestionReportes
                    .getInstancia()
                    .consultarDetalle(idReporte);

            if (detalleActual == null) {
                lblTitulo.setText("Reporte no encontrado");
                return;
            }

            UsuarioDTO reportante = detalleActual.getReportante();
            if (reportante != null) {
                lblNombreReportante.setText(reportante.getNombre());
            }

            UsuarioDTO reportado = detalleActual.getReportado();
            if (reportado != null) {
                lblNombreReportado.setText(reportado.getNombre());
                lblProfesionReportado.setText(reportado.getEstadoCuenta());
                lblBioReportado.setText(
                        "<html>Usuario con "
                        + reportado.getAdvertencias()
                        + " advertencias</html>");
            }

            lblTipoReporte.setText(
                    "<html><b>Tipo de reporte:</b><br>"
                    + detalleActual.getMotivo()
                    + "</html>");

        } catch (Exception e) {
            logger.severe("error al cargar detalle: " + e.getMessage());
        }
    }

    private void procesarAccion(String accion) {
        try {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "Confirmas aplicar la accion: " + accion + "?",
                    "Confirmar accion",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            boolean resultado = ControlGestionReportes
                    .getInstancia()
                    .procesarSancion(idReporte, accion);

            if (resultado) {
                Container padre = buscarContenedorPrincipal();

                if (padre instanceof IContenedorPrincipal) {
                    ((IContenedorPrincipal) padre).mostrarPanel(
                            new FrmExitoEvaluacion(
                                    (IContenedorPrincipal) padre));
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Ocurrio un error al procesar la accion",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            logger.severe("error al procesar accion: " + e.getMessage());
        }
    }

    private void volverBandeja() {
        Container padre = buscarContenedorPrincipal();

        if (padre instanceof IContenedorPrincipal) {
            ((IContenedorPrincipal) padre).mostrarPanel(
                    new FrmBandejaReportes());
        }
    }

    private Container buscarContenedorPrincipal() {
        Container padre = getParent();

        while (padre != null && !(padre instanceof IContenedorPrincipal)) {
            padre = padre.getParent();
        }

        return padre;
    }

    private JButton crearBotonAccion(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(COLOR_BTN_ACCION);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 8));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_BTN_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_BTN_ACCION);
            }
        });

        return btn;
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

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(),
                    radius, radius);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class DecorativeCircle extends JPanel {

        private final Color color;

        public DecorativeCircle(Color color) {
            this.color = color;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(color);
            g2.fillOval(0, 0, getWidth(), getHeight());

            g2.dispose();
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

            g2.setColor(new Color(245, 250, 255));
            g2.fillOval(0, 0, size - 1, size - 1);

            g2.setColor(new Color(63, 135, 205));
            g2.fillOval(14, 8, 16, 16);

            g2.setColor(new Color(45, 105, 170));
            g2.fillArc(8, 22, 30, 22, 0, 180);

            g2.setColor(new Color(230, 238, 248));
            g2.fillPolygon(
                    new int[]{22, 14, 30},
                    new int[]{27, 42, 42},
                    3
            );

            g2.dispose();
        }
    }

    private static class FacePanel extends JPanel {

        public FacePanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(220, 236, 250));
            g2.fillOval(38, 20, 62, 72);

            g2.setColor(new Color(60, 127, 195));
            g2.fillArc(34, 18, 70, 48, 0, 180);

            g2.setColor(new Color(40, 105, 170));
            g2.drawArc(43, 34, 14, 10, 180, 180);
            g2.drawArc(78, 34, 14, 10, 180, 180);
            g2.drawLine(70, 47, 66, 62);
            g2.drawArc(56, 62, 30, 14, 200, 140);

            g2.setColor(new Color(55, 125, 195));
            g2.fillArc(22, 88, 96, 68, 0, 180);

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

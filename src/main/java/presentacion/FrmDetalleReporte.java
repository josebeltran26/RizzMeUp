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
public class FrmDetalleReporte extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(
            FrmDetalleReporte.class.getName());

    private static final Color COLOR_FONDO = new Color(255, 235, 245);
    private static final Color COLOR_TEXTO = new Color(102, 0, 51);
    private static final Color COLOR_TARJETA = new Color(255, 213, 233);
    private static final Color COLOR_BTN_ACCION = new Color(220, 80, 120);
    private static final Color COLOR_BTN_HOVER = new Color(190, 50, 90);

    private final String idReporte;
    private ReporteDetalleDTO detalleActual;

    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblFotoReportante;
    private javax.swing.JLabel lblNombreReportante;
    private javax.swing.JLabel lblFotoReportado;
    private javax.swing.JLabel lblNombreReportado;
    private javax.swing.JLabel lblProfesionReportado;
    private javax.swing.JLabel lblBioReportado;
    private javax.swing.JLabel lblTipoReporte;
    private javax.swing.JButton btnAceptarFoto;
    private javax.swing.JButton btnFotoAlterada;
    private javax.swing.JButton btnOcultarFoto;
    private javax.swing.JButton btnDarAviso;
    private javax.swing.JButton btnSaberComportamiento;

    public FrmDetalleReporte(String idReporte)
    {
        this.idReporte = idReporte;
        initVista();
        cargarDetalle();
    }
    // </editor-fold>


    private void initVista()
    {
        setBackground(COLOR_FONDO);
        setLayout(null);
        setPreferredSize(new Dimension(860, 770));

        // titulo
        lblTitulo = new javax.swing.JLabel("Detalles del Reporte");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 20, 860, 40);
        add(lblTitulo);

        // ── tarjeta reportante ──────────────────────────
        JPanel pnlReportante = new JPanel();
        pnlReportante.setBackground(COLOR_TARJETA);
        pnlReportante.setLayout(null);
        pnlReportante.setBounds(40, 90, 160, 200);
        add(pnlReportante);

        lblFotoReportante = new javax.swing.JLabel();
        lblFotoReportante.setBackground(new Color(180, 140, 160));
        lblFotoReportante.setOpaque(true);
        lblFotoReportante.setHorizontalAlignment(SwingConstants.CENTER);
        lblFotoReportante.setBounds(30, 10, 100, 100);
        pnlReportante.add(lblFotoReportante);

        JLabel lblTituloRep = new JLabel("Reportante");
        lblTituloRep.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTituloRep.setForeground(COLOR_TEXTO);
        lblTituloRep.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloRep.setBounds(0, 118, 160, 20);
        pnlReportante.add(lblTituloRep);

        lblNombreReportante = new javax.swing.JLabel("cargando...");
        lblNombreReportante.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblNombreReportante.setForeground(COLOR_TEXTO);
        lblNombreReportante.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombreReportante.setBounds(0, 138, 160, 20);
        pnlReportante.add(lblNombreReportante);

        // tipo de reporte
        lblTipoReporte = new javax.swing.JLabel("Tipo de reporte: --");
        lblTipoReporte.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTipoReporte.setForeground(COLOR_TEXTO);
        lblTipoReporte.setBounds(40, 310, 280, 25);
        add(lblTipoReporte);

        // ── botones de accion ───────────────────────────
        btnAceptarFoto = crearBotonAccion("Aceptar foto");
        btnFotoAlterada = crearBotonAccion("Foto alterada / edicion");
        btnOcultarFoto = crearBotonAccion("Ocultar foto");
        btnDarAviso = crearBotonAccion("Dar aviso");
        btnSaberComportamiento = crearBotonAccion("Saber comportamiento");

        btnAceptarFoto.setBounds(310, 120, 200, 36);
        btnFotoAlterada.setBounds(310, 166, 200, 36);
        btnOcultarFoto.setBounds(310, 212, 200, 36);
        btnDarAviso.setBounds(310, 258, 200, 36);
        btnSaberComportamiento.setBounds(310, 304, 200, 36);

        add(btnAceptarFoto);
        add(btnFotoAlterada);
        add(btnOcultarFoto);
        add(btnDarAviso);
        add(btnSaberComportamiento);

        // ── tarjeta reportado ───────────────────────────
        JPanel pnlReportado = new JPanel();
        pnlReportado.setBackground(COLOR_TARJETA);
        pnlReportado.setLayout(null);
        pnlReportado.setBounds(560, 90, 260, 340);
        add(pnlReportado);

        JLabel lblTituloFoto = new JLabel("Foto Reportada");
        lblTituloFoto.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTituloFoto.setForeground(COLOR_TEXTO);
        lblTituloFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloFoto.setBounds(0, 10, 260, 20);
        pnlReportado.add(lblTituloFoto);

        lblFotoReportado = new javax.swing.JLabel();
        lblFotoReportado.setBackground(new Color(180, 200, 220));
        lblFotoReportado.setOpaque(true);
        lblFotoReportado.setHorizontalAlignment(SwingConstants.CENTER);
        lblFotoReportado.setBounds(55, 38, 150, 150);
        pnlReportado.add(lblFotoReportado);

        lblNombreReportado = new javax.swing.JLabel("cargando...");
        lblNombreReportado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNombreReportado.setForeground(COLOR_TEXTO);
        lblNombreReportado.setBounds(10, 200, 240, 22);
        pnlReportado.add(lblNombreReportado);

        lblProfesionReportado = new javax.swing.JLabel("");
        lblProfesionReportado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblProfesionReportado.setForeground(COLOR_TEXTO);
        lblProfesionReportado.setBounds(10, 224, 240, 18);
        pnlReportado.add(lblProfesionReportado);

        lblBioReportado = new javax.swing.JLabel("");
        lblBioReportado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblBioReportado.setForeground(new Color(150, 80, 100));
        lblBioReportado.setBounds(10, 244, 240, 40);
        pnlReportado.add(lblBioReportado);

        JLabel lblInfoUsuario = new JLabel("Informacion del Usuario");
        lblInfoUsuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblInfoUsuario.setForeground(COLOR_TEXTO);
        lblInfoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblInfoUsuario.setBounds(0, 296, 260, 20);
        pnlReportado.add(lblInfoUsuario);

        // ── eventos botones ─────────────────────────────
        initEventos();
    }

    private void initEventos()
    {
        btnAceptarFoto.addActionListener(
                e -> procesarAccion("ACEPTAR"));

        btnFotoAlterada.addActionListener(
                e -> procesarAccion("FOTO_ALTERADA"));

        btnOcultarFoto.addActionListener(
                e -> procesarAccion("OCULTAR_FOTO"));

        btnDarAviso.addActionListener(
                e -> procesarAccion("AVISO"));

        btnSaberComportamiento.addActionListener(
                e -> procesarAccion("COMPORTAMIENTO"));
    }

    // carga los datos del reporte desde la capa de negocio
    private void cargarDetalle()
    {
        try
        {
            detalleActual = ControlGestionReportes
                    .getInstancia()
                    .consultarDetalle(idReporte);

            if (detalleActual == null)
            {
                lblTitulo.setText("Reporte no encontrado");
                return;
            }

            // llena datos del reportante
            UsuarioDTO reportante = detalleActual.getReportante();
            if (reportante != null)
            {
                lblNombreReportante.setText(reportante.getNombre());
            }

            // llena datos del reportado
            UsuarioDTO reportado = detalleActual.getReportado();
            if (reportado != null)
            {
                lblNombreReportado.setText(reportado.getNombre());
                lblProfesionReportado.setText(
                        reportado.getEstadoCuenta());
                lblBioReportado.setText(
                        "<html>usuario con "
                        + reportado.getAdvertencias()
                        + " advertencias</html>");
            }

            // tipo de reporte
            lblTipoReporte.setText(
                    "Tipo de reporte: " + detalleActual.getMotivo());
        }
        catch (Exception e)
        {
            logger.severe("error al cargar detalle: " + e.getMessage());
        }
    }

    private void procesarAccion(String accion)
{
    try
    {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "confirmas aplicar la accion: " + accion + "?",
                "confirmar accion",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion != JOptionPane.YES_OPTION)
        {
            return;
        }

        boolean resultado = ControlGestionReportes
                .getInstancia()
                .procesarSancion(idReporte, accion);

        if (resultado)
        {
            Container padre = getParent();
            while (padre != null
                    && !(padre instanceof IContenedorPrincipal))
            {
                padre = padre.getParent();
            }

            if (padre instanceof IContenedorPrincipal)
            {
                ((IContenedorPrincipal) padre).mostrarPanel(
                        new FrmExitoEvaluacion(
                                (IContenedorPrincipal) padre));
            }
        }
        else
        {
            JOptionPane.showMessageDialog(
                    this,
                    "ocurrio un error al procesar la accion",
                    "error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    catch (Exception e)
    {
        logger.severe("error al procesar accion: " + e.getMessage());
    }
}

    private JButton crearBotonAccion(String texto)
    {
        JButton btn = new JButton(texto);
        btn.setBackground(COLOR_BTN_ACCION);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e)
            {
                btn.setBackground(COLOR_BTN_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e)
            {
                btn.setBackground(COLOR_BTN_ACCION);
            }
        });

        return btn;
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

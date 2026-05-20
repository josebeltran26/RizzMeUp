/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package presentacion;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import negocio.dto.ReporteDTO;
import negocio.subsistema.ControlGestionReportes;
import java.util.ArrayList;

/**
 *
 * @author Roger Jr
 */
public class FrmReportarUsuario extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmReportarUsuario.class.getName());
    
    private static final Color COLOR_FONDO = new Color(255, 220, 235);
    private static final Color COLOR_TEXTO = new Color(102, 0, 51);
    private static final Color COLOR_BTN_MOTIVO = new Color(255, 200, 220);
    private static final Color COLOR_BTN_HOVER = new Color(240, 150, 190);

    private final String idUsuarioReportante;
    private final String idUsuarioReportado;

    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblPregunta;
    private javax.swing.JButton btnNoMeGusta;
    private javax.swing.JButton btnFotoInapropiada;
    private javax.swing.JButton btnEstafas;
    private javax.swing.JButton btnSuplantacion;
    private javax.swing.JButton btnCerrar;

    /**
     * Creates new form FrmReportarUsuario
     */
    public FrmReportarUsuario(java.awt.Frame parent,
            String idUsuarioReportante,
            String idUsuarioReportado) 
    {
        super(parent, true);
        this.idUsuarioReportante = idUsuarioReportante;
        this.idUsuarioReportado = idUsuarioReportado;
        initVista();
        initEventos();
    }
    
    private void initVista()
    {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reportar");
        setResizable(false);
        setSize(380, 360);
        setLocationRelativeTo(getParent());
        setUndecorated(true);

        pnlPrincipal = new javax.swing.JPanel();
        pnlPrincipal.setBackground(COLOR_FONDO);
        pnlPrincipal.setBorder(
                BorderFactory.createLineBorder(new Color(200, 150, 170), 1));
        pnlPrincipal.setLayout(
                new org.netbeans.lib.awtextra.AbsoluteLayout());

        // boton cerrar x
        btnCerrar = new javax.swing.JButton("X");
        btnCerrar.setBackground(COLOR_FONDO);
        btnCerrar.setForeground(COLOR_TEXTO);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlPrincipal.add(btnCerrar,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(
                        10, 10, 40, 28));

        // titulo centrado
        lblTitulo = new javax.swing.JLabel("Reportar");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        pnlPrincipal.add(lblTitulo,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(
                        0, 10, 380, 30));

        // pregunta
        lblPregunta = new javax.swing.JLabel(
                "Por que quieres reportar este asunto?");
        lblPregunta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPregunta.setForeground(COLOR_TEXTO);
        lblPregunta.setHorizontalAlignment(SwingConstants.CENTER);
        pnlPrincipal.add(lblPregunta,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(
                        0, 52, 380, 25));

        // botones de motivo
        btnNoMeGusta = crearBotonMotivo("No me gusta");
        btnFotoInapropiada = crearBotonMotivo("Foto inapropiada");
        btnEstafas = crearBotonMotivo("Estafas, fraude o spam");
        btnSuplantacion = crearBotonMotivo("Suplantacion de identidad");

        pnlPrincipal.add(btnNoMeGusta,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(
                        20, 98, 340, 45));
        pnlPrincipal.add(btnFotoInapropiada,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(
                        20, 153, 340, 45));
        pnlPrincipal.add(btnEstafas,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(
                        20, 208, 340, 45));
        pnlPrincipal.add(btnSuplantacion,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(
                        20, 263, 340, 45));

        setContentPane(pnlPrincipal);
        pack();
    }

    private void initEventos()
    {
        btnCerrar.addActionListener(e -> dispose());

        btnNoMeGusta.addActionListener(
                e -> enviarReporte("No me gusta"));

        btnFotoInapropiada.addActionListener(
                e -> enviarReporte("Foto inapropiada"));

        btnEstafas.addActionListener(
                e -> enviarReporte("Estafas, fraude o spam"));

        btnSuplantacion.addActionListener(
                e -> enviarReporte("Suplantacion de identidad"));
    }

    private void enviarReporte(String motivo)
    {
        try
        {
            // verifica si ya existe un reporte pendiente entre estos usuarios
            boolean duplicado = ControlGestionReportes
                    .getInstancia()
                    .validarDuplicado(idUsuarioReportante, idUsuarioReportado);

            dispose();

            Frame padre = (Frame) getParent();

            if (duplicado)
            {
                FrmConfirmacionReporte confirmacion =
                        new FrmConfirmacionReporte(
                                padre,
                                false,
                                "Ya reportaste a este usuario");
                confirmacion.setLocationRelativeTo(padre);
                confirmacion.setVisible(true);
                return;
            }

            // construye el dto y envia el reporte
            ReporteDTO dto = new ReporteDTO();
            dto.setIdUsuarioReportante(idUsuarioReportante);
            dto.setIdUsuarioReportado(idUsuarioReportado);
            dto.setMotivo(motivo);
            dto.setEvidencias(new ArrayList<>());

            boolean creado = ControlGestionReportes
                    .getInstancia()
                    .crearReporte(dto);

            if (creado)
            {
                FrmConfirmacionReporte confirmacion =
                        new FrmConfirmacionReporte(
                                padre,
                                true,
                                "Gracias por reportar esta publicacion");
                confirmacion.setLocationRelativeTo(padre);
                confirmacion.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(
                        padre,
                        "ocurrio un error al enviar el reporte",
                        "error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception e)
        {
            logger.severe("error al enviar reporte: " + e.getMessage());
        }
    }

    private JButton crearBotonMotivo(String texto)
    {
        JButton btn = new JButton(texto + "  >");
        btn.setBackground(COLOR_BTN_MOTIVO);
        btn.setForeground(COLOR_TEXTO);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
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
                btn.setBackground(COLOR_BTN_MOTIVO);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

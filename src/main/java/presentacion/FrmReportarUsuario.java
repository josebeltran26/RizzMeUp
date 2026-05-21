/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package presentacion;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import negocio.dto.ReporteDTO;
import negocio.subsistema.ControlGestionReportes;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Roger Jr
 */
public class FrmReportarUsuario extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(
                    FrmReportarUsuario.class.getName());

    private static final Color COLOR_FONDO = new Color(255, 180, 205);
    private static final Color COLOR_FONDO_HOVER = new Color(255, 153, 192);
    private static final Color COLOR_TEXTO = new Color(45, 18, 32);
    private static final Color COLOR_LINEA = new Color(205, 105, 145);

    private final String idUsuarioReportante;
    private final String idUsuarioReportado;

    private JPanel pnlPrincipal;
    private JLabel lblTitulo;
    private JLabel lblPregunta;
    private JButton btnNoMeGusta;
    private JButton btnFotoInapropiada;
    private JButton btnEstafas;
    private JButton btnSuplantacion;
    private JButton btnCerrar;

    public FrmReportarUsuario(Frame parent,
            String idUsuarioReportante,
            String idUsuarioReportado) {
        super(parent, true);
        this.idUsuarioReportante = idUsuarioReportante;
        this.idUsuarioReportado = idUsuarioReportado;
        initVista();
        initEventos();
    }

    private void initVista() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reportar");
        setResizable(false);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        pnlPrincipal = new RoundedPanel(18);
        pnlPrincipal.setBackground(COLOR_FONDO);
        pnlPrincipal.setLayout(null);
        pnlPrincipal.setBorder(BorderFactory.createEmptyBorder());
        pnlPrincipal.setPreferredSize(new java.awt.Dimension(340, 210));

        btnCerrar = new JButton("X");
        btnCerrar.setBackground(COLOR_FONDO);
        btnCerrar.setForeground(COLOR_TEXTO);
        btnCerrar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.setBounds(12, 8, 38, 32);
        pnlPrincipal.add(btnCerrar);

        lblTitulo = new JLabel("Reportar");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 12, 340, 24);
        pnlPrincipal.add(lblTitulo);

        lblPregunta = new JLabel("Por que quieres reportar este usuario?");
        lblPregunta.setFont(new Font("Segoe UI", Font.BOLD, 9));
        lblPregunta.setForeground(COLOR_TEXTO);
        lblPregunta.setBounds(18, 48, 300, 18);
        pnlPrincipal.add(lblPregunta);

        btnNoMeGusta = crearBotonMotivo("No me gusta");
        btnFotoInapropiada = crearBotonMotivo("Foto inapropiada");
        btnEstafas = crearBotonMotivo("Estafas, fraude o spam");
        btnSuplantacion = crearBotonMotivo("Suplantacion de identidad");

        btnNoMeGusta.setBounds(0, 75, 340, 28);
        btnFotoInapropiada.setBounds(0, 103, 340, 28);
        btnEstafas.setBounds(0, 131, 340, 28);
        btnSuplantacion.setBounds(0, 159, 340, 28);

        pnlPrincipal.add(btnNoMeGusta);
        pnlPrincipal.add(btnFotoInapropiada);
        pnlPrincipal.add(btnEstafas);
        pnlPrincipal.add(btnSuplantacion);

        setContentPane(pnlPrincipal);
        pack();
        setLocationRelativeTo(getParent());
    }

    private void initEventos() {
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

    private void enviarReporte(String motivo) {
        try {
            boolean duplicado = ControlGestionReportes
                    .getInstancia()
                    .validarDuplicado(idUsuarioReportante, idUsuarioReportado);

            dispose();

            Frame padre = (Frame) getParent();

            if (duplicado) {
                FrmConfirmacionReporte confirmacion =
                        new FrmConfirmacionReporte(
                                padre,
                                false,
                                "Ya reportaste a este usuario");
                confirmacion.setLocationRelativeTo(padre);
                confirmacion.setVisible(true);
                return;
            }

            ReporteDTO dto = new ReporteDTO();
            dto.setIdUsuarioReportante(idUsuarioReportante);
            dto.setIdUsuarioReportado(idUsuarioReportado);
            dto.setMotivo(motivo);
            dto.setEvidencias(new ArrayList<>());

            boolean creado = ControlGestionReportes
                    .getInstancia()
                    .crearReporte(dto);

            if (creado) {
                FrmConfirmacionReporte confirmacion =
                        new FrmConfirmacionReporte(
                                padre,
                                true,
                                "Gracias por reportar esta publicacion");
                confirmacion.setLocationRelativeTo(padre);
                confirmacion.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(
                        padre,
                        "Ocurrio un error al enviar el reporte",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            logger.severe("error al enviar reporte: " + e.getMessage());
        }
    }

    private JButton crearBotonMotivo(String texto) {
        JButton btn = new JButton(texto + "        >");
        btn.setBackground(COLOR_FONDO);
        btn.setForeground(COLOR_TEXTO);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        btn.setBorder(BorderFactory.createMatteBorder(
                1, 0, 0, 0, COLOR_LINEA));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_FONDO_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_FONDO);
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

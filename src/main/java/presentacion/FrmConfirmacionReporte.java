/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Roger Jr
 */
public class FrmConfirmacionReporte extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(
                    FrmConfirmacionReporte.class.getName());

    private static final Color COLOR_FONDO = new Color(255, 180, 205);
    private static final Color COLOR_TEXTO = new Color(45, 18, 32);
    private static final Color COLOR_LINEA = new Color(205, 105, 145);
    private static final Color COLOR_BTN = new Color(0, 0, 0);
    private static final Color COLOR_BTN_HOVER = new Color(55, 35, 45);

    private final boolean exitoso;
    private final String mensaje;

    private JPanel pnlPrincipal;
    private JLabel lblIcono;
    private JLabel lblMensaje;
    private JButton btnCerrar;

    public FrmConfirmacionReporte(java.awt.Frame parent,
            boolean exitoso,
            String mensaje) {
        super(parent, true);
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        initVista();
        initEventos();
    }

    private void initVista() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Confirmacion");
        setResizable(false);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        pnlPrincipal = new RoundedPanel(18);
        pnlPrincipal.setBackground(COLOR_FONDO);
        pnlPrincipal.setLayout(null);
        pnlPrincipal.setBorder(BorderFactory.createEmptyBorder());
        pnlPrincipal.setPreferredSize(new java.awt.Dimension(285, 175));

        lblIcono = new JLabel();
        lblIcono.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcono.setForeground(Color.BLACK);

        if (exitoso) {
            lblIcono.setText("✓");
            lblIcono.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            lblIcono.setBounds(127, 22, 31, 31);
        } else {
            lblIcono.setText("!");
            lblIcono.setBorder(new TriangleBorder());
            lblIcono.setBounds(126, 22, 34, 34);
        }

        pnlPrincipal.add(lblIcono);

        lblMensaje = new JLabel(
                "<html><div style='text-align:center;'>"
                + mensaje
                + "</div></html>");
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblMensaje.setForeground(COLOR_TEXTO);
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setBounds(20, 68, 245, 38);
        pnlPrincipal.add(lblMensaje);

        JPanel separador = new JPanel();
        separador.setBackground(COLOR_LINEA);
        separador.setBounds(0, 112, 285, 1);
        pnlPrincipal.add(separador);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(COLOR_BTN);
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 8));
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.setBounds(10, 137, 265, 18);
        pnlPrincipal.add(btnCerrar);

        setContentPane(pnlPrincipal);
        pack();
        setLocationRelativeTo(getParent());
    }

    private void initEventos() {
        btnCerrar.addActionListener(e -> dispose());

        btnCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnCerrar.setBackground(COLOR_BTN_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnCerrar.setBackground(COLOR_BTN);
            }
        });
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

    private static class TriangleBorder extends javax.swing.border.AbstractBorder {

        @Override
        public void paintBorder(java.awt.Component c,
                Graphics g,
                int x,
                int y,
                int width,
                int height) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int[] xs = {
                x + width / 2,
                x + 3,
                x + width - 3
            };

            int[] ys = {
                y + 2,
                y + height - 3,
                y + height - 3
            };

            g2.setColor(Color.BLACK);
            g2.drawPolygon(xs, ys, 3);

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

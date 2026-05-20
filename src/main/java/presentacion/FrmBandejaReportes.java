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
import java.util.List;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

    private static final Color COLOR_FONDO = new Color(255, 235, 245);
    private static final Color COLOR_TEXTO = new Color(102, 0, 51);
    private static final Color COLOR_ITEM = new Color(255, 213, 233);
    private static final Color COLOR_ITEM_HOVER = new Color(240, 180, 210);

    private javax.swing.JPanel pnlLista;
    private javax.swing.JScrollPane scrollLista;
    private javax.swing.JLabel lblSinReportes;

    public FrmBandejaReportes()
    {
        initVista();
        cargarReportes();
    }
    // </editor-fold>


    private void initVista()
    {
        setBackground(COLOR_FONDO);
        setLayout(null);
        setPreferredSize(new Dimension(860, 770));

        // panel interno donde se agregan las filas de reportes
        pnlLista = new javax.swing.JPanel();
        pnlLista.setBackground(COLOR_FONDO);
        pnlLista.setLayout(new BoxLayout(pnlLista,
                BoxLayout.Y_AXIS));

        // scroll para la lista
        scrollLista = new javax.swing.JScrollPane(pnlLista);
        scrollLista.setBorder(BorderFactory.createEmptyBorder());
        scrollLista.setBackground(COLOR_FONDO);
        scrollLista.getViewport().setBackground(COLOR_FONDO);
        scrollLista.setBounds(20, 20, 820, 720);
        add(scrollLista);

        // mensaje cuando no hay reportes
        lblSinReportes = new javax.swing.JLabel(
                "<html><div style='text-align:center;'>"
                + "Por el momento no existen<br>reportes a evaluar"
                + "</div></html>");
        lblSinReportes.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblSinReportes.setForeground(COLOR_TEXTO);
        lblSinReportes.setHorizontalAlignment(SwingConstants.CENTER);
        lblSinReportes.setBounds(0, 300, 860, 80);
        lblSinReportes.setVisible(false);
        add(lblSinReportes);
    }

    // carga los reportes pendientes desde la capa de negocio
    private void cargarReportes()
    {
        try
        {
            List<ReporteDetalleDTO> reportes = ControlGestionReportes
                    .getInstancia()
                    .obtenerPendientes();

            pnlLista.removeAll();

            if (reportes == null || reportes.isEmpty())
            {
                // muestra mensaje de bandeja vacia
                scrollLista.setVisible(false);
                lblSinReportes.setVisible(true);
                revalidate();
                repaint();
                return;
            }

            // genera una fila por cada reporte pendiente
            for (ReporteDetalleDTO reporte : reportes)
            {
                JPanel fila = crearFilaReporte(reporte);
                pnlLista.add(fila);
                // separador visual entre filas
                pnlLista.add(Box.createVerticalStrut(2));
            }

            scrollLista.setVisible(true);
            lblSinReportes.setVisible(false);
            pnlLista.revalidate();
            pnlLista.repaint();
        }
        catch (Exception e)
        {
            logger.severe("error al cargar reportes: " + e.getMessage());
        }
    }

    // construye la fila visual de un reporte en la lista
    private JPanel crearFilaReporte(ReporteDetalleDTO reporte)
    {
        JPanel fila = new JPanel();
        fila.setBackground(COLOR_ITEM);
        fila.setLayout(null);
        fila.setMaximumSize(new Dimension(820, 64));
        fila.setPreferredSize(new Dimension(820, 64));
        fila.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // foto placeholder del reportante
        JLabel lblFoto = new JLabel();
        lblFoto.setBackground(new Color(180, 140, 160));
        lblFoto.setOpaque(true);
        lblFoto.setBounds(10, 10, 44, 44);
        fila.add(lblFoto);

        // titulo del reporte
        String tituloTexto = "Reporte: " + reporte.getMotivo();
        JLabel lblTitulo = new JLabel(tituloTexto);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setBounds(66, 10, 560, 22);
        fila.add(lblTitulo);

        // nombre del reportado si esta disponible
        String nombreReportado = reporte.getReportado() != null
                ? reporte.getReportado().getNombre()
                : "usuario desconocido";
        JLabel lblNombre = new JLabel(nombreReportado);
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblNombre.setForeground(new Color(150, 80, 100));
        lblNombre.setBounds(66, 34, 400, 18);
        fila.add(lblNombre);

        // hora del reporte
        String hora = reporte.getFecha() != null
                ? new java.text.SimpleDateFormat("hh:mm a")
                        .format(reporte.getFecha())
                : "--:--";
        JLabel lblHora = new JLabel(hora);
        lblHora.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblHora.setForeground(new Color(150, 80, 100));
        lblHora.setHorizontalAlignment(SwingConstants.RIGHT);
        lblHora.setBounds(640, 10, 160, 18);
        fila.add(lblHora);

        // efecto hover en la fila
        fila.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e)
            {
                fila.setBackground(COLOR_ITEM_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e)
            {
                fila.setBackground(COLOR_ITEM);
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e)
            {
                abrirDetalle(reporte.getId());
            }
        });

        return fila;
    }

    private void abrirDetalle(String idReporte)
{
    try
    {
        Container padre = getParent();
        while (padre != null && !(padre instanceof IContenedorPrincipal))
        {
            padre = padre.getParent();
        }

        if (padre instanceof IContenedorPrincipal)
        {
            ((IContenedorPrincipal) padre).mostrarPanel(
                    new FrmDetalleReporte(idReporte));
        }
    }
    catch (Exception e)
    {
        logger.severe("error al abrir detalle: " + e.getMessage());
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

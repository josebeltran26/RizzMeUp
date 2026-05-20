/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Roger Jr
 */
public class MainPrueba extends JFrame implements IContenedorPrincipal
{
    private static final java.awt.Color COLOR_FONDO =
            new java.awt.Color(255, 235, 245);

    public MainPrueba()
    {
        setTitle("RizzMeUp - Prueba Rogelio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1030, 770);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(COLOR_FONDO);
        getContentPane().setLayout(new java.awt.BorderLayout());

        // registra este frame como contenedor principal
        CoordinadorAplicacion.getInstancia().setContenedorPrincipal(this);

        // arranca mostrando la bandeja del admin para probar flujo admin
        // cambia a probarFlujoUsuario() si quieres probar el reporte
        probarFlujoAdmin();
    }

    @Override
    public void mostrarPanel(JPanel panel)
    {
        getContentPane().removeAll();
        getContentPane().add(panel, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // ── prueba flujo administrador ──────────────────────
    // muestra la bandeja de reportes directamente
    private void probarFlujoAdmin()
    {
        mostrarPanel(new FrmBandejaReportes());
    }

    // ── prueba flujo usuario ────────────────────────────
    // abre el dialogo de reportar usuario con ids de prueba
    private void probarFlujoUsuario()
    {
        // estos ids deben existir en tu coleccion de mongo
        // si no tienes datos aun mira la seccion de datos de prueba abajo
        String idReportante = "6507f1f77bcf86cd799439011";
        String idReportado  = "6507f1f77bcf86cd799439012";

        // abre el dialogo encima de este frame
        FrmReportarUsuario dialogo = new FrmReportarUsuario(
                this,
                idReportante,
                idReportado);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            new MainPrueba().setVisible(true);
        });
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package presentacion;

/**
 *
 * @author Erik
 */
public class Presentacion_RizzMeUp {

    public static void main(String[] args) {
        // Ejecutar en el hilo de la interfaz grafica
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }
}

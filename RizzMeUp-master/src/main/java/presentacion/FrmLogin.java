package presentacion;

import dto.UsuarioDTO;
import gestionperfil.ControlGestion;
import gestionperfil.IBoundaryGestion;
import infraestructura.ActualizarPersonas;
import infraestructura.IActualizarPersonas;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class FrmLogin extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnIrRegistro;

    public FrmLogin() {
        initComponents();
    }

    private void initComponents() {
        setTitle("RizzMeUp - Iniciar Sesion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 240, 245));

        JLabel lblTitulo = new JLabel("RizzMeUp");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(255, 105, 180));
        lblTitulo.setBounds(110, 50, 200, 50);
        add(lblTitulo);

        JLabel lblCorreo = new JLabel("Correo Electronico:");
        lblCorreo.setBounds(50, 150, 300, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(50, 180, 300, 35);
        add(txtCorreo);

        JLabel lblContra = new JLabel("Contrasena:");
        lblContra.setBounds(50, 230, 300, 25);
        add(lblContra);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(50, 260, 300, 35);
        add(txtContrasena);

        btnIniciarSesion = new JButton("Iniciar Sesion");
        btnIniciarSesion.setBounds(50, 330, 300, 45);
        btnIniciarSesion.setBackground(new Color(255, 105, 180));
        btnIniciarSesion.setForeground(Color.WHITE);
        btnIniciarSesion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIniciarSesion.setFocusPainted(false);
        add(btnIniciarSesion);

        btnIrRegistro = new JButton("¿No tienes cuenta? Registrate aqui");
        btnIrRegistro.setBounds(50, 390, 300, 30);
        btnIrRegistro.setContentAreaFilled(false);
        btnIrRegistro.setBorderPainted(false);
        btnIrRegistro.setForeground(new Color(100, 100, 255));
        add(btnIrRegistro);

        // Eventos
        btnIniciarSesion.addActionListener(e -> iniciarSesion());
        btnIrRegistro.addActionListener(e -> {
            new FrmRegistro().setVisible(true);
            dispose();
        });
    }

    private void iniciarSesion() {
        try {
            IActualizarPersonas infra = new ActualizarPersonas();
            ControlGestion control = new ControlGestion(infra);

            control.setBoundaryGestion(new IBoundaryGestion() {
                @Override public void mostrarPerfil(UsuarioDTO perfil) {}
                @Override public void actualizacionExitosa() {}
                @Override public void eliminacionExitosa() {}
                
                @Override 
                public void registroExitoso(Long idAsignado) {
                    // Obtener el perfil completo y guardarlo en sesion
                    try {
                        UsuarioDTO perfil = infra.obtenerPerfilPorCorreo(txtCorreo.getText());
                        if (perfil != null) {
                            SesionUsuario.getInstancia().iniciarSesion(perfil);
                        }
                    } catch (Exception ex) {
                        System.err.println("[FrmLogin] No se pudo cargar el perfil completo: " + ex.getMessage());
                    }
                    JOptionPane.showMessageDialog(FrmLogin.this, "¡Bienvenido a RizzMeUp!");
                    FrmPrincipal frm = new FrmPrincipal();
                    frm.setVisible(true);
                    dispose();
                }
                
                @Override 
                public void mostrarError(String mensaje) {
                    JOptionPane.showMessageDialog(FrmLogin.this, mensaje, "Error de Login", JOptionPane.ERROR_MESSAGE);
                }
            });

            control.iniciarSesion(txtCorreo.getText(), new String(txtContrasena.getPassword()));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error de conexion: " + ex.getMessage());
        }
    }
}

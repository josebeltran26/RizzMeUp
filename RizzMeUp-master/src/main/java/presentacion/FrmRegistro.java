package presentacion;

import dto.UsuarioDTO;
import gestionperfil.ControlGestion;
import gestionperfil.IBoundaryGestion;
import infraestructura.ActualizarPersonas;
import infraestructura.IActualizarPersonas;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class FrmRegistro extends JFrame {

    private JTextField txtNombre, txtCorreo, txtEdad, txtCiudad;
    private JPasswordField txtContrasena;
    private JComboBox<String> cbxGenero;
    private JButton btnRegistrar, btnIrLogin;

    public FrmRegistro() {
        initComponents();
    }

    private void initComponents() {
        setTitle("RizzMeUp - Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 240, 245));

        JLabel lblTitulo = new JLabel("Crear Cuenta");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(255, 105, 180));
        lblTitulo.setBounds(110, 20, 200, 40);
        add(lblTitulo);

        int y = 80;
        add(crearLabel("Nombre:", 50, y));
        txtNombre = crearTextField(50, y += 25);
        add(txtNombre);

        add(crearLabel("Correo:", 50, y += 45));
        txtCorreo = crearTextField(50, y += 25);
        add(txtCorreo);

        add(crearLabel("Contrasena:", 50, y += 45));
        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(50, y += 25, 300, 35);
        add(txtContrasena);

        add(crearLabel("Edad:", 50, y += 45));
        txtEdad = crearTextField(50, y += 25);
        add(txtEdad);

        add(crearLabel("Ciudad:", 50, y += 45));
        txtCiudad = crearTextField(50, y += 25);
        add(txtCiudad);

        add(crearLabel("Genero:", 50, y += 45));
        cbxGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        cbxGenero.setBounds(50, y += 25, 300, 35);
        add(cbxGenero);

        btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBounds(50, y += 60, 300, 45);
        btnRegistrar.setBackground(new Color(255, 105, 180));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        add(btnRegistrar);

        btnIrLogin = new JButton("Volver al Login");
        btnIrLogin.setBounds(50, y += 50, 300, 30);
        btnIrLogin.setContentAreaFilled(false);
        btnIrLogin.setBorderPainted(false);
        btnIrLogin.setForeground(new Color(100, 100, 255));
        add(btnIrLogin);

        // Eventos
        btnRegistrar.addActionListener(e -> registrarse());
        btnIrLogin.addActionListener(e -> {
            new FrmLogin().setVisible(true);
            dispose();
        });
    }

    private JLabel crearLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(x, y, 300, 20);
        return lbl;
    }

    private JTextField crearTextField(int x, int y) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, 300, 35);
        return txt;
    }

    private void registrarse() {
        try {
            int edad = Integer.parseInt(txtEdad.getText());

            IActualizarPersonas infra = new ActualizarPersonas();
            ControlGestion control = new ControlGestion(infra);

            control.setBoundaryGestion(new IBoundaryGestion() {
                @Override public void mostrarPerfil(UsuarioDTO perfil) {}
                @Override public void actualizacionExitosa() {}
                @Override public void eliminacionExitosa() {}
                
                @Override 
                public void registroExitoso(Long idAsignado) {
                    JOptionPane.showMessageDialog(FrmRegistro.this, "¡Cuenta creada con exito! Inicia sesion ahora.");
                    new FrmLogin().setVisible(true);
                    dispose();
                }
                
                @Override 
                public void mostrarError(String mensaje) {
                    JOptionPane.showMessageDialog(FrmRegistro.this, mensaje, "Error", JOptionPane.WARNING_MESSAGE);
                }
            });

            UsuarioDTO u = new UsuarioDTO();
            u.setNombre(txtNombre.getText());
            u.setCorreo(txtCorreo.getText());
            u.setContrasena(new String(txtContrasena.getPassword()));
            u.setEdad(edad);
            u.setCiudad(txtCiudad.getText());
            u.setGenero(cbxGenero.getSelectedItem().toString());
            u.setActivo(true);

            control.registrarUsuario(u);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un numero.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package presentacion;

import dto.UsuarioDTO;
import dto.Hobbie;
import gestionperfil.IGestionPerfil;
import gestionperfil.GestionPerfil;
import java.util.ArrayList;
import java.util.List;

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.util.Base64;
import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author Erik
 */
public class PnlPerfilUsuario extends javax.swing.JPanel {
    
    private IGestionPerfil negocioPerfil;
    private UsuarioDTO usuarioLogueado;
    private javax.swing.JButton btnGuardar;

    /**
     * Creates new form PnlPerfilUsuario
     */
    public PnlPerfilUsuario() {
        initComponents();
        
        negocioPerfil = new GestionPerfil();
        
        // Configurar combobox de ciudades con valores válidos de RizzMeUp
        cbCiudad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { 
            "Guadalajara", "CDMX", "Monterrey", "Querétaro", "Mérida", "Cancún", "Puebla", "Tijuana" 
        }));
        
        // Modificar posicion y tamaño de avatar y boton de subir foto programaticamente para que se vea premium
        if (getLayout() instanceof org.netbeans.lib.awtextra.AbsoluteLayout) {
            org.netbeans.lib.awtextra.AbsoluteLayout layout = (org.netbeans.lib.awtextra.AbsoluteLayout) getLayout();
            layout.addLayoutComponent(lblfoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 40, 150, 150));
            layout.addLayoutComponent(btnsubirfoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 205, 150, 35));
        } else {
            lblfoto.setBounds(650, 40, 150, 150);
            btnsubirfoto.setBounds(650, 205, 150, 35);
        }
        
        // Agregar accion para subir foto de perfil
        btnsubirfoto.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subirFotoActionPerformed(evt);
            }
        });
        
        // Crear e integrar boton premium para guardar cambios
        btnGuardar = new javax.swing.JButton("GUARDAR CAMBIOS");
        btnGuardar.setBackground(new java.awt.Color(255, 51, 153));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        btnGuardar.setForeground(java.awt.Color.WHITE);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    guardarCambios();
                    javax.swing.JOptionPane.showMessageDialog(PnlPerfilUsuario.this, 
                            "¡Perfil guardado con éxito!", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(PnlPerfilUsuario.this, 
                            "Error al guardar el perfil: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 600, 250, 45));
        
        cargarDatosPerfil();
    }
    
    private void subirFotoActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Selecciona tu Foto de Perfil");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "jpeg", "png"));
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToUpload = fileChooser.getSelectedFile();
            try {
                byte[] fileContent = java.nio.file.Files.readAllBytes(fileToUpload.toPath());
                String base64Image = java.util.Base64.getEncoder().encodeToString(fileContent);
                usuarioLogueado.setFotoPerfilBase64(base64Image);
                actualizarLabelAvatar();
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarLabelAvatar() {
        if (usuarioLogueado != null && usuarioLogueado.getFotoPerfilBase64() != null && !usuarioLogueado.getFotoPerfilBase64().isEmpty()) {
            lblfoto.setIcon(new javax.swing.ImageIcon(crearImagenCircular(usuarioLogueado.getFotoPerfilBase64(), 150)));
            lblfoto.setText("");
        } else {
            lblfoto.setIcon(new javax.swing.ImageIcon(crearAvatarPorDefecto(150, usuarioLogueado != null ? usuarioLogueado.getNombre() : "?")));
            lblfoto.setText("");
        }
    }
    
    private Image crearImagenCircular(String base64, int diametro) {
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            BufferedImage src = ImageIO.read(new ByteArrayInputStream(bytes));
            if (src == null) {
                return crearAvatarPorDefecto(diametro, "?");
            }
            BufferedImage formatted = new BufferedImage(diametro, diametro, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = formatted.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setClip(new Ellipse2D.Float(0, 0, diametro, diametro));
            g2.drawImage(src, 0, 0, diametro, diametro, null);
            g2.dispose();
            return formatted;
        } catch (Exception e) {
            return crearAvatarPorDefecto(diametro, "?");
        }
    }

    private Image crearAvatarPorDefecto(int diametro, String nombre) {
        BufferedImage img = new BufferedImage(diametro, diametro, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 51, 153));
        g2.fill(new Ellipse2D.Float(0, 0, diametro, diametro));
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 48)); // Letra grande para avatar de 150px
        String inicial = nombre != null && !nombre.isEmpty() ? nombre.substring(0, 1).toUpperCase() : "?";
        FontMetrics fm = g2.getFontMetrics();
        int x = (diametro - fm.stringWidth(inicial)) / 2;
        int y = ((diametro - fm.getHeight()) / 2) + fm.getAscent();
        g2.drawString(inicial, x, y);
        g2.dispose();
        return img;
    }
    
    private void cargarDatosPerfil() {
        Long miId = SesionUsuario.getInstancia().getUsuarioId();
        if (miId == null) miId = 1L;
        usuarioLogueado = negocioPerfil.obtenerPerfil(miId);
        
        if (usuarioLogueado != null) {
            tfNombre.setText(usuarioLogueado.getNombre());
            tfEdad.setText(String.valueOf(usuarioLogueado.getEdad()));
            tfProfesion.setText(usuarioLogueado.getProfesion());
            taDesc.setText(usuarioLogueado.getDescripcionPersonal());
            cbCiudad.setSelectedItem(usuarioLogueado.getCiudad());
            actualizarLabelAvatar();
            
            List<Hobbie> misHobbies = usuarioLogueado.getHobbies();
            if (misHobbies != null) {
                cbLectura.setSelected(misHobbies.contains(Hobbie.LEER));
                cbVideojuegos.setSelected(misHobbies.contains(Hobbie.VIDEOJUEGOS));
                cbCine.setSelected(misHobbies.contains(Hobbie.CINE));
                cbMascotas.setSelected(misHobbies.contains(Hobbie.MASCOTAS));
                cbGimnasio.setSelected(misHobbies.contains(Hobbie.GYM));
                cbGastronomia.setSelected(misHobbies.contains(Hobbie.COCINAR));
                cbViajes.setSelected(misHobbies.contains(Hobbie.VIAJAR));
                cbSenderismo.setSelected(misHobbies.contains(Hobbie.SENDERISMO));
                cbMusica.setSelected(misHobbies.contains(Hobbie.MUSICA));
            }
        }
    }
    
    private void guardarCambios() {
        usuarioLogueado.setNombre(tfNombre.getText());
        usuarioLogueado.setEdad(Integer.parseInt(tfEdad.getText()));
        usuarioLogueado.setProfesion(tfProfesion.getText());
        usuarioLogueado.setDescripcionPersonal(taDesc.getText());
        usuarioLogueado.setCiudad(cbCiudad.getSelectedItem().toString());
        
        List<Hobbie> nuevosHobbies = new ArrayList<>();
        if (cbLectura.isSelected()) nuevosHobbies.add(Hobbie.LEER);
        if (cbVideojuegos.isSelected()) nuevosHobbies.add(Hobbie.VIDEOJUEGOS);
        if (cbCine.isSelected()) nuevosHobbies.add(Hobbie.CINE);
        if (cbMascotas.isSelected()) nuevosHobbies.add(Hobbie.MASCOTAS);
        if (cbGimnasio.isSelected()) nuevosHobbies.add(Hobbie.GYM);
        if (cbGastronomia.isSelected()) nuevosHobbies.add(Hobbie.COCINAR);
        if (cbViajes.isSelected()) nuevosHobbies.add(Hobbie.VIAJAR);
        if (cbSenderismo.isSelected()) nuevosHobbies.add(Hobbie.SENDERISMO);
        if (cbMusica.isSelected()) nuevosHobbies.add(Hobbie.MUSICA);
        
        usuarioLogueado.setHobbies(nuevosHobbies);
        
        negocioPerfil.actualizarPerfil(usuarioLogueado);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnsubirfoto = new javax.swing.JButton();
        lblfoto = new javax.swing.JLabel();
        taDescripcion = new javax.swing.JScrollPane();
        taDesc = new javax.swing.JTextArea();
        tfNombre = new javax.swing.JTextField();
        tfEdad = new javax.swing.JTextField();
        tfProfesion = new javax.swing.JTextField();
        cbCiudad = new javax.swing.JComboBox<>();
        cbMusica = new javax.swing.JCheckBox();
        cbGastronomia = new javax.swing.JCheckBox();
        cbCine = new javax.swing.JCheckBox();
        cbGimnasio = new javax.swing.JCheckBox();
        cbViajes = new javax.swing.JCheckBox();
        cbLectura = new javax.swing.JCheckBox();
        cbMascotas = new javax.swing.JCheckBox();
        cbSenderismo = new javax.swing.JCheckBox();
        cbVideojuegos = new javax.swing.JCheckBox();
        cbFiesta = new javax.swing.JCheckBox();
        cbNaturaleza = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(860, 770));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(73, 30, 50));
        jLabel1.setText("CUALES SON TUS INTERESES?");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(73, 30, 50));
        jLabel2.setText("NOMBRE:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(73, 30, 50));
        jLabel3.setText("EDAD:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(73, 30, 50));
        jLabel4.setText("PROFESION:");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(73, 30, 50));
        jLabel5.setText("DESCRIPCION PERSONAL:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(73, 30, 50));
        jLabel6.setText("CIUDAD: ");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, -1, -1));

        btnsubirfoto.setBackground(new java.awt.Color(255, 213, 233));
        btnsubirfoto.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnsubirfoto.setForeground(new java.awt.Color(51, 0, 51));
        btnsubirfoto.setText("SUBIR FOTO");
        btnsubirfoto.setBorderPainted(false);
        add(btnsubirfoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 180, -1, -1));

        lblfoto.setText("jLabel7");
        lblfoto.setPreferredSize(new java.awt.Dimension(300, 300));
        add(lblfoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 80, 70, 70));

        taDesc.setBackground(new java.awt.Color(213, 185, 212));
        taDesc.setColumns(20);
        taDesc.setRows(5);
        taDescripcion.setViewportView(taDesc);

        add(taDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 280, -1));

        tfNombre.setBackground(new java.awt.Color(213, 185, 212));
        add(tfNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 320, 30));

        tfEdad.setBackground(new java.awt.Color(213, 185, 212));
        add(tfEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        tfProfesion.setBackground(new java.awt.Color(213, 185, 212));
        add(tfProfesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 140, -1));

        cbCiudad.setBackground(new java.awt.Color(213, 185, 212));
        cbCiudad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCiudad.addActionListener(this::cbCiudadActionPerformed);
        add(cbCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, -1, -1));

        cbMusica.setForeground(new java.awt.Color(73, 30, 50));
        cbMusica.setText("Musica");
        cbMusica.addActionListener(this::cbMusicaActionPerformed);
        add(cbMusica, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, -1, -1));

        cbGastronomia.setForeground(new java.awt.Color(73, 30, 50));
        cbGastronomia.setText("Gastronomia");
        add(cbGastronomia, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 510, -1, 20));

        cbCine.setForeground(new java.awt.Color(73, 30, 50));
        cbCine.setText("Cine");
        cbCine.addActionListener(this::cbCineActionPerformed);
        add(cbCine, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, -1, -1));

        cbGimnasio.setForeground(new java.awt.Color(73, 30, 50));
        cbGimnasio.setText("Gimnasio");
        add(cbGimnasio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 480, -1, 20));

        cbViajes.setForeground(new java.awt.Color(73, 30, 50));
        cbViajes.setText("Viajes");
        add(cbViajes, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, -1, 20));

        cbLectura.setForeground(new java.awt.Color(73, 30, 50));
        cbLectura.setText("Lectura");
        add(cbLectura, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, -1, 20));

        cbMascotas.setForeground(new java.awt.Color(73, 30, 50));
        cbMascotas.setText("Mascotas");
        add(cbMascotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 450, -1, -1));

        cbSenderismo.setForeground(new java.awt.Color(73, 30, 50));
        cbSenderismo.setText("Senderismo");
        add(cbSenderismo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 540, -1, -1));

        cbVideojuegos.setForeground(new java.awt.Color(73, 30, 50));
        cbVideojuegos.setText("Videojuegos");
        add(cbVideojuegos, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 450, -1, -1));

        cbFiesta.setForeground(new java.awt.Color(73, 30, 50));
        cbFiesta.setText("Fiesta");
        add(cbFiesta, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 480, -1, -1));

        cbNaturaleza.setForeground(new java.awt.Color(73, 30, 50));
        cbNaturaleza.setText("Naturaleza");
        add(cbNaturaleza, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 510, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void cbCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCiudadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCiudadActionPerformed

    private void cbMusicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMusicaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMusicaActionPerformed

    private void cbCineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCineActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnsubirfoto;
    private javax.swing.JCheckBox cbCine;
    private javax.swing.JComboBox<String> cbCiudad;
    private javax.swing.JCheckBox cbFiesta;
    private javax.swing.JCheckBox cbGastronomia;
    private javax.swing.JCheckBox cbGimnasio;
    private javax.swing.JCheckBox cbLectura;
    private javax.swing.JCheckBox cbMascotas;
    private javax.swing.JCheckBox cbMusica;
    private javax.swing.JCheckBox cbNaturaleza;
    private javax.swing.JCheckBox cbSenderismo;
    private javax.swing.JCheckBox cbViajes;
    private javax.swing.JCheckBox cbVideojuegos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblfoto;
    private javax.swing.JTextArea taDesc;
    private javax.swing.JScrollPane taDescripcion;
    private javax.swing.JTextField tfEdad;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfProfesion;
    // End of variables declaration//GEN-END:variables
}

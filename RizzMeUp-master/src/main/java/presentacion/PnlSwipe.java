/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package presentacion;

import dto.UsuarioDTO;
import dto.LikeDTO;
import dto.MatchDTO;
import explorarperfiles.IExplorarPerfiles;
import explorarperfiles.ExplorarPerfiles;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import presentacion.GestorLikes;
import presentacion.GestorMatches;

/**
 *
 * @author Erik
 */
public class PnlSwipe extends javax.swing.JPanel {

    private IExplorarPerfiles negocioExplorar;
    private List<UsuarioDTO> candidatos;
    private int indiceActual = 0;
    private JLabel lblFoto;
    private UsuarioDTO candidatoActual;
    
    private GestorLikes gestorLikes;
    private GestorMatches gestorMatches;
    private JLabel lblRizzBanner;

    /**
     * Creates new form PnlSwipe
     */
    public PnlSwipe() {
        initComponents();
        
        negocioExplorar = new ExplorarPerfiles();
        gestorLikes = new GestorLikes();
        gestorMatches = new GestorMatches();
        
        lblRizzBanner = new JLabel("", SwingConstants.CENTER);
        lblRizzBanner.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblRizzBanner.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(lblRizzBanner, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 350, 25));
        
        lblFoto = new JLabel();
        pnlFotoContenedor.setLayout(new BorderLayout());
        pnlFotoContenedor.add(lblFoto, BorderLayout.CENTER);

        // Escalar dinámicamente el icono del botón de reportar
        try {
            java.net.URL imgUrl = getClass().getResource("/ICONOREPORTAR.png");
            if (imgUrl != null) {
                ImageIcon iconOriginal = new ImageIcon(imgUrl);
                Image imgEscalada = iconOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                abrirReporte.setIcon(new ImageIcon(imgEscalada));
            }
        } catch (Exception e) {
            // Silently ignore or fallback
        }
        
        setComponentZOrder(abrirReporte, 0);

        abrirReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirReporte();
            }
        });
        
        cargarPerfil();
        configurarAccionesBotones();
    }
    
    private void abrirReporte() {
        if (candidatoActual == null) {
            JOptionPane.showMessageDialog(this, "No hay ningún perfil seleccionado para reportar.");
            return;
        }
        java.awt.Window ventanaPadre = javax.swing.SwingUtilities.getWindowAncestor(this);
        Long idUsuarioSesion = SesionUsuario.getInstancia().getUsuarioId();
        if (idUsuarioSesion == null) idUsuarioSesion = 1L;

        // Convertimos los Long a String aquí adentro al crear el diálogo
        presentacion.FrmReportarUsuario dialogo =
                new presentacion.FrmReportarUsuario(
                        (java.awt.Frame) ventanaPadre,
                        String.valueOf(idUsuarioSesion),         // <--- Cambiado a String
                        String.valueOf(candidatoActual.getId())  // <--- Cambiado a String
                );
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }
    
    private void cargarPerfil() {
        Long miId = SesionUsuario.getInstancia().getUsuarioId();
        if (miId == null) miId = 1L;
        candidatos = negocioExplorar.obtenerCandidatos(miId);
        indiceActual = 0;
        mostrarCandidatoActual();
    }
    
    private void mostrarCandidatoActual() {
        if (candidatos == null || candidatos.isEmpty()) {
            candidatoActual = null;
            actualizarBannerRizz();
            mostrarError();
            return;
        }

        if (indiceActual < candidatos.size()) {
            candidatoActual = candidatos.get(indiceActual);
            actualizarBannerRizz();
            
            // Llenar los labels con los datos del DTO
            lblNombreEdad2.setText(candidatoActual.getNombre() + ", " + candidatoActual.getEdad());
            
            if (candidatoActual.getProfesion() != null) {
                lblProfesion.setText(candidatoActual.getProfesion());
            } else {
                lblProfesion.setText("Estudiante");
            }
            
            lblDescripcion.setText(candidatoActual.getDescripcionPersonal());
            
            // Mostrar imagen
            String base64Image = candidatoActual.getFotoPerfilBase64();
            if (base64Image != null && !base64Image.isEmpty()) {
                try {
                    byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                    Image image = ImageIO.read(bis);
                    if (image != null) {
                        Image scaledImage = image.getScaledInstance(pnlFotoContenedor.getWidth(), pnlFotoContenedor.getHeight(), Image.SCALE_SMOOTH);
                        lblFoto.setIcon(new ImageIcon(scaledImage));
                        lblFoto.setText("");
                    }
                } catch (Exception e) {
                    System.out.println("Error decodificando imagen: " + e.getMessage());
                    lblFoto.setIcon(null);
                    lblFoto.setText("Imagen no disponible");
                }
            } else {
                lblFoto.setIcon(null);
                lblFoto.setText("Sin foto");
            }
        } else {
            // Ya no hay candidatos, llegamos al final
            candidatoActual = null;
            actualizarBannerRizz();
            mostrarError();
        }
    }

    private void actualizarBannerRizz() {
        if (lblRizzBanner == null) return;
        if (candidatoActual != null) {
            Long miId = SesionUsuario.getInstancia().getUsuarioId();
            if (miId == null) miId = 1L;
            boolean yaDioRizz = gestorLikes.verificarReciprocidad(miId, candidatoActual.getId());
            if (yaDioRizz) {
                lblRizzBanner.setText("🔥 ¡Este usuario te ha dado Rizz! 🔥");
                lblRizzBanner.setBackground(new Color(255, 51, 153));
                lblRizzBanner.setForeground(Color.WHITE);
                lblRizzBanner.setOpaque(true);
            } else {
                lblRizzBanner.setText("");
                lblRizzBanner.setOpaque(false);
            }
        } else {
            lblRizzBanner.setText("");
            lblRizzBanner.setOpaque(false);
        }
        lblRizzBanner.repaint();
    }

    private void mostrarError() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(new PnlError(), BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private void avanzarSiguienteCandidato() {
        indiceActual++;
        mostrarCandidatoActual();
    }

    private void configurarAccionesBotones() {
        btnRizz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (candidatoActual != null) {
                    Long miId = SesionUsuario.getInstancia().getUsuarioId();
                    if (miId == null) miId = 1L;
                    final Long usuarioId = miId;
                    try {
                        // 1. Registrar like a través del subsistema de Likes
                        boolean esReciproco = gestorLikes.darLike(usuarioId, candidatoActual.getId());
                        
                        // 2. Si hay reciprocidad, intentar generar el Match
                        if (esReciproco) {
                            try {
                                gestorMatches.generarMatch(usuarioId, candidatoActual.getId());
                                
                                // Crear el MatchDTO temporal para la vista de celebración
                                MatchDTO match = new MatchDTO(null, usuarioId, candidatoActual.getId(), LocalDateTime.now(), true);
                                
                                // Obtener perfiles para el FrmMatch
                                UsuarioDTO usuarioLogueado = negocioExplorar.obtenerPerfilPorId(usuarioId);
                                if (usuarioLogueado == null || usuarioLogueado.getNombre() == null) {
                                    usuarioLogueado = new UsuarioDTO();
                                    usuarioLogueado.setId(usuarioId);
                                    usuarioLogueado.setNombre(SesionUsuario.getInstancia().getNombre() != null ? SesionUsuario.getInstancia().getNombre() : "Tú");
                                    usuarioLogueado.setCorreo(SesionUsuario.getInstancia().getCorreo() != null ? SesionUsuario.getInstancia().getCorreo() : "usuario@rizzmeup.com");
                                }
                                
                                // Desplegar pantalla de celebración
                                gestorMatches.desplegarPantallaMatch(match, usuarioLogueado, candidatoActual);
                            } catch (Exception ex) {
                                // Captura la excepción si falla la validación de match (Flujo Alterno del Match)
                                System.out.println("[PnlSwipe] Excepción en flujo alterno de Match: " + ex.getMessage());
                            }
                        }
                    } catch (Exception ex) {
                        // Captura la excepción si falla el registro de like (Flujo Alterno del Like)
                        System.out.println("[PnlSwipe] Excepción en flujo alterno de Like: " + ex.getMessage());
                    }
                    
                    avanzarSiguienteCandidato();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay mas perfiles para interactuar");
                }
            }
        });

        btnSaltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (candidatoActual != null) {
                    Long miId = SesionUsuario.getInstancia().getUsuarioId();
                    if (miId == null) miId = 1L;
                    LikeDTO skip = new LikeDTO(null, miId, candidatoActual.getId(), false, LocalDateTime.now());
                    gestorLikes.registrarLike(skip);
                    avanzarSiguienteCandidato();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay mas perfiles para interactuar");
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlInfoContenedor = new javax.swing.JPanel();
        lblNombreEdad = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        lblNombreEdad2 = new javax.swing.JLabel();
        lblProfesion = new javax.swing.JLabel();
        btnSaltar = new javax.swing.JButton();
        btnRizz = new javax.swing.JButton();
        contenedorEtiquetas = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnlFotoContenedor = new javax.swing.JPanel();
        abrirReporte = new javax.swing.JButton();

        setBackground(new java.awt.Color(228, 114, 159));
        setPreferredSize(new java.awt.Dimension(860, 770));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlInfoContenedor.setBackground(new java.awt.Color(255, 213, 233));

        lblNombreEdad.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNombreEdad.setForeground(new java.awt.Color(47, 21, 37));

        lblDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDescripcion.setForeground(new java.awt.Color(47, 21, 37));
        lblDescripcion.setText("Descripcion");

        lblNombreEdad2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNombreEdad2.setForeground(new java.awt.Color(47, 21, 37));
        lblNombreEdad2.setText("Nombre, Edad");

        lblProfesion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblProfesion.setForeground(new java.awt.Color(47, 21, 37));
        lblProfesion.setText("Profesion");

        btnSaltar.setBackground(new java.awt.Color(255, 169, 217));
        btnSaltar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSaltar.setText("SALTAR");
        btnSaltar.setBorderPainted(false);

        btnRizz.setBackground(new java.awt.Color(255, 51, 153));
        btnRizz.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnRizz.setText("RIZZ ME UP!");
        btnRizz.setBorderPainted(false);

        contenedorEtiquetas.setBackground(new java.awt.Color(238, 197, 217));

        jLabel1.setBackground(new java.awt.Color(55, 28, 45));
        jLabel1.setForeground(new java.awt.Color(55, 28, 45));
        jLabel1.setText("ETIQUETA 1");
        contenedorEtiquetas.add(jLabel1);

        jLabel2.setForeground(new java.awt.Color(55, 28, 45));
        jLabel2.setText("ETIQUETA 2");
        contenedorEtiquetas.add(jLabel2);

        jLabel3.setForeground(new java.awt.Color(55, 28, 45));
        jLabel3.setText("ETIQUETA 3");
        contenedorEtiquetas.add(jLabel3);

        javax.swing.GroupLayout pnlInfoContenedorLayout = new javax.swing.GroupLayout(pnlInfoContenedor);
        pnlInfoContenedor.setLayout(pnlInfoContenedorLayout);
        pnlInfoContenedorLayout.setHorizontalGroup(
            pnlInfoContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoContenedorLayout.createSequentialGroup()
                .addGroup(pnlInfoContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlInfoContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlInfoContenedorLayout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addGroup(pnlInfoContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblNombreEdad2)
                                .addGroup(pnlInfoContenedorLayout.createSequentialGroup()
                                    .addComponent(lblNombreEdad)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(contenedorEtiquetas, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(pnlInfoContenedorLayout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addGroup(pnlInfoContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblProfesion)
                                .addComponent(lblDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlInfoContenedorLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnSaltar)
                        .addGap(41, 41, 41)
                        .addComponent(btnRizz)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        pnlInfoContenedorLayout.setVerticalGroup(
            pnlInfoContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoContenedorLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblNombreEdad2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProfesion)
                .addGroup(pnlInfoContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoContenedorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(contenedorEtiquetas, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                    .addGroup(pnlInfoContenedorLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(lblNombreEdad)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRizz)
                    .addComponent(btnSaltar))
                .addGap(16, 16, 16))
        );

        add(pnlInfoContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, 350, 230));

        pnlFotoContenedor.setBackground(new java.awt.Color(255, 255, 255));
        pnlFotoContenedor.setPreferredSize(new java.awt.Dimension(350, 350));

        javax.swing.GroupLayout pnlFotoContenedorLayout = new javax.swing.GroupLayout(pnlFotoContenedor);
        pnlFotoContenedor.setLayout(pnlFotoContenedorLayout);
        pnlFotoContenedorLayout.setHorizontalGroup(
            pnlFotoContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        pnlFotoContenedorLayout.setVerticalGroup(
            pnlFotoContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );

        add(pnlFotoContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 350, -1));

        abrirReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOREPORTAR.png"))); // NOI18N
        abrirReporte.setBorderPainted(false);
        abrirReporte.setContentAreaFilled(false);
        abrirReporte.setFocusPainted(false);
        abrirReporte.setOpaque(false);
        abrirReporte.setToolTipText("Reportar perfil o foto inapropiada");
        add(abrirReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 50, 30, 30));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abrirReporte;
    private javax.swing.JButton btnRizz;
    private javax.swing.JButton btnSaltar;
    private javax.swing.JPanel contenedorEtiquetas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblNombreEdad;
    private javax.swing.JLabel lblNombreEdad2;
    private javax.swing.JLabel lblProfesion;
    private javax.swing.JPanel pnlFotoContenedor;
    private javax.swing.JPanel pnlInfoContenedor;
    // End of variables declaration//GEN-END:variables
}

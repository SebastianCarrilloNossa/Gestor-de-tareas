/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;

import Vistas.VentanaPrincipal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
        
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(500,550);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblContrasena = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtContrasena = new javax.swing.JPasswordField();
        btnIngresar = new javax.swing.JButton();
        btnRegistrarUsuario = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 51, 51));
        jPanel1.setMinimumSize(new java.awt.Dimension(800, 400));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Tahoma", 3, 24)); 
        lblTitulo.setForeground(new java.awt.Color(255, 255, 204));
        lblTitulo.setText("GESTOR DE TAREAS ");
        jPanel1.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 250, 110));

        lblUsuario.setFont(new java.awt.Font("Tahoma", 3, 12)); 
        lblUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuario.setText("Usuario:");
        jPanel1.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 60, 20));

        lblContrasena.setFont(new java.awt.Font("Tahoma", 3, 12)); 
        lblContrasena.setForeground(new java.awt.Color(255, 255, 204));
        lblContrasena.setText("Contraseña:");
        jPanel1.add(lblContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, -1, -1));

        jPanel1.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, 170, 30));

        jPanel1.add(txtContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 170, 30));

        btnIngresar.setBackground(new java.awt.Color(255, 153, 153));
        btnIngresar.setForeground(new java.awt.Color(255, 0, 0));
        btnIngresar.setText("INGRESAR");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, 110, 50));

        btnRegistrarUsuario.setText("Registrarse");
        btnRegistrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarUsuarioActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistrarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 420, 100, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        
        String usuarioIngresado = txtUsuario.getText().trim();
        String contrasenaIngresada = new String(txtContrasena.getPassword()).trim();

        if (usuarioIngresado.isEmpty() || contrasenaIngresada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Complete todos los campos");
            return;
        }

        boolean acceso = false;

        File carpeta = new File("C:\\Users\\HP\\OneDrive\\Documents\\NetBeansProjects\\GESTOR_DE_TAREAS\\USUARIOS_txt\\");
        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (archivos == null || archivos.length == 0) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay usuarios registrados.");
            return;
        }

        for (File archivo : archivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

                String usuario = "";
                String contrasena = "";
                String linea;

                while ((linea = br.readLine()) != null) {
                    if (linea.startsWith("Usuario:")) {
                        usuario = linea.substring(8).trim();
                    }
                    if (linea.startsWith("Contrasena:")) {
                        contrasena = linea.substring(11).trim();
                    }
                }

                if (usuario.equals(usuarioIngresado) && contrasena.equals(contrasenaIngresada)) {
                    acceso = true;
                    break;
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "❌ Error al leer archivo: " + archivo.getName());
            }
        }

        if (acceso) {
            JOptionPane.showMessageDialog(this, "✅ Inicio de sesión exitoso");

            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
            this.dispose();

        } else {
            JOptionPane.showMessageDialog(this, "❌ Usuario o contraseña incorrectos");
        }
    }                                          

    private void btnRegistrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        CrearUsuario crearUsuario = new CrearUsuario();
        crearUsuario.setVisible(true);
    }                                                   

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnRegistrarUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration
}

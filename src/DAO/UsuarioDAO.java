/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author HP
 */

import Modelos.Usuario;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    private static final String DIRECTORIO_USUARIOS = 
            "C:\\Users\\HP\\OneDrive\\Documents\\NetBeansProjects\\GESTOR_DE_TAREAS\\USUARIOS_txt\\";

    // ✅ Constructor: Crea la carpeta si no existe
    public UsuarioDAO() {
        File carpeta = new File(DIRECTORIO_USUARIOS);
        if (!carpeta.exists()) {
            carpeta.mkdirs(); 
        }
    }

    // ✅ Registrar un nuevo usuario (crea un archivo por usuario)
    public boolean registrarUsuario(Usuario usuario) {
        String nombreArchivo = DIRECTORIO_USUARIOS + usuario.getNombre().replace(" ", "_") + ".txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {

            bw.write("=== DATOS DEL USUARIO ===\n");
            bw.write("Usuario: " + usuario.getNombre() + "\n");
            bw.write("Contrasena: " + usuario.getContrasena() + "\n");

            JOptionPane.showMessageDialog(null,
                    "✅ Usuario registrado correctamente:\n" + nombreArchivo,
                    "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);

            return true;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "❌ Error al registrar usuario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ✅ Validar credenciales de login
    public boolean validarUsuario(String user, String pass) {
        File carpeta = new File(DIRECTORIO_USUARIOS);

        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (archivos == null) return false;

        for (File archivo : archivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                String usuarioFile = "", contraFile = "";

                while ((linea = br.readLine()) != null) {
                    if (linea.startsWith("Usuario:")) usuarioFile = linea.substring(8).trim();
                    if (linea.startsWith("Contrasena:")) contraFile = linea.substring(11).trim();
                }

                if (usuarioFile.equals(user) && contraFile.equals(pass)) {
                    return true;
                }

            } catch (Exception e) {
                System.out.println("⚠️ Error al leer: " + archivo.getName());
            }
        }
        return false;
    }

    // ✅ Cargar lista completa de usuarios (opcional para administración)
    public List<Usuario> cargarUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        File carpeta = new File(DIRECTORIO_USUARIOS);

        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".txt"));
        if (archivos == null) return listaUsuarios;

        for (File archivo : archivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String user = "", pass = "";
                String linea;

                while ((linea = br.readLine()) != null) {
                    if (linea.startsWith("Usuario:")) user = linea.substring(8).trim();
                    else if (linea.startsWith("Contrasena:")) pass = linea.substring(11).trim();
                }

                listaUsuarios.add(new Usuario(user, pass));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listaUsuarios;
    }
    
 // ✅ Verifica si ya existe un archivo con el nombre del usuario
public boolean usuarioExiste(String nombre) {
    String nombreArchivo = DIRECTORIO_USUARIOS + nombre.replace(" ", "_") + ".txt";
    File archivoUsuario = new File(nombreArchivo);
    return archivoUsuario.exists();
}


}

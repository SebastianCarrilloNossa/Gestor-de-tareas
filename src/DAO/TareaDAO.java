/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
/**
 *
 * @author HP
 */

import Modelos.Tarea;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.JOptionPane;

public class TareaDAO {

    private static final String DIRECTORIO_TAREAS = "C:\\Users\\Andre\\OneDrive\\Documents\\NetBeansProjects\\Gestor-de-tareas\\TAREAS_txt\\";

    // ✅ Constructor: Crea la carpeta si no existe
    public TareaDAO() {
        File carpeta = new File(DIRECTORIO_TAREAS);
        if (!carpeta.exists()) {
            carpeta.mkdirs(); 
        }
    }
    
    // METODO Guarda una tarea en un archivo TXT individual
    public boolean registrarTarea(Tarea tarea) {
        String nombreArchivo = DIRECTORIO_TAREAS + "tarea_" + tarea.getNombre().replace(" ", "_") + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {

            writer.write("=== DATOS DE LA TAREA ===\n");
            writer.write("ID: " + tarea.getId() + "\n");
            writer.write("Nombre: " + tarea.getNombre() + "\n");
            writer.write("Asignatura: " + tarea.getAsignatura() + "\n");
            writer.write("Fecha de inicio: " + tarea.getFechaInicio() + "\n");
            writer.write("Fecha de entrega: " + tarea.getFechaEntrega() + "\n");
            writer.write("Prioridad: " + tarea.getPrioridad() + "\n");

            JOptionPane.showMessageDialog(null,
                    "✅ Tarea registrada correctamente:\n" + nombreArchivo,
                    "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);

            return true;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "❌ Error al registrar tarea: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // METODO Carga todas las tareas guardadas en los archivos TXT
    public List<Tarea> cargarTareasDesdeArchivos() {
        List<Tarea> listaTareas = new ArrayList<>();
        File carpeta = new File(DIRECTORIO_TAREAS);

        if (!carpeta.exists() || !carpeta.isDirectory()) {
            JOptionPane.showMessageDialog(null, "⚠️ No se encontró la carpeta de tareas.");
            return listaTareas;
        }

        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (archivos == null || archivos.length == 0) {
            JOptionPane.showMessageDialog(null, "No hay archivos de tareas en la carpeta.");
            return listaTareas;
        }

        for (File archivo : archivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                String nombre = "", asignatura = "", prioridad = "";
                LocalDate fechaInicio = null, fechaEntrega = null;
                int id = 0;

                while ((linea = br.readLine()) != null) {
                    if (linea.startsWith("ID:")) id = Integer.parseInt(linea.substring(3).trim());
                    else if (linea.startsWith("Nombre:")) nombre = linea.substring(7).trim();
                    else if (linea.startsWith("Asignatura:")) asignatura = linea.substring(11).trim();
                    else if (linea.startsWith("Fecha de inicio:")) fechaInicio = LocalDate.parse(linea.substring(16).trim());
                    else if (linea.startsWith("Fecha de entrega:")) fechaEntrega = LocalDate.parse(linea.substring(17).trim());
                    else if (linea.startsWith("Prioridad:")) prioridad = linea.substring(10).trim();
                }

                // 🔧 Crea la tarea usando el constructor original
                Tarea tarea = new Tarea(nombre, asignatura, fechaInicio, fechaEntrega);
                tarea.actualizarPrioridad(); // recalcula prioridad
                listaTareas.add(tarea);

            } catch (Exception e) {
                System.out.println("Error al leer archivo: " + archivo.getName() + " -> " + e.getMessage());
            }
        }
 // 🔹 Ordenar las tareas antes de devolverlas
        ordenarTareasPorFecha(listaTareas);

        return listaTareas;
    }
     // 🔹 Método para ordenar tareas según tu lógica
    private void ordenarTareasPorFecha(List<Tarea> listaTareas) {
        listaTareas.sort((t1, t2) -> {
            boolean t1Caducada = t1.getPrioridad().equalsIgnoreCase("Caducada");
            boolean t2Caducada = t2.getPrioridad().equalsIgnoreCase("Caducada");

            // 1️⃣ Si una está caducada y la otra no
            if (t1Caducada && !t2Caducada) return 1;  // t1 va después
            if (!t1Caducada && t2Caducada) return -1; // t2 va después

            // 2️⃣ Ambas no son caducadas → ordenar por fecha de entrega
            return t1.getFechaEntrega().compareTo(t2.getFechaEntrega());
        });
    }
}



package Logica;

import javax.swing.JOptionPane;

public class UI {

    // Sustituye a System.out.println
    public static void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    // Sustituye a System.out.println("Error...")
    public static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Sustituye a sc.nextLine()
    public static String pedirDato(String mensaje) {
        String input = JOptionPane.showInputDialog(null, mensaje);
        // Manejo del botón "Cancelar" (devuelve null)
        if (input == null) {
            return null; // O podrías lanzar una excepción para cancelar la operación
        }
        return input.trim();
    }

    // Sustituye a tu menú numérico
    public static int pedirOpcionMenu(String titulo, String[] opciones) {
        // Esto muestra un menú con botones bonitos en lugar de pedir un número
        int seleccion = JOptionPane.showOptionDialog(
                null,
                titulo,
                "Seleccione una opción",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0] // Opción por defecto
        );
        // showOptionDialog devuelve el índice (0, 1, 2...)
        // Tu switch usa (1, 2, 3...), así que sumamos 1
        return seleccion + 1;
    }
}
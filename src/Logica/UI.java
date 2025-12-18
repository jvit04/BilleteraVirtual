package Logica;

import javax.swing.JOptionPane;


    public class UI {

        public static void mostrarMensaje(String mensaje) {
            JOptionPane.showMessageDialog(null, mensaje);
        }

        public static void mostrarError(String mensaje) {
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        }

        public static String pedirDato(String mensaje) {
            return JOptionPane.showInputDialog(null, mensaje);
        }

        public static int pedirOpcionMenu(String titulo, String[] opciones) {
            Object seleccion = JOptionPane.showInputDialog(
                    null,
                    titulo,
                    "Billetera Virtual", // Título de la ventana
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (seleccion == null) {
                return 0;
            }

            for (int i = 0; i < opciones.length; i++) {
                if (opciones[i].equals(seleccion)) {
                    return i + 1; // Retornamos i+1 porque tu switch usa 1, 2, 3...
                }
            }
            return 0;
        }
    }

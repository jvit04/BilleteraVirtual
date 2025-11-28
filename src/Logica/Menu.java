package Logica;

import java.util.Scanner;
import Logica.Excepciones.OpcionMenuNoValidoException;

public abstract class Menu {
    protected static final Scanner sc = new Scanner(System.in);

    /**
     * Método genérico para mostrar cualquier menú basado en un array.
     * @param titulo El título del menú
     * @param OPCIONES El array de Strings con las opciones
     */
    protected static void mostrarOpciones(String titulo, String[] OPCIONES) {
        System.out.println("\n--- " + titulo + " ---");
        for (int i = 0; i < OPCIONES.length; i++) {
            // Imprime "1. Opción", "2. Opción", etc.
            System.out.println((i + 1) + ". " + OPCIONES[i]);
        }
    }

    /**
     * Pide una opción y valida que esté entre 1 y el tamaño del array.
     * @param cantidadOpciones El número máximo de opciones (array.length)
     * @return La opción válida elegida por el usuario
     */
    protected static int elegirOpcion(int cantidadOpciones) {
        System.out.print("Escoge una opción: ");
        try {
            // Leemos texto para evitar errores de buffer
            int opcion = Integer.parseInt(sc.nextLine());

            // Validar usando el tamaño del array
        Validador.validarOpcion(opcion,cantidadOpciones);
            return opcion;

        } catch (NumberFormatException e) {
            System.out.println("Error: Debes ingresar un número.");
            return -1;
        } catch (OpcionMenuNoValidoException e) {
            System.out.println("Error: " + e.getMessage());
            return -1;
        }
    }
}
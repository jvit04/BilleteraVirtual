package Logica;

public abstract class Menu {

    // Eliminamos Scanner y excepciones de consola.
    // Esta clase ahora sirve para delegar la construcción visual a la UI.

    /**
     * Método protegido que usan los hijos para lanzar su menú.
     * @param titulo El título de la ventana.
     * @param opciones El arreglo de strings con las opciones.
     * @return El índice de la opción seleccionada (1, 2, 3...) ajustado para tu switch.
     */
    protected static int mostrarYSeleccionar(String titulo, String[] opciones) {
        // Delegamos todo el trabajo sucio a la clase UI
        int opcion = UI.pedirOpcionMenu(titulo, opciones);

        // Si el usuario cierra la ventana (0), devolvemos una señal de salida
        // En tus menús, la última opción suele ser "Salir" o "Volver".
        if (opcion == 0) {
            return opciones.length; // Retorna la última opción (Salir/Volver)
        }
        return opcion;
    }
}
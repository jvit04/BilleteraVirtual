package Logica;

public class MenuUsuario extends Menu {

    // Tu array de opciones
    private static final String[] OPCIONES = {
            "Depósito",
            "Retiro",
            "Pago de Servicios",
            "Transferencia"
    };

    public static void mostrar() {
        // Usamos la lógica de la clase padre
        mostrarOpciones("Menú de Transacciones", OPCIONES);
    }

    public static int pedirOpcion() {
        // Le pasamos la longitud de NUESTRO array para la validación
        return elegirOpcion(OPCIONES.length);
    }
}
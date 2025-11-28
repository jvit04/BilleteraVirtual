package Logica;

public class MenuAdministrador extends Menu {
    private static final String[] OPCIONES = {
            "Consultar en repositorio de usuarios",       // Opción 1
            "Consultar en repositorio de transacciones",  // Opción 2
            "Ver transacciones totales",                  // Opción 3
            "Cargar desde archivo",                       // Opción 4
            "Volver al menú principal"                    // Opción 5 (Necesaria para salir)
    };

    public static void mostrar() {
        mostrarOpciones("Menú Administrador", OPCIONES);
    }

    public static int pedirOpcion() {
        return elegirOpcion(OPCIONES.length);
    }
}
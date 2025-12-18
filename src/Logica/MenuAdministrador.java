package Logica;

public class MenuAdministrador extends Menu {

    // Tus nuevas opciones
    private static final String[] OPCIONES = {
            "Ver todos los usuarios",                    // Opción 1
            "Consulta de usuario por alias",             // Opción 2
            "Buscar transacción por ID",                 // Opción 3
            "Ver historial global de transacciones",     // Opción 4
            "Cargar usuarios desde archivo",             // Opción 5
            "Cargar transacciones desde archivo",        // Opción 6
            "Volver al menú principal"                   // Opción 7
    };

    public static int mostrar() {
        return mostrarYSeleccionar("Menú Administrador", OPCIONES);
    }
}
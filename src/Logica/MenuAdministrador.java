package Logica;

public class MenuAdministrador extends Menu {

    // Tus nuevas opciones
    private static final String[] OPCIONES = {
            "Ver todos los usuarios",                    // Opción 1
            "Consulta de usuario por alias",             // Opción 2 (NUEVA)
            "Buscar transacción por ID",                 // Opción 3
            "Ver historial global de transacciones",     // Opción 4
            "Cargar desde archivo",                      // Opción 5
            "Volver al menú principal"                   // Opción 6
    };

    public static int mostrar() {
        return mostrarYSeleccionar("Menú Administrador", OPCIONES);
    }
}
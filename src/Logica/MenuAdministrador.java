package Logica;

public class MenuAdministrador extends Menu {

    private static final String[] OPCIONES = {
            //Los roles estrictamente necesarios para un administrador serían la carga masiva
            // de tanto usuarios como transacciones.
            "Agregar en repositorio de usuarios",
            "Agregar en repositorio transacciones",
            //función de administrador adicional, no es necesario, pero sí recomendada, permite control
            //sobre todas las transacciones realizadas
            "Ver transacciones totales",
            "Volver al menú principal"
    };

    public static void mostrar() {
        mostrarOpciones("Menú Administrador", OPCIONES);
    }

    public static int pedirOpcion() {
        return elegirOpcion(OPCIONES.length);
    }
}
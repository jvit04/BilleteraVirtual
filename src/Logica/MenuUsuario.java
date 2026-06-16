package Logica;

public class MenuUsuario extends Menu {

    private static final String[] OPCIONES = {
            "Depósito",
            "Retiro",
            "Pago de Servicios",
            "Transferencia",
            "Volver"
    };

    public static int mostrar() {
        // Llamamos al método padre con NUESTROS datos
        return mostrarYSeleccionar("Menú de Transacciones", OPCIONES);
    }
}
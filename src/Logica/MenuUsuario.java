package Logica;

public class MenuUsuario extends Menu {

    private static final String[] OPCIONES = {
            "Depósito",
            "Retiro",
            "Pago de Servicios",
            "Transferencia",
            "Volver" // Agregué "Volver" explícitamente para que coincida con la lógica
    };

    public static int mostrar() {
        // Llamamos al método padre con NUESTROS datos
        return mostrarYSeleccionar("Menú de Transacciones", OPCIONES);
    }
}
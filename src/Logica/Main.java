package Logica;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //datos de prueba

        // creando usuarios
        Usuario juan = new Usuario("0912345678", "Juan Pérez", "juancho152");
        Usuario maria = new Usuario("0923456789", "María García", "maryGC");

        try {
            // 1. depositos (Juan=200, Maria=300)
            System.out.println("--- 1. depositos ---");
            Deposito deposito1 = new Deposito(200, juan);
            Deposito deposito2 = new Deposito(300, maria);
            deposito1.getInfoTransaccion();
            deposito2.getInfoTransaccion();

            // 2. transferencia (Maria transfiere 200 a Juan)
            // Maria queda con 100 | Juan queda con 400
            System.out.println("\n--- 2. transferencias ---");
            Transferencia transferencia1 = new Transferencia(200, maria, juan);
            transferencia1.getInfoTransaccion();
            System.out.println("✅ Transferencia exitosa.\n");

            // 3. retiro (Juan retira 50)
            System.out.println("\n--- 3. retiro de fondos---");
            Retiro retiroJuan = new Retiro(juan, 50);
            retiroJuan.getInfoTransaccion();
            System.out.println("✅ Retiro realizado con éxito.\n");

            // 4. pagos de servicios (Maria paga 25.50)
            System.out.println("--- 4. pagos de servicio ---");
            PagoServicio pagoLuz = new PagoServicio(25.50, maria, "CNEL", "Electricidad");
            pagoLuz.getInfoTransaccion();
            System.out.println("✅ Pago de servicio realizado.\n");

        } catch (Exception e) {
            System.out.println("\n❌ ERROR EN LA TRANSACCIÓN: " + e.getMessage());
        }

        // --- mostramos los saldos finales
        System.out.println("\n--- Resultados finales: ---");
        System.out.print("Juan:");
        juan.getBilletera().infoSaldo();

        System.out.print("\nMaría: ");
        maria.getBilletera().infoSaldo();
    }
}
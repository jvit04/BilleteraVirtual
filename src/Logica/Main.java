package Logica;

import Logica.Excepciones.OpcionMenuNoValidoException;
import Logica.Excepciones.SaldoInsuficienteException;

import java.util.Scanner;

public class Main {

    // Escaner local para entradas de datos (Nombres, montos, etc.)
    // Nota: Para elegir opciones de menú, usamos el Scanner interno de la clase Menu
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        boolean activo = true;

        // Usuario base para pruebas
       // Usuario usuarioActual = new Usuario("9999999999", "Usuario Prueba", "Tester");

        System.out.println("Iniciando sistema...");


        while (activo) {
            try {
                // 1. Mostrar el menú principal
                mostrarMenuPrincipal();

                // 2. Pedir opción (Validamos que sea entre 1 y 5)
                // Como Main está en el paquete Logica, puede acceder al método protected de Menu
                int opcion = Menu.elegirOpcion(5);

                switch (opcion) {
                    case 1: // Registro de usuario
                        System.out.println("\n--- Registro de Nuevo Usuario ---");

                        System.out.print("Ingrese Cédula: ");
                        String cedula = sc.next();
                        sc.nextLine(); // Consumir resto de línea

                        System.out.print("Ingrese Nombre Completo: ");
                        String nombre = sc.nextLine();

                        System.out.print("Ingrese Alias: ");
                        String alias = sc.nextLine();

                        usuarioActual = new Usuario(cedula, nombre, alias);
                        System.out.println("¡Usuario " + usuarioActual.getNombre() + " registrado con éxito!");
                        break;

                    case 2: // Consultas de usuario
                        System.out.println("\n--- Consulta de Saldo ---");
                        System.out.println("Usuario: " + usuarioActual.getNombre());
                        usuarioActual.getBilletera().infoSaldo();
                        break;

                    case 3: // Realizar transacción
                        // Ahora delegamos la lógica de MOSTRAR el menú a la clase MenuUsuario
                        realizarTransaccionInteractiva(usuarioActual);
                        break;

                    case 4: // Menú Administrador
                        manejarMenuAdministrador();
                        break;

                    case 5: // Salir
                        System.out.println("Cerrando sesión... ¡Hasta luego!");
                        activo = false;
                        break;

                    case -1:
                        // Error de input controlado en Menu.java
                        break;
                }

            } catch (OpcionMenuNoValidoException e) {
                System.out.println("\n[!] Error de Menú: " + e.getMessage());
            } catch (SaldoInsuficienteException e) {
                System.out.println("\n[!] Error de Fondos: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n[!] Error inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Registro de usuario");
        System.out.println("2. Consultas de usuario");
        System.out.println("3. Realizar transacción");
        System.out.println("4. Administrador (Repositorios)");
        System.out.println("5. Salir");
    }

    private static void realizarTransaccionInteractiva(Usuario usuario) {
        // 1. Mostrar opciones usando el array de MenuUsuario
        MenuUsuario.mostrar();

        // 2. Pedir la opción validada automáticamente por MenuUsuario
        int tipo = MenuUsuario.pedirOpcion();

        if (tipo == -1) return; // Si hubo error en la selección, salimos

        double monto = 0;

        // Pedimos el monto para todas las transacciones (1, 2, 3, 4)
        // Nota: Si agregas una opción "Volver" en el futuro, ajusta este if
        System.out.print("Ingrese el monto a operar: $");
        try {
            monto = Double.parseDouble(sc.nextLine()); // Usar parseDouble es más seguro para evitar bugs de buffer
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido. Cancelando operación.");
            return;
        }

        switch (tipo) {
            case 1: // Depósito
                Deposito deposito = new Deposito(monto, usuario);
                deposito.getInfoTransaccion();
                break;

            case 2: // Retiro
                Retiro retiro = new Retiro(usuario, monto);
                retiro.getInfoTransaccion();
                break;

            case 3: // Pago Servicio
                System.out.print("Ingrese nombre de la empresa (ej. Luz, Agua): ");
                String empresa = sc.nextLine();
                System.out.print("Ingrese tipo de servicio: ");
                String servicio = sc.nextLine();

                PagoServicio pago = new PagoServicio(monto, usuario, empresa, servicio);
                pago.getInfoTransaccion();
                break;

            case 4: // Transferencia
                Usuario destino = new Usuario("0000000000", "Usuario Destino Genérico");
                Transferencia transf = new Transferencia(monto, usuario, destino);
                transf.getInfoTransaccion();
                break;
        }
    }

    private static void manejarMenuAdministrador() {
        MenuAdministrador.mostrar();
        int opAdmin = MenuAdministrador.pedirOpcion();

        switch (opAdmin) {
            case 1:
                System.out.println("Consultando repositorio de usuarios... (Pendiente)");
                break;
            case 2:
                System.out.println("Consultando transacciones... (Pendiente)");
                break;
            case 3:
                System.out.println("Realizando búsqueda específica... (Pendiente)");
                break;
            case 4:
                System.out.println("Volviendo al menú principal...");
                break;
        }
    }
}
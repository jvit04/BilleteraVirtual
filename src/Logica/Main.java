package Logica;

import Logica.Excepciones.*;
import Repositorios.RepositorioUsuarios;
import Repositorios.RepositorioTransacciones;
import java.util.List;
import java.util.Scanner;

//Para ejecutar el programa desde terminal usar: java -cp bin Logica.Main
public class Main {

    private static Scanner sc = new Scanner(System.in);

    private static final String[] OPCIONES_PRINCIPAL = {
            "Registro de usuario",
            "Consultas de usuario",
            "Realizar transacción",
            "Administrador (Repositorios)",
            "Salir"
    };

    public static void main(String[] args) {
        boolean activo = true;

        System.out.println("Iniciando sistema de Billetera Virtual...");

        cargarDatosDePrueba();

        while (activo) {
            limpiarPantalla();
            try {
                // Se usa el Menu para mostrar las opciones visualmente iguales
                Menu.mostrarOpciones("MENÚ PRINCIPAL", OPCIONES_PRINCIPAL);

                // Se envia tamaño del arreglo dinámicamente
                int opcion = Menu.elegirOpcion(OPCIONES_PRINCIPAL.length);

                switch (opcion) {
                    case 1: // --- REGISTRO DE USUARIO ---
                        System.out.println("\n--- Registro de Nuevo Usuario ---");

                        String cedula = "";
                        boolean cancelarRegistro = false; // Bandera para saber si canceló


                            while(true) {
                                System.out.print("Ingrese Cédula (o '0' para cancelar): ");
                                cedula = sc.nextLine();

                                if (cedula.equals("0")) {
                                    cancelarRegistro = true;
                                    break;
                                }

                                try {
                                    Validador.validarCedula(cedula); //
                                    if (RepositorioUsuarios.buscarPorCedula(cedula) != null) {
                                        System.out.println("⚠ Error: Esa cédula ya está registrada.");
                                        continue;
                                    }
                                    break;
                                } catch (CedulaInvalidaException e) {
                                    System.out.println("⚠ " + e.getMessage());
                                }
                            }

                        if (cancelarRegistro) {
                            System.out.println("Registro cancelado.");
                            break; // Sale del case 1 y vuelve al menú principal
                        }

                        String nombre;
                        while (true) {
                            try {
                                System.out.print("Ingrese Nombre Completo: ");
                                nombre = sc.nextLine();
                                Validador.validarNombreCampo(nombre);
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("⚠ " + e.getMessage());
                            }
                        }
                        String ciudad;
                        while (true) {
                            try {
                                System.out.print("Ingrese Ciudad: ");
                                ciudad = sc.nextLine();
                                Validador.validarNombreCampo(ciudad);
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("⚠ " + e.getMessage());
                            }
                        }

                        String alias;
                        while (true){
                            try {
                                System.out.print("Ingrese Alias: ");
                                alias = sc.nextLine();
                                Validador.validarAlias(alias);
                                break;
                            }
                            catch (AliasInvalidoException e){
                                System.out.println("⚠ " + e.getMessage());
                            }
                        }


                        String email;
                        while (true){
                            try {
                                System.out.print("Ingrese Email: ");
                                email = sc.nextLine();
                                Validador.validarCorreo(email);
                                break;
                            }
                            catch (EmailNoValidoException e){
                                System.out.println("⚠ " + e.getMessage());
                            }
                        }

                        try {
                            Usuario usuarioActual = new Usuario(cedula, nombre, ciudad, alias, email); //
                            RepositorioUsuarios.guardarUsuario(usuarioActual); //
                            System.out.println("¡Bienvenido, " + usuarioActual.getNombre() + "! Has sido registrado exitosamente.");
                        } catch (CredencialYaExistenteException e) {
                            System.out.println("\n[!] Error: " + e.getMessage());
                        }
                        break;

                    case 2: // --- CONSULTA DE SALDO ---
                        System.out.println("\n--- Consulta de Saldo ---");

                        String cedulaConsulta;
                        while(true) {
                            //Para hacer que se pueda consultar por alias habría que hacer un hashmap con alias, usuario.
                            System.out.print("Ingrese Cédula: ");
                            cedulaConsulta = sc.nextLine();
                            try {
                                Validador.validarCedula(cedulaConsulta);

                            } catch (CedulaInvalidaException e) {
                                System.out.println("⚠ " + e.getMessage());
                            }

                            Usuario usuarioConsulta = RepositorioUsuarios.buscarPorCedula(cedulaConsulta);

                            if (usuarioConsulta != null) {
                                System.out.println("Hola, " + usuarioConsulta.getNombre());
                                System.out.println("Alias: " + usuarioConsulta.getAlias());
                                usuarioConsulta.getBilletera().infoSaldo(); //
                                break;
                            } else {
                                System.out.println("⚠ Error: Usuario no encontrado.");
                            }

                        }
                        break;

                    case 3: // --- REALIZAR TRANSACCIÓN ---
                        System.out.println("\n--- Realizar Transacción ---");
                        System.out.print("Ingrese su Cédula para operar: ");
                        String cedulaOperacion = sc.nextLine();

                        Usuario usuarioOperacion = RepositorioUsuarios.buscarPorCedula(cedulaOperacion);

                        if (usuarioOperacion != null) {
                            System.out.println("Bienvenido/a " + usuarioOperacion.getNombre());
                            realizarTransaccionInteractiva(usuarioOperacion);
                        } else {
                            System.out.println("⚠ Error: Usuario no encontrado.");
                        }
                        break;

                    case 4: // --- MENÚ ADMINISTRADOR ---
                        manejarMenuAdministrador();
                        break;

                    case 5: // --- SALIR ---
                        System.out.println("Cerrando sesión... ¡Hasta luego!");
                        activo = false;
                        break;
                }

            } catch (Exception e) {
                // Captura cualquier error inesperado para que el programa no se cierre de golpe
                System.out.println("\n[!] Ocurrió un error inesperado: " + e.getMessage());
            }
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private static void realizarTransaccionInteractiva(Usuario usuario) {
        // Muestra menú de depósito, retiro, etc.
        MenuUsuario.mostrar(); //
        int tipo = MenuUsuario.pedirOpcion();

        if (tipo == -1) return; // Si hubo error en la selección, volvemos

        System.out.print("Ingrese el monto a operar: $");
        double monto;
        try {
            monto = Double.parseDouble(sc.nextLine());
            if (monto <= 0) {
                System.out.println("⚠ El monto debe ser mayor a 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠ Monto inválido. Debe ser un número.");
            return;
        }

        try {
            switch (tipo) {
                case 1: // Depósito
                    Deposito deposito = new Deposito(monto, usuario); //
                    deposito.getInfoTransaccion();
                    RepositorioTransacciones.guardarTransaccion(deposito); //
                    System.out.println("✅ Depósito guardado en historial.");
                    break;

                case 2: // Retiro
                    Retiro retiro = new Retiro(usuario, monto); //
                    retiro.getInfoTransaccion();
                    RepositorioTransacciones.guardarTransaccion(retiro);
                    System.out.println("✅ Retiro guardado en historial.");
                    break;

                case 3: // Pago Servicio
                    System.out.print("Ingrese tipo de servicio: ");
                    String servicio = sc.nextLine();
                    while(servicio.trim().isEmpty()){
                        System.out.print("⚠ El servicio no puede estar vacío: ");
                        servicio = sc.nextLine();
                    }


                    System.out.print("Ingrese nombre de la empresa: ");
                    String empresa = sc.nextLine();
                    while(empresa.trim().isEmpty()){
                        System.out.print("⚠ El nombre no puede estar vacío: ");
                        empresa = sc.nextLine();
                    }


                    PagoServicio pago = new PagoServicio(monto, usuario, empresa, servicio); //
                    pago.getInfoTransaccion();
                    RepositorioTransacciones.guardarTransaccion(pago);
                    System.out.println("✅ Pago guardado en historial.");
                    break;

                case 4: // Transferencia
                    System.out.print("Ingrese el ALIAS del destinatario: ");
                    String aliasDestino = sc.nextLine();

                    // Validación: No permitir alias vacío
                    if (aliasDestino.trim().isEmpty()) {
                        System.out.println("❌ Error: El alias no puede estar vacío.");
                        break;
                    }

                    Usuario destino = RepositorioUsuarios.buscarPorAlias(aliasDestino);

                    if (destino == null) {
                        System.out.println("❌ Error: El alias '" + aliasDestino + "' no existe.");
                    } else if (destino.getCedula().equals(usuario.getCedula())) {
                        System.out.println("❌ Error: No puedes transferirte a ti mismo.");
                    } else {
                        Transferencia transf = new Transferencia(monto, usuario, destino); //
                        transf.getInfoTransaccion();
                        RepositorioTransacciones.guardarTransaccion(transf);
                        System.out.println("✅ Transferencia exitosa.");
                    }
                    break;
            }
        } catch (SaldoInsuficienteException | MontoInvalidoException e) { //
            System.out.println("\n[!] No se pudo realizar la transacción: " + e.getMessage());
        }
    }

    private static void manejarMenuAdministrador() {
        int opAdmin;
        do {
            // Muestra el menú con las nuevas opciones
            MenuAdministrador.mostrar();
            opAdmin = MenuAdministrador.pedirOpcion();

            switch (opAdmin) {
                case 1: // --- CONSULTAR REPOSITORIO DE USUARIOS ---
                    List<Usuario> usuarios = RepositorioUsuarios.obtenerTodos();
                    System.out.println("\n--- Repositorio de Usuarios (" + usuarios.size() + ") ---");
                    if (usuarios.isEmpty()) {
                        System.out.println("(El repositorio está vacío)");
                    } else {
                        for (Usuario u : usuarios) {
                            System.out.println("- " + u.getNombre() + " | Alias: " + u.getAlias() + " | C.I: " + u.getCedula());
                        }
                    }
                    break;

                case 2: // --- CONSULTAR REPOSITORIO DE TRANSACCIONES (Buscar una específica) ---
                    System.out.print("Ingrese el ID de la transacción a buscar (ej. TRX-1): ");
                    String idBuscar = sc.nextLine();
                    Transaccion tEncontrada = RepositorioTransacciones.buscarPorID(idBuscar);

                    if (tEncontrada != null) {
                        System.out.println("\n--- Detalle de Transacción ---");
                        tEncontrada.getInfoTransaccion();
                    } else {
                        System.out.println("⚠ No se encontró ninguna transacción con el ID: " + idBuscar);
                    }
                    break;

                case 3: // --- VER TRANSACCIONES TOTALES (Historial completo) ---
                    List<Transaccion> historial = RepositorioTransacciones.obtenerHistorialGlobal();
                    System.out.println("\n--- Historial Global de Transacciones (" + historial.size() + ") ---");
                    if (historial.isEmpty()) {
                        System.out.println("(No hay transacciones registradas)");
                    } else {
                        for (Transaccion t : historial) {
                            // Mostramos un resumen rápido
                            System.out.println("- " + t.getIdTransaccion() + " | $" + t.getMonto() + " | Tipo: " + t.getClass().getSimpleName());
                        }
                    }
                    break;

                case 4: // --- CARGAR DESDE ARCHIVO --- aun en proceso, no se ha hecho
                    System.out.println("\n--- Carga de Datos ---");
                    System.out.print("Ingrese el nombre del archivo de usuarios: ");
                    String archivoUsuarios = sc.nextLine();


                    new RepositorioUsuarios().cargarDesdeArchivo(archivoUsuarios);
                    System.out.println("ℹ Llamada a carga de usuarios realizada (Revisar implementación en RepositorioUsuarios).");


                    System.out.println("ℹ La carga de transacciones se realizaría de forma similar.");
                    break;

                case 5: // --- VOLVER ---
                    System.out.println("Volviendo al menú principal...");
                    break;
            }
        } while (opAdmin != 5);
    }
    // Agrega esto al final de la clase Main, junto a los otros métodos privados
    private static void cargarDatosDePrueba() {
        System.out.println("--- Cargando datos de prueba... ---");
        try {
            // 1. Crear y registrar Usuarios
            // Recuerda usar datos que pasen tus validaciones (cédula 10 dígitos, emails válidos, etc.)
            Usuario u1 = new Usuario("1111111111", "José Viteri", "Quito", "jose04", "jviteri@2004gmail.com");
            Usuario u2 = new Usuario("2222222222", "Paula Martillo", "Guayaquil", "pau123", "pau123@gmail.com");
            Usuario u3 = new Usuario("3333333333", "Rafael Brito", "Cuenca", "rbrito42", "rbrito@hotmail.com");

            RepositorioUsuarios.guardarUsuario(u1);
            RepositorioUsuarios.guardarUsuario(u2);
            RepositorioUsuarios.guardarUsuario(u3);

            // 2. Crear Transacciones iniciales (para que tengan saldo y movimiento)

            // Depósitos iniciales (fondos)
            Deposito d1 = new Deposito(500.00, u1); // Ana empieza con $500
            RepositorioTransacciones.guardarTransaccion(d1);

            Deposito d2 = new Deposito(1000.00, u2); // Beto empieza con $1000
            RepositorioTransacciones.guardarTransaccion(d2);

            // Un retiro de Ana
            Retiro r1 = new Retiro(u1, 50.00);
            RepositorioTransacciones.guardarTransaccion(r1);

            // Una transferencia de Beto a Ana
            Transferencia t1 = new Transferencia(200.00, u2, u1);
            RepositorioTransacciones.guardarTransaccion(t1);

            System.out.println("✅ Datos de prueba cargados: 3 usuarios y 4 transacciones.");

        } catch (Exception e) {
            System.out.println("⚠ Error al cargar datos de prueba: " + e.getMessage());
        }
        System.out.println("-----------------------------------\n");
    }
    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
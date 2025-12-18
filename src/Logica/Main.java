package Logica;

import Logica.Excepciones.*;
import Repositorios.RepositorioUsuarios;
import Repositorios.RepositorioTransacciones;
import java.util.List;

public class Main {

    private static final String[] OPCIONES_PRINCIPAL = {
            "Registro de usuario",
            "Consultas de usuario",
            "Realizar transacción",
            "Administrador (Repositorios)",
            "Salir"
    };

    public static void main(String[] args) {
        boolean activo = true;

        UI.mostrarMensaje("Iniciando sistema de Billetera Virtual...");

        cargarDatosDePrueba();

        while (activo) {
            try {
                int opcion = UI.pedirOpcionMenu("Menú Principal", OPCIONES_PRINCIPAL);
                if (opcion == 0) opcion = 5; // Forzar salida si cierra ventana

                switch (opcion) {
                    case 1: registrarUsuario(); break;
                    case 2: consultarSaldo(); break;
                    case 3: prepararTransaccion(); break;

                    case 4:
                        manejarMenuAdministrador(); // <--- Aquí usamos la nueva clase
                        break;

                    case 5:
                        UI.mostrarMensaje("Cerrando sesión... ¡Hasta luego!");
                        activo = false;
                        break;
                }
            } catch (Exception e) {
                UI.mostrarError("[!] Error crítico: " + e.getMessage());
            }
        }
    }


    // Este método ahora usa MenuUsuario para pedir la opción
    private static void realizarTransaccionInteractiva(Usuario usuario) {
        int tipo = MenuUsuario.mostrar();

        if (tipo == 5 || tipo == 0) return;

        double monto;
        while (true) {
            String montoStr = UI.pedirDato("Ingrese el monto a operar: $");
            if (montoStr == null) return;
            try {
                monto = Double.parseDouble(montoStr);
                Validador.validarMonto(monto);
                if (tipo != 1) { // Si no es depósito, validamos saldo
                    Validador.validarTransaccion(usuario, monto);
                }
                break;
            } catch (NumberFormatException e) {
                UI.mostrarError("Monto inválido.");
            } catch (Exception e) {
                UI.mostrarError(e.getMessage());
            }
        }

        try {
            switch (tipo) {
                case 1: // Depósito
                    Deposito deposito = new Deposito(monto, usuario);
                    RepositorioTransacciones.guardarTransaccion(deposito);
                    UI.mostrarMensaje("✅ Depósito realizado.");
                    break;
                case 2: // Retiro
                    Retiro retiro = new Retiro(usuario, monto);
                    RepositorioTransacciones.guardarTransaccion(retiro);
                    UI.mostrarMensaje("✅ Retiro realizado.");
                    break;
                case 3: // Pago Servicio
                    String servicio = UI.pedirDato("Tipo de servicio:");
                    String empresa = UI.pedirDato("Nombre de empresa:");
                    if (servicio != null && empresa != null) {
                        PagoServicio pago = new PagoServicio(monto, usuario, empresa, servicio);
                        RepositorioTransacciones.guardarTransaccion(pago);
                        UI.mostrarMensaje("✅ Pago registrado.");
                    }
                    break;
                case 4: // Transferencia
                    String alias = UI.pedirDato("Alias del destinatario:");
                    if (alias != null) {
                        Usuario destino = RepositorioUsuarios.buscarPorAlias(alias);
                        if (destino != null && !destino.getCedula().equals(usuario.getCedula())) {
                            Transferencia transf = new Transferencia(monto, usuario, destino);
                            RepositorioTransacciones.guardarTransaccion(transf);
                            UI.mostrarMensaje("✅ Transferencia realizada.");
                        } else {
                            UI.mostrarError("Destinatario inválido.");
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            UI.mostrarError("Error en transacción: " + e.getMessage());
        }
    }

    // Este método usa MenuAdministrador y maneja las NUEVAS opciones
    private static void manejarMenuAdministrador() {
        int opcion;
        do {
            opcion = MenuAdministrador.mostrar();

            switch (opcion) {
                case 1: // Ver usuarios
                    List<Usuario> usuarios = RepositorioUsuarios.obtenerTodosStatic();
                    StringBuilder sb = new StringBuilder("--- Usuarios ---\n");
                    for (Usuario u : usuarios) {
                        sb.append(u.toString()).append("\n");
                    }
                    UI.mostrarMensaje(sb.toString());
                    break;

                case 2: // NUEVA OPCIÓN: Consulta por Alias
                    String alias = UI.pedirDato("Ingrese el alias a buscar:");
                    if (alias != null) {
                        Usuario u = RepositorioUsuarios.buscarPorAlias(alias);
                        if (u != null) {
                            UI.mostrarMensaje("Usuario encontrado:\n" + u.getNombre() + "\nCédula: " + u.getCedula());
                        } else {
                            UI.mostrarError("Usuario no encontrado con alias: " + alias);
                        }
                    }
                    break;

                case 3:
                    String id = UI.pedirDato("ID Transacción (ej. TRX-1):");
                    if (id != null) {
                        Transaccion t = RepositorioTransacciones.buscarPorID(id);
                        if (t != null) {
                            UI.mostrarMensaje("ID: " + t.getIdTransaccion() + "\nMonto: " + t.getMonto());
                        } else {
                            UI.mostrarError("No encontrada.");
                        }
                    }
                    break;

                case 4:
                    List<Transaccion> hist = RepositorioTransacciones.obtenerHistorialGlobal();
                    StringBuilder sbTrx = new StringBuilder("--- Transacciones ---\n");
                    for (Transaccion t : hist) sbTrx.append(t.getIdTransaccion()).append(" | $").append(t.getMonto()).append("\n");
                    UI.mostrarMensaje(sbTrx.toString());
                    break;

                case 5:
                    String arch = UI.pedirDato("Nombre archivo usuarios:");
                    if (arch != null) new RepositorioUsuarios().cargarDesdeArchivo(arch);
                    break;

                case 6: // Volver
                    break;
            }
        } while (opcion != 6);
    }


    private static void registrarUsuario() {
        String cedula = "";

        while (true) {
            cedula = UI.pedirDato("Ingrese Cédula para el nuevo registro:");
            if (cedula == null) return; // Si cancela, volvemos al menú

            try {
                Validador.validarCedula(cedula);
                if (RepositorioUsuarios.buscarPorCedula(cedula) != null) {
                    UI.mostrarError("Error: Esa cédula ya está registrada.");
                    continue;
                }
                break;
            } catch (CedulaInvalidaException e) {
                UI.mostrarError(e.getMessage());
            }
        }

        String nombre;
        while (true) {
            nombre = UI.pedirDato("Ingrese Nombre Completo:");
            if (nombre == null) return;
            try {
                Validador.validarNombreCampo(nombre);
                break;
            } catch (IllegalArgumentException e) {
                UI.mostrarError(e.getMessage());
            }
        }

        String ciudad;
        while (true) {
            ciudad = UI.pedirDato("Ingrese Ciudad:");
            if (ciudad == null) return;
            try {
                Validador.validarNombreCampo(ciudad);
                break;
            } catch (IllegalArgumentException e) {
                UI.mostrarError(e.getMessage());
            }
        }

        String alias;
        while (true) {
            alias = UI.pedirDato("Ingrese Alias:");
            if (alias == null) return;
            try {
                Validador.validarAlias(alias);
                break;
            } catch (AliasInvalidoException e) {
                UI.mostrarError(e.getMessage());
            }
        }

        String email;
        while (true) {
            email = UI.pedirDato("Ingrese Email:");
            if (email == null) return;
            try {
                Validador.validarCorreo(email);
                break;
            } catch (EmailNoValidoException e) {
                UI.mostrarError(e.getMessage());
            }
        }

        try {
            Usuario usuarioActual = new Usuario(cedula, nombre, ciudad, alias, email);
            RepositorioUsuarios.guardarUsuario(usuarioActual);
            UI.mostrarMensaje("¡Bienvenido, " + usuarioActual.getNombre() + "!\nHas sido registrado exitosamente.");
        } catch (CredencialYaExistenteException e) {
            UI.mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    private static void consultarSaldo() {
        String cedulaConsulta = UI.pedirDato("Ingrese Cédula para consultar saldo:");
        if (cedulaConsulta == null) return;

        try {
            Validador.validarCedula(cedulaConsulta);
            Usuario usuarioConsulta = RepositorioUsuarios.buscarPorCedula(cedulaConsulta);

            if (usuarioConsulta != null) {
                // Construimos el mensaje para la ventana
                String mensaje = "Hola, " + usuarioConsulta.getNombre() + "\n" +
                        "Alias: " + usuarioConsulta.getAlias() + "\n" +
                        "Saldo actual: $" + usuarioConsulta.getBilletera().getSaldo();
                UI.mostrarMensaje(mensaje);
            } else {
                UI.mostrarError("Error: Usuario no encontrado.");
            }
        } catch (CedulaInvalidaException e) {
            UI.mostrarError(e.getMessage());
        }
    }

    private static void prepararTransaccion() {
        String cedulaOperacion = UI.pedirDato("Ingrese su Cédula para operar:");
        if (cedulaOperacion == null) return;

        try {
            Validador.validarCedula(cedulaOperacion);
            Usuario usuarioOperacion = RepositorioUsuarios.buscarPorCedula(cedulaOperacion);

            if (usuarioOperacion != null) {
                UI.mostrarMensaje("Bienvenido/a " + usuarioOperacion.getNombre());
                realizarTransaccionInteractiva(usuarioOperacion);
            } else {
                UI.mostrarError("Error: Usuario no encontrado.");
            }
        } catch (CedulaInvalidaException e) {
            UI.mostrarError(e.getMessage());
        }
    }


    private static void cargarDatosDePrueba() {
        // Este método se mantiene igual, ya que solo carga datos en memoria
        // Podrías quitar los System.out.println internos si quieres que sea totalmente silencioso
        try {
            Usuario u1 = new Usuario("1111111111", "José Viteri", "Quito", "jose04", "jviteri@2004gmail.com");
            Usuario u2 = new Usuario("2222222222", "Paula Martillo", "Guayaquil", "pau123", "pau123@gmail.com");
            Usuario u3 = new Usuario("3333333333", "Rafael Brito", "Cuenca", "rbrito42", "rbrito@hotmail.com");

            RepositorioUsuarios.guardarUsuario(u1);
            RepositorioUsuarios.guardarUsuario(u2);
            RepositorioUsuarios.guardarUsuario(u3);

            Deposito d1 = new Deposito(500.00, u1);
            RepositorioTransacciones.guardarTransaccion(d1);
            Deposito d2 = new Deposito(1000.00, u2);
            RepositorioTransacciones.guardarTransaccion(d2);
            Retiro r1 = new Retiro(u1, 50.00);
            RepositorioTransacciones.guardarTransaccion(r1);
            Transferencia t1 = new Transferencia(200.00, u2, u1);
            RepositorioTransacciones.guardarTransaccion(t1);

        } catch (Exception e) {
            System.out.println("Error carga prueba: " + e.getMessage());
        }
    }
}
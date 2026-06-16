package Repositorios;

import Logica.Transaccion;
import Logica.UI;
import Logica.Usuario;
import Persistencia.Persistencia;
import Persistencia.Persistible;
import Paths.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioTransacciones implements Repositorio<Transaccion>, Almacenable {

    // 1. Datos en Memoria (Static para compatibilidad con Main.java)
    private static Map<String, Transaccion> mapaTransacciones = new HashMap<>();

    // 2. Herramienta de Persistencia (Composición)
    private Persistible<Map<String, Transaccion>> servicioPersistencia;

    public RepositorioTransacciones() {
        // Inicializamos la herramienta concreta
        this.servicioPersistencia = new Persistencia<Map<String, Transaccion>>();
    }


    public static void guardarTransaccion(Transaccion t) {
        mapaTransacciones.put(t.getIdTransaccion(), t);
        try{
            new RepositorioTransacciones(). guardarEnArchivo();
        }
        catch(IOException e){
            UI.mostrarError("⚠ Advertencia: El usuario se registró en memoria pero no se pudo guardar en el archivo: " + e.getMessage());
        }
    }

    public static Transaccion buscarPorID(String id) {
        return mapaTransacciones.get(id);
    }

    public static List<Transaccion> obtenerHistorialGlobal() {
        return new ArrayList<>(mapaTransacciones.values());
    }

    public static List<Transaccion> obtenerHistorialPorUsuario(String cedulaUsuario) {
        List<Transaccion> resultado = new ArrayList<>();
        for (Transaccion t : mapaTransacciones.values()) {
            if (t.getUsuario().getCedula().equals(cedulaUsuario)) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    // --- IMPLEMENTACIÓN DE INTERFAZ REPOSITORIO<Transaccion> (SOLID) ---

    @Override
    public void guardar(Transaccion transaccion) {
        guardarTransaccion(transaccion);
    }

    @Override
    public Transaccion buscar(String id) {
        return buscarPorID(id);
    }

    @Override
    public List<Transaccion> obtenerTodos() {
        return obtenerHistorialGlobal();
    }

    // --- IMPLEMENTACIÓN DE INTERFAZ ALMACENABLE (SOLID) ---
    // Lógica de archivos delegada al servicio

    @Override
    public void guardarEnArchivo() throws IOException {
        servicioPersistencia.guardar(Paths.ARCHIVO_TRANSACCIONES, mapaTransacciones);
    }

    public static Map<String, Transaccion> getMapaTransacciones() {
        return mapaTransacciones;
    }
    private void recalcularSaldosGlobalmente() {
        // 1. Resetear el saldo de TODOS los usuarios a 0 para evitar duplicados
        for (Usuario u : RepositorioUsuarios.obtenerTodosStatic()) {
            // Truco: Como setSaldo estaba vacío o es privado, restamos todo lo que tiene
            double saldoActual = u.getBilletera().getSaldo();
            if (saldoActual != 0) {
                // Si es positivo restamos, si es negativo sumamos (para llegar a 0)
                if (saldoActual > 0) u.getBilletera().restarSaldo(saldoActual);
                else u.getBilletera().aumentarSaldo(Math.abs(saldoActual));
            }
        }

        // 2. Volver a aplicar TODAS las transacciones del historial
        for (Transaccion t : mapaTransacciones.values()) {
            // Reconexión de seguridad (por si acaso)
            Usuario u = t.getUsuario();
            if (u == null) continue;

            // Aplicamos lógica según el tipo
            if (t instanceof Logica.Deposito) {
                u.getBilletera().aumentarSaldo(t.getMonto());

            } else if (t instanceof Logica.Retiro || t instanceof Logica.PagoServicio) {
                u.getBilletera().restarSaldo(t.getMonto());

            } else if (t instanceof Logica.Transferencia) {
                Logica.Transferencia transf = (Logica.Transferencia) t;

                // Restar al origen
                u.getBilletera().restarSaldo(t.getMonto());

                // Sumar al destino (Necesitamos reconectar el destino también)
                Usuario destinoReal = RepositorioUsuarios.buscarPorCedula(transf.getUsuarioDestino().getCedula());
                if (destinoReal != null) {
                    transf.setUsuarioDestino(destinoReal); // Actualizamos referencia
                    destinoReal.getBilletera().aumentarSaldo(t.getMonto());
                }
            }
        }
    }
    @Override
    public void cargarDesdeArchivo(String archivo) {
        if (RepositorioUsuarios.getMapaUsuarios().isEmpty()) {
            Logica.UI.mostrarError("⛔ Error de Dependencia:\n" +
                    "No se pueden cargar transacciones si la lista de usuarios está vacía.\n\n" +
                    "Por favor, cargue primero el archivo de Usuarios.");
            return; // Detenemos la ejecución aquí.
        }
        try {

            Map<String, Transaccion> datosDelArchivo = new Persistencia<Map<String, Transaccion>>().cargar(archivo);

            if (datosDelArchivo != null) {
                int nuevos = 0;

                for (Transaccion tArchivo : datosDelArchivo.values()) {

                    // 1. RECONEXIÓN DE USUARIO (Crucial para consistencia)
                    // Se busca al usuario real en memoria usando la cédula del usuario que viene en el archivo
                    Usuario usuarioReal = RepositorioUsuarios.buscarPorCedula(tArchivo.getUsuario().getCedula());

                    if (usuarioReal != null) {
                        // Se conecta la transacción al objeto Usuario vivo en memoria
                        tArchivo.setUsuario(usuarioReal);
                    } else {
                        // Opcional: Si el usuario no existe en memoria, podríamos decidir no cargar la transacción
                        // o cargarla tal cual. Por ahora la dejamos pasar.
                    }

                    // 2. INSERTAR SI NO EXISTE
                    if (!mapaTransacciones.containsKey(tArchivo.getIdTransaccion())) {
                        mapaTransacciones.put(tArchivo.getIdTransaccion(), tArchivo);
                        nuevos++;
                    }
                }
                //Esta condición actualiza el contador estatico al subir archivos.
                if (!mapaTransacciones.isEmpty()) {
                    // Se calcula el número maximo actual (ej: si hay TRX-3, el max es 3)
                    int maxId = 0;
                    for (String key : mapaTransacciones.keySet()) {
                        try {
                            // Se usa el formato "TRX-123", separando por "-"
                            int idNum = Integer.parseInt(key.split("-")[1]);
                            if (idNum > maxId) maxId = idNum;
                        } catch (Exception e) { /* se ignoran IDs incoherentes */ }
                    }
                    //
                    Transaccion.setContadorID(maxId);
                }

                if (nuevos > 0) {
                    Logica.UI.mostrarMensaje("✅ Se han importado " + nuevos + " transacciones desde el archivo.");
                } else {
                    Logica.UI.mostrarMensaje("⚠ El archivo se leyó correctamente, pero todas las transacciones ya existían en memoria.");
                }
                Logica.UI.mostrarMensaje("🔄 Recalculando saldos basados en el historial importado...");
                recalcularSaldosGlobalmente();
                Logica.UI.mostrarMensaje("✅ Saldos actualizados correctamente.");
            }
        } catch (Exception e) { // Captura Exception general por si falla el cast o IO
            Logica.UI.mostrarError("Error al cargar archivo: " + e.getMessage());
        }
    }
}
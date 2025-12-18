package Repositorios;

import Logica.Transaccion;
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

    // --- MÉTODOS ESTÁTICOS (LEGACY) ---
    // Se mantienen igual para que tu Main.java siga funcionando

    public static void guardarTransaccion(Transaccion t) {
        mapaTransacciones.put(t.getIdTransaccion(), t);
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
    // Puenteamos a los estáticos

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

    @Override
    public void cargarDesdeArchivo(String archivo) {
        try {
            Map<String, Transaccion> datosCargados = servicioPersistencia.cargar(archivo);
            if (datosCargados != null) {
                mapaTransacciones = datosCargados;
                System.out.println("Historial de transacciones cargado exitosamente.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se pudo cargar el historial (se iniciará vacío): " + e.getMessage());
        }
    }
}
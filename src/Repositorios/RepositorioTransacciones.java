package Repositorios;

import Logica.Transaccion;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTransacciones implements Repositorio {

    // Clave: ID Transacción, Valor: Objeto Transacción
    private static Map<String, Transaccion> mapaTransacciones = new HashMap<>();

    public static void guardarTransaccion(Transaccion t) {
        // Usa el ID como clave.
        mapaTransacciones.put(t.getIdTransaccion(), t);
    }

    // Buscar una transacción específica por su código
    public static Transaccion buscarPorID(String id) {
        return mapaTransacciones.get(id);
    }

    // Obtener todo el historial
    public static List<Transaccion> obtenerHistorialGlobal() {
        return new ArrayList<>(mapaTransacciones.values());
    }

    // Buscar transacciones de un usuario específico
    public static List<Transaccion> obtenerHistorialPorUsuario(String cedulaUsuario) {
        List<Transaccion> resultado = new ArrayList<>();

        // Recorremos los valores del mapa
        for (Transaccion t : mapaTransacciones.values()) {
            if (t.getUsuario().getCedula().equals(cedulaUsuario)) {
                resultado.add(t);
            }}
        return resultado;
    }

    @Override
    public void cargarDesdeArchivo(String archivo) {
        //Logica futura para cargar desde Archivo
    }
}
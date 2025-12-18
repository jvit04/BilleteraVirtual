package Repositorios;

import Logica.Excepciones.CredencialYaExistenteException;
import Logica.Usuario;
import Logica.Validador;
import Paths.Paths;
import Persistencia.Persistencia;
import Persistencia.Persistible;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Implementa Repositorio (Lógica) y Almacenable (Archivos)
public class RepositorioUsuarios implements Repositorio<Usuario>, Almacenable {

    // 1. Datos en Memoria (Static para mantener compatibilidad con Main y Validador)
    private static Map<String, Usuario> mapaUsuarios = new HashMap<>();

    // 2. Herramienta de Persistencia (Composición)
    // Usamos la interfaz Persistible para cumplir DIP (Inversión de Dependencias)
    private Persistible<Map<String, Usuario>> servicioPersistencia;

    public RepositorioUsuarios() {
        // Inicializamos la implementación concreta (Persistencia binaria)
        this.servicioPersistencia = new Persistencia<Map<String, Usuario>>();
    }

    // --- MÉTODOS ESTÁTICOS (LEGACY) ---
    // Se mantienen intactos para que Main.java y Validador.java funcionen sin cambios

    public static void guardarUsuario(Usuario nuevoUsuario) throws CredencialYaExistenteException {
        Validador.validarUsuarioExistente(nuevoUsuario.getAlias());
        Validador.validarCedulaNoRegistrada(mapaUsuarios, nuevoUsuario);
        mapaUsuarios.put(nuevoUsuario.getCedula(), nuevoUsuario);
    }

    public static Usuario buscarPorCedula(String cedula) {
        return mapaUsuarios.get(cedula);
    }

    public static Usuario buscarPorAlias(String alias) {
        for (Usuario u : mapaUsuarios.values()) {
            if (u.getAlias().equals(alias)) {
                return u;
            }
        }
        return null;
    }

    public static boolean existeAlias(String alias){
        return buscarPorAlias(alias) != null;
    }

    // Usado por el menú de admin para listar
    public static List<Usuario> obtenerTodosStatic() {
        return new ArrayList<>(mapaUsuarios.values());
    }

    // --- IMPLEMENTACIÓN DE INTERFAZ REPOSITORIO<Usuario> ---
    // Estos métodos puentean a los estáticos

    @Override
    public void guardar(Usuario usuario) {
        guardarUsuario(usuario);
        // Nota: Si la interfaz no permite excepciones, deberás usar try-catch aquí
    }

    @Override
    public Usuario buscar(String id) {
        return buscarPorCedula(id);
    }

    public List<Usuario> obtenerTodos() {
        return obtenerTodosStatic();
    }

    // --- IMPLEMENTACIÓN DE INTERFAZ ALMACENABLE (Manejo de Archivos) ---

    @Override
    public void guardarEnArchivo() throws IOException {
        // El repositorio le dice a la herramienta QUÉ guardar y DÓNDE
        servicioPersistencia.guardar(Paths.ARCHIVO_USUARIOS, mapaUsuarios);
    }

    @Override
    public void cargarDesdeArchivo(String archivo) {
        try {
            // Usamos la herramienta para leer los datos
            Map<String, Usuario> datosCargados = servicioPersistencia.cargar(archivo);

            // Si cargó bien, actualizamos el mapa en memoria
            if (datosCargados != null) {
                mapaUsuarios = datosCargados;
                System.out.println("Base de datos de usuarios cargada exitosamente.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se pudo cargar el archivo (se iniciará vacío o con datos previos): " + e.getMessage());
        }
    }
}
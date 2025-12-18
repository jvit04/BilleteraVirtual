package Repositorios;

import Logica.Excepciones.CredencialYaExistenteException;
import Logica.UI;
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
        try{
            new RepositorioUsuarios().guardarEnArchivo();
        }
        catch(IOException e){
            UI.mostrarError("⚠ Advertencia: El usuario se registró en memoria pero no se pudo guardar en el archivo: " + e.getMessage());
        }
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

    public static Map<String, Usuario> getMapaUsuarios() {
        return mapaUsuarios;
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
            Map<String, Usuario> datosDelArchivo = servicioPersistencia.cargar(archivo);

            if (datosDelArchivo != null) {
                int nuevos = 0;
                for (Usuario u : datosDelArchivo.values()) {
                    //si NO existe en memoria (putIfAbsent)
                    if (!mapaUsuarios.containsKey(u.getCedula())) {
                        mapaUsuarios.put(u.getCedula(), u);
                        nuevos++;
                    }
                }


                if (nuevos > 0) {

                    Logica.UI.mostrarMensaje("✅ Se han importado " + nuevos + " usuarios desde el archivo.");

                } else {
                    Logica.UI.mostrarMensaje("⚠ El archivo se leyó, pero todos los usuarios ya estaban cargados.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            Logica.UI.mostrarError("Error al cargar archivo: " + e.getMessage());
        }
    }
}
package Repositorios;

import Logica.Excepciones.CredencialYaExistenteException;
import Logica.Usuario;
import Logica.Validador;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios implements Repositorio {

    // Clave: Cédula (String), Valor: Usuario
    private static Map<String, Usuario> mapaUsuarios = new HashMap<>();

    public RepositorioUsuarios() {
    }

    public static void guardarUsuario(Usuario nuevoUsuario) throws CredencialYaExistenteException {
        // 1. Validar que el alias no exista (recorriendo el mapa)
        Validador.validarUsuarioExistente(nuevoUsuario.getAlias());

        // 2. Validar que la cédula (la CLAVE) no exista ya
        Validador.validarCedulaNoRegistrada(mapaUsuarios, nuevoUsuario);

        // Si pasa las validaciones, lo metemos al mapa
        mapaUsuarios.put(nuevoUsuario.getCedula(), nuevoUsuario);
    }

    // Búsqueda instantánea por cédula
    public static Usuario buscarPorCedula(String cedula) {
        return mapaUsuarios.get(cedula);
    }

    // Búsqueda por alias
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

    // Los valores del mapa se convierten a una lista para devolverlos todos
    public static List<Usuario> obtenerTodos() {
        return new ArrayList<>(mapaUsuarios.values());
    }

    @Override
    public void cargarDesdeArchivo(String archivo) {
        // Lógica futura...
    }
}
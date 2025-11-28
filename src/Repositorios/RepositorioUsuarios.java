package Repositorios;
import Logica.Excepciones.CredencialYaExistenteException;
import Logica.Usuario;
import Logica.Validador;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios implements Repositorio{
    private static List<Usuario> usuariosRegistrados=  new ArrayList<>();

    public RepositorioUsuarios() {
    }

    //Se encarga de guardar el usuario, no sin antes pasar por metodos y validación
    public static void guardarUsuario(Usuario nuevoUsuario) throws CredencialYaExistenteException {
        Validador.validarUsuarioExistente(nuevoUsuario.getAlias()); //se dirige a la clase Validador con el throw
        usuariosRegistrados.add(nuevoUsuario);
    }

    //metodo busca al usuario dentro del arreglo
public static Usuario buscarPorAlias(String alias){
        for (Usuario usuario:usuariosRegistrados){
            if (usuario.getAlias().equals(alias)){
                return  usuario;
            }
        }
        return null; // No encontrado
}

//compara si el retorno del metodo buscarPorAlias no es null, de eso depende que se guarde un usuario nuevo
    public static boolean existeAlias(String alias){
        return buscarPorAlias(alias) != null;
    }

    //Permite obtener el listado de todos los usuarios registrados.
    public static List<Usuario> obtenerTodos() {
        return usuariosRegistrados;
    }


    @Override
    public void cargarDesdeArchivo(String archivo) {
        //aqui se registra usuarios desde archivo .csv
    }
}

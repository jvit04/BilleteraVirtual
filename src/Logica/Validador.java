package Logica;
import Logica.Excepciones.*;
import Repositorios.RepositorioUsuarios;

import java.util.regex.Pattern;

//Clase con el objetivo de agrupar todos los metodos validadores de datos
public class Validador {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"; //Regex para validación de correo
    private static final String ALIAS_REGEX = "^[a-zA-Z0-9._]{5,15}$"; //Regex para validación de alias
    private static final String CEDULA_REGEX = "^[0-9]{10}$"; //Regex para la validacion de la cedula

    public Validador() {
    }

    public static void validarCorreo(String correo) throws EmailNoValidoException {
        if (correo == null || !Pattern.matches(EMAIL_REGEX, correo)) {
            throw new EmailNoValidoException("El correo " + correo + " no tiene un formato válido. ");
        }

    }

    public static void validarAlias(String alias) throws AliasInvalidoException {
        if (alias == null || !Pattern.matches(ALIAS_REGEX, alias)) {
            throw new EmailNoValidoException("El alias " + alias + " no tiene un formato válido. ");
        }
    }
    public static void validarCedula(String cedula) throws CedulaInvalidaException {
        if (cedula == null || !Pattern.matches(CEDULA_REGEX, cedula)) {
            throw new EmailNoValidoException("La cedula " + cedula + " no tiene un formato válido. ");
        }
    }

    public static void validarOpcion(int opcion, int cantidadOpciones){
        if (opcion < 1 || opcion > cantidadOpciones) {
            throw new OpcionMenuNoValidoException("Opción inválida. Debe ser entre 1 y " + cantidadOpciones);
        }
    }

public static void validarTransaccion(Usuario usuario, double monto){
    if (usuario.getBilletera().getSaldo() < monto) {
        throw new SaldoInsuficienteException("Saldo insuficiente para retiro");
    }
}
//Aqui va la excepcion de validar el usuario existente
public static void validarUsuarioExistente(String alias){
        if (RepositorioUsuarios.existeAlias(alias)){ //lanza el throw una vez se confirme la condición como falsa
            throw new CredencialYaExistenteException("El alias " + alias + " ya está en uso.");
        }
}

}

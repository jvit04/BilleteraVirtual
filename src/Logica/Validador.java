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

    //metodo estatico que valida el correo de acuerdo al formato Regex, que se puede ver en los atributos
    public static void validarCorreo(String correo) throws EmailNoValidoException {
        if (correo == null || !Pattern.matches(EMAIL_REGEX, correo)) {
            throw new EmailNoValidoException("El correo " + correo + " no tiene un formato válido. ");
        }

    }
    //metodo estatico que valida el alias de acuerdo al formato Regex, que se puede ver en los atributos
    public static void validarAlias(String alias) throws AliasInvalidoException {
        if (alias == null || !Pattern.matches(ALIAS_REGEX, alias)) {
            throw new EmailNoValidoException("El alias " + alias + " no tiene un formato válido. ");
        }
    }
    //metodo estatico que valida la cedula de acuerdo al formato Regex, que se puede ver en los atributos
    public static void validarCedula(String cedula) throws CedulaInvalidaException {
        if (cedula == null || !Pattern.matches(CEDULA_REGEX, cedula)) {
            throw new EmailNoValidoException("La cedula " + cedula + " no tiene un formato válido. ");
        }
    }

    //metodo que valida la opcion insertada, la cual debe ser acorde a la condición
    public static void validarOpcion(int opcion, int cantidadOpciones) throws OpcionMenuNoValidoException{
        if (opcion < 1 || opcion > cantidadOpciones) {
            throw new OpcionMenuNoValidoException("Opción inválida. Debe ser entre 1 y " + cantidadOpciones);
        }
    }
//sirve para validar que una transacción sea realizable
public static void validarTransaccion(Usuario usuario, double monto) throws SaldoInsuficienteException{
    if (usuario.getBilletera().getSaldo() < monto) {
        throw new SaldoInsuficienteException("Saldo insuficiente para retiro");
    }
}
//Aquí va el metodo para validar a un usuario existente
public static void validarUsuarioExistente(String alias) throws CredencialYaExistenteException{
        if (RepositorioUsuarios.existeAlias(alias)){ //lanza el throw una vez se confirme la condición como falsa
            throw new CredencialYaExistenteException("El alias " + alias + " ya está en uso.");
        }
}
//Esta metodo sirve para validar que el monto no sea menor a 0
public static void validarMonto(double monto) throws MontoInvalidoException{
        if(monto<=0){
            throw new MontoInvalidoException("El monto no puede ser negativo, ni cero.");
        }
}
}

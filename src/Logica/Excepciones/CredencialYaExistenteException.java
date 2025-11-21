package Logica.Excepciones;

public class CredencialYaExistenteException extends RuntimeException {
    public CredencialYaExistenteException(String message) {
        super(message);
    }
}

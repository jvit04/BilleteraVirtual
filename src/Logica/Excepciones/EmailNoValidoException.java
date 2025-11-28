package Logica.Excepciones;

public class EmailNoValidoException extends RuntimeException {
    public EmailNoValidoException(String message) {
        super(message);
    }

}

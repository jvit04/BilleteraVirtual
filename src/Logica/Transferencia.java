package Logica;

public class Transferencia extends Transaccion {
    //atributos
    private Usuario usuarioDestino;
    //constructor
    public Transferencia(double monto, Usuario usuario) {
        super(monto, usuario);
        this.usuarioDestino = usuarioDestino;
    }

    @Override
    public boolean esIngreso() {
        return false;
    }

    @Override
    public void getInfoTransaccion() {
        super.getInfoTransaccion();
        System.out.println("Transferecia realizada a: " + this.usuarioDestino.getNombre());
        System.out.println("Cedula del destinatario: " + this.usuarioDestino.getCedula());
    }

    public Usuario getUsuarioDestino() {
        return usuarioDestino;
    }
}

package Logica;


//atributos
public class Retiro extends Transaccion {

    //Constructor
    public Retiro(Usuario usuario, double monto) {
        super(monto, usuario);
    }

    @Override
    public boolean esIngreso() {
        return false;
    }

    @Override
    public void getInfoTransaccion() {
        super.getInfoTransaccion();
        System.out.println("Retiro de fondos");
    }
}

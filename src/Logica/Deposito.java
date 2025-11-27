package Logica;


//Constructor
public class Deposito extends Transaccion {
    public Deposito(double monto, Usuario usuario) {
        super(monto, usuario);
        usuario.getBilletera().aumentarSaldo(monto);
        usuario.getBilletera().agregarTransaccion(this);
    }

    @Override
    public boolean esIngreso() {
        return true;
    }

    @Override
    public void getInfoTransaccion() {
        super.getInfoTransaccion();
        System.out.println("Deposito en efectivo");
    }


}

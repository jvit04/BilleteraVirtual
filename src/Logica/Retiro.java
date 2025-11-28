package Logica;


import Logica.Excepciones.SaldoInsuficienteException;

//atributos
public class Retiro extends Transaccion {
    //Constructor
    public Retiro(Usuario usuario, double monto) {
        super(monto, usuario);
        validarTransaccion();

        usuario.getBilletera().restarSaldo(monto);
        usuario.getBilletera().agregarTransaccion(this);
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

    @Override
    public void validarTransaccion() {
    Validador.validarTransaccion(this.usuario, this.monto);
    }
}

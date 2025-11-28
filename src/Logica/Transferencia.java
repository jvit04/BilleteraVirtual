package Logica;

import Logica.Excepciones.SaldoInsuficienteException;

public class Transferencia extends Transaccion {
    //atributos
    private Usuario usuarioDestino;
    private Usuario usuarioOrigen;

    //constructor
    public Transferencia(double monto, Usuario usuarioOrigen, Usuario usuarioDestino) {
        super(monto, usuarioOrigen);
        this.usuarioOrigen = usuarioOrigen;
        this.usuarioDestino = usuarioDestino;

        validarTransaccion();


        usuarioOrigen.getBilletera().restarSaldo(monto);
        usuarioOrigen.getBilletera().agregarTransaccion(this);
        usuarioDestino.getBilletera().aumentarSaldo(monto);
        usuarioDestino.getBilletera().agregarTransaccion(this);
    }


    @Override
    public boolean esIngreso() {
        return false;
    }

    @Override
    public void validarTransaccion() {
      Validador.validarTransaccion(this.usuarioOrigen,this.monto);
    }

    @Override
    public void getInfoTransaccion() {
        super.getInfoTransaccion();
        System.out.println("\nTransferencia realizada a: " + this.usuarioDestino.getNombre() + " de: " + this.usuarioOrigen.getNombre());
        System.out.println("De un valor de: $" + monto);
        System.out.println("Cédula del destinatario: " + this.usuarioDestino.getCedula());
        System.out.println("Cédula del transfiriente: " + this.usuarioOrigen.getCedula());
    }
}

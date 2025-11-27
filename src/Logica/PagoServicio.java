package Logica;

import Logica.Excepciones.SaldoInsuficienteException;

import java.util.Scanner;

public class PagoServicio extends Transaccion {
    //atributos
    private String empresa, tipoServicio;

    //Constructor
    public PagoServicio(double monto, Usuario usuario, String empresa, String tipoServicio) {
        super(monto, usuario);
        this.empresa = empresa;
        this.tipoServicio = tipoServicio;

        validarTransaccion();

        usuario.getBilletera().restarSaldo(monto);
        usuario.getBilletera().agregarTransaccion(this);
    }

    @Override
    public boolean esIngreso() {
        return false;
    }

    @Override
    public void validarTransaccion() {
        if (usuario.getBilletera().getSaldo() < monto) {
            throw new SaldoInsuficienteException("Saldo insuficiente para pagar el servicio " + tipoServicio);
        }
    }

    @Override
    public void getInfoTransaccion() {
        super.getInfoTransaccion();
        System.out.println("Empresa:" + this.empresa);
        System.out.println("Servicio:" + this.tipoServicio);
    }
}

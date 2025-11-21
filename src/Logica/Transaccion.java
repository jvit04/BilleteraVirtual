package Logica;

import java.time.LocalDateTime;

public abstract class Transaccion {
    //atributos
    protected double  monto;
    protected String idTransaccion;
    private LocalDateTime fechaHora;
    private Usuario usuario;
    private int contadorID;

    //Constructores
    public Transaccion(double monto, Usuario usuario) {
        this.monto = monto;
        this.usuario = usuario;
    }

    public Transaccion(Usuario usuario, double monto, LocalDateTime fechaHora) {
        this.usuario = usuario;
        this.monto = monto;
        this.fechaHora = fechaHora;
    }

    public double getMonto() {
        return monto;
    }
    public void getInfoTransaccion(){
        System.out.println(":)");
    }

    public abstract boolean esIngreso();
}


package Logica;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Transaccion {
    //atributos
    protected double  monto;
    protected String idTransaccion;
    private LocalDateTime fechaHora;
    private Usuario usuario;
    private int contadorID;
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    String fechaFormateada = this.fechaHora.format(formato);
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
            System.out.println("Tipo: " + this.getClass().getSimpleName());
            System.out.println("ID Transacción: " + this.idTransaccion);
            System.out.println("Fecha: " + this.fechaFormateada);
            System.out.println("Monto: $" + this.monto);
            if (this.usuario != null) {
                System.out.println("Realizado por: " + this.usuario.getNombre());
            }
        }


    public abstract boolean esIngreso();
}


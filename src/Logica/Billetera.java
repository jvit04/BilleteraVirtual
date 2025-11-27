package Logica;

import Logica.Excepciones.SaldoInsuficienteException;

import java.util.ArrayList;
import java.util.List;

public class Billetera {
    //Atributos
private double saldo;
private List<Transaccion> transacciones;

//Constructores
    public Billetera() {this.saldo = 0;
    transacciones = new ArrayList<>();} // si no ingresa un monto, entonces empieza con 0

    public Billetera(double saldo) {
        this.saldo = saldo;
        transacciones = new ArrayList<Transaccion>();
    } // creamos nuestra billetera con un saldo inicial

    //Metodos
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {}

    public void infoSaldo(){
        System.out.println("Su saldo actual es: " + " $" + saldo);
    }

    public List<Transaccion> getHistorial(){
        return transacciones;
        } // nos devuelve la lista de transacciones


    protected void aumentarSaldo(double monto){
    saldo+=monto;
    }
    protected void restarSaldo(double monto){saldo-=monto;}


    /**
     * Para agregar la transaccion a la lista de transacciones de nuestra billetera
     */
    protected void agregarTransaccion(Transaccion transaccion){
        this.transacciones.add(transaccion);
    }
}



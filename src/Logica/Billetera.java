package Logica;

import java.util.List;

public class Billetera {
    //Atributos
private double saldo;
private List<Transaccion> transacciones;

//Constructores
    public Billetera() {
    }
    public Billetera(double saldo) {
        this.saldo = saldo;
    }

    //Metodos


    public double getSaldo() {
        return saldo;
    }
    public void infoSaldo(){
        System.out.println("Su saldo actual es: " + saldo);
    }

    public List<Transaccion> getHistorial(){
        return transacciones;
        }
protected void actualizarSaldo(double monto){

}
protected void agregarTransaccion(Transaccion transaccion){
this.transacciones.add(transaccion);

if(transaccion.esIngreso()){
    actualizarSaldo(transaccion.getMonto());
}
else {
    if (this.saldo >= transaccion.getMonto()){
        actualizarSaldo(-transaccion.getMonto());
    } else {
        //excepcion
    }
}
}

}



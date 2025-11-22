package Logica;

import java.time.LocalDateTime;


//Constructor
public class Deposito extends Transaccion {
    public Deposito(double monto, Usuario usuario, LocalDateTime fechaHora) {
        super(monto, usuario);
    }

    @Override
    public boolean esIngreso() {
        return false;
    }
    @Override
    public void getInfoTransaccion() {
        super.getInfoTransaccion();
        System.out.println("Deposito en efectivo");
    }


}

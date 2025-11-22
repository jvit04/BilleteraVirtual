package Logica;

public class PagoServicio extends Transaccion {
    //atributos
    private String empresa, tipoServicio;

    //Constructor
    public PagoServicio(double monto, Usuario usuario) {
        super(monto, usuario);
        this.empresa = empresa;
        this.tipoServicio = tipoServicio;
    }

    @Override
    public boolean esIngreso() {
        return false;
    }

    @Override
    public void getInfoTransaccion() {
        super.getInfoTransaccion();
        System.out.println("Empresa:" + this.empresa);
        System.out.println("Servicio:" + this.tipoServicio);
    }
}

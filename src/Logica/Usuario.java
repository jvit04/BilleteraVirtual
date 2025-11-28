package Logica;

import java.time.LocalDate;

public class Usuario {
    //Atributos
    private String cedula;
    private LocalDate fechaNacimiento;
    private String nombre;
    private String ciudad;
    private String alias;
    private String email;
    private Billetera billetera;

    //Constructores

    public Usuario(String cedula, LocalDate fechaNacimiento, String nombre, String ciudad, String alias, String email) {
        //Zona de validaciones
        Validador.validarCedula(cedula);
        Validador.validarCorreo(email);
        Validador.validarAlias(alias);

        this.cedula = cedula;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.alias = alias;
        this.email = email;
        this.billetera = new Billetera();


    }
/* Nota:  Verificar la utilidad de estos constructores, realmente es conveniente tener a los usuarios registrados solo
ciertos datos?

    public Usuario(String cedula, String nombre, String alias) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.alias = alias;
        this.billetera = new Billetera();
    }

    public Usuario(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }
*/
    //Getters
    public String getCedula() {
        return cedula;
    }

    public String getAlias() {
        return alias;
    }

    public Billetera getBilletera() {
        return billetera;
    }
    public String getNombre() {
        return nombre;
    }

}


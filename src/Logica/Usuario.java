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
    public Usuario(String cedula, String nombre, String alias) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.alias = alias;
    }

    public Usuario(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

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
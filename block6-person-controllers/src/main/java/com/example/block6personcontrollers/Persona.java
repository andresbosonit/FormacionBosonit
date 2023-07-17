package com.example.block6personcontrollers;

public class Persona {
    private String nombre;
    private int edad;
    private String poblacion;

    public Persona(){

    }
    public Persona(String nombre, int edad, String poblacion) {
        this.nombre = nombre;
        this.edad = edad;
        this.poblacion = poblacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }
}

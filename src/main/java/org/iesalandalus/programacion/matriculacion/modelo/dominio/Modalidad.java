package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum Modalidad {
    PRESENCIAL("Presencial"),
    SEMIPRESENCIAL("Semi-presencial");

    private String cadenaAMostrar;

    private Modalidad(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    @Override
    public String toString() {
        return cadenaAMostrar;
    }

    public String imprimir() {
        return cadenaAMostrar;
    }





}


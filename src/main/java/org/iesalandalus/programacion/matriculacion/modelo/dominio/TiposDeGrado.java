package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum TiposDeGrado {
    GRADOD("Grado D"),
    GRADOE("Grado E");

    private final String cadenaAMostrar;

    private TiposDeGrado(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String imprimir() {
        return cadenaAMostrar;
    }
    @Override
    public String toString() {
        return cadenaAMostrar;
    }


}

package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public class GradoE extends Grado {
    private int numEdiciones;

    public GradoE(String nombre, int numAnios, int numEdiciones) {
        super(nombre);
        setNumAnios(numAnios);
        setNumEdiciones(numEdiciones);
    }

    public int getNumEdiciones() {
        return numEdiciones;
    }

    public void setNumEdiciones(int numEdiciones) {
        if (numEdiciones < 0) {
            throw new IllegalArgumentException("El número de ediciones no puede ser negativo");
        }
        this.numEdiciones = numEdiciones;
    }

    @Override
    public void setNumAnios(int numAnios) {
        if (numAnios != 1) {
            throw new IllegalArgumentException("Los Grados E deben tener 1 año");
        }
        this.numAnios = numAnios;
    }
    @Override
    public String toString() {
        return String.format("(%s) - %s (%d ediciones)", iniciales, getNombre(), numEdiciones);
    }
}
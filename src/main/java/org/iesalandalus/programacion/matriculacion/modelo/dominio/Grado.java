package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum Grado {
    GDCFGB("Grado D Ciclo Formativo de Grado Básico"),
    GDCFGM("Grado D Ciclo formativo de Grado Medio"),
    GDCFGS("Grado D Ciclo Formativo de Grado Superior");


    private final String cadenaAMostrar;

    // Constructor
    private Grado(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    // Método imprimir
    public String imprimir() {
        // Aquí, el índice de cada constante se obtiene con ordinal()
        return ordinal() + 1 + ".- " + cadenaAMostrar;
    }

    // Método toString
    @Override
    public String toString() {
        return cadenaAMostrar;
    }
}
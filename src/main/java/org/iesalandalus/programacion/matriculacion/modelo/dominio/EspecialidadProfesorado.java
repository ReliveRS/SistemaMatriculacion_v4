package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum EspecialidadProfesorado {

    // Declaración de las constantes del enumerado con su correspondiente representación en texto.
    INFORMATICA("Informática"),
    SISTEMAS("Sistemas y Aplicaciones Informáticas"),
    FOL("Formación y Orientación Laboral");

    // Atributo que almacena la representación textual del curso.
    private final String cadenaAMostrar;


    EspecialidadProfesorado(String cadenaAMostrar) {
        if (cadenaAMostrar == null || cadenaAMostrar.trim().isEmpty()) {
            throw new IllegalArgumentException("La cadena a mostrar no puede ser nula ni vacía.");
        }
        this.cadenaAMostrar = cadenaAMostrar;
    }

    // Método imprimir: devuelve una cadena en el formato "dígito.-cadenaAMostrar".
    // Recibe un parámetro 'digito' para incluir en el formato.
    public String imprimir(int digito) {
        return digito + ".-" + cadenaAMostrar; // Ejemplo: "1.-Primero" o "2.-Segundo".
    }

    // Sobrescribe el método toString de la clase Object.
    // Devuelve la representación textual almacenada en cadenaAMostrar.
    @Override
    public String toString() {
        return cadenaAMostrar; // Ejemplo: "Primero" o "Segundo".
    }










}

package org.iesalandalus.programacion.matriculacion.modelo.dominio;

// Enumerado Curso: representa los posibles valores de un curso (PRIMERO o SEGUNDO).
public enum Curso {

    // Declaración de las constantes del enumerado con su correspondiente representación en texto.
    PRIMERO("Primero"),
    SEGUNDO("Segundo");

    // Atributo que almacena la representación textual del curso.
    private final String cadenaAMostrar;

    // Constructor privado: se invoca automáticamente para cada valor del enumerado.
    // Asocia la cadena proporcionada a cada constante.
    private Curso(String cadenaAMostrar) {
        if (cadenaAMostrar == null || cadenaAMostrar.trim().isEmpty()) {
            throw new IllegalArgumentException("La cadena a mostrar no puede ser nula ni vacía.");
        }
        this.cadenaAMostrar = cadenaAMostrar;
    }

    // Método imprimir: devuelve una cadena en el formato "dígito.-cadenaAMostrar".
    // Recibe un parámetro 'digito' para incluir en el formato.
    public String imprimir() {
        // Aquí, el índice de cada constante se obtiene con ordinal()
        return ordinal() + 1 + ".- " + cadenaAMostrar;
    }

    // Sobrescribe el método toString de la clase Object.
    // Devuelve la representación textual almacenada en cadenaAMostrar.
    @Override
    public String toString() {
        return cadenaAMostrar; // Ejemplo: "Primero" o "Segundo".
    }
}
package org.iesalandalus.programacion.matriculacion.vista;
//QUIZAS  ES POSIBLE QUITAR EL METODO OPCION
public enum Opcion {
    SALIR("Salir de la aplicación"),
    INSERTAR_ALUMNO("Insertar un nuevo alumno"),
    BUSCAR_ALUMNO("Buscar un alumno"),
    BORRAR_ALUMNO("Borrar un alumno"),
    MOSTRAR_ALUMNOS("Mostrar todos los alumnos"),
    INSERTAR_CICLO_FORMATIVO("Insertar un nuevo ciclo formativo"),
    BUSCAR_CICLO_FORMATIVO("Buscar un ciclo formativo"),
    BORRAR_CICLO_FORMATIVO("Borrar un ciclo formativo"),
    MOSTRAR_CICLOS_FORMATIVOS("Mostrar todos los ciclos formativos"),
    INSERTAR_ASIGNATURA("Insertar una nueva asignatura"),
    BUSCAR_ASIGNATURA("Buscar una asignatura"),
    BORRAR_ASIGNATURA("Borrar una asignatura"),
    MOSTRAR_ASIGNATURAS("Mostrar todas las asignaturas"),
    INSERTAR_MATRICULA("Insertar una nueva matrícula"),
    BUSCAR_MATRICULA("Buscar una matrícula"),
    ANULAR_MATRICULA("Anular una matrícula"),
    MOSTRAR_MATRICULAS("Mostrar todas las matrículas"),
    MOSTRAR_MATRICULAS_ALUMNO("Mostrar las matrículas de un alumno"),
    MOSTRAR_MATRICULAS_CURSO_ACADEMICO("Mostrar las matrículas de un curso académico"),
    MOSTRAR_MATRICULAS_CICLO_FORMATIVO("Mostrar las matrículas de un ciclo formativo");

    private String mensajeAMostrar;

    private Opcion(String mensajeAMostrar) {
        this.mensajeAMostrar = mensajeAMostrar;
    }

    public String getMensaje() {
        return mensajeAMostrar;
    }


    @Override
    public String toString() {
        return String.format("%d.- %s", ordinal(), mensajeAMostrar);
    }

    public static Opcion getOpcionSegunOrdinal(int ordinal) {
        if (esOrdinalValido(ordinal))
            return values()[ordinal];
        else
            throw new IllegalArgumentException("Ordinal de la opción no válido");
    }

    public static boolean esOrdinalValido(int ordinal) {
        return (ordinal >= 0 && ordinal <= values().length - 1);
    }
}
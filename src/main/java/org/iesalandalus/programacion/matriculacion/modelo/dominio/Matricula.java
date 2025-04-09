package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Matricula {

    // Constantes
    private static final int MAXIMO_MESES_ANTERIOR_ANULACION = 6;
    private static final int MAXIMO_DIAS_ANTERIOR_MATRICULA = 15;
    private static final int MAXIMO_NUMERO_HORAS_MATRICULA = 1000;
    private static final int MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA = 10;
    private static final String ER_CURSO_ACADEMICO = "\\d{2}-\\d{2}";

    // Atributos
    private int idMatricula;
    private String cursoAcademico;
    private LocalDate fechaMatriculacion;
    private LocalDate fechaAnulacion;
    private List<Asignatura> coleccionAsignaturas;
    private Alumno alumno;

    public Matricula(int idMatricula, String cursoAcademico, LocalDate fechaMatriculacion, Alumno alumno, List<Asignatura> coleccionAsignaturas) {
        setIdMatricula(idMatricula);
        setCursoAcademico(cursoAcademico);
        setFechaMatriculacion(fechaMatriculacion);
        setAlumno(alumno);
        setColeccionAsignaturas(coleccionAsignaturas);
    }

    // Constructor copia
    public Matricula(Matricula matricula) {
        if (matricula == null) {
            throw new IllegalArgumentException("No se puede copiar una matrícula nula.");
        }
        setIdMatricula(matricula.getIdMatricula());
        setCursoAcademico(matricula.getCursoAcademico());
        setFechaMatriculacion(matricula.getFechaMatriculacion());
        setAlumno(matricula.getAlumno());
        setColeccionAsignaturas(matricula.getColeccionAsignaturas());
    }



    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        if (idMatricula <= 0) {
            throw new IllegalArgumentException("La id de la matrícula debe ser positiva."); // Lanzamos excepción si la ID es negativa o cero
        }
        this.idMatricula = idMatricula; // Asignamos el valor de idMatricula
    }

    public String getCursoAcademico() {
        return cursoAcademico;
    }

    // Setter para cursoAcademico, validando que cumpla con el formato "dd-dd"
    public void setCursoAcademico(String cursoAcademico) {
        if (!cursoAcademico.matches(ER_CURSO_ACADEMICO)) {
            throw new IllegalArgumentException("El curso académico debe tener el formato dd-dd."); // Lanzamos excepción si el formato no es válido
        }
        this.cursoAcademico = cursoAcademico; // Asignamos el valor de cursoAcademico
    }

    public LocalDate getFechaMatriculacion() {
        return fechaMatriculacion;
    }

    public void setFechaMatriculacion(LocalDate fechaMatriculacion) {
        if (fechaMatriculacion == null) {
            throw new IllegalArgumentException("La fecha de matriculación no puede ser nula.");
        }
        if (fechaMatriculacion.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de matriculación no puede ser futura.");
        }
        this.fechaMatriculacion = fechaMatriculacion;
    }

    public LocalDate getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(LocalDate fechaAnulacion) {
        if (fechaAnulacion != null) {
            if (fechaAnulacion.isBefore(fechaMatriculacion)) {
                throw new IllegalArgumentException("La fecha de anulación no puede ser anterior a la matriculación.");
            }
            LocalDate fechaLimite = fechaMatriculacion.plusMonths(MAXIMO_MESES_ANTERIOR_ANULACION);
            if (fechaAnulacion.isAfter(fechaLimite)) {
                throw new IllegalArgumentException("La anulación no puede realizarse después de " +
                        MAXIMO_MESES_ANTERIOR_ANULACION + " meses de la matriculación.");
            }
        }
        this.fechaAnulacion = fechaAnulacion;
    }

    public Alumno getAlumno() {
        if (alumno == null) {
            throw new IllegalStateException("El alumno no ha sido establecido.");
        }
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        if (alumno == null) {
            throw new NullPointerException("El alumno no puede ser null.");
        }
        this.alumno = alumno;
    }

    public List<Asignatura> getColeccionAsignaturas() {
        return new ArrayList<>(coleccionAsignaturas); // Devuelve una copia de la lista
    }

    public void setColeccionAsignaturas(List<Asignatura> coleccionAsignaturas) {
        if (coleccionAsignaturas == null) {
            throw new IllegalArgumentException("La colección de asignaturas no puede ser nula.");
        }
        if (coleccionAsignaturas.size() > MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA) {
            throw new IllegalArgumentException("La colección de asignaturas supera el máximo permitido (" + MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA + ").");
        }
        this.coleccionAsignaturas = new ArrayList<>(coleccionAsignaturas); // Copia la lista
    }


    private boolean superaMaximoNumeroHorasMatricula() {
        int totalHoras = 0;
        for (Asignatura asignatura : coleccionAsignaturas) {
            if (asignatura == null) {
                throw new IllegalStateException("La colección de asignaturas contiene elementos nulos.");
            }
            totalHoras += asignatura.getHorasAnuales();
        }
        return totalHoras > MAXIMO_NUMERO_HORAS_MATRICULA;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Matricula matricula = (Matricula) obj;
        return idMatricula == matricula.idMatricula;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMatricula);
    }

    private String asignaturasMatricula() {
        StringBuilder sb = new StringBuilder();
        for (Asignatura asignatura : coleccionAsignaturas) {
            sb.append(asignatura.getNombre()).append(" ");
        }
        return sb.toString().trim();
    }

    // Método para imprimir la matrícula
    public String imprimir() {
        return String.format("Matrícula [%d, %s, %s, %s, %s]",
                idMatricula, cursoAcademico, fechaMatriculacion,
                fechaAnulacion != null ? fechaAnulacion : "No anulada", asignaturasMatricula());
    }

    @Override
    public String toString() {
        return "Matrícula [ID: " + idMatricula + ", Curso Académico: " + cursoAcademico +
                ", Fecha Matriculación: " + fechaMatriculacion +
                ", Alumno: " + alumno.getNombre() + ", Asignaturas: " + asignaturasMatricula() + "]";
    }


}
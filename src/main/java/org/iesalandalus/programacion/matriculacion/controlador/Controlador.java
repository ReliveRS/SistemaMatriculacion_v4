package org.iesalandalus.programacion.matriculacion.controlador;

import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public class Controlador {
    private Vista vista;
    private Modelo modelo;

    public Controlador(Modelo modelo, Vista vista) {
        if (modelo == null) {
            throw new IllegalArgumentException("ERROR: El modelo no puede ser nulo.");
        }
        if (vista == null) {
            throw new IllegalArgumentException("ERROR: La vista no puede ser nula.");
        }
        this.modelo = modelo;
        this.vista = vista;
        this.vista.setControlador(this);
    }

    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar() {
        modelo.terminar();
    }

    public void insertar(Alumno alumno) throws OperationNotSupportedException {

        modelo.insertar(alumno);
    }

    public Alumno buscar(Alumno alumno) {

        return modelo.buscar(alumno);
    }

    public void borrar(Alumno alumno) throws OperationNotSupportedException {

        modelo.borrar(alumno);
    }

    public List<Alumno> getAlumnos() {
        List<Alumno> alumnos = modelo.getAlumnos();
        if (alumnos == null) {
            throw new IllegalStateException("ERROR: La lista de alumnos es nula.");
        }
        return List.copyOf(alumnos);
    }
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {

        modelo.insertar(asignatura);
    }

    public Asignatura buscar(Asignatura asignatura) {

        return modelo.buscar(asignatura);
    }

    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new IllegalArgumentException("ERROR: La asignatura no puede ser nula.");
        }
        modelo.borrar(asignatura);
    }

    public List<Asignatura> getAsignaturas() {
        return List.copyOf(modelo.getAsignaturas());
    }

    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: El ciclo formativo no puede ser nulo.");
        }
        modelo.insertar(cicloFormativo);
    }

    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: El ciclo formativo no puede ser nulo.");
        }
        return modelo.buscar(cicloFormativo);
    }

    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: El ciclo formativo no puede ser nulo.");
        }
        modelo.borrar(cicloFormativo);
    }

    public List<CicloFormativo> getCiclosFormativos() {
        return List.copyOf(modelo.getCiclosFormativos());
    }

    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new IllegalArgumentException("ERROR: La matrícula no puede ser nula.");
        }
        modelo.insertar(matricula);
    }

    public Matricula buscar(Matricula matricula) {
        if (matricula == null) {
            throw new IllegalArgumentException("ERROR: La matrícula no puede ser nula.");
        }
        return modelo.buscar(matricula);
    }

    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new IllegalArgumentException("ERROR: La matrícula no puede ser nula.");
        }
        modelo.borrar(matricula);
    }

    public List<Matricula> getMatriculas() {
        return List.copyOf(modelo.getMatriculas());
    }

    public List<Matricula> getMatriculas(Alumno alumno) {
        if (alumno == null) {
            throw new IllegalArgumentException("ERROR: El alumno no puede ser nulo.");
        }
        return List.copyOf(modelo.getMatriculas(alumno));
    }

    public List<Matricula> getMatriculas(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: El ciclo formativo no puede ser nulo.");
        }
        return List.copyOf(modelo.getMatriculas(String.valueOf(cicloFormativo)));
    }

    public List<Matricula> getMatriculas(String cursoAcademico) {
        if (cursoAcademico == null || cursoAcademico.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El curso académico no puede ser nulo o vacío.");
        }
        return List.copyOf(modelo.getMatriculas(cursoAcademico));
    }
}
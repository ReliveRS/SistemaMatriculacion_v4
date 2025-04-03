package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Matriculas;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Modelo {
    private List<Alumno> alumnos;
    private List<Asignatura> asignaturas;
    private List<CicloFormativo> ciclosFormativos;
    private List<Matricula> matriculas;

    //Crea el método comenzar que creará la instancia de las clases de negocio.
    public void comenzar() {
        this.alumnos = new ArrayList<>();
        this.asignaturas = new ArrayList<>();
        this.ciclosFormativos = new ArrayList<>();
        this.matriculas = new ArrayList<>();
    }

    public void terminar() {
        System.out.println("El modelo ha terminado.");
    }



    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new OperationNotSupportedException("ERROR: El alumno no puede ser nulo.");
        }
        if (alumnos.contains(alumno)) {
            throw new OperationNotSupportedException("ERROR: El alumno ya existe.");
        }
        alumnos.add(alumno);
    }

    public Alumno buscar(Alumno alumno) {
        if (alumno == null) {
            return null;
        }
        for (Alumno a : alumnos) {
            if (a.equals(alumno)) {
                return a;
            }
        }
        return null;
    }

    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new OperationNotSupportedException("ERROR: El alumno no puede ser nulo.");
        }
        if (!alumnos.remove(alumno)) {
            throw new OperationNotSupportedException("ERROR: El alumno no existe.");
        }
    }

    public List<Alumno> getAlumnos() {
        return new ArrayList<>(alumnos); // Devuelve una copia de la lista
    }




    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new OperationNotSupportedException("ERROR: La asignatura no puede ser nula.");
        }
        if (asignaturas.contains(asignatura)) {
            throw new OperationNotSupportedException("ERROR: La asignatura ya existe.");
        }
        asignaturas.add(asignatura);
    }


    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null) {
            return null;
        }
        for (Asignatura a : asignaturas) {
            if (a.equals(asignatura)) {
                return a;
            }
        }
        return null;
    }

    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new OperationNotSupportedException("ERROR: La asignatura no puede ser nula.");
        }
        if (!asignaturas.remove(asignatura)) {
            throw new OperationNotSupportedException("ERROR: La asignatura no existe.");
        }
    }

    public List<Asignatura> getAsignaturas() {
        return new ArrayList<>(asignaturas); // Devuelve una copia de la lista
    }



    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new OperationNotSupportedException("ERROR: El ciclo formativo no puede ser nulo.");
        }
        if (ciclosFormativos.contains(cicloFormativo)) {
            throw new OperationNotSupportedException("ERROR: El ciclo formativo ya existe.");
        }
        ciclosFormativos.add(cicloFormativo);
    }

    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            return null;
        }
        for (CicloFormativo cf : ciclosFormativos) {
            if (cf.equals(cicloFormativo)) {
                return cf;
            }
        }
        return null;
    }

    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new OperationNotSupportedException("ERROR: El ciclo formativo no puede ser nulo.");
        }
        if (!ciclosFormativos.remove(cicloFormativo)) {
            throw new OperationNotSupportedException("ERROR: El ciclo formativo no existe.");
        }
    }

    public List<CicloFormativo> getCiclosFormativos() {
        return new ArrayList<>(ciclosFormativos); // Devuelve una copia de la lista
    }



    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new OperationNotSupportedException("ERROR: La matrícula no puede ser nula.");
        }
        if (matriculas.contains(matricula)) {
            throw new OperationNotSupportedException("ERROR: La matrícula ya existe.");
        }
        matriculas.add(matricula);
    }

    public Matricula buscar(Matricula matricula) {
        if (matricula == null) {
            return null;
        }
        for (Matricula m : matriculas) {
            if (m.equals(matricula)) {
                return m;
            }
        }
        return null;
    }

    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new OperationNotSupportedException("ERROR: La matrícula no puede ser nula.");
        }
        if (!matriculas.remove(matricula)) {
            throw new OperationNotSupportedException("ERROR: La matrícula no existe.");
        }
    }

    public List<Matricula> getMatriculas() {
        return new ArrayList<>(matriculas); // Devuelve una copia de la lista
    }



    public List<Matricula> getMatriculas(Alumno alumno) {
        List<Matricula> matriculasAlumno = new ArrayList<>();
        for (Matricula m : matriculas) {
            if (m.getAlumno().equals(alumno)) {
                matriculasAlumno.add(m);
            }
        }
        return matriculasAlumno;
    }

    public List<Matricula> getMatriculas(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: El ciclo formativo no puede ser nulo.");
        }
        return matriculas.stream()
                .filter(matricula -> matricula.getColeccionAsignaturas().stream()
                        .anyMatch(asignatura -> asignatura.getCicloFormativo().equals(cicloFormativo)))
                .collect(Collectors.toList());
    }

    public List<Matricula> getMatriculas(String cursoAcademico) {
        List<Matricula> matriculasCurso = new ArrayList<>();
        for (Matricula m : matriculas) {
            if (m.getCursoAcademico().equals(cursoAcademico)) {
                matriculasCurso.add(m);
            }
        }
        return matriculasCurso;
    }




}

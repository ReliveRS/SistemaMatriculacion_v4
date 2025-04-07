package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IFuenteDatos;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;
public class Modelo {
    /*
    private IFuenteDatos fuenteDatos;
    private List<Alumno> alumnos;
    private List<Asignatura> asignaturas;
    private List<CicloFormativo> ciclosFormativos;
    private List<Matricula> matriculas;

     */
    private IFuenteDatos fuenteDatos;
    private IAlumnos alumnos;
    private IAsignaturas asignaturas;
    private ICiclosFormativos ciclosFormativos;
    private IMatriculas matriculas;

    public Modelo(FactoriaFuenteDatos factoriaFuenteDatos) {
        if (factoriaFuenteDatos == null) {
            throw new IllegalArgumentException("La factoría de fuente de datos no puede ser nula.");
        }
        setFuenteDatos(factoriaFuenteDatos.crear());
    }
    private void setFuenteDatos(IFuenteDatos fuenteDatos) {
        if (fuenteDatos == null) {
            throw new IllegalArgumentException("La fuente de datos no puede ser nula.");
        }
        this.fuenteDatos = fuenteDatos;
    }
    public void comenzar() {
        alumnos = fuenteDatos.crearAlumnos();
        asignaturas = fuenteDatos.crearAsignaturas();
        ciclosFormativos = fuenteDatos.crearCiclosFormativos();
        matriculas = fuenteDatos.crearMatriculas();

        System.out.println("El modelo ha comenzado con la fuente de datos: " + fuenteDatos.getClass().getSimpleName());
    }

    public void terminar() {
        System.out.println("El modelo está finalizando...");

        if (alumnos != null) {
            alumnos.terminar();
        }
        if (asignaturas != null) {
            asignaturas.terminar();
        }
        if (ciclosFormativos != null) {
            ciclosFormativos.terminar();
        }
        if (matriculas != null) {
            matriculas.terminar();
        }

        System.out.println("El modelo ha terminado correctamente.");
    }



    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }

        // Delegar la inserción en la implementación concreta
        alumnos.insertar(alumno);
    }



    public Alumno buscar(Alumno alumno) {
        if (alumno == null) {
            return null;
        }
        return alumnos.buscar(alumno);
    }

    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }
        alumnos.borrar(alumno); // Delegamos en la implementación concreta
    }

    public List<Alumno> getAlumnos() {
        System.out.println("Obteniendo lista de alumnos desde el modelo...");

        List<Alumno> listaAlumnos = alumnos.get();
        if (listaAlumnos == null) {
            return new ArrayList<>(); // Devuelve una lista vacía si es nula
        }
        return listaAlumnos;
    }




    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }
        asignaturas.insertar(asignatura);
    }

    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null) {
            return null;
        }
        return asignaturas.buscar(asignatura);
    }

    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }
        asignaturas.borrar(asignatura);
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas.get();
    }



    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }
        ciclosFormativos.insertar(cicloFormativo);
    }

    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            return null;
        }
        return ciclosFormativos.buscar(cicloFormativo);
    }

    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede borrar un ciclo formativo nulo.");
        }
        ciclosFormativos.borrar(cicloFormativo);
    }

    public List<CicloFormativo> getCiclosFormativos() {
        List<CicloFormativo> listaCiclos = ciclosFormativos.get();
        if (listaCiclos == null) {
            return new ArrayList<>(); // Devuelve una lista vacía si es nula
        }
        return listaCiclos;
    }





    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }

        // Buscar al alumno asociado a la matrícula
        Alumno alumno = alumnos.buscar(matricula.getAlumno());
        if (alumno == null) {
            throw new OperationNotSupportedException("ERROR: El alumno no existe.");
        }

        // Verificar que todas las asignaturas de la matrícula existen en el sistema
        for (Asignatura asignatura : matricula.getColeccionAsignaturas()) {
            Asignatura asignaturaExistente = asignaturas.buscar(asignatura);
            if (asignaturaExistente == null) {
                throw new OperationNotSupportedException("ERROR: La asignatura " + asignatura.getNombre() + " no existe.");
            }
        }

        // Insertar la matrícula en el sistema
        matriculas.insertar(new Matricula(matricula));
    }


    public Matricula buscar(Matricula matricula) {
        if (matricula == null) {
            return null;
        }

        return matriculas.buscar(matricula);
    }

    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }

        matriculas.borrar(matricula);
    }

    public List<Matricula> getMatriculas() {
        return matriculas.get(); // Delegamos en la implementación de IMatriculas
    }



    public List<Matricula> getMatriculas(Alumno alumno) {
        if (alumno == null) {
            throw new IllegalArgumentException("ERROR: El alumno no puede ser nulo.");
        }
        return matriculas.get(alumno); // Delegamos en la implementación de IMatriculas
    }

    public List<Matricula> getMatriculas(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: El ciclo formativo no puede ser nulo.");
        }
        return matriculas.get().stream()
                .filter(matricula -> matricula.getColeccionAsignaturas().stream()
                        .anyMatch(asignatura -> asignatura.getCicloFormativo().equals(cicloFormativo)))
                .collect(Collectors.toList());
    }


    public List<Matricula> getMatriculas(String cursoAcademico) {
        if (cursoAcademico == null || cursoAcademico.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El curso académico no puede ser nulo ni vacío.");
        }
        return matriculas.get().stream()
                .filter(matricula -> matricula.getCursoAcademico().equals(cursoAcademico))
                .collect(Collectors.toList());
    }





}

package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Matriculas implements IMatriculas {

    private static Matriculas instancia; // Instancia única para el patrón Singleton
    private List<Matricula> coleccionMatriculas;

    // Constructor privado para evitar instanciación directa
    public Matriculas() {
        coleccionMatriculas = new ArrayList<>();
    }

    // Método estático para obtener la instancia única
    public static Matriculas getInstancia() {
        if (instancia == null) {
            instancia = new Matriculas();
        }
        return instancia;
    }

    public void comenzar() {
        coleccionMatriculas = new ArrayList<>(); // Inicializa una nueva lista vacía
        System.out.println("Colección de matrículas inicializada.");
    }

    public void terminar() {
        coleccionMatriculas.clear(); // Limpia la lista existente
        System.out.println("Colección de matrículas limpiada.");
    }

    public List<Matricula> get() {
        return copiaProfundaMatriculas(coleccionMatriculas);
    }

    @Override
    public int getTamano() {
        return 0;
    }

    public List<Matricula> copiaProfundaMatriculas(List<Matricula> matriculas) {
        List<Matricula> otrasMatriculas = new ArrayList<>();
        for (Matricula matricula : matriculas) {
            otrasMatriculas.add(new Matricula(matricula));
        }
        return otrasMatriculas;
    }

    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new IllegalArgumentException("ERROR: No se puede insertar una matrícula nula.");
        }
        if (!coleccionMatriculas.contains(matricula)) {
            coleccionMatriculas.add(new Matricula(matricula));
        } else {
            throw new OperationNotSupportedException("ERROR: La matrícula ya existe en la lista.");
        }
    }

    public Matricula buscar(Matricula matricula) {
        if (matricula == null) {
            throw new IllegalArgumentException("ERROR: La matrícula no puede ser nula.");
        }
        int indice = coleccionMatriculas.indexOf(matricula);
        if (indice == -1) {
            return null;
        } else {
            return new Matricula(coleccionMatriculas.get(indice));
        }
    }

    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new IllegalArgumentException("ERROR: La matrícula no puede ser nula.");
        }
        int indice = coleccionMatriculas.indexOf(matricula);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: La matrícula no existe en la lista.");
        } else {
            coleccionMatriculas.remove(indice);
        }
    }

    public List<Matricula> get(Alumno alumno) {
        if (alumno == null) {
            throw new IllegalArgumentException("ERROR: El alumno no puede ser nulo.");
        }

        List<Matricula> matriculasAlumno = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula.getAlumno().equals(alumno)) {
                matriculasAlumno.add(new Matricula(matricula));
            }
        }
        return matriculasAlumno;
    }

    public List<Matricula> get(String cursoAcademico) {
        if (cursoAcademico == null || cursoAcademico.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El curso académico no puede ser nulo o vacío.");
        }

        List<Matricula> matriculasCurso = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula.getCursoAcademico().equals(cursoAcademico)) {
                matriculasCurso.add(new Matricula(matricula));
            }
        }
        return matriculasCurso;
    }

    public List<Matricula> get(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: El ciclo formativo no puede ser nulo.");
        }

        List<Matricula> matriculasCiclo = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula.getColeccionAsignaturas().stream()
                    .anyMatch(asignatura -> asignatura.getCicloFormativo().equals(cicloFormativo))) {
                matriculasCiclo.add(new Matricula(matricula));
            }
        }
        return matriculasCiclo;
    }
}
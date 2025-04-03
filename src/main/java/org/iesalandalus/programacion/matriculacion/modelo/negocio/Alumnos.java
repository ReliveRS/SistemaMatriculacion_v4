package org.iesalandalus.programacion.matriculacion.modelo.negocio;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Alumnos {

    private List<Alumno> coleccionAlumnos;



    public Alumnos() {
        coleccionAlumnos = new ArrayList<>();
    }

    public List<Alumno> get(){
        return copiaProfundaAlumnos(coleccionAlumnos);
    }

    private List<Alumno> copiaProfundaAlumnos(List<Alumno> alumnos) {
        List<Alumno> otrosAlumnos = new ArrayList<>();
        for (Alumno alumno : alumnos){
            otrosAlumnos.add(new Alumno(alumno));
        }
        return otrosAlumnos;
    }

    private int GetTamano(){
        return coleccionAlumnos.size();

    }

    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new IllegalArgumentException("El alumno no puede ser nulo.");
        }

        if(!coleccionAlumnos.contains(alumno))
        {   coleccionAlumnos.add(new Alumno(alumno));

        }else {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }

    }




    public Alumno buscar(Alumno alumno) {
        if(alumno == null) {
            throw new IllegalArgumentException("El alumno no puede ser nulo.");
        }
        int indice = coleccionAlumnos.indexOf(alumno);
        if(indice == -1){
            return null;
        }else{
            return new Alumno(coleccionAlumnos.get(indice));
        }
    }


    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new IllegalArgumentException("ERROR: El alumno no puede ser nulo.");
        }
        int indice = coleccionAlumnos.indexOf(alumno);
        if (indice == -1) {
            throw new OperationNotSupportedException("Error: No existe ningun alumno con ese DNI");
        }else{
            coleccionAlumnos.remove(indice);
        }
    }










}

package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Asignaturas {

    private List<Asignatura> coleccionAsignaturas;

    public Asignaturas() {

        coleccionAsignaturas = new ArrayList<>();
    }

    public List<Asignatura> get(){
        return copiaProfundaAsignaturas(coleccionAsignaturas);
    }


    private List<Asignatura> copiaProfundaAsignaturas(List<Asignatura> asignaturas) {
        List<Asignatura> otrosAsignaturas = new ArrayList<>();
        for (Asignatura asignatura : asignaturas) {
            otrosAsignaturas.add(new Asignatura(asignatura));
        }
        return otrosAsignaturas;
    }

    private int getTamano(){
        return coleccionAsignaturas.size();
    }

    public void insertar(Asignatura asignatura)throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new IllegalArgumentException("ERROR:No se puede insertar una asignatura nula.");
        }
       if(!coleccionAsignaturas.contains(asignatura)) {
           coleccionAsignaturas.add(new Asignatura(asignatura));
       }else{
           throw new OperationNotSupportedException("ERROR: La asignatura ya existe en la lista.");
       }
    }


    public Asignatura buscar(Asignatura asignatura) {
        if(asignatura == null) {
            throw new IllegalArgumentException("La asignatura no puede ser nula.");
        }
        int indice = coleccionAsignaturas.indexOf(asignatura);
        if(indice == -1) {
            return null;
        }else{
            return new Asignatura(coleccionAsignaturas.get(indice));
        }
    }


    public void borrar(Asignatura asignatura)throws OperationNotSupportedException {
        if(asignatura == null) {
            throw new NullPointerException("La asignatura no puede ser nula.");
        }
        int indice = coleccionAsignaturas.indexOf(asignatura);
        if(indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura con ese nombre.");
        }else{
        }coleccionAsignaturas.remove(indice);
    }










}

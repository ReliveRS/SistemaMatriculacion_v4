package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface IAlumnos {

    void comenzar();

    void terminar();

    List<Alumno> get();

    int getTamano();

    void insertar(Alumno alumno) throws OperationNotSupportedException;

    Alumno buscar(Alumno Alumno);

    void borrar(Alumno Alumno) throws OperationNotSupportedException;









}

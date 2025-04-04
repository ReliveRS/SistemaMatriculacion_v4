package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.util.List;

public class Alumnos implements IAlumnos {

    private static final String ERROR = "ERROR: ";

    Connection conexion = null;


    @Override
    public void comenzar() {
        conexion = MySQL.establecerConexion();
    }

    @Override
    public void terminar() {
        MySQL.cerrarConexion();
    }

    @Override
    public List<Alumno> get() {
        return List.of();
    }

    @Override
    public int getTamano() {
        return 0;
    }

    @Override
    public void insertar(Alumno alumno) throws OperationNotSupportedException {

    }

    @Override
    public Alumno buscar(Alumno Alumno) {
        return null;
    }

    @Override
    public void borrar(Alumno Alumno) throws OperationNotSupportedException {

    }
}

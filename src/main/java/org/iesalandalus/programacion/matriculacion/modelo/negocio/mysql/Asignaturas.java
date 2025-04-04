package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.util.List;

public class Asignaturas implements IAsignaturas {
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
    public List<Asignatura> get() {
        return List.of();
    }

    @Override
    public int getTamano() {
        return 0;
    }

    @Override
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {

    }

    @Override
    public Asignatura buscar(Asignatura asignatura) {
        return null;
    }

    @Override
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {

    }
}

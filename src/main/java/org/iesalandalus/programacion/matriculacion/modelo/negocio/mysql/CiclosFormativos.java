package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.util.List;

public class CiclosFormativos implements ICiclosFormativos {
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
    public List<CicloFormativo> get() {
        return List.of();
    }

    @Override
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {

    }

    @Override
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        return null;
    }

    @Override
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {

    }
}

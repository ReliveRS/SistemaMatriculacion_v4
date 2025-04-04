package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.util.List;

public class Matriculas implements IMatriculas {
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
    public List<Matricula> get() {
        return List.of();
    }

    @Override
    public int getTamano() {
        return 0;
    }

    @Override
    public void insertar(Matricula matricula) throws OperationNotSupportedException {

    }

    @Override
    public Matricula buscar(Matricula matricula) {
        return null;
    }

    @Override
    public void borrar(Matricula matricula) throws OperationNotSupportedException {

    }

    @Override
    public List<Matricula> get(Alumno alumno) {
        return List.of();
    }

    @Override
    public List<Matricula> get(String cursoAcademico) {
        return List.of();
    }

    @Override
    public List<Matricula> get(CicloFormativo cicloFormativo) {
        return List.of();
    }
}

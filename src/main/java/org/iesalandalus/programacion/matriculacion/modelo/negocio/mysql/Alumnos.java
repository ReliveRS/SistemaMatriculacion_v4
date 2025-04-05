package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Alumnos implements IAlumnos {

    private static final String ERROR = "ERROR: ";
    private static Alumnos instancia;
    Connection conexion = null;

    public Alumnos() {
        comenzar();
    }

    public static Alumnos getInstancia()
    {
        if(instancia == null)
        {
            instancia = new Alumnos();
        }

        return instancia;
    }

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
        List<Alumno> alumnos = new ArrayList<>();
        try {
            String sentenciaStr = "SELECT nombre, telefono, correo, dni, fechaNacimiento FROM alumno ORDER BY nombre";
            Statement sentencia = conexion.createStatement();
            ResultSet filas = sentencia.executeQuery(sentenciaStr);
            while (filas.next()) {
                String nombre = filas.getString(1);
                String telefono = filas.getString(2);
                String correo = filas.getString(3);
                String dni = filas.getString(4);
                LocalDate fechaNacimiento = filas.getDate(5).toLocalDate();//Habria que revisar en un futuro si funciona bien.

                Alumno alumno = new Alumno(nombre, telefono,correo,dni,fechaNacimiento);
                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.getMessage());
        }
        return alumnos;
    }

    @Override
    public int getTamano() {
        int tamano = 0;
        try {
            String sentenciaStr = "select count(*) from alumno";
            Statement sentencia = conexion.createStatement();
            ResultSet filas = sentencia.executeQuery(sentenciaStr);
            if (filas.next()) {
                tamano = filas.getInt(1);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.getMessage());
        }
        return tamano;
    }

    @Override
    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }
        if (buscar(alumno) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese DNI.");
        }
        try {
            String sentenciaStr = "INSERT INTO alumno (nombre, telefono, correo, dni, fechaNacimiento) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setString(1, alumno.getNombre());
            sentencia.setString(2, alumno.getTelefono());
            sentencia.setString(3, alumno.getCorreo());
            sentencia.setString(4, alumno.getDni());
            sentencia.setDate(5, Date.valueOf(alumno.getFechaNacimiento()));
            sentencia.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese nombre.");
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + e.getMessage());
        }
    }

    @Override
    public Alumno buscar(Alumno alumno) {

        if (alumno == null) {
            throw new IllegalArgumentException("ERROR: No se puede buscar un alumno nulo.");
        }
        try {
            String sentenciaStr = "select * from Alumno where dni=?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setString(1, alumno.getDni());

            ResultSet filas = sentencia.executeQuery();
            if (filas.next()) {
                String nombre = filas.getString(1);
                String telefono = filas.getString(2);
                String correo = filas.getString(3);
                String dni = filas.getString(4);
                LocalDate fechaNacimiento = filas.getDate(5).toLocalDate();//Habria que revisar en un futuro si funciona bien.

                alumno = new Alumno(nombre, telefono,correo,dni,fechaNacimiento);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.getMessage());
        }
        return alumno;
    }

    @Override
    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new IllegalArgumentException("ERROR: No se puede borrar un alumno nulo.");
        }
        try {
            String sentenciaStr = "delete from alumno where dni = ?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setString(1, alumno.getDni());
            if (sentencia.executeUpdate() == 0) {
                throw new OperationNotSupportedException("ERROR: No existe ningún alumno con ese DNI.");
            }
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + e.toString());
        }
    }
}

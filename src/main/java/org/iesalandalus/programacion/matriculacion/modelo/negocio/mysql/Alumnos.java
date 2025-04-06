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
        if (conexion == null) {
            throw new IllegalStateException("ERROR: No se pudo establecer la conexión con la base de datos.");
        }
    }

    @Override
    public void terminar() {
        MySQL.cerrarConexion();
    }

    @Override
    public List<Alumno> get() {
        List<Alumno> alumnos = new ArrayList<>();
        try {
            System.out.println("Ejecutando consulta SQL para obtener alumnos...");
            String sentenciaStr = "SELECT nombre, telefono, correo, dni, fechaNacimiento FROM alumno ORDER BY nombre";
            Statement sentencia = conexion.createStatement();
            ResultSet filas = sentencia.executeQuery(sentenciaStr);
            while (filas.next()) {
                String nombre = filas.getString("nombre");
                String telefono = filas.getString("telefono");
                String correo = filas.getString("correo");
                String dni = filas.getString("dni");
                LocalDate fechaNacimiento = filas.getDate("fechaNacimiento").toLocalDate();

                Alumno alumno = new Alumno(nombre, telefono, correo, dni, fechaNacimiento);
                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            System.err.println(ERROR + e.getMessage());
        }
        System.out.println("Número de alumnos obtenidos: " + alumnos.size());
        return alumnos; // Devuelve una lista vacía si ocurre un error
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

        // Verificar si el DNI ya existe en la base de datos
        try {
            String consultaStr = "SELECT COUNT(*) FROM alumno WHERE dni = ?";
            PreparedStatement consulta = conexion.prepareStatement(consultaStr);
            consulta.setString(1, alumno.getDni());
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next() && resultado.getInt(1) > 0) {
                throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese DNI.");
            }
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + e.getMessage());
        }

        // Insertar el alumno si no existe
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
        if (alumno == null || alumno.getDni() == null || alumno.getDni().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El DNI del alumno no puede ser nulo ni vacío.");
        }

        Alumno resultado = null; // Variable para almacenar el resultado
        try {
            String sentenciaStr = "SELECT nombre, telefono, correo, dni, fechaNacimiento FROM alumno WHERE dni=?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setString(1, alumno.getDni());

            ResultSet filas = sentencia.executeQuery();
            if (filas.next()) {
                String nombre = filas.getString("nombre");
                String telefono = filas.getString("telefono");
                String correo = filas.getString("correo");
                String dni = filas.getString("dni");
                LocalDate fechaNacimiento = filas.getDate("fechaNacimiento").toLocalDate();

                // Crear un nuevo objeto Alumno con los datos recuperados
                resultado = new Alumno(nombre, telefono, correo, dni, fechaNacimiento);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.getMessage());
        }

        return resultado; // Devuelve null si no se encontró ningún registro
    }


    @Override
    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        System.out.println("Usando implementación MySQL para borrar alumno.");

        if (alumno == null || alumno.getDni() == null || alumno.getDni().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El DNI del alumno no puede ser nulo ni vacío.");
        }
        try {
            String sentenciaStr = "DELETE FROM alumno WHERE dni=?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setString(1, alumno.getDni()); // Usar solo el DNI como parámetro

            if (sentencia.executeUpdate() == 0) {
                throw new OperationNotSupportedException("ERROR: No existe ningún alumno con ese DNI.");
            }
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + e.toString());
        }
    }


}

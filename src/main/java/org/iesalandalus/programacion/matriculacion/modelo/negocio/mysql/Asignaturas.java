package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Asignaturas implements IAsignaturas {
    private static final String ERROR = "ERROR: ";
    private static Asignaturas instancia;
    private Connection conexion;

    public Asignaturas() {
        comenzar();
    }

    public static Asignaturas getInstancia() {
        if (instancia == null) {
            instancia = new Asignaturas();
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


    public Curso getCurso(String curso) {
        if (curso == null || curso.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El curso no puede ser nulo ni vacío.");
        }
        try {
            return Curso.valueOf(curso.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ERROR: El curso proporcionado no es válido.");
        }
    }


    public EspecialidadProfesorado getEspecialidadProfesorado(String especialidad) {
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: La especialidad no puede ser nula ni vacía.");
        }
        try {
            return EspecialidadProfesorado.valueOf(especialidad.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ERROR: La especialidad proporcionada no es válida.");
        }
    }

    @Override
    public List<Asignatura> get() {
        List<Asignatura> asignaturas = new ArrayList<>();
        try {
            String sentenciaStr = "SELECT codigo, nombre, horasAnuales, horasDesdoble, curso, especialidadProfesorado, cicloFormativoCodigo FROM asignatura ORDER BY nombre";
            Statement sentencia = conexion.createStatement();
            ResultSet filas = sentencia.executeQuery(sentenciaStr);
            while (filas.next()) {
                String codigo = filas.getString("codigo");
                String nombre = filas.getString("nombre");
                int horasAnuales = filas.getInt("horasAnuales");
                int horasDesdoble = filas.getInt("horasDesdoble");
                Curso curso = getCurso(filas.getString("curso"));
                EspecialidadProfesorado especialidad = getEspecialidadProfesorado(filas.getString("especialidadProfesorado"));

                // Buscar el ciclo formativo asociado a esta asignatura
                int codigoCiclo = filas.getInt("cicloFormativoCodigo");
                CicloFormativo cicloFormativo = CiclosFormativos.getInstancia().buscar(new CicloFormativo(codigoCiclo, "", null, "", 0));

                Asignatura asignatura = new Asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, especialidad, cicloFormativo);
                asignaturas.add(asignatura);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.getMessage());
        }
        return asignaturas;
    }

    @Override
    public int getTamano() {
        int tamano = 0;
        try {
            String sentenciaStr = "SELECT COUNT(*) FROM asignatura";
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
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }
        if (buscar(asignatura) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }
        try {
            String sentenciaStr = "INSERT INTO asignatura (codigo, nombre, horasAnuales, horasDesdoble, curso, especialidadProfesorado, cicloFormativoCodigo) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setString(1, asignatura.getCodigo());
            sentencia.setString(2, asignatura.getNombre());
            sentencia.setInt(3, asignatura.getHorasAnuales());
            sentencia.setInt(4, asignatura.getHorasDesdoble());
            sentencia.setString(5, asignatura.getCurso().name());
            sentencia.setString(6, asignatura.getEspecialidadProfesorado().name());

            // Usar el código del ciclo formativo asociado
            sentencia.setInt(7, asignatura.getCicloFormativo().getCodigo());

            sentencia.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + e.getMessage());
        }
    }

    @Override
    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null) {
            throw new IllegalArgumentException("ERROR: No se puede buscar una asignatura nula.");
        }
        Asignatura resultado = null;
        try {
            String sentenciaStr = "SELECT codigo, nombre, horasAnuales, horasDesdoble, curso, especialidadProfesorado, cicloFormativoCodigo FROM asignatura WHERE codigo=?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setString(1, asignatura.getCodigo());

            ResultSet filas = sentencia.executeQuery();
            if (filas.next()) {
                String codigo = filas.getString("codigo");
                String nombre = filas.getString("nombre");
                int horasAnuales = filas.getInt("horasAnuales");
                int horasDesdoble = filas.getInt("horasDesdoble");
                Curso curso = getCurso(filas.getString("curso"));
                EspecialidadProfesorado especialidad = getEspecialidadProfesorado(filas.getString("especialidadProfesorado"));

                // Buscar el ciclo formativo asociado a esta asignatura
                int codigoCiclo = filas.getInt("cicloFormativoCodigo");
                CicloFormativo cicloFormativo = CiclosFormativos.getInstancia().buscar(new CicloFormativo(codigoCiclo, "", null, "", 0));

                resultado = new Asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, especialidad, cicloFormativo);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.getMessage());
        }
        return resultado;
    }

    @Override
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new IllegalArgumentException("ERROR: No se puede borrar una asignatura nula.");
        }
        try {
            String sentenciaStr = "DELETE FROM asignatura WHERE codigo=?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setString(1, asignatura.getCodigo());

            if (sentencia.executeUpdate() == 0) {
                throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura con ese código.");
            }
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + e.toString());
        }
    }
}

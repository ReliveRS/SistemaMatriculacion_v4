package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Grado;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CiclosFormativos implements ICiclosFormativos {
    private static final String ERROR = "ERROR: ";
    private static CiclosFormativos instancia;
    private Connection conexion;

    public CiclosFormativos() {
        comenzar();
    }

    public static CiclosFormativos getInstancia() {
        if (instancia == null) {
            instancia = new CiclosFormativos();
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

    public Grado getGrado(String tipoGrado) {
        if (tipoGrado == null || tipoGrado.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El tipo de grado no puede ser nulo ni vacío.");
        }
        try {
            return Grado.valueOf(tipoGrado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ERROR: El tipo de grado proporcionado no es válido.");
        }
    }

    @Override
    public List<CicloFormativo> get() {
        List<CicloFormativo> ciclosFormativos = new ArrayList<>();
        try {
            String sentenciaStr = "SELECT codigo, familiaProfesional, grado, nombre, horas FROM ciclo_formativo ORDER BY nombre";
            Statement sentencia = conexion.createStatement();
            ResultSet filas = sentencia.executeQuery(sentenciaStr);
            while (filas.next()) {
                int codigo = filas.getInt("codigo");
                String familiaProfesional = filas.getString("familiaProfesional");
                Grado grado = getGrado(filas.getString("grado")); // Uso del método getGrado
                String nombre = filas.getString("nombre");
                int horas = filas.getInt("horas");

                CicloFormativo cicloFormativo = new CicloFormativo(codigo, familiaProfesional, grado, nombre, horas);
                ciclosFormativos.add(cicloFormativo);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.getMessage());
        }
        return ciclosFormativos;
    }

    @Override
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }
        if (buscar(cicloFormativo) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        }
        try {
            String sentenciaStr = "INSERT INTO ciclo_formativo (codigo, familiaProfesional, grado, nombre, horas) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setInt(1, cicloFormativo.getCodigo());
            sentencia.setString(2, cicloFormativo.getFamiliaProfesional());
            sentencia.setString(3, cicloFormativo.getGrado().name());
            sentencia.setString(4, cicloFormativo.getNombre());
            sentencia.setInt(5, cicloFormativo.getHoras());

            sentencia.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + e.getMessage());
        }
    }

    @Override
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: No se puede buscar un ciclo formativo nulo.");
        }
        CicloFormativo resultado = null;
        try {
            String sentenciaStr = "SELECT codigo, familiaProfesional, grado, nombre, horas FROM ciclo_formativo WHERE codigo=?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setInt(1, cicloFormativo.getCodigo());

            ResultSet filas = sentencia.executeQuery();
            if (filas.next()) {
                int codigo = filas.getInt("codigo");
                String familiaProfesional = filas.getString("familiaProfesional");
                Grado grado = getGrado(filas.getString("grado")); // Uso del método getGrado
                String nombre = filas.getString("nombre");
                int horas = filas.getInt("horas");

                resultado = new CicloFormativo(codigo, familiaProfesional, grado, nombre, horas);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.getMessage());
        }
        return resultado;
    }

    @Override
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: No se puede borrar un ciclo formativo nulo.");
        }
        try {
            String sentenciaStr = "DELETE FROM ciclo_formativo WHERE codigo=?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setInt(1, cicloFormativo.getCodigo());

            if (sentencia.executeUpdate() == 0) {
                throw new OperationNotSupportedException("ERROR: No existe ningún ciclo formativo con ese código.");
            }
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + e.toString());
        }
    }
}

package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
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

    public Grado getGrado(String tipoGrado, String nombre, int numAnios, Modalidad modalidad, int numEdiciones) {
        if (tipoGrado == null || tipoGrado.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El tipo de grado no puede ser nulo ni vacío.");
        }

        switch (tipoGrado.toUpperCase()) {
            case "GRADOD":
                return new GradoD(nombre, numAnios, modalidad);
            case "GRADOE":
                return new GradoE(nombre, numAnios, numEdiciones);
            default:
                throw new IllegalArgumentException("ERROR: El tipo de grado proporcionado no es válido.");
        }
    }

    public List<CicloFormativo> get() {
        List<CicloFormativo> ciclos = new ArrayList<>();
        try {
            System.out.println("Ejecutando consulta SQL para obtener ciclos formativos...");
            String sentenciaStr = "SELECT codigo, familiaProfesional, grado, nombre, horas, nombreGrado, numAniosGrado, modalidad, numEdiciones FROM cicloFormativo ORDER BY nombre";
            Statement sentencia = conexion.createStatement();
            ResultSet filas = sentencia.executeQuery(sentenciaStr);
            while (filas.next()) {
                int codigo = filas.getInt("codigo");
                String familiaProfesional = filas.getString("familiaProfesional");
                String tipoGrado = filas.getString("grado");
                String nombre = filas.getString("nombre");
                int horas = filas.getInt("horas");
                String nombreGrado = filas.getString("nombreGrado");
                int numAnios = filas.getInt("numAniosGrado");
                String modalidadStr = filas.getString("modalidad");
                int numEdiciones = filas.getInt("numEdiciones");

                Modalidad modalidad = modalidadStr != null ? Modalidad.valueOf(modalidadStr.toUpperCase()) : Modalidad.PRESENCIAL;

                Grado grado;
                if ("gradod".equalsIgnoreCase(tipoGrado)) {
                    grado = new GradoD(nombreGrado, numAnios, modalidad);
                } else if ("gradoe".equalsIgnoreCase(tipoGrado)) {
                    grado = new GradoE(nombreGrado, numAnios, numEdiciones);
                } else {
                    throw new IllegalArgumentException("ERROR: Tipo de grado desconocido.");
                }

                CicloFormativo ciclo = new CicloFormativo(codigo, familiaProfesional, grado, nombre, horas);
                ciclos.add(ciclo);
            }
        } catch (SQLException e) {
            System.err.println(ERROR + e.getMessage());
        }
        System.out.println("Número de ciclos formativos obtenidos: " + ciclos.size());
        return ciclos;
    }





    @Override
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }

        // Verificar si el código ya existe en la base de datos
        try {
            String consultaStr = "SELECT COUNT(*) FROM cicloFormativo WHERE codigo=?";
            PreparedStatement consulta = conexion.prepareStatement(consultaStr);
            consulta.setInt(1, cicloFormativo.getCodigo());
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next() && resultado.getInt(1) > 0) {
                throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
            }
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + e.getMessage());
        }

        // Insertar el ciclo formativo si no existe
        try {
            String sentenciaStr = "INSERT INTO cicloFormativo (codigo, familiaProfesional, grado, nombre, horas, nombreGrado, numAniosGrado, modalidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);

            sentencia.setInt(1, cicloFormativo.getCodigo());
            sentencia.setString(2, cicloFormativo.getFamiliaProfesional());

            // Usar el tipo de grado basado en la clase del objeto
            sentencia.setString(3, cicloFormativo.getGrado().getClass().getSimpleName().toLowerCase());

            sentencia.setString(4, cicloFormativo.getNombre());
            sentencia.setInt(5, cicloFormativo.getHoras());

            // Proporcionar el nombre del grado
            sentencia.setString(6, cicloFormativo.getGrado().getNombre());

            // Obtener el número de años del grado directamente desde el objeto CicloFormativo
            sentencia.setInt(7, cicloFormativo.getGrado().getNumAnios());

            // Proporcionar la modalidad (si aplica)
            if (cicloFormativo.getGrado() instanceof GradoD) {
                GradoD gradoD = (GradoD) cicloFormativo.getGrado();
                sentencia.setString(8, gradoD.getModalidad().toString().toLowerCase());
            } else {
                sentencia.setString(8, null); // Si no aplica modalidad
            }

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
        String sql = "SELECT codigo, familiaProfesional, grado, nombre, horas, nombreGrado, numAniosGrado, modalidad, numEdiciones FROM cicloFormativo WHERE codigo = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cicloFormativo.getCodigo());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int codigo = rs.getInt("codigo");
                String familiaProfesional = rs.getString("familiaProfesional");
                String tipoGrado = rs.getString("grado");
                String nombre = rs.getString("nombre");
                int horas = rs.getInt("horas");
                String nombreGrado = rs.getString("nombreGrado");
                int numAnios = rs.getInt("numAniosGrado");
                String modalidadStr = rs.getString("modalidad");
                int numEdiciones = rs.getInt("numEdiciones");

                // Construir el Grado correctamente
                Grado grado;
                if ("gradod".equalsIgnoreCase(tipoGrado)) {
                    Modalidad modalidad = Modalidad.valueOf(modalidadStr.toUpperCase());
                    grado = new GradoD(nombreGrado, numAnios, modalidad);
                } else {
                    grado = new GradoE(nombreGrado, numAnios, numEdiciones);
                }

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
            String sentenciaStr = "DELETE FROM cicloFormativo WHERE codigo=?";
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

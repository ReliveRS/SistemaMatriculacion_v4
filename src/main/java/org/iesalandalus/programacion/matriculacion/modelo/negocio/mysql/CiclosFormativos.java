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

    @Override
    public List<CicloFormativo> get() {
        List<CicloFormativo> ciclos = new ArrayList<>();
        try {
            System.out.println("Ejecutando consulta SQL para obtener ciclos formativos...");
            String sentenciaStr = "SELECT codigo, familiaProfesional, grado, nombre, horas FROM cicloFormativo ORDER BY nombre";
            Statement sentencia = conexion.createStatement();
            ResultSet filas = sentencia.executeQuery(sentenciaStr);
            while (filas.next()) {
                int codigo = filas.getInt("codigo");
                String familiaProfesional = filas.getString("familiaProfesional");
                if (familiaProfesional == null || familiaProfesional.trim().isEmpty()) {
                    familiaProfesional = "Sin definir"; // Valor por defecto para evitar errores
                }
                String tipoGrado = filas.getString("grado");
                String nombre = filas.getString("nombre");
                int horas = filas.getInt("horas");

                // Crear el objeto Grado correspondiente
                Grado grado;
                if ("GRADOD".equalsIgnoreCase(tipoGrado)) {
                    grado = new GradoD(nombre, 2, Modalidad.PRESENCIAL); // Valores ficticios para modalidad y años
                } else if ("GRADOE".equalsIgnoreCase(tipoGrado)) {
                    grado = new GradoE(nombre, 1, 5); // Valores ficticios para ediciones
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
        try {
            String sentenciaStr = "SELECT codigo, familiaProfesional, grado, nombre, horas FROM cicloFormativo WHERE codigo=?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setInt(1, cicloFormativo.getCodigo());

            ResultSet filas = sentencia.executeQuery();
            if (filas.next()) {
                int codigo = filas.getInt("codigo");
                String familiaProfesional = filas.getString("familiaProfesional");
                String tipoGrado = filas.getString("grado");
                String nombre = filas.getString("nombre");
                int horas = filas.getInt("horas");

                // Crear el objeto Grado correspondiente basado en el valor de la columna 'grado'
                Grado grado;
                if ("gradod".equalsIgnoreCase(tipoGrado)) {
                    grado = new GradoD(nombre, 2, Modalidad.PRESENCIAL); // Valores ficticios para modalidad y años
                } else if ("gradoe".equalsIgnoreCase(tipoGrado)) {
                    grado = new GradoE(nombre, 1, 5); // Valores ficticios para ediciones
                } else {
                    throw new IllegalArgumentException("ERROR: Tipo de grado desconocido.");
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

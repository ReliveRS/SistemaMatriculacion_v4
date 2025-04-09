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
        // Mapeo manual entre los valores posibles de la base de datos y el ENUM
        switch (especialidad.toUpperCase()) {
            case "INFORMATICA":
                return EspecialidadProfesorado.INFORMATICA;
            case "SISTEMAS":
                return EspecialidadProfesorado.SISTEMAS;
            case "FOL":
                return EspecialidadProfesorado.FOL;
            default:
                throw new IllegalArgumentException("ERROR: La especialidad proporcionada no es válida: " + especialidad);
        }
    }



    @Override
    public List<Asignatura> get() {
        List<Asignatura> asignaturas = new ArrayList<>();
        try {
            String sentenciaStr = "SELECT codigo, nombre, horasAnuales, horasDesdoble, curso, especialidadProfesorado, cicloFormativo FROM asignatura ORDER BY nombre";
            Statement sentencia = conexion.createStatement();
            ResultSet filas = sentencia.executeQuery(sentenciaStr);
            while (filas.next()) {
                try {
                    // Recuperar datos de la asignatura
                    String codigo = filas.getString("codigo");
                    String nombre = filas.getString("nombre");
                    int horasAnuales = filas.getInt("horasAnuales");
                    int horasDesdoble = filas.getInt("horasDesdoble");
                    Curso curso = getCurso(filas.getString("curso"));
                    EspecialidadProfesorado especialidad = getEspecialidadProfesorado(filas.getString("especialidadProfesorado"));

                    // Buscar el ciclo formativo asociado a esta asignatura
                    int codigoCiclo = filas.getInt("cicloFormativo");
                    CicloFormativo cicloFormativo = CiclosFormativos.getInstancia().buscar(new CicloFormativo(codigoCiclo, "Familia profesional", new GradoD("Grado ficticio", 2, Modalidad.PRESENCIAL), "Nombre ficticio", 100));

                    if (cicloFormativo == null) {
                        throw new IllegalArgumentException("ERROR: No se encontró un ciclo formativo con el código proporcionado.");
                    }

                    // Crear objeto Asignatura y añadirlo a la lista
                    Asignatura asignatura = new Asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, especialidad, cicloFormativo);
                    asignaturas.add(asignatura);
                } catch (IllegalArgumentException e) {
                    System.err.println("ERROR al procesar una asignatura: " + e.getMessage());
                }
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
            String sentenciaStr = "INSERT INTO asignatura (codigo, nombre, horasAnuales, horasDesdoble, curso, especialidadProfesorado, cicloFormativo) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            String sentenciaStr = "SELECT codigo, nombre, horasAnuales, horasDesdoble, curso, especialidadProfesorado, cicloFormativo FROM asignatura WHERE codigo=?";
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
                int codigoCiclo = filas.getInt("cicloFormativo");

                String sentenciaCiclo = "SELECT codigo, familiaProfesional, grado, nombre, horas FROM cicloFormativo WHERE codigo=?";
                PreparedStatement sentenciaCicloStmt = conexion.prepareStatement(sentenciaCiclo);
                sentenciaCicloStmt.setInt(1, codigoCiclo);
                ResultSet cicloFilas = sentenciaCicloStmt.executeQuery();

                CicloFormativo cicloFormativo;
                if (cicloFilas.next()) {
                    String familiaProfesional = cicloFilas.getString("familiaProfesional");
                    String tipoGrado = cicloFilas.getString("grado");
                    String nombreCiclo = cicloFilas.getString("nombre");
                    int horas = cicloFilas.getInt("horas");

                    Grado grado;
                    if ("gradod".equalsIgnoreCase(tipoGrado)) {
                        grado = new GradoD(nombreCiclo, 2, Modalidad.PRESENCIAL); // Ajusta según tu lógica
                    } else if ("gradoe".equalsIgnoreCase(tipoGrado)) {
                        grado = new GradoE(nombreCiclo, 1, 5); // Ajusta según tu lógica
                    } else {
                        throw new IllegalArgumentException("ERROR: Tipo de grado desconocido.");
                    }

                    cicloFormativo = new CicloFormativo(codigoCiclo, familiaProfesional, grado, nombreCiclo, horas);
                } else {
                    throw new IllegalArgumentException("ERROR: No se encontró el ciclo formativo asociado.");
                }

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

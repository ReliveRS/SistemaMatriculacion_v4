package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Matriculas implements IMatriculas {
    private static final String ERROR = "ERROR: ";
    private static Matriculas instancia;
    private Connection conexion = null;

    public static Matriculas getInstancia() {
        if (instancia == null) {
            instancia = new Matriculas();
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


    public List<Asignatura> getAsignaturasMatricula(int idMatricula) {
        List<Asignatura> asignaturas = new ArrayList<>();
        try {
            String sentenciaStr = "SELECT a.codigo, a.nombre, a.horasAnuales, a.horasDesdoble, a.curso, a.especialidadProfesorado, a.cicloFormativoCodigo " +
                    "FROM asignatura a " +
                    "JOIN matricula_asignatura ma ON a.codigo = ma.codigoAsignatura " +
                    "WHERE ma.idMatricula=?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setInt(1, idMatricula);

            ResultSet filas = sentencia.executeQuery();
            while (filas.next()) {
                String codigo = filas.getString("codigo");
                String nombre = filas.getString("nombre");
                int horasAnuales = filas.getInt("horasAnuales");
                int horasDesdoble = filas.getInt("horasDesdoble");
                Curso curso = Curso.valueOf(filas.getString("curso"));
                EspecialidadProfesorado especialidadProfesorado = EspecialidadProfesorado.valueOf(filas.getString("especialidadProfesorado"));

                // Obtener el ciclo formativo asociado
                int codigoCiclo = filas.getInt("cicloFormativoCodigo");
                CicloFormativo cicloFormativo = CiclosFormativos.getInstancia().buscar(new CicloFormativo(codigoCiclo, "", null, "", 0));

                // Crear la asignatura con todos los parámetros requeridos
                Asignatura asignatura = new Asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, especialidadProfesorado, cicloFormativo);
                asignaturas.add(asignatura);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.getMessage());
        }
        return asignaturas;
    }



    @Override
    public List<Matricula> get() {
        List<Matricula> matriculas = new ArrayList<>();
        try {
            String sentenciaStr = "SELECT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, m.fechaAnulacion, m.dniAlumno " +
                    "FROM matricula m " +
                    "JOIN alumno a ON m.dniAlumno = a.dni " +
                    "ORDER BY m.fechaMatriculacion DESC, a.nombre ASC";
            Statement sentencia = conexion.createStatement();
            ResultSet filas = sentencia.executeQuery(sentenciaStr);
            while (filas.next()) {
                int idMatricula = filas.getInt("idMatricula");
                String cursoAcademico = filas.getString("cursoAcademico");
                LocalDate fechaMatriculacion = filas.getDate("fechaMatriculacion").toLocalDate();
                LocalDate fechaAnulacion = filas.getDate("fechaAnulacion") != null ? filas.getDate("fechaAnulacion").toLocalDate() : null;
                String dniAlumno = filas.getString("dniAlumno");

                Alumno alumno = Alumnos.getInstancia().buscar(new Alumno("", "", "", dniAlumno, LocalDate.now()));
                List<Asignatura> asignaturas = getAsignaturasMatricula(idMatricula);

                Matricula matricula = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumno, asignaturas);
                matricula.setFechaAnulacion(fechaAnulacion);
                matriculas.add(matricula);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.getMessage());
        }
        return matriculas;
    }


    @Override
    public int getTamano() {
        int tamano = 0;
        try {
            String sentenciaStr = "SELECT COUNT(*) FROM matricula";
            Statement sentencia = conexion.createStatement();
            ResultSet filas = sentencia.executeQuery(sentenciaStr);
            if (filas.next()) {
                tamano = filas.getInt(1); // Obtiene el resultado del COUNT(*)
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.getMessage());
        }
        return tamano;
    }

    public void insertarAsignaturasMatricula(int idMatricula, List<Asignatura> asignaturas) throws OperationNotSupportedException {
        if (asignaturas == null || asignaturas.isEmpty()) {
            throw new IllegalArgumentException("ERROR: La lista de asignaturas no puede ser nula o vacía.");
        }
        try {
            String sentenciaStr = "INSERT INTO matricula_asignatura (idMatricula, codigoAsignatura) VALUES (?, ?)";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);

            for (Asignatura asignatura : asignaturas) {
                sentencia.setInt(1, idMatricula);
                sentencia.setString(2, asignatura.getCodigo());
                sentencia.executeUpdate();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new OperationNotSupportedException("ERROR: Ya existe una relación entre la matrícula y alguna de las asignaturas.");
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + e.getMessage());
        }
    }

    @Override
    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }
        if (buscar(matricula) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese ID.");
        }
        try {
            // Desactivar el autocommit para iniciar una transacción
            conexion.setAutoCommit(false);

            // Inserción de la matrícula
            String sentenciaStr = "INSERT INTO matricula (idMatricula, cursoAcademico, fechaMatriculacion, fechaAnulacion, dniAlumno) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setInt(1, matricula.getIdMatricula());
            sentencia.setString(2, matricula.getCursoAcademico());
            sentencia.setDate(3, Date.valueOf(matricula.getFechaMatriculacion()));

            if (matricula.getFechaAnulacion() != null) {
                sentencia.setDate(4, Date.valueOf(matricula.getFechaAnulacion()));
            } else {
                sentencia.setNull(4, Types.DATE);
            }

            sentencia.setString(5, matricula.getAlumno().getDni());
            sentencia.executeUpdate();

            // Inserción de las asignaturas asociadas
            insertarAsignaturasMatricula(matricula.getIdMatricula(), matricula.getColeccionAsignaturas());

            // Confirmar la transacción
            conexion.commit();
        } catch (SQLException e) {
            try {
                // Revertir los cambios si ocurre un error
                conexion.rollback();
            } catch (SQLException rollbackEx) {
                throw new OperationNotSupportedException(ERROR + rollbackEx.getMessage());
            }
            throw new OperationNotSupportedException(ERROR + e.getMessage());
        } finally {
            try {
                // Restaurar el modo de autocommit
                conexion.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                throw new OperationNotSupportedException(ERROR + autoCommitEx.getMessage());
            }
        }
    }



    @Override
    public Matricula buscar(Matricula matricula) {
        if (matricula == null) {
            throw new IllegalArgumentException("ERROR: No se puede buscar una matrícula nula.");
        }
        Matricula resultado = null;
        try {
            String sentenciaStr = "SELECT idMatricula, cursoAcademico, fechaMatriculacion, fechaAnulacion, dniAlumno FROM matricula WHERE idMatricula=?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setInt(1, matricula.getIdMatricula());

            ResultSet filas = sentencia.executeQuery();
            if (filas.next()) {
                int idMatricula = filas.getInt("idMatricula");
                String cursoAcademico = filas.getString("cursoAcademico");
                LocalDate fechaMatriculacion = filas.getDate("fechaMatriculacion").toLocalDate();
                LocalDate fechaAnulacion = filas.getDate("fechaAnulacion") != null ? filas.getDate("fechaAnulacion").toLocalDate() : null;
                String dniAlumno = filas.getString("dniAlumno");

                // Buscar el alumno utilizando la clase Alumnos
                Alumno alumno = Alumnos.getInstancia().buscar(new Alumno("", "", "", dniAlumno, LocalDate.now()));

                // Crear una lista vacía de asignaturas (puedes cargar las asignaturas asociadas si es necesario)
                List<Asignatura> asignaturas = new ArrayList<>();

                resultado = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumno, asignaturas);
                resultado.setFechaAnulacion(fechaAnulacion);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.getMessage());
        }
        return resultado;
    }

    @Override
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new IllegalArgumentException("ERROR: No se puede borrar una matrícula nula.");
        }
        try {
            String sentenciaStr = "DELETE FROM matricula WHERE idMatricula=?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setInt(1, matricula.getIdMatricula());

            if (sentencia.executeUpdate() == 0) {
                throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula con ese ID.");
            }
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + e.toString());
        }
    }

    @Override
    public List<Matricula> get(Alumno alumno) {
        List<Matricula> matriculasAlumno = new ArrayList<>();
        try {
            String sentenciaStr = "SELECT * FROM matricula WHERE dniAlumno=?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setString(1, alumno.getDni());

            ResultSet filas = sentencia.executeQuery();
            while (filas.next()) {
                Matricula matriculaBuscada = buscar(new Matricula(filas.getInt("idMatricula"), "", LocalDate.now(), alumno, List.of()));
                if (matriculaBuscada != null) {
                    matriculasAlumno.add(matriculaBuscada);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.toString());
        }
        return matriculasAlumno;
    }

    @Override
    public List<Matricula> get(String cursoAcademico) {
        List<Matricula> matriculasCursoAcademico = new ArrayList<>();
        try {
            String sentenciaStr = "SELECT * FROM matricula WHERE cursoAcademico=?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setString(1, cursoAcademico);

            ResultSet filas = sentencia.executeQuery();
            while (filas.next()) {
                Matricula matriculaBuscada = buscar(new Matricula(filas.getInt("idMatricula"), cursoAcademico, LocalDate.now(), null, List.of()));
                if (matriculaBuscada != null) {
                    matriculasCursoAcademico.add(matriculaBuscada);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.toString());
        }
        return matriculasCursoAcademico;
    }

    @Override
    public List<Matricula> get(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: No se puede buscar matrículas para un ciclo formativo nulo.");
        }

        List<Matricula> matriculasCiclo = new ArrayList<>();
        try {
            // Consulta para obtener las matrículas relacionadas con el ciclo formativo
            String sentenciaStr = "SELECT DISTINCT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, m.fechaAnulacion, m.dniAlumno " +
                    "FROM matricula m " +
                    "JOIN matricula_asignatura ma ON m.idMatricula = ma.idMatricula " +
                    "JOIN asignatura a ON ma.codigoAsignatura = a.codigo " +
                    "WHERE a.cicloFormativoCodigo = ?";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
            sentencia.setInt(1, cicloFormativo.getCodigo());

            ResultSet filas = sentencia.executeQuery();
            while (filas.next()) {
                int idMatricula = filas.getInt("idMatricula");
                String cursoAcademico = filas.getString("cursoAcademico");
                LocalDate fechaMatriculacion = filas.getDate("fechaMatriculacion").toLocalDate();
                LocalDate fechaAnulacion = filas.getDate("fechaAnulacion") != null ? filas.getDate("fechaAnulacion").toLocalDate() : null;
                String dniAlumno = filas.getString("dniAlumno");

                // Buscar el alumno utilizando la clase Alumnos
                Alumno alumno = Alumnos.getInstancia().buscar(new Alumno("", "", "", dniAlumno, LocalDate.now()));

                // Crear una lista vacía de asignaturas (puedes cargar las asignaturas asociadas si es necesario)
                List<Asignatura> asignaturas = new ArrayList<>();

                // Crear la matrícula y añadirla a la lista
                Matricula matricula = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumno, asignaturas);
                matricula.setFechaAnulacion(fechaAnulacion);
                matriculasCiclo.add(matricula);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.toString());
        }
        return matriculasCiclo;
    }

}

package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Matriculas implements IMatriculas {
    private static final String ERROR = "ERROR: ";
    private static Matriculas instancia;
    private Connection conexion;

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

        String sql = "SELECT a.codigo, a.nombre, a.horasAnuales, a.horasDesdoble, " +
                "a.curso, a.especialidadProfesorado, " +
                "cf.codigo AS cicloCodigo, cf.familiaProfesional, cf.grado, " +
                "cf.nombre AS cicloNombre, cf.horas AS cicloHoras " +
                "FROM asignatura a " +
                "JOIN asignaturasMatricula am ON a.codigo = am.codigo " +
                "JOIN cicloFormativo cf ON a.cicloFormativo = cf.codigo " +
                "WHERE am.idMatricula = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idMatricula);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Construir el ciclo formativo directamente
                    int cicloCodigo = rs.getInt("cicloCodigo");
                    String familia = rs.getString("familiaProfesional");
                    String tipoGrado = rs.getString("grado");
                    String cicloNombre = rs.getString("cicloNombre");
                    int cicloHoras = rs.getInt("cicloHoras");

                    Grado grado;
                    if ("gradod".equalsIgnoreCase(tipoGrado)) {
                        grado = new GradoD(cicloNombre, 2, Modalidad.PRESENCIAL);
                    } else {
                        grado = new GradoE(cicloNombre, 1, 5);
                    }

                    CicloFormativo ciclo = new CicloFormativo(cicloCodigo, familia, grado, cicloNombre, cicloHoras);

                    // Construir la asignatura directamente
                    Asignatura asignatura = new Asignatura(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getInt("horasAnuales"),
                            Curso.valueOf(rs.getString("curso").toUpperCase()),
                            rs.getInt("horasDesdoble"),
                            EspecialidadProfesorado.valueOf(rs.getString("especialidadProfesorado").toUpperCase()),
                            ciclo
                    );
                    asignaturas.add(asignatura);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + "Error al obtener asignaturas: " + e.getMessage(), e);
        }
        return asignaturas;
    }

    @Override
    public List<Matricula> get() {
        List<Matricula> matriculas = new ArrayList<>();

        try {
            String sentenciaStr = "SELECT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, m.fechaAnulacion, " +
                    "a.dni, a.nombre, a.telefono, a.correo, a.fechaNacimiento " +  // Cambiado dniAlumno por dni
                    "FROM matricula m " +
                    "JOIN alumno a ON m.dni = a.dni " +  // Cambiado dniAlumno por dni
                    "ORDER BY m.fechaMatriculacion DESC, a.nombre ASC";

            try (Statement sentencia = conexion.createStatement();
                 ResultSet filas = sentencia.executeQuery(sentenciaStr)) {

                while (filas.next()) {
                    // Recuperar datos del alumno
                    Alumno alumno = new Alumno(
                            filas.getString("nombre"),
                            filas.getString("telefono"),
                            filas.getString("correo"),
                            filas.getString("dni"),
                            filas.getDate("fechaNacimiento").toLocalDate()
                    );

                    // Recuperar datos de la matrícula
                    int idMatricula = filas.getInt("idMatricula");
                    String cursoAcademico = filas.getString("cursoAcademico");
                    LocalDate fechaMatriculacion = filas.getDate("fechaMatriculacion").toLocalDate();
                    LocalDate fechaAnulacion = filas.getDate("fechaAnulacion") != null ?
                            filas.getDate("fechaAnulacion").toLocalDate() : null;

                    // Obtener asignaturas
                    List<Asignatura> asignaturas = getAsignaturasMatricula(idMatricula);

                    // Crear matrícula
                    Matricula matricula = new Matricula(idMatricula, cursoAcademico,
                            fechaMatriculacion, alumno, asignaturas);
                    matricula.setFechaAnulacion(fechaAnulacion);
                    matriculas.add(matricula);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + "Error al obtener las matrículas: " + e.getMessage(), e);
        }

        return matriculas.isEmpty() ? new ArrayList<>() : matriculas;  // Evitar retorno nulo
    }



    @Override
    public int getTamano() {
        int tamano = 0;
        try {
            String sentenciaStr = "SELECT COUNT(*) FROM matricula";
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

    public void insertarAsignaturasMatricula(int idMatricula, List<Asignatura> asignaturas)
            throws OperationNotSupportedException {
        if (asignaturas == null || asignaturas.isEmpty()) {
            throw new IllegalArgumentException("ERROR: La lista de asignaturas no puede ser nula o vacía.");
        }

        String sentenciaStr = "INSERT INTO asignaturasMatricula (idMatricula, codigo) VALUES (?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr)) {
            for (Asignatura asignatura : asignaturas) {
                sentencia.setInt(1, idMatricula);
                sentencia.setString(2, asignatura.getCodigo());
                sentencia.addBatch();
            }

            sentencia.executeBatch();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new OperationNotSupportedException("ERROR: Ya existe una relación entre la matrícula y alguna asignatura.");
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + "Error al insertar asignaturas: " + e.getMessage());
        }
    }

    @Override
    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }

        // Verificar si la matrícula ya existe
        String consultaStr = "SELECT COUNT(*) FROM matricula WHERE idMatricula=?";
        try (PreparedStatement consulta = conexion.prepareStatement(consultaStr)) {
            consulta.setInt(1, matricula.getIdMatricula());
            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next() && resultado.getInt(1) > 0) {
                    throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese ID.");
                }
            }
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + "Error al verificar la existencia de la matrícula: " + e.getMessage());
        }

        // Insertar la matrícula y asignaturas
        try {
            conexion.setAutoCommit(false);

            // Inserción de la matrícula
            String sentenciaStr = "INSERT INTO matricula (idMatricula, cursoAcademico, fechaMatriculacion, fechaAnulacion, dni) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr)) {
                sentencia.setInt(1, matricula.getIdMatricula());
                sentencia.setString(2, matricula.getCursoAcademico());
                sentencia.setDate(3, Date.valueOf(matricula.getFechaMatriculacion()));
                sentencia.setDate(4, matricula.getFechaAnulacion() != null ?
                        Date.valueOf(matricula.getFechaAnulacion()) : null);
                sentencia.setString(5, matricula.getAlumno().getDni());
                sentencia.executeUpdate();
            }

            // Inserción de las asignaturas
            insertarAsignaturasMatricula(matricula.getIdMatricula(), matricula.getColeccionAsignaturas());

            conexion.commit();
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException rollbackEx) {
                throw new OperationNotSupportedException("ERROR al revertir cambios: " + rollbackEx.getMessage());
            }
            throw new OperationNotSupportedException("ERROR al insertar matrícula: " + e.getMessage());
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                throw new OperationNotSupportedException("ERROR al restaurar autocommit: " + autoCommitEx.getMessage());
            }
        }
    }



    @Override
    public Matricula buscar(Matricula matricula) {
        if (matricula == null) {
            throw new IllegalArgumentException(ERROR + "No se puede buscar una matrícula nula");
        }

        String sql = "SELECT m.*, a.nombre, a.telefono, a.correo, a.dni, a.fechaNacimiento " +
                "FROM matricula m JOIN alumno a ON m.dni = a.dni " +
                "WHERE m.idMatricula = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, matricula.getIdMatricula());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Alumno alumno = new Alumno(
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            rs.getString("correo"),
                            rs.getString("dni"),
                            rs.getDate("fechaNacimiento").toLocalDate()
                    );

                    List<Asignatura> asignaturas = getAsignaturasMatricula(rs.getInt("idMatricula"));

                    Matricula resultado = new Matricula(
                            rs.getInt("idMatricula"),
                            rs.getString("cursoAcademico"),
                            rs.getDate("fechaMatriculacion").toLocalDate(),
                            alumno,
                            asignaturas
                    );

                    if (rs.getDate("fechaAnulacion") != null) {
                        resultado.setFechaAnulacion(rs.getDate("fechaAnulacion").toLocalDate());
                    }

                    return resultado;
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + "Error al buscar matrícula: " + e.getMessage(), e);
        }
        return null;
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
        if (alumno == null) {
            throw new IllegalArgumentException(ERROR + "El alumno no puede ser nulo");
        }

        List<Matricula> matriculasAlumno = new ArrayList<>();
        String sql = "SELECT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, m.fechaAnulacion, " +
                "a.nombre, a.telefono, a.correo, a.dni, a.fechaNacimiento " +
                "FROM matricula m " +
                "JOIN alumno a ON m.dni = a.dni " +
                "WHERE m.dni = ? " +
                "ORDER BY m.fechaMatriculacion DESC";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, alumno.getDni());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Crea el objeto Alumno con todos los datos del ResultSet
                    Alumno alumnoCompleto = new Alumno(
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            rs.getString("correo"),
                            rs.getString("dni"),
                            rs.getDate("fechaNacimiento").toLocalDate()
                    );

                    // Obtiene la lista de asignaturas de la matrícula
                    List<Asignatura> asignaturas = getAsignaturasMatricula(rs.getInt("idMatricula"));

                    // Crea la matrícula con todos los datos
                    Matricula matricula = new Matricula(
                            rs.getInt("idMatricula"),
                            rs.getString("cursoAcademico"),
                            rs.getDate("fechaMatriculacion").toLocalDate(),
                            alumnoCompleto,
                            asignaturas
                    );

                    // Si hay fecha de anulación, la establece
                    if (rs.getDate("fechaAnulacion") != null) {
                        matricula.setFechaAnulacion(rs.getDate("fechaAnulacion").toLocalDate());
                    }

                    matriculasAlumno.add(matricula);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + "Error al obtener matrículas del alumno", e);
        }

        return matriculasAlumno;
    }





    @Override
    public List<Matricula> get(String cursoAcademico) {

        if (cursoAcademico == null || cursoAcademico.trim().isEmpty()) {
            throw new IllegalArgumentException(ERROR + "El curso académico no puede ser nulo o vacío");
        }

        List<Matricula> matriculas = new ArrayList<>();
        String sql = "SELECT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, " +
                "m.fechaAnulacion, m.dni, a.nombre, a.telefono, a.correo, a.fechaNacimiento " +
                "FROM matricula m " +
                "JOIN alumno a ON m.dni = a.dni " +
                "WHERE m.cursoAcademico = ? " +
                "ORDER BY m.fechaMatriculacion DESC";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cursoAcademico);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    try {
                        // Crear alumno
                        Alumno alumno = new Alumno(
                                rs.getString("nombre"),
                                rs.getString("telefono"),
                                rs.getString("correo"),
                                rs.getString("dni"),
                                rs.getDate("fechaNacimiento").toLocalDate()
                        );

                        // Obtener asignaturas
                        List<Asignatura> asignaturas = new ArrayList<>();
                        String sqlAsignaturas = "SELECT a.codigo, a.nombre, a.horasAnuales, " +
                                "a.horasDesdoble, a.curso, a.especialidadProfesorado, " +
                                "cf.codigo AS cicloCodigo, cf.familiaProfesional, " +
                                "cf.grado, cf.nombre AS cicloNombre, cf.horas AS cicloHoras " +
                                "FROM asignatura a " +
                                "JOIN asignaturasMatricula am ON a.codigo = am.codigo " +
                                "JOIN cicloFormativo cf ON a.cicloFormativo = cf.codigo " +
                                "WHERE am.idMatricula = ?";

                        try (PreparedStatement psAsignaturas = conexion.prepareStatement(sqlAsignaturas)) {
                            psAsignaturas.setInt(1, rs.getInt("idMatricula"));
                            try (ResultSet rsAsignaturas = psAsignaturas.executeQuery()) {
                                while (rsAsignaturas.next()) {
                                    // Crear ciclo formativo
                                    int cicloCodigo = rsAsignaturas.getInt("cicloCodigo");
                                    String familia = rsAsignaturas.getString("familiaProfesional");
                                    String tipoGrado = rsAsignaturas.getString("grado");
                                    String cicloNombre = rsAsignaturas.getString("cicloNombre");
                                    int cicloHoras = rsAsignaturas.getInt("cicloHoras");

                                    Grado grado;
                                    if ("gradod".equalsIgnoreCase(tipoGrado)) {
                                        grado = new GradoD(cicloNombre, 2, Modalidad.PRESENCIAL);
                                    } else {
                                        grado = new GradoE(cicloNombre, 1, 5);
                                    }

                                    CicloFormativo ciclo = new CicloFormativo(cicloCodigo, familia, grado, cicloNombre, cicloHoras);

                                    // Crear asignatura
                                    Asignatura asignatura = new Asignatura(
                                            rsAsignaturas.getString("codigo"),
                                            rsAsignaturas.getString("nombre"),
                                            rsAsignaturas.getInt("horasAnuales"),
                                            Curso.valueOf(rsAsignaturas.getString("curso").toUpperCase()),
                                            rsAsignaturas.getInt("horasDesdoble"),
                                            EspecialidadProfesorado.valueOf(rsAsignaturas.getString("especialidadProfesorado").toUpperCase()),
                                            ciclo
                                    );
                                    asignaturas.add(asignatura);
                                }
                            }
                        }

                        // Crear matrícula
                        Matricula matricula = new Matricula(
                                rs.getInt("idMatricula"),
                                rs.getString("cursoAcademico"),
                                rs.getDate("fechaMatriculacion").toLocalDate(),
                                alumno,
                                asignaturas
                        );

                        if (rs.getDate("fechaAnulacion") != null) {
                            matricula.setFechaAnulacion(rs.getDate("fechaAnulacion").toLocalDate());
                        }

                        matriculas.add(matricula);
                    } catch (SQLException | IllegalArgumentException e) {
                        System.err.println(ERROR + "Error al procesar matrícula: " + e.getMessage());
                        // Continuar con la siguiente matrícula
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + "Error al obtener matrículas por curso académico", e);
        }

        return matriculas;
    }


    @Override
    public List<Matricula> get(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException(ERROR + "El ciclo formativo no puede ser nulo");
        }

        List<Matricula> matriculas = new ArrayList<>();
        String sql = "SELECT DISTINCT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, " +
                "m.fechaAnulacion, m.dni, al.nombre, al.telefono, al.correo, al.fechaNacimiento " +
                "FROM matricula m " +
                "JOIN asignaturasMatricula am ON m.idMatricula = am.idMatricula " +
                "JOIN asignatura a ON am.codigo = a.codigo " +
                "JOIN alumno al ON m.dni = al.dni " +
                "WHERE a.cicloFormativo = ? " +
                "ORDER BY m.fechaMatriculacion DESC";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Depuración clave: Verifica el código recibido
            System.out.println("DEBUG - Código ciclo recibido: " + cicloFormativo.getCodigo());
            ps.setInt(1, cicloFormativo.getCodigo());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Alumno alumno = new Alumno(
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            rs.getString("correo"),
                            rs.getString("dni"),
                            rs.getDate("fechaNacimiento").toLocalDate()
                    );

                    List<Asignatura> asignaturas = getAsignaturasMatricula(rs.getInt("idMatricula"));

                    Matricula matricula = new Matricula(
                            rs.getInt("idMatricula"),
                            rs.getString("cursoAcademico"),
                            rs.getDate("fechaMatriculacion").toLocalDate(),
                            alumno,
                            asignaturas
                    );

                    if (rs.getDate("fechaAnulacion") != null) {
                        matricula.setFechaAnulacion(rs.getDate("fechaAnulacion").toLocalDate());
                    }

                    matriculas.add(matricula);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + "Error al obtener matrículas por ciclo formativo", e);
        }

        return matriculas;
    }










}

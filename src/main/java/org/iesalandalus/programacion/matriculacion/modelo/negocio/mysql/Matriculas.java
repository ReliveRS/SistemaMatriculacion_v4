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
    private Connection conexion;

    public static Matriculas getInstancia() {
        if (instancia == null) {
            instancia = new Matriculas();
        }
        return instancia;
    }
    @Override
    public void comenzar() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = MySQL.establecerConexion();
                // Configuración adicional recomendada
                conexion.setAutoCommit(true);
                conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(ERROR + "No se pudo establecer la conexión: " + e.getMessage(), e);
        }
    }

    @Override
    public void terminar() {
        MySQL.cerrarConexion();
    }


    public List<Asignatura> getAsignaturasMatricula(int idMatricula) {
        verificarConexion();
        List<Asignatura> asignaturas = new ArrayList<>();

        String sql = "SELECT a.codigo, a.nombre, a.horasAnuales, a.horasDesdoble, " +
                "a.curso, a.especialidadProfesorado, " +
                "cf.codigo AS cicloCodigo, cf.familiaProfesional, cf.grado, " +
                "cf.nombre AS cicloNombre, cf.horas AS cicloHoras " +
                "FROM asignatura a " +
                "JOIN asignaturasMatricula am ON a.codigo = am.codigo " +
                "JOIN cicloFormativo cf ON a.cicloFormativo = cf.codigo " +  // Cambiado aquí
                "WHERE am.idMatricula = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idMatricula);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Asignatura asignatura = crearAsignaturaDesdeResultSet(rs);
                    asignaturas.add(asignatura);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + "Error al obtener asignaturas: " + e.getMessage(), e);
        }
        return asignaturas;
    }

    private Asignatura crearAsignaturaDesdeResultSet(ResultSet rs) throws SQLException {
        String codigo = rs.getString("codigo");
        String nombre = rs.getString("nombre");
        int horasAnuales = rs.getInt("horasAnuales");
        int horasDesdoble = rs.getInt("horasDesdoble");
        Curso curso = Curso.valueOf(rs.getString("curso").toUpperCase());
        EspecialidadProfesorado especialidad = EspecialidadProfesorado.valueOf(
                rs.getString("especialidadProfesorado").toUpperCase());

        CicloFormativo ciclo = crearCicloFormativoDesdeResultSet(rs);

        return new Asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, especialidad, ciclo);
    }

    private CicloFormativo crearCicloFormativoDesdeResultSet(ResultSet rs) throws SQLException {
        int codigo = rs.getInt("cicloCodigo");
        String familia = rs.getString("familiaProfesional");
        String tipoGrado = rs.getString("grado");
        String nombre = rs.getString("cicloNombre");
        int horas = rs.getInt("cicloHoras");

        Grado grado;
        if ("gradod".equalsIgnoreCase(tipoGrado)) {
            grado = new GradoD(nombre, 2, Modalidad.PRESENCIAL);
        } else {
            grado = new GradoE(nombre, 1, 5);
        }

        return new CicloFormativo(codigo, familia, grado, nombre, horas);
    }




    @Override
    public List<Matricula> get() {
        verificarConexion();
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
                tamano = filas.getInt(1); // Obtiene el resultado del COUNT(*)
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + e.getMessage());
        }
        return tamano;
    }

    public void insertarAsignaturasMatricula(int idMatricula, List<Asignatura> asignaturas)
            throws OperationNotSupportedException {
        verificarConexion();
        if (asignaturas == null || asignaturas.isEmpty()) {
            throw new IllegalArgumentException("ERROR: La lista de asignaturas no puede ser nula o vacía.");
        }

        String sentenciaStr = "INSERT INTO asignaturasMatricula (idMatricula, codigo) VALUES (?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr)) {
            conexion.setAutoCommit(false); // Desactivar autocommit para transacción

            for (Asignatura asignatura : asignaturas) {
                sentencia.setInt(1, idMatricula);
                sentencia.setString(2, asignatura.getCodigo());
                sentencia.addBatch();
            }

            sentencia.executeBatch();
            conexion.commit(); // Confirmar transacción
        } catch (SQLIntegrityConstraintViolationException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                throw new OperationNotSupportedException(ERROR + "Error al hacer rollback: " + ex.getMessage());
            }
            throw new OperationNotSupportedException("ERROR: Ya existe una relación entre la matrícula y alguna asignatura.");
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                throw new OperationNotSupportedException(ERROR + "Error al hacer rollback: " + ex.getMessage());
            }
            throw new OperationNotSupportedException(ERROR + "Error al insertar asignaturas: " + e.getMessage());
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                throw new OperationNotSupportedException(ERROR + "Error al restaurar autocommit: " + e.getMessage());
            }
        }
    }

    @Override
    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }

        // Verificar si la matrícula ya existe en la base de datos
        try {
            String consultaStr = "SELECT COUNT(*) FROM matricula WHERE idMatricula=?";
            PreparedStatement consulta = conexion.prepareStatement(consultaStr);
            consulta.setInt(1, matricula.getIdMatricula());
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next() && resultado.getInt(1) > 0) {
                throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese ID.");
            }
        } catch (SQLException e) {
            throw new OperationNotSupportedException(ERROR + "Error al verificar la existencia de la matrícula: " + e.getMessage());
        }

        // Insertar la matrícula si no existe
        try {
            // Desactivar el autocommit para iniciar una transacción
            conexion.setAutoCommit(false);

            // Inserción de la matrícula
            String sentenciaStr = "INSERT INTO matricula (idMatricula, cursoAcademico, fechaMatriculacion, fechaAnulacion, dni) VALUES (?, ?, ?, ?, ?)";
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
                throw new OperationNotSupportedException("ERROR al insertar matrícula: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                throw new OperationNotSupportedException("ERROR al revertir los cambios: " + rollbackEx.getMessage());
            }
        } finally {
            try {
                // Restaurar el modo autocommit
                conexion.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                throw new OperationNotSupportedException("ERROR al restaurar autocommit: " + autoCommitEx.getMessage());
            }
        }
    }



    @Override
    public Matricula buscar(Matricula matricula) {
        verificarConexion();
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
        verificarConexion();
        if (alumno == null) {
            throw new IllegalArgumentException(ERROR + "El alumno no puede ser nulo");
        }

        List<Matricula> matriculasAlumno = new ArrayList<>();
        String sql = "SELECT m.* FROM matricula m WHERE m.dni = ? ORDER BY m.fechaMatriculacion DESC";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, alumno.getDni());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    try {
                        Matricula matricula = crearMatriculaDesdeResultSet(rs);
                        matriculasAlumno.add(matricula);
                    } catch (IllegalArgumentException e) {
                        System.err.println(ERROR + "Error al procesar matrícula: " + e.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ERROR + "Error al obtener matrículas del alumno", e);
        }

        return matriculasAlumno;
    }

    private Matricula crearMatriculaDesdeResultSet(ResultSet rs) throws SQLException {
        // Obtener alumno desde la base de datos
        Alumno alumno = Alumnos.getInstancia().buscar(
                new Alumno("", "", "", rs.getString("dni"), LocalDate.now()));

        if (alumno == null) {
            throw new IllegalArgumentException("Alumno no encontrado para DNI: " + rs.getString("dni"));
        }

        // Obtener asignaturas
        List<Asignatura> asignaturas = getAsignaturasMatricula(rs.getInt("idMatricula"));

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

        return matricula;
    }




    @Override
    public List<Matricula> get(String cursoAcademico) {
        verificarConexion();
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
        verificarConexion();
        if (cicloFormativo == null) {
            throw new IllegalArgumentException(ERROR + "El ciclo formativo no puede ser nulo");
        }

        List<Matricula> matriculas = new ArrayList<>();
        String sql = "SELECT DISTINCT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, " +
                "m.fechaAnulacion, m.dni, a.nombre, a.telefono, a.correo, a.fechaNacimiento " +
                "FROM matricula m " +
                "JOIN asignaturasMatricula am ON m.idMatricula = am.idMatricula " +
                "JOIN asignatura asig ON am.codigo = asig.codigo " +
                "JOIN alumno a ON m.dni = a.dni " +
                "WHERE asig.cicloFormativo = ? " +
                "ORDER BY m.fechaMatriculacion DESC";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cicloFormativo.getCodigo());

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
            throw new IllegalArgumentException(ERROR + "Error al obtener matrículas por ciclo formativo", e);
        }

        return matriculas;
    }
    private void verificarConexion() {
        try {
            if (conexion == null || conexion.isClosed() || !conexion.isValid(2)) {
                terminar();
                comenzar();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(ERROR + "Error al verificar conexión: " + e.getMessage(), e);
        }
    }



}

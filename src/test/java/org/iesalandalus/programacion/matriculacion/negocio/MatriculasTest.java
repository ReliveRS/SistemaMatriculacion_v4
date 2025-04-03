package org.iesalandalus.programacion.matriculacion.negocio;

/*
import org.iesalandalus.programacion.matriculacion.MainApp;
import org.iesalandalus.programacion.matriculacion.dominio.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
*/

public class MatriculasTest {
/*
    private static final String ERROR_NO_EXCEPCION = "No debería haber saltado la excepción.";
    private static final String ERROR_CAPACIDAD_NO_CORRECTA = "ERROR: La capacidad debe ser mayor que cero.";
    private static final String ERROR_INSERTAR_MATRICULA_NULA = "ERROR: No se puede insertar una matrícula nula.";
    private static final String ERROR_BORRAR_MATRICULA_NULA = "ERROR: No se puede borrar una matrícula nula.";
    private static final String ERROR_NO_MAS_MATRICULAS = "ERROR: No se aceptan más matrículas.";
    private static final String ERROR_MATRICULA_EXISTE = "ERROR: Ya existe una matrícula con ese identificador.";
    private static final String ERROR_MATRICULA_BORRAR_NO_EXISTE = "ERROR: No existe ninguna matrícula como la indicada.";

    private static final String OPERACION_NO_PERMITIDA = "Debería haber saltado una excepción indicando que dicha operación no está permitida.";
    private static final String MATRICULA_NULA = "Debería haber saltado una excepción indicando que no se puede operar con una matrícula nula.";
    private static final String MENSAJE_EXCEPCION_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
    private static final String TIPO_EXCEPCION_NO_CORRECTO = "El tipo de la excepción no es correcto.";
    private static final String EXCEPCION_NO_PROCEDE = "No debería haber saltado la excepción.";
    private static final String OPERACION_NO_REALIZADA = "La operación no la ha realizado correctamente.";
    private static final String MATRICULAS_NO_CREADAS = "Debería haber creado las matrículas correctamente.";
    private static final String REFERENCIA_NO_ESPERADA = "La referencia devuelta es la misma que la pasada.";
    private static final String TAMANO_NO_ESPERADO = "El tamaño devuelto no es el esperado.";
    private static final String CAPACIDAD_NO_ESPERADA="La capacidad devuelta no es la esperada.";
    private static final String MATRICULA_NO_ESPERADA = "La matrícula devuelta no es la que debería ser.";
    private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto.";

    private static final String NOMBRE_JRJR = "José Ramón Jiménez Reyes";
    private static final String DNI_JRJR = "11223344B";
    private static final String TELEFONO_JRJR = "950112233";
    private static final String CORREO_JRJR = "joseramon.jimenez@iesalandalus.org";
    private static final LocalDate FECHA_NACIMIENTO_JRJR=LocalDate.of(2002, 9, 15);
    private static final String NOMBRE_ARDR = "Andrés Rubio Del Río";
    private static final String DNI_ARDR = "22334455Y";
    private static final String TELEFONO_ARDR = "666223344";
    private static final String CORREO_ARDR = "andres.rubio@iesalandalus.org";
    private static final LocalDate FECHA_NACIMIENTO_ARDR=LocalDate.of(1992, 7, 3);
    private static final String NOMBRE_BE = "Bobi Esponja";
    private static final String DNI_BE = "33445566R";
    private static final String TELEFONO_BE = "600334455";
    private static final String CORREO_BE = "bog.esponja@iesalandalus.org";
    private static final LocalDate FECHA_NACIMIENTO_BE=LocalDate.of(1996, 10, 30);

    private static final int ID_MATRICULA=100;
    private static final int ID_MATRICULA_2=200;
    private static final int ID_MATRICULA_3=300;
    private static final String CURSO_ACADEMICO="24-25";
    private static final LocalDate FECHA_MATRICULACION=LocalDate.now().minusDays(3);

    private static final String CODIGO_ASIGNATURA="0100";
    private static final String NOMBRE_ASIGNATURA="Programación";
    private static final int HORAS_ASIGNATURA=256;
    private static final Curso CURSO_ASIGNATURA=Curso.PRIMERO;
    private static final int HORAS_DESDOBLE_ASIGNATURA=4;
    private static final EspecialidadProfesorado ESPECIALIDAD_PROFESORADO_ASIGNATURA=EspecialidadProfesorado.INFORMATICA;

    private static final String CODIGO_ASIGNATURA_2="0200";
    private static final String NOMBRE_ASIGNATURA_2="Base de Datos";
    private static final int HORAS_ASIGNATURA_2=100;
    private static final int HORAS_DESDOBLE_ASIGNATURA_2=3;

    private static final String CODIGO_ASIGNATURA_3="0300";
    private static final String NOMBRE_ASIGNATURA_3="Administración de Sistemas Operativos";
    private static final int HORAS_ASIGNATURA_3=120;
    private static final Curso CURSO_ASIGNATURA_3=Curso.SEGUNDO;
    private static final int HORAS_DESDOBLE_ASIGNATURA_3=0;
    private static final EspecialidadProfesorado ESPECIALIDAD_PROFESORADO_ASIGNATURA_2=EspecialidadProfesorado.SISTEMAS;



    private static final int CODIGO_CF_1=1225;
    private static final String FAMILIA_PROFESIONAL_CF="Informática y Comunicaciones";
    private static final String NOMBRE_CICLO_FORMATIVO="DAW";
    private static final Grado GRADO_CF=Grado.GDCFGS;
    private static final int HORAS_CICLO_FORMATIVO=1000;

    private static final int CODIGO_CF_2=1226;
    private static final String NOMBRE_CICLO_FORMATIVO_2="DAM";
    private static final int CODIGO_CF_3=1227;
    private static final String NOMBRE_CICLO_FORMATIVO_3="ASIR";
    private static CicloFormativo cf1, cf2, cf3;
    private static Asignatura asignatura1, asignatura2, asignatura3;

    private static Alumno alumno1;
    private static Alumno alumno2;
    private static Alumno alumno3;
    private static Matricula matricula1, matricula2, matricula3, matriculaRepetida1;
    private static Asignatura[] coleccionAsignaturas;

    @BeforeAll
    public static void asignarValoresAtributos() {
        cf1 =new CicloFormativo(CODIGO_CF_1, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);
        cf2 = new CicloFormativo(CODIGO_CF_2, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO_2, HORAS_CICLO_FORMATIVO);
        cf3 = new CicloFormativo(CODIGO_CF_3, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO_3, HORAS_CICLO_FORMATIVO);

        alumno1 = new Alumno(NOMBRE_JRJR, DNI_JRJR, CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
        alumno2 = new Alumno(NOMBRE_ARDR, DNI_ARDR, CORREO_ARDR, TELEFONO_ARDR, FECHA_NACIMIENTO_ARDR);
        alumno3 = new Alumno(NOMBRE_BE, DNI_BE, CORREO_BE, TELEFONO_BE, FECHA_NACIMIENTO_BE);

        asignatura1 = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf1);
        asignatura2 = new Asignatura(CODIGO_ASIGNATURA_2,NOMBRE_ASIGNATURA_2,HORAS_ASIGNATURA_2,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA_2,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf2);
        asignatura3= new Asignatura(CODIGO_ASIGNATURA_3, NOMBRE_ASIGNATURA_3,HORAS_ASIGNATURA_3,CURSO_ASIGNATURA_3,HORAS_DESDOBLE_ASIGNATURA_3,ESPECIALIDAD_PROFESORADO_ASIGNATURA_2,cf3);

        coleccionAsignaturas=new Asignatura[5];
        coleccionAsignaturas[0]=asignatura1;
        coleccionAsignaturas[1]=asignatura2;
        coleccionAsignaturas[2]=asignatura3;

        try
        {
            matricula1 = new Matricula(ID_MATRICULA, CURSO_ACADEMICO, FECHA_MATRICULACION, alumno1, coleccionAsignaturas);
            matricula2 = new Matricula(ID_MATRICULA_2, CURSO_ACADEMICO, FECHA_MATRICULACION, alumno2, coleccionAsignaturas);
            matricula3 = new Matricula(ID_MATRICULA_3, CURSO_ACADEMICO, FECHA_MATRICULACION, alumno3, coleccionAsignaturas);
            matriculaRepetida1 =new Matricula(ID_MATRICULA, CURSO_ACADEMICO, FECHA_MATRICULACION, alumno1, coleccionAsignaturas);
        }
        catch (OperationNotSupportedException e)
        {
            fail(ERROR_NO_EXCEPCION);
        }

    }

    @Test
    public void constructorCapacidadValidaCreaAsignaturasCorrectamente() {
        Matriculas matriculas = new Matriculas(MainApp.CAPACIDAD);
        assertNotEquals(null, matriculas, MATRICULAS_NO_CREADAS);
        assertEquals(MainApp.CAPACIDAD, matriculas.getCapacidad(), CAPACIDAD_NO_ESPERADA);
        assertEquals(0, matriculas.getTamano(), TAMANO_NO_ESPERADO);
    }

    @Test
    public void constructorCapacidadNoValidaLanzaExcepcion() {
        Matriculas matriculas = null;

        try {
            matriculas = new Matriculas(0);
            fail(OPERACION_NO_PERMITIDA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CAPACIDAD_NO_CORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(matriculas, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }

        try {
            matriculas = new Matriculas(-1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CAPACIDAD_NO_CORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(matriculas, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void insertarMatriculaValidaInsertaMatriculaCorrectamente() {
        Matriculas matriculas = new Matriculas(5);

        try {
            matriculas.insertar(matricula1);

            Matricula[] copiaMatriculas = matriculas.get();
            assertEquals(1, matriculas.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(matricula1, matriculas.buscar(matricula1), MATRICULA_NO_ESPERADA);
            assertNotSame(matricula1, copiaMatriculas[0], REFERENCIA_NO_ESPERADA);
            assertEquals(matricula1, copiaMatriculas[0], OPERACION_NO_REALIZADA);
        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarDosMatriculasValidasInsertaMatriculasCorrectamente() {
        Matriculas matriculas = new Matriculas(5);

        try {
            matriculas.insertar(matricula1);
            matriculas.insertar(matricula2);

            Matricula[] copiaMatriculas = matriculas.get();
            assertEquals(2, matriculas.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(matricula1, matriculas.buscar(matricula1), MATRICULA_NO_ESPERADA);
            assertNotSame(matricula1, copiaMatriculas[0], REFERENCIA_NO_ESPERADA);
            assertEquals(matricula1, copiaMatriculas[0], OPERACION_NO_REALIZADA);
            assertEquals(matricula2, matriculas.buscar(matricula2), MATRICULA_NO_ESPERADA);
            assertNotSame(matricula2, copiaMatriculas[1], REFERENCIA_NO_ESPERADA);
            assertEquals(matricula2, copiaMatriculas[1], OPERACION_NO_REALIZADA);
        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarTresMatriculasValidasInsertaMatriculasCorrectamente() {
        Matriculas matriculas = new Matriculas(5);

        try {
            matriculas.insertar(matricula1);
            matriculas.insertar(matricula2);
            matriculas.insertar(matricula3);

            Matricula[] copiaMatriculas = matriculas.get();

            assertEquals(3, matriculas.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(matricula1, matriculas.buscar(matricula1), MATRICULA_NO_ESPERADA);
            assertNotSame(matricula1, copiaMatriculas[0], REFERENCIA_NO_ESPERADA);
            assertEquals(matricula1, copiaMatriculas[0], OPERACION_NO_REALIZADA);
            assertEquals(matricula2, matriculas.buscar(matricula2), MATRICULA_NO_ESPERADA);
            assertNotSame(matricula2, copiaMatriculas[1], REFERENCIA_NO_ESPERADA);
            assertEquals(matricula2, copiaMatriculas[1], OPERACION_NO_REALIZADA);
            assertEquals(matricula3, matriculas.buscar(matricula3), MATRICULA_NO_ESPERADA);
            assertNotSame(matricula3, copiaMatriculas[2], REFERENCIA_NO_ESPERADA);
            assertEquals(matricula3, copiaMatriculas[2], OPERACION_NO_REALIZADA);


        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarMatriculaNulaLanzaExcepcion() {
        Matriculas matriculas = new Matriculas(5);

        try {
            matriculas.insertar(null);
            fail(MATRICULA_NULA);
        } catch (NullPointerException e) {
            assertEquals(ERROR_INSERTAR_MATRICULA_NULA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(0, matriculas.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void insertarMatriculaRepetidaLanzaExcepcion() {
        Matriculas matriculas = new Matriculas(5);

        try {
            matriculas.insertar(matricula1);
            matriculas.insertar(matricula2);
            matriculas.insertar(matricula3);
            matriculas.insertar(matriculaRepetida1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_MATRICULA_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, matriculas.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }

        matriculas = new Matriculas(5);
        try {
            matriculas.insertar(matricula2);
            matriculas.insertar(matricula1);
            matriculas.insertar(matricula3);
            matriculas.insertar(matriculaRepetida1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_MATRICULA_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, matriculas.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }

        matriculas = new Matriculas(5);
        try {
            matriculas.insertar(matricula2);
            matriculas.insertar(matricula3);
            matriculas.insertar(matricula1);
            matriculas.insertar(matriculaRepetida1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_MATRICULA_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, matriculas.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void insertarMatriculaValidaConMatriculasLlenoLanzaExcepcion() {
        Matriculas matriculas = new Matriculas(2);

        try {
            matriculas.insertar(matricula1);
            matriculas.insertar(matricula2);
            matriculas.insertar(matricula3);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_NO_MAS_MATRICULAS, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(2, matriculas.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(matricula1, matriculas.buscar(matricula1), MATRICULA_NO_ESPERADA);
            assertNotSame(matricula1, matriculas.buscar(matricula1), REFERENCIA_NO_ESPERADA);
            try
            {
                assertEquals(matricula1, matriculas.get()[0], OPERACION_NO_REALIZADA);

            }
            catch (OperationNotSupportedException e1)
            {
                System.out.println(e1.getMessage());
            }

            assertEquals(matricula2, matriculas.buscar(matricula2), MATRICULA_NO_ESPERADA);
            assertNotSame(matricula2, matriculas.buscar(matricula2), REFERENCIA_NO_ESPERADA);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void borrarMatriculaExistenteBorraMatriculaCorrectamente() throws OperationNotSupportedException {
        Matriculas matriculas = new Matriculas(5);

        try {
            matriculas.insertar(matricula1);
            matriculas.borrar(matricula1);
            assertEquals(0, matriculas.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(matriculas.buscar(matricula1), MATRICULA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        matriculas = new Matriculas(5);
        try {
            matriculas.insertar(matricula1);
            matriculas.insertar(matricula2);
            matriculas.borrar(matricula1);
            assertEquals(1, matriculas.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(matricula2, matriculas.buscar(matricula2), MATRICULA_NO_ESPERADA);
            assertNull(matriculas.buscar(matricula1), MATRICULA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        matriculas = new Matriculas(5);
        try {
            matriculas.insertar(matricula1);
            matriculas.insertar(matricula2);
            matriculas.borrar(matricula2);
            assertEquals(1, matriculas.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(matricula1, matriculas.buscar(matricula1), MATRICULA_NO_ESPERADA);
            assertNull(matriculas.buscar(matricula2), MATRICULA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        matriculas = new Matriculas(5);
        try {
            matriculas.insertar(matricula1);
            matriculas.insertar(matricula2);
            matriculas.insertar(matricula3);
            matriculas.borrar(matricula1);
            assertEquals(2, matriculas.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(matriculas.buscar(matricula1), MATRICULA_NO_ESPERADA);
            assertEquals(matricula2, matriculas.buscar(matricula2), MATRICULA_NO_ESPERADA);
            assertEquals(matricula3, matriculas.buscar(matricula3), MATRICULA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        matriculas = new Matriculas(5);
        try {
            matriculas.insertar(matricula1);
            matriculas.insertar(matricula2);
            matriculas.insertar(matricula3);
            matriculas.borrar(matricula2);
            assertEquals(2, matriculas.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(matriculas.buscar(matricula2), MATRICULA_NO_ESPERADA);
            assertEquals(matricula1, matriculas.buscar(matricula1), MATRICULA_NO_ESPERADA);
            assertEquals(matricula3, matriculas.buscar(matricula3), MATRICULA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        matriculas = new Matriculas(5);
        try {
            matriculas.insertar(matricula1);
            matriculas.insertar(matricula2);
            matriculas.insertar(matricula3);
            matriculas.borrar(matricula3);
            assertEquals(2, matriculas.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(matriculas.buscar(matricula3), MATRICULA_NO_ESPERADA);
            assertEquals(matricula1, matriculas.buscar(matricula1), MATRICULA_NO_ESPERADA);
            assertEquals(matricula2, matriculas.buscar(matricula2), MATRICULA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        matriculas = new Matriculas(3);
        try {
            matriculas.insertar(matricula1);
            matriculas.insertar(matricula2);
            matriculas.insertar(matricula3);
            matriculas.borrar(matricula2);
            assertEquals(2, matriculas.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(matriculas.buscar(matricula2), MATRICULA_NO_ESPERADA);
            assertEquals(matricula1, matriculas.buscar(matricula1), MATRICULA_NO_ESPERADA);
            assertEquals(matricula3, matriculas.buscar(matricula3), MATRICULA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void borrarMatriculaNoExistenteLanzaExcepcion() {
        Matriculas matriculas = new Matriculas(5);

        try {
            matriculas.insertar(matricula1);
            matriculas.borrar(matricula2);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_MATRICULA_BORRAR_NO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(1, matriculas.getTamano(),TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }


        matriculas = new Matriculas(5);
        try {
            matriculas.insertar(matricula1);
            matriculas.insertar(matricula2);
            matriculas.borrar(matricula3);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_MATRICULA_BORRAR_NO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(2, matriculas.getTamano(),TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void borrarMatriculaNulaLanzaExcepcion() {
        Matriculas matriculas = new Matriculas(5);
        try {
            matriculas.insertar(matricula1);
            matriculas.borrar(null);
            fail(MATRICULA_NULA);
        } catch (NullPointerException e) {
            assertEquals(ERROR_BORRAR_MATRICULA_NULA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(1, matriculas.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }


*/
}

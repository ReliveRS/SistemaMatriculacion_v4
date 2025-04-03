package org.iesalandalus.programacion.matriculacion.negocio;

/*
import org.iesalandalus.programacion.matriculacion.MainApp;
import org.iesalandalus.programacion.matriculacion.dominio.Alumno;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
*/

public class AlumnosTest {
/*
    private static final String ERROR_CAPACIDAD_NO_CORRECTA = "ERROR: La capacidad debe ser mayor que cero.";
    private static final String ERROR_INSERTAR_ALUMNO_NULO = "ERROR: No se puede insertar un alumno nulo.";
    private static final String ERROR_BORRAR_ALUMNO_NULO = "ERROR: No se puede borrar un alumno nulo.";
    private static final String ERROR_NO_MAS_ALUMNOS = "ERROR: No se aceptan más alumnos.";
    private static final String ERROR_ALUMNO_EXISTE = "ERROR: Ya existe un alumno con ese dni.";
    private static final String ERROR_ALUMNO_BORRAR_NO_EXISTE = "ERROR: No existe ningún alumno como el indicado.";

    private static final String OPERACION_NO_PERMITIDA = "Debería haber saltado una excepción indicando que dicha operación no está permitida.";
    private static final String ALUMNO_NULO = "Debería haber saltado una excepción indicando que no se puede operar con un alumno nulo.";
    private static final String MENSAJE_EXCEPCION_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
    private static final String TIPO_EXCEPCION_NO_CORRECTO = "El tipo de la excepción no es correcto.";
    private static final String EXCEPCION_NO_PROCEDE = "No debería haber saltado la excepción.";
    private static final String OPERACION_NO_REALIZADA = "La operación no la ha realizado correctamente.";
    private static final String ALUMNOS_NO_CREADOS = "Debería haber creado los alumnos correctamente.";
    private static final String REFERENCIA_NO_ESPERADA = "La referencia devuelta es la misma que la pasada.";
    private static final String TAMANO_NO_ESPERADO = "El tamaño devuelto no es el esperado.";
    private static final String CAPACIDAD_NO_ESPERADA="La capacidad devuelta no es la esperada.";
    private static final String ALUMNO_NO_ESPERADO = "El alumno devuelto no es el que debería ser.";
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


    private static Alumno alumno1;
    private static Alumno alumno2;
    private static Alumno alumno3;
    private static Alumno alumnoRepetido1;


    @BeforeAll
    public static void asignarValoresAtributos() {
        alumno1 = new Alumno(NOMBRE_JRJR, DNI_JRJR, CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
        alumno2 = new Alumno(NOMBRE_ARDR, DNI_ARDR, CORREO_ARDR, TELEFONO_ARDR, FECHA_NACIMIENTO_ARDR);
        alumno3 = new Alumno(NOMBRE_BE, DNI_BE, CORREO_BE, TELEFONO_BE, FECHA_NACIMIENTO_BE);
        alumnoRepetido1 =new Alumno(NOMBRE_ARDR, DNI_ARDR, CORREO_ARDR, TELEFONO_ARDR, FECHA_NACIMIENTO_ARDR);

    }

    @Test
    public void constructorCapacidadValidaCreaAlumnosCorrectamente() {
        Alumnos alumnos = new Alumnos(MainApp.CAPACIDAD);
        assertNotEquals(null, alumnos, ALUMNOS_NO_CREADOS);
        assertEquals(MainApp.CAPACIDAD, alumnos.getCapacidad(), CAPACIDAD_NO_ESPERADA);
        assertEquals(0, alumnos.getTamano(), TAMANO_NO_ESPERADO);
    }

    @Test
    public void constructorCapacidadNoValidaLanzaExcepcion() {
        Alumnos alumnos = null;

        try {
            alumnos = new Alumnos(0);
            fail(OPERACION_NO_PERMITIDA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CAPACIDAD_NO_CORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(alumnos, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
        try {
            alumnos = new Alumnos(-1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CAPACIDAD_NO_CORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(alumnos, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void insertarAlumnoValidoInsertaAlumnoCorrectamente() {
        Alumnos alumnos = new Alumnos(5);

        try {
            alumnos.insertar(alumno1);

            Alumno[] copiaAlumnos = alumnos.get();
            assertEquals(1, alumnos.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(alumno1, alumnos.buscar(alumno1), ALUMNO_NO_ESPERADO);
            assertNotSame(alumno1, copiaAlumnos[0], REFERENCIA_NO_ESPERADA);
            assertEquals(alumno1, copiaAlumnos[0], OPERACION_NO_REALIZADA);
        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarDosAlumnosValidosInsertaAlumnosCorrectamente() {
        Alumnos alumnos = new Alumnos(5);

        try {
            alumnos.insertar(alumno1);
            alumnos.insertar(alumno2);

            Alumno[] copiaAlumnos = alumnos.get();
            assertEquals(2, alumnos.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(alumno1, alumnos.buscar(alumno1), ALUMNO_NO_ESPERADO);
            assertNotSame(alumno1, copiaAlumnos[0], REFERENCIA_NO_ESPERADA);
            assertEquals(alumno1, copiaAlumnos[0], OPERACION_NO_REALIZADA);
            assertEquals(alumno2, alumnos.buscar(alumno2), ALUMNO_NO_ESPERADO);
            assertNotSame(alumno2, copiaAlumnos[1], REFERENCIA_NO_ESPERADA);
            assertEquals(alumno2, copiaAlumnos[1], OPERACION_NO_REALIZADA);
        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarTresAlumnosValidosInsertaAlumnosCorrectamente() {
        Alumnos alumnos = new Alumnos(5);

        try {
            alumnos.insertar(alumno1);
            alumnos.insertar(alumno2);
            alumnos.insertar(alumno3);

            Alumno[] copiaAlumnos = alumnos.get();

            assertEquals(3, alumnos.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(alumno1, alumnos.buscar(alumno1), ALUMNO_NO_ESPERADO);
            assertNotSame(alumno1, copiaAlumnos[0], REFERENCIA_NO_ESPERADA);
            assertEquals(alumno1, copiaAlumnos[0], OPERACION_NO_REALIZADA);
            assertEquals(alumno2, alumnos.buscar(alumno2), ALUMNO_NO_ESPERADO);
            assertNotSame(alumno2, copiaAlumnos[1], REFERENCIA_NO_ESPERADA);
            assertEquals(alumno2, copiaAlumnos[1], OPERACION_NO_REALIZADA);
            assertEquals(alumno3, alumnos.buscar(alumno3), ALUMNO_NO_ESPERADO);
            assertNotSame(alumno3, copiaAlumnos[2], REFERENCIA_NO_ESPERADA);
            assertEquals(alumno3, copiaAlumnos[2], OPERACION_NO_REALIZADA);


        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarAlumnoNuloLanzaExcepcion() {
        Alumnos alumnos = new Alumnos(5);

        try {
            alumnos.insertar(null);
            fail(ALUMNO_NULO);
        } catch (NullPointerException e) {
            assertEquals(ERROR_INSERTAR_ALUMNO_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(0, alumnos.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void insertarAlumnoRepetidoLanzaExcepcion() {
        Alumnos alumnos = new Alumnos(5);

        try {
            alumnos.insertar(alumno1);
            alumnos.insertar(alumno2);
            alumnos.insertar(alumno3);
            alumnos.insertar(alumnoRepetido1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_ALUMNO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, alumnos.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }

        alumnos = new Alumnos(5);
        try {
            alumnos.insertar(alumno2);
            alumnos.insertar(alumno1);
            alumnos.insertar(alumno3);
            alumnos.insertar(alumnoRepetido1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_ALUMNO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, alumnos.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }

        alumnos = new Alumnos(5);
        try {
            alumnos.insertar(alumno2);
            alumnos.insertar(alumno3);
            alumnos.insertar(alumno1);
            alumnos.insertar(alumnoRepetido1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_ALUMNO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, alumnos.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void insertarAlumnoValidoConAlumnosLlenoLanzaExcepcion() {
        Alumnos alumnos = new Alumnos(2);

        try {
            alumnos.insertar(alumno1);
            alumnos.insertar(alumno2);
            alumnos.insertar(alumno3);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_NO_MAS_ALUMNOS, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(2, alumnos.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(alumno1, alumnos.buscar(alumno1), ALUMNO_NO_ESPERADO);
            assertNotSame(alumno1, alumnos.buscar(alumno1), REFERENCIA_NO_ESPERADA);
            assertEquals(alumno1, alumnos.get()[0], OPERACION_NO_REALIZADA);
            assertEquals(alumno2, alumnos.buscar(alumno2), ALUMNO_NO_ESPERADO);
            assertNotSame(alumno2, alumnos.buscar(alumno2), REFERENCIA_NO_ESPERADA);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void borrarAlumnoExistenteBorraAlumnoCorrectamente() throws OperationNotSupportedException {
        Alumnos alumnos = new Alumnos(5);

        try {
            alumnos.insertar(alumno1);
            alumnos.borrar(alumno1);
            assertEquals(0, alumnos.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(alumnos.buscar(alumno1), ALUMNO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        alumnos = new Alumnos(5);
        try {
            alumnos.insertar(alumno1);
            alumnos.insertar(alumno2);
            alumnos.borrar(alumno1);
            assertEquals(1, alumnos.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(alumno2, alumnos.buscar(alumno2), ALUMNO_NO_ESPERADO);
            assertNull(alumnos.buscar(alumno1), ALUMNO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        alumnos = new Alumnos(5);
        try {
            alumnos.insertar(alumno1);
            alumnos.insertar(alumno2);
            alumnos.borrar(alumno2);
            assertEquals(1, alumnos.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(alumno1, alumnos.buscar(alumno1), ALUMNO_NO_ESPERADO);
            assertNull(alumnos.buscar(alumno2), ALUMNO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        alumnos = new Alumnos(5);
        try {
            alumnos.insertar(alumno1);
            alumnos.insertar(alumno2);
            alumnos.insertar(alumno3);
            alumnos.borrar(alumno1);
            assertEquals(2, alumnos.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(alumnos.buscar(alumno1), ALUMNO_NO_ESPERADO);
            assertEquals(alumno2, alumnos.buscar(alumno2), ALUMNO_NO_ESPERADO);
            assertEquals(alumno3, alumnos.buscar(alumno3), ALUMNO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        alumnos = new Alumnos(5);
        try {
            alumnos.insertar(alumno1);
            alumnos.insertar(alumno2);
            alumnos.insertar(alumno3);
            alumnos.borrar(alumno2);
            assertEquals(2, alumnos.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(alumnos.buscar(alumno2), ALUMNO_NO_ESPERADO);
            assertEquals(alumno1, alumnos.buscar(alumno1), ALUMNO_NO_ESPERADO);
            assertEquals(alumno3, alumnos.buscar(alumno3), ALUMNO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        alumnos = new Alumnos(5);
        try {
            alumnos.insertar(alumno1);
            alumnos.insertar(alumno2);
            alumnos.insertar(alumno3);
            alumnos.borrar(alumno3);
            assertEquals(2, alumnos.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(alumnos.buscar(alumno3), ALUMNO_NO_ESPERADO);
            assertEquals(alumno1, alumnos.buscar(alumno1), ALUMNO_NO_ESPERADO);
            assertEquals(alumno2, alumnos.buscar(alumno2), ALUMNO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        alumnos = new Alumnos(3);
        try {
            alumnos.insertar(alumno1);
            alumnos.insertar(alumno2);
            alumnos.insertar(alumno3);
            alumnos.borrar(alumno2);
            assertEquals(2, alumnos.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(alumnos.buscar(alumno2), ALUMNO_NO_ESPERADO);
            assertEquals(alumno1, alumnos.buscar(alumno1), ALUMNO_NO_ESPERADO);
            assertEquals(alumno3, alumnos.buscar(alumno3), ALUMNO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void borrarAlumnoNoExistenteLanzaExcepcion() {
        Alumnos alumnos = new Alumnos(5);

        try {
            alumnos.insertar(alumno1);
            alumnos.borrar(alumno2);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_ALUMNO_BORRAR_NO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(1, alumnos.getTamano(),TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }


        alumnos = new Alumnos(5);
        try {
            alumnos.insertar(alumno1);
            alumnos.insertar(alumno2);
            alumnos.borrar(alumno3);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_ALUMNO_BORRAR_NO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(2, alumnos.getTamano(),TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void borrarAlumnoNuloLanzaExcepcion() {
        Alumnos alumnos = new Alumnos(5);
        try {
            alumnos.insertar(alumno1);
            alumnos.borrar(null);
            fail(ALUMNO_NULO);
        } catch (NullPointerException e) {
            assertEquals(ERROR_BORRAR_ALUMNO_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(1, alumnos.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

*/

}

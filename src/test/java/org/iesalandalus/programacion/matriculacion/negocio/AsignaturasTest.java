package org.iesalandalus.programacion.matriculacion.negocio;

/*
import org.iesalandalus.programacion.matriculacion.MainApp;
import org.iesalandalus.programacion.matriculacion.dominio.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.jupiter.api.Assertions.*;
*/

public class AsignaturasTest {
/*
    private static final String ERROR_CAPACIDAD_NO_CORRECTA = "ERROR: La capacidad debe ser mayor que cero.";
    private static final String ERROR_INSERTAR_ASIGNATURA_NULA = "ERROR: No se puede insertar una asignatura nula.";
    private static final String ERROR_BORRAR_ASIGNATURA_NULA = "ERROR: No se puede borrar una asignatura nula.";
    private static final String ERROR_NO_MAS_ASIGNATURAS = "ERROR: No se aceptan más asignaturas.";
    private static final String ERROR_ASIGNATURA_EXISTE = "ERROR: Ya existe una asignatura con ese código.";
    private static final String ERROR_ASIGNATURA_BORRAR_NO_EXISTE = "ERROR: No existe ninguna asignatura como la indicada.";

    private static final String OPERACION_NO_PERMITIDA = "Debería haber saltado una excepción indicando que dicha operación no está permitida.";
    private static final String ASIGNATURA_NULA = "Debería haber saltado una excepción indicando que no se puede operar con una asignatura nula.";
    private static final String MENSAJE_EXCEPCION_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
    private static final String TIPO_EXCEPCION_NO_CORRECTO = "El tipo de la excepción no es correcto.";
    private static final String EXCEPCION_NO_PROCEDE = "No debería haber saltado la excepción.";
    private static final String OPERACION_NO_REALIZADA = "La operación no la ha realizado correctamente.";
    private static final String ASIGNATURAS_NO_CREADAS = "Debería haber creado las asignaturas correctamente.";
    private static final String REFERENCIA_NO_ESPERADA = "La referencia devuelta es la misma que la pasada.";
    private static final String TAMANO_NO_ESPERADO = "El tamaño devuelto no es el esperado.";
    private static final String CAPACIDAD_NO_ESPERADA="La capacidad devuelta no es la esperada.";
    private static final String ASIGNATURA_NO_ESPERADA = "La asignatura devuelta no es la que debería ser.";
    private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto.";

    private static final String CODIGO_ASIGNATURA="0100";
    private static final String NOMBRE_ASIGNATURA="Programación";
    private static final int HORAS_ASIGNATURA=256;
    private static final Curso CURSO_ASIGNATURA=Curso.PRIMERO;
    private static final int HORAS_DESDOBLE_ASIGNATURA=4;
    private static final EspecialidadProfesorado ESPECIALIDAD_PROFESORADO_ASIGNATURA=EspecialidadProfesorado.INFORMATICA;

    private static final String CODIGO_ASIGNATURA_2="0200";
    private static final String NOMBRE_ASIGNATURA_2="Base de Datos";
    private static final int HORAS_ASIGNATURA_2=100;
    private static final Curso CURSO_ASIGNATURA_2=Curso.PRIMERO;
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
    private static Asignatura asignatura1, asignatura2, asignatura3,asignaturaRepetida1;
    @BeforeAll
    public static void asignarValoresAtributos() {
        cf1 =new CicloFormativo(CODIGO_CF_1, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);
        cf2 = new CicloFormativo(CODIGO_CF_2, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO_2, HORAS_CICLO_FORMATIVO);
        cf3 = new CicloFormativo(CODIGO_CF_3, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO_3, HORAS_CICLO_FORMATIVO);

       asignatura1 = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf1);
       asignatura2 = new Asignatura(CODIGO_ASIGNATURA_2,NOMBRE_ASIGNATURA_2,HORAS_ASIGNATURA_2,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA_2,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf2);
       asignatura3= new Asignatura(CODIGO_ASIGNATURA_3, NOMBRE_ASIGNATURA_3,HORAS_ASIGNATURA_3,CURSO_ASIGNATURA_3,HORAS_DESDOBLE_ASIGNATURA_3,ESPECIALIDAD_PROFESORADO_ASIGNATURA_2,cf3);
       asignaturaRepetida1=new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf1);

    }

    @Test
    public void constructorCapacidadValidaCreaAsignaturasCorrectamente() {
        Asignaturas asignaturas = new Asignaturas(MainApp.CAPACIDAD);
        assertNotEquals(null, asignaturas, ASIGNATURAS_NO_CREADAS);
        assertEquals(MainApp.CAPACIDAD, asignaturas.getCapacidad(), CAPACIDAD_NO_ESPERADA);
        assertEquals(0, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
    }

    @Test
    public void constructorCapacidadNoValidaLanzaExcepcion() {
        Asignaturas asignaturas = null;

        try {
            asignaturas = new Asignaturas(0);
            fail(OPERACION_NO_PERMITIDA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CAPACIDAD_NO_CORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignaturas, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
        try {
            asignaturas = new Asignaturas(-1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CAPACIDAD_NO_CORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignaturas, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void insertarAsignaturaValidaInsertaAsignaturaCorrectamente() {
        Asignaturas asignaturas = new Asignaturas(5);

        try {
            asignaturas.insertar(asignatura1);

            Asignatura[] copiaAsignaturas = asignaturas.get();
            assertEquals(1, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(asignatura1, asignaturas.buscar(asignatura1), ASIGNATURA_NO_ESPERADA);
            assertNotSame(asignatura1, copiaAsignaturas[0], REFERENCIA_NO_ESPERADA);
            assertEquals(asignatura1, copiaAsignaturas[0], OPERACION_NO_REALIZADA);
        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarDosAsignaturasValidasInsertaAsignaturasCorrectamente() {
        Asignaturas asignaturas = new Asignaturas(5);

        try {
            asignaturas.insertar(asignatura1);
            asignaturas.insertar(asignatura2);

            Asignatura[] copiaAsignaturas = asignaturas.get();
            assertEquals(2, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(asignatura1, asignaturas.buscar(asignatura1), ASIGNATURA_NO_ESPERADA);
            assertNotSame(asignatura1, copiaAsignaturas[0], REFERENCIA_NO_ESPERADA);
            assertEquals(asignatura1, copiaAsignaturas[0], OPERACION_NO_REALIZADA);
            assertEquals(asignatura2, asignaturas.buscar(asignatura2), ASIGNATURA_NO_ESPERADA);
            assertNotSame(asignatura2, copiaAsignaturas[1], REFERENCIA_NO_ESPERADA);
            assertEquals(asignatura2, copiaAsignaturas[1], OPERACION_NO_REALIZADA);
        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarTresAsignaturasValidasInsertaAsignaturasCorrectamente() {
        Asignaturas asignaturas = new Asignaturas(5);

        try {
            asignaturas.insertar(asignatura1);
            asignaturas.insertar(asignatura2);
            asignaturas.insertar(asignatura3);

            Asignatura[] copiaAsignaturas = asignaturas.get();

            assertEquals(3, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(asignatura1, asignaturas.buscar(asignatura1), ASIGNATURA_NO_ESPERADA);
            assertNotSame(asignatura1, copiaAsignaturas[0], REFERENCIA_NO_ESPERADA);
            assertEquals(asignatura1, copiaAsignaturas[0], OPERACION_NO_REALIZADA);
            assertEquals(asignatura2, asignaturas.buscar(asignatura2), ASIGNATURA_NO_ESPERADA);
            assertNotSame(asignatura2, copiaAsignaturas[1], REFERENCIA_NO_ESPERADA);
            assertEquals(asignatura2, copiaAsignaturas[1], OPERACION_NO_REALIZADA);
            assertEquals(asignatura3, asignaturas.buscar(asignatura3), ASIGNATURA_NO_ESPERADA);
            assertNotSame(asignatura3, copiaAsignaturas[2], REFERENCIA_NO_ESPERADA);
            assertEquals(asignatura3, copiaAsignaturas[2], OPERACION_NO_REALIZADA);


        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarAsignaturaNulaLanzaExcepcion() {
        Asignaturas asignaturas = new Asignaturas(5);

        try {
            asignaturas.insertar(null);
            fail(ASIGNATURA_NULA);
        } catch (NullPointerException e) {
            assertEquals(ERROR_INSERTAR_ASIGNATURA_NULA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(0, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void insertarAsignaturaRepetidaLanzaExcepcion() {
        Asignaturas asignaturas = new Asignaturas(5);

        try {
            asignaturas.insertar(asignatura1);
            asignaturas.insertar(asignatura2);
            asignaturas.insertar(asignatura3);
            asignaturas.insertar(asignaturaRepetida1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_ASIGNATURA_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }

        asignaturas = new Asignaturas(5);
        try {
            asignaturas.insertar(asignatura2);
            asignaturas.insertar(asignatura1);
            asignaturas.insertar(asignatura3);
            asignaturas.insertar(asignaturaRepetida1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_ASIGNATURA_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }

        asignaturas = new Asignaturas(5);
        try {
            asignaturas.insertar(asignatura2);
            asignaturas.insertar(asignatura3);
            asignaturas.insertar(asignatura1);
            asignaturas.insertar(asignaturaRepetida1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_ASIGNATURA_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void insertarAsignaturaValidaConAsignaturasLlenoLanzaExcepcion() {
        Asignaturas asignaturas = new Asignaturas(2);

        try {
            asignaturas.insertar(asignatura1);
            asignaturas.insertar(asignatura2);
            asignaturas.insertar(asignatura3);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_NO_MAS_ASIGNATURAS, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(2, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(asignatura1, asignaturas.buscar(asignatura1), ASIGNATURA_NO_ESPERADA);
            assertNotSame(asignatura1, asignaturas.buscar(asignatura1), REFERENCIA_NO_ESPERADA);
            assertEquals(asignatura1, asignaturas.get()[0], OPERACION_NO_REALIZADA);
            assertEquals(asignatura2, asignaturas.buscar(asignatura2), ASIGNATURA_NO_ESPERADA);
            assertNotSame(asignatura2, asignaturas.buscar(asignatura2), REFERENCIA_NO_ESPERADA);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void borrarAsignaturaExistenteBorraAsignaturaCorrectamente() throws OperationNotSupportedException {
        Asignaturas asignaturas = new Asignaturas(5);

        try {
            asignaturas.insertar(asignatura1);
            asignaturas.borrar(asignatura1);
            assertEquals(0, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(asignaturas.buscar(asignatura1), ASIGNATURA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        asignaturas = new Asignaturas(5);
        try {
            asignaturas.insertar(asignatura1);
            asignaturas.insertar(asignatura2);
            asignaturas.borrar(asignatura1);
            assertEquals(1, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(asignatura2, asignaturas.buscar(asignatura2), ASIGNATURA_NO_ESPERADA);
            assertNull(asignaturas.buscar(asignatura1), ASIGNATURA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        asignaturas = new Asignaturas(5);
        try {
            asignaturas.insertar(asignatura1);
            asignaturas.insertar(asignatura2);
            asignaturas.borrar(asignatura2);
            assertEquals(1, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(asignatura1, asignaturas.buscar(asignatura1), ASIGNATURA_NO_ESPERADA);
            assertNull(asignaturas.buscar(asignatura2), ASIGNATURA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        asignaturas = new Asignaturas(5);
        try {
            asignaturas.insertar(asignatura1);
            asignaturas.insertar(asignatura2);
            asignaturas.insertar(asignatura3);
            asignaturas.borrar(asignatura1);
            assertEquals(2, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(asignaturas.buscar(asignatura1), ASIGNATURA_NO_ESPERADA);
            assertEquals(asignatura2, asignaturas.buscar(asignatura2), ASIGNATURA_NO_ESPERADA);
            assertEquals(asignatura3, asignaturas.buscar(asignatura3), ASIGNATURA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        asignaturas = new Asignaturas(5);
        try {
            asignaturas.insertar(asignatura1);
            asignaturas.insertar(asignatura2);
            asignaturas.insertar(asignatura3);
            asignaturas.borrar(asignatura2);
            assertEquals(2, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(asignaturas.buscar(asignatura2), ASIGNATURA_NO_ESPERADA);
            assertEquals(asignatura1, asignaturas.buscar(asignatura1), ASIGNATURA_NO_ESPERADA);
            assertEquals(asignatura3, asignaturas.buscar(asignatura3), ASIGNATURA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        asignaturas = new Asignaturas(5);
        try {
            asignaturas.insertar(asignatura1);
            asignaturas.insertar(asignatura2);
            asignaturas.insertar(asignatura3);
            asignaturas.borrar(asignatura3);
            assertEquals(2, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(asignaturas.buscar(asignatura3), ASIGNATURA_NO_ESPERADA);
            assertEquals(asignatura1, asignaturas.buscar(asignatura1), ASIGNATURA_NO_ESPERADA);
            assertEquals(asignatura2, asignaturas.buscar(asignatura2), ASIGNATURA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        asignaturas = new Asignaturas(3);
        try {
            asignaturas.insertar(asignatura1);
            asignaturas.insertar(asignatura2);
            asignaturas.insertar(asignatura3);
            asignaturas.borrar(asignatura2);
            assertEquals(2, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(asignaturas.buscar(asignatura2), ASIGNATURA_NO_ESPERADA);
            assertEquals(asignatura1, asignaturas.buscar(asignatura1), ASIGNATURA_NO_ESPERADA);
            assertEquals(asignatura3, asignaturas.buscar(asignatura3), ASIGNATURA_NO_ESPERADA);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void borrarAsignaturaNoExistenteLanzaExcepcion() {
        Asignaturas asignaturas = new Asignaturas(5);

        try {
            asignaturas.insertar(asignatura1);
            asignaturas.borrar(asignatura2);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_ASIGNATURA_BORRAR_NO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(1, asignaturas.getTamano(),TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }


        asignaturas = new Asignaturas(5);
        try {
            asignaturas.insertar(asignatura1);
            asignaturas.insertar(asignatura2);
            asignaturas.borrar(asignatura3);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_ASIGNATURA_BORRAR_NO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(2, asignaturas.getTamano(),TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void borrarAsignaturaNulaLanzaExcepcion() {
        Asignaturas asignaturas = new Asignaturas(5);
        try {
            asignaturas.insertar(asignatura1);
            asignaturas.borrar(null);
            fail(ASIGNATURA_NULA);
        } catch (NullPointerException e) {
            assertEquals(ERROR_BORRAR_ASIGNATURA_NULA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(1, asignaturas.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }


*/
}

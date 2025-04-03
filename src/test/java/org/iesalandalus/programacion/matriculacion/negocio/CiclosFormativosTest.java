package org.iesalandalus.programacion.matriculacion.negocio;
/*
import org.iesalandalus.programacion.matriculacion.MainApp;
import org.iesalandalus.programacion.matriculacion.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.dominio.Grado;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
*/

public class CiclosFormativosTest {
/*
    private static final String ERROR_CAPACIDAD_NO_CORRECTA = "ERROR: La capacidad debe ser mayor que cero.";
    private static final String ERROR_INSERTAR_CICLO_FORMATIVO_NULO = "ERROR: No se puede insertar un ciclo formativo nulo.";
    private static final String ERROR_BORRAR_CICLO_FORMATIVO_NULO = "ERROR: No se puede borrar un ciclo formativo nulo.";
    private static final String ERROR_NO_MAS_CICLOS_FORMATIVOS = "ERROR: No se aceptan más ciclos formativos.";
    private static final String ERROR_CICLO_FORMATIVO_EXISTE = "ERROR: Ya existe un ciclo formativo con ese código.";
    private static final String ERROR_CICLO_FORMATIVO_BORRAR_NO_EXISTE = "ERROR: No existe ningún ciclo formativo como el indicado.";

    private static final String OPERACION_NO_PERMITIDA = "Debería haber saltado una excepción indicando que dicha operación no está permitida.";
    private static final String CICLO_FORMATIVO_NULO = "Debería haber saltado una excepción indicando que no se puede operar con un ciclo formativo nulo.";
    private static final String MENSAJE_EXCEPCION_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
    private static final String TIPO_EXCEPCION_NO_CORRECTO = "El tipo de la excepción no es correcto.";
    private static final String EXCEPCION_NO_PROCEDE = "No debería haber saltado la excepción.";
    private static final String OPERACION_NO_REALIZADA = "La operación no la ha realizado correctamente.";
    private static final String CICLOS_FORMATIVOS_NO_CREADOS = "Debería haber creado los ciclos formativos correctamente.";
    private static final String REFERENCIA_NO_ESPERADA = "La referencia devuelta es la misma que la pasada.";
    private static final String TAMANO_NO_ESPERADO = "El tamaño devuelto no es el esperado.";
    private static final String CAPACIDAD_NO_ESPERADA="La capacidad devuelta no es la esperada.";
    private static final String CICLO_FORMATIVO_NO_ESPERADO = "El ciclo formativo devuelto no es el que debería ser.";
    private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto.";

    private static CicloFormativo cf1;
    private static CicloFormativo cf2;
    private static CicloFormativo cf3;
    private static CicloFormativo cfRepetido1;

    private static final int CODIGO_CF_1=1225;
    private static final String FAMILIA_PROFESIONAL_CF="Informática y Comunicaciones";
    private static final String NOMBRE_CICLO_FORMATIVO="DAW";
    private static final Grado GRADO_CF=Grado.GDCFGS;
    private static final int HORAS_CICLO_FORMATIVO=1000;

    private static final int CODIGO_CF_2=1226;
    private static final String NOMBRE_CICLO_FORMATIVO_2="DAM";
    private static final int CODIGO_CF_3=1227;
    private static final String NOMBRE_CICLO_FORMATIVO_3="ASIR";


    @BeforeAll
    public static void asignarValoresAtributos() {
        cf1 =new CicloFormativo(CODIGO_CF_1, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);

        cf2 = new CicloFormativo(CODIGO_CF_2, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO_2, HORAS_CICLO_FORMATIVO);
        cf3 = new CicloFormativo(CODIGO_CF_3, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO_3, HORAS_CICLO_FORMATIVO);
        cfRepetido1 =new CicloFormativo(CODIGO_CF_1, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);

    }

    @Test
    public void constructorCapacidadValidaCreaCiclosFormativosCorrectamente() {
        CiclosFormativos ciclosFormativos = new CiclosFormativos(MainApp.CAPACIDAD);
        assertNotEquals(null, ciclosFormativos, CICLOS_FORMATIVOS_NO_CREADOS);
        assertEquals(MainApp.CAPACIDAD, ciclosFormativos.getCapacidad(), CAPACIDAD_NO_ESPERADA);
        assertEquals(0, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
    }

    @Test
    public void constructorCapacidadNoValidaLanzaExcepcion() {
        CiclosFormativos ciclosFormativos = null;

        try {
            ciclosFormativos = new CiclosFormativos(0);
            fail(OPERACION_NO_PERMITIDA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CAPACIDAD_NO_CORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(ciclosFormativos, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
        try {
            ciclosFormativos = new CiclosFormativos(-1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CAPACIDAD_NO_CORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(ciclosFormativos, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void insertarCicloFormativoValidoInsertaCicloFormativoCorrectamente() {
        CiclosFormativos ciclosFormativos = new CiclosFormativos(5);

        try {
            ciclosFormativos.insertar(cf1);

            CicloFormativo[] copiaCiclos = ciclosFormativos.get();
            assertEquals(1, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(cf1, ciclosFormativos.buscar(cf1), CICLO_FORMATIVO_NO_ESPERADO);
            assertNotSame(cf1, copiaCiclos[0], REFERENCIA_NO_ESPERADA);
            assertEquals(cf1, copiaCiclos[0], OPERACION_NO_REALIZADA);
        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarDosCiclosFormativosValidosInsertaCiclosFormativosCorrectamente() {
        CiclosFormativos ciclosFormativos = new CiclosFormativos(5);

        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.insertar(cf2);

            CicloFormativo[] copiaCiclos = ciclosFormativos.get();
            assertEquals(2, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(cf1, ciclosFormativos.buscar(cf1), CICLO_FORMATIVO_NO_ESPERADO);
            assertNotSame(cf1, copiaCiclos[0], REFERENCIA_NO_ESPERADA);
            assertEquals(cf1, copiaCiclos[0], OPERACION_NO_REALIZADA);
            assertEquals(cf2, ciclosFormativos.buscar(cf2), CICLO_FORMATIVO_NO_ESPERADO);
            assertNotSame(cf2, copiaCiclos[1], REFERENCIA_NO_ESPERADA);
            assertEquals(cf2, copiaCiclos[1], OPERACION_NO_REALIZADA);
        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarTresCiclosFormativosValidosInsertaCiclosFormativosCorrectamente() {
        CiclosFormativos ciclosFormativos = new CiclosFormativos(5);

        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.insertar(cf2);
            ciclosFormativos.insertar(cf3);

            CicloFormativo[] copiaCiclos = ciclosFormativos.get();

            assertEquals(3, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(cf1, ciclosFormativos.buscar(cf1), CICLO_FORMATIVO_NO_ESPERADO);
            assertNotSame(cf1, copiaCiclos[0], REFERENCIA_NO_ESPERADA);
            assertEquals(cf1, copiaCiclos[0], OPERACION_NO_REALIZADA);
            assertEquals(cf2, ciclosFormativos.buscar(cf2), CICLO_FORMATIVO_NO_ESPERADO);
            assertNotSame(cf2, copiaCiclos[1], REFERENCIA_NO_ESPERADA);
            assertEquals(cf2, copiaCiclos[1], OPERACION_NO_REALIZADA);
            assertEquals(cf3, ciclosFormativos.buscar(cf3), CICLO_FORMATIVO_NO_ESPERADO);
            assertNotSame(cf3, copiaCiclos[2], REFERENCIA_NO_ESPERADA);
            assertEquals(cf3, copiaCiclos[2], OPERACION_NO_REALIZADA);


        } catch (OperationNotSupportedException e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void insertarCicloFormativoNuloLanzaExcepcion() {
        CiclosFormativos ciclosFormativos = new CiclosFormativos(5);

        try {
            ciclosFormativos.insertar(null);
            fail(CICLO_FORMATIVO_NULO);
        } catch (NullPointerException e) {
            assertEquals(ERROR_INSERTAR_CICLO_FORMATIVO_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(0, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void insertarCicloFormativoRepetidoLanzaExcepcion() {
        CiclosFormativos ciclosFormativos = new CiclosFormativos(5);

        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.insertar(cf2);
            ciclosFormativos.insertar(cf3);
            ciclosFormativos.insertar(cfRepetido1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_CICLO_FORMATIVO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }

        ciclosFormativos = new CiclosFormativos(5);
        try {
            ciclosFormativos.insertar(cf2);
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.insertar(cf3);
            ciclosFormativos.insertar(cfRepetido1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_CICLO_FORMATIVO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }

        ciclosFormativos = new CiclosFormativos(5);
        try {
            ciclosFormativos.insertar(cf2);
            ciclosFormativos.insertar(cf3);
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.insertar(cfRepetido1);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_CICLO_FORMATIVO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(3, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void insertarCicloFormativoValidoConCiclosFormativosLlenoLanzaExcepcion() {
        CiclosFormativos ciclosFormativos = new CiclosFormativos(2);

        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.insertar(cf2);
            ciclosFormativos.insertar(cf3);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_NO_MAS_CICLOS_FORMATIVOS, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(2, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(cf1, ciclosFormativos.buscar(cf1), CICLO_FORMATIVO_NO_ESPERADO);
            assertNotSame(cf1, ciclosFormativos.buscar(cf1), REFERENCIA_NO_ESPERADA);
            assertEquals(cf1, ciclosFormativos.get()[0], OPERACION_NO_REALIZADA);
            assertEquals(cf2, ciclosFormativos.buscar(cf2), CICLO_FORMATIVO_NO_ESPERADO);
            assertNotSame(cf2, ciclosFormativos.buscar(cf2), REFERENCIA_NO_ESPERADA);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void borrarCicloFormativoExistenteBorraCicloFormativoCorrectamente() throws OperationNotSupportedException {
        CiclosFormativos ciclosFormativos = new CiclosFormativos(5);

        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.borrar(cf1);
            assertEquals(0, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(ciclosFormativos.buscar(cf1), CICLO_FORMATIVO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        ciclosFormativos = new CiclosFormativos(5);
        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.insertar(cf2);
            ciclosFormativos.borrar(cf1);
            assertEquals(1, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(cf2, ciclosFormativos.buscar(cf2), CICLO_FORMATIVO_NO_ESPERADO);
            assertNull(ciclosFormativos.buscar(cf1), CICLO_FORMATIVO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        ciclosFormativos = new CiclosFormativos(5);
        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.insertar(cf2);
            ciclosFormativos.borrar(cf2);
            assertEquals(1, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
            assertEquals(cf1, ciclosFormativos.buscar(cf1), CICLO_FORMATIVO_NO_ESPERADO);
            assertNull(ciclosFormativos.buscar(cf2), CICLO_FORMATIVO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        ciclosFormativos = new CiclosFormativos(5);
        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.insertar(cf2);
            ciclosFormativos.insertar(cf3);
            ciclosFormativos.borrar(cf1);
            assertEquals(2, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(ciclosFormativos.buscar(cf1), CICLO_FORMATIVO_NO_ESPERADO);
            assertEquals(cf2, ciclosFormativos.buscar(cf2), CICLO_FORMATIVO_NO_ESPERADO);
            assertEquals(cf3, ciclosFormativos.buscar(cf3), CICLO_FORMATIVO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        ciclosFormativos = new CiclosFormativos(5);
        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.insertar(cf2);
            ciclosFormativos.insertar(cf3);
            ciclosFormativos.borrar(cf2);
            assertEquals(2, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(ciclosFormativos.buscar(cf2), CICLO_FORMATIVO_NO_ESPERADO);
            assertEquals(cf1, ciclosFormativos.buscar(cf1), CICLO_FORMATIVO_NO_ESPERADO);
            assertEquals(cf3, ciclosFormativos.buscar(cf3), CICLO_FORMATIVO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        ciclosFormativos = new CiclosFormativos(5);
        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.insertar(cf2);
            ciclosFormativos.insertar(cf3);
            ciclosFormativos.borrar(cf3);
            assertEquals(2, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(ciclosFormativos.buscar(cf3), CICLO_FORMATIVO_NO_ESPERADO);
            assertEquals(cf1, ciclosFormativos.buscar(cf1), CICLO_FORMATIVO_NO_ESPERADO);
            assertEquals(cf2, ciclosFormativos.buscar(cf2), CICLO_FORMATIVO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }

        ciclosFormativos = new CiclosFormativos(3);
        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.insertar(cf2);
            ciclosFormativos.insertar(cf3);
            ciclosFormativos.borrar(cf2);
            assertEquals(2, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
            assertNull(ciclosFormativos.buscar(cf2), CICLO_FORMATIVO_NO_ESPERADO);
            assertEquals(cf1, ciclosFormativos.buscar(cf1), CICLO_FORMATIVO_NO_ESPERADO);
            assertEquals(cf3, ciclosFormativos.buscar(cf3), CICLO_FORMATIVO_NO_ESPERADO);
        } catch (Exception e) {
            fail(EXCEPCION_NO_PROCEDE);
        }
    }

    @Test
    public void borrarCicloFormativoNoExistenteLanzaExcepcion() {
        CiclosFormativos ciclosFormativos = new CiclosFormativos(5);

        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.borrar(cf2);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_CICLO_FORMATIVO_BORRAR_NO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(1, ciclosFormativos.getTamano(),TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }


        ciclosFormativos = new CiclosFormativos(5);
        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.insertar(cf2);
            ciclosFormativos.borrar(cf3);
            fail(OPERACION_NO_PERMITIDA);
        } catch (OperationNotSupportedException e) {
            assertEquals(ERROR_CICLO_FORMATIVO_BORRAR_NO_EXISTE, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(2, ciclosFormativos.getTamano(),TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

    @Test
    public void borrarCicloFormativoNuloLanzaExcepcion() {
        CiclosFormativos ciclosFormativos = new CiclosFormativos(5);
        try {
            ciclosFormativos.insertar(cf1);
            ciclosFormativos.borrar(null);
            fail(CICLO_FORMATIVO_NULO);
        } catch (NullPointerException e) {
            assertEquals(ERROR_BORRAR_CICLO_FORMATIVO_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(1, ciclosFormativos.getTamano(), TAMANO_NO_ESPERADO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTO);
        }
    }

*/

}

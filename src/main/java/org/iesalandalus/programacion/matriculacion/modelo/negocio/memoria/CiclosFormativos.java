package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class CiclosFormativos implements ICiclosFormativos {

    private static CiclosFormativos instancia; // Instancia única para el patrón Singleton
    private List<CicloFormativo> coleccionCiclosFormativos;

    // Constructor privado para evitar instanciación directa
    public CiclosFormativos() {
        coleccionCiclosFormativos = new ArrayList<>();
    }

    // Método estático para obtener la instancia única
    public static CiclosFormativos getInstancia() {
        if (instancia == null) {
            instancia = new CiclosFormativos();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        coleccionCiclosFormativos = new ArrayList<>(); // Inicializa una nueva lista vacía
        System.out.println("Colección de ciclos formativos inicializada.");
    }

    @Override
    public void terminar() {
        coleccionCiclosFormativos.clear(); // Limpia la lista existente
        System.out.println("Colección de ciclos formativos limpiada.");
    }

    @Override
    public List<CicloFormativo> get() {
        return copiaProfundaCiclosFormativos(coleccionCiclosFormativos);
    }


    public int getTamano() {
        return coleccionCiclosFormativos.size();
    }

    @Override
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: El ciclo formativo no puede ser nulo.");
        }
        if (!coleccionCiclosFormativos.contains(cicloFormativo)) {
            coleccionCiclosFormativos.add(new CicloFormativo(cicloFormativo));
        } else {
            throw new OperationNotSupportedException("ERROR: El ciclo formativo ya existe.");
        }
    }

    @Override
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: El ciclo formativo no puede ser nulo.");
        }
        int indice = coleccionCiclosFormativos.indexOf(cicloFormativo);
        if (indice == -1) {
            return null;
        } else {
            return new CicloFormativo(coleccionCiclosFormativos.get(indice));
        }
    }

    @Override
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: El ciclo formativo no puede ser nulo.");
        }
        int indice = coleccionCiclosFormativos.indexOf(cicloFormativo);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: El ciclo formativo no existe.");
        } else {
            coleccionCiclosFormativos.remove(indice);
        }
    }

    private List<CicloFormativo> copiaProfundaCiclosFormativos(List<CicloFormativo> ciclos) {
        List<CicloFormativo> otrosCiclos = new ArrayList<>();
        for (CicloFormativo ciclo : ciclos) {
            otrosCiclos.add(new CicloFormativo(ciclo));
        }
        return otrosCiclos;
    }
}

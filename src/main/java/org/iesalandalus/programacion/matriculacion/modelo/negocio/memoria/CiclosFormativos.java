package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class CiclosFormativos {


    private List<CicloFormativo> coleccionCiclosFormativos;

    public CiclosFormativos() {
        coleccionCiclosFormativos = new ArrayList<>();
    }
    public List<CicloFormativo> get() {
        return copiaProfundaCiclosFormativos(coleccionCiclosFormativos);
    }


    private List<CicloFormativo> copiaProfundaCiclosFormativos(List<CicloFormativo> cicloFormativos) {
        List<CicloFormativo> otrosCiclosFormativos = new ArrayList<>();
        for (CicloFormativo cicloFormativo : cicloFormativos) {
            otrosCiclosFormativos.add(new CicloFormativo(cicloFormativo));
        }
        return otrosCiclosFormativos;
    }



    public void insertar(CicloFormativo cicloFormativo)throws OperationNotSupportedException {
        if(cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR:El ciclo formativo no puede ser nulo");
        }
        int indice = coleccionCiclosFormativos.indexOf(cicloFormativo);
        if(indice == -1) {
            coleccionCiclosFormativos.add(new CicloFormativo(cicloFormativo));
        }else{
            throw new OperationNotSupportedException("ERROR:El ciclo formativo ya existe");
        }
    }



    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if(cicloFormativo == null) {
            throw new NullPointerException("ERROR:El ciclo formativo no puede ser nulo");
        }
        int indice = coleccionCiclosFormativos.indexOf(cicloFormativo);
        if(indice == -1) {
            return null;
        }else{
            return new CicloFormativo(coleccionCiclosFormativos.get(indice));
        }
    }

    public void borrar(CicloFormativo cicloFormativo)throws OperationNotSupportedException {
        if(cicloFormativo == null) {
            throw new NullPointerException("ERROR:El ciclo formativo no puede ser nulo");
        }
        int indice = coleccionCiclosFormativos.indexOf(cicloFormativo);
        if(indice == -1) {
            throw new OperationNotSupportedException("ERROR:El ciclo formativo no existe");
        }else{
            coleccionCiclosFormativos.remove(indice);
        }
    }























}

package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.FuenteDatosMemoria;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.FuenteDatosMySQL;

public enum FactoriaFuenteDatos {
    MEMORIA {
        @Override
        public IFuenteDatos crear() {
            return new FuenteDatosMemoria();
        }
    },
    MYSQL {
        @Override
        public IFuenteDatos crear() {
            return new FuenteDatosMySQL();
        }
    };


    public abstract IFuenteDatos crear();
}

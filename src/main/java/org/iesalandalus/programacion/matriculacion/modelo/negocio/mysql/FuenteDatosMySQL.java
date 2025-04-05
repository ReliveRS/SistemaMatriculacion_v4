package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;

public class FuenteDatosMySQL implements IFuenteDatos {

    @Override
    public IAlumnos crearAlumnos() {
        return new Alumnos();
    }

    @Override
    public ICiclosFormativos crearCiclosFormativos() {
        return new CiclosFormativos();
    }

    @Override
    public IAsignaturas crearAsignaturas() {
        return new Asignaturas();
    }

    @Override
    public IMatriculas crearMatriculas() {
        return new Matriculas();
    }
}

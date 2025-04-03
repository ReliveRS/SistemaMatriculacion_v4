package org.iesalandalus.programacion.matriculacion.modelo.dominio;
//COMPROBADO
import java.util.Objects;

public class CicloFormativo {

    public static final int MAXIMO_NUMERO_HORAS = 2000;

    private int codigo;
    private String familiaProfesional;
    private Grado grado;
    private String nombre;
    private int horas;


    public CicloFormativo(int codigo, String familiaProfesional, Grado grado, String nombre, int horas) {
        setCodigo(codigo);
        setFamiliaProfesional(familiaProfesional);
        setGrado(grado);
        setNombre(nombre);
        setHoras(horas);
    }

    public CicloFormativo(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("No se puede copiar un ciclo formativo nulo."); // Excepción si el objeto es nulo.
        }
        setCodigo(cicloFormativo.getCodigo());
        setFamiliaProfesional(cicloFormativo.getFamiliaProfesional());
        setGrado(cicloFormativo.getGrado());
        setNombre(cicloFormativo.getNombre());
        setHoras(cicloFormativo.getHoras());

    }

    public int getCodigo() {
        return codigo;
    }

    private void setCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("El código debe ser un número positivo.");
        }
        this.codigo = codigo;
    }




    public String getFamiliaProfesional() {
        return familiaProfesional;
    }

    public void setFamiliaProfesional(String familiaProfesional) {
        if (familiaProfesional == null || familiaProfesional.trim().isEmpty()) {
            throw new IllegalArgumentException("La familia profesional no puede ser nula ni vacía.");
        }
        this.familiaProfesional = familiaProfesional.trim();
    }



    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        if (grado == null) {
            throw new IllegalArgumentException("El grado no puede ser nulo.");
        }
        this.grado = grado;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
        }
        this.nombre = nombre.trim();
    }


    public int getHoras() {
        return horas;
    }
    public void setHoras(int horas) {
        if (horas <= 0 || horas > MAXIMO_NUMERO_HORAS) {
            throw new IllegalArgumentException("Las horas deben estar entre 1 y " + MAXIMO_NUMERO_HORAS + ".");
        }
        this.horas = horas;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CicloFormativo cicloFormativo = (CicloFormativo) obj;
        return codigo == cicloFormativo.codigo;
    }


    @Override
    public int hashCode() {
        return Objects.hash(codigo, familiaProfesional, grado, nombre, horas);
    }



    public String imprimir() {
        return String.format("Código ciclo formativo=%d, nombre ciclo formativo=%s", getCodigo(), getNombre());
    }

@Override
public String toString() {
    return String.format("Código ciclo formativo=%d, familia profesional=%s, grado=%s, nombre ciclo formativo=%s, horas=%d", getCodigo(), getFamiliaProfesional(), getGrado(), getNombre(), getHoras());
}
}

package org.iesalandalus.programacion.matriculacion.modelo.dominio;
//COMPROBADO
import java.util.Objects;

public class Asignatura {

    //Definimos constantes para después compararlas y validar los datos:

    public static final int MAXIMO_NUMERO_HORAS_ANUALES = 300;
    public static final int MAXIMO_NUMERO_HORAS_DESDOBLES = 6;
    private static final String ER_CODIGO = "\\d{4}"; // codigo de 4 digitos


//Definimos los atributos de Asignatura

    private String codigo;
    private String nombre;
    private int horasAnuales;
    private Curso curso;
    private int horasDesdoble;
    private EspecialidadProfesorado especialidadProfesorado;
    private CicloFormativo cicloFormativo;

    // Constructor principal que inicializa la asignatura con los parámetros proporcionados
    public Asignatura(String codigo, String nombre, int horasAnuales, Curso curso, int horasDesdoble, EspecialidadProfesorado especialidadProfesorado, CicloFormativo cicloFormativo) {
        setCodigo(codigo); // Se valida el código de la asignatura.
        setNombre(nombre); // Se valida el nombre.
        setHorasAnuales(horasAnuales);
        setCurso(curso);
        setHorasDesdoble(horasDesdoble);
        setEspecialidadProfesorado(especialidadProfesorado);
        setCicloFormativo(cicloFormativo);
    }

    public Asignatura(Asignatura asignatura) {
        if (asignatura == null) {
            throw new IllegalArgumentException("No se puede copiar una asignatura nula.");
        }
        setCodigo(asignatura.getCodigo());
        setNombre(asignatura.getNombre());
        setHorasAnuales(asignatura.getHorasAnuales());
        setCurso(asignatura.getCurso());
        setHorasDesdoble(asignatura.getHorasDesdoble());
        setEspecialidadProfesorado(asignatura.getEspecialidadProfesorado());
        setCicloFormativo(asignatura.getCicloFormativo());

    }

    public CicloFormativo getCicloFormativo(){
        return  cicloFormativo;
    }

    public CicloFormativo setCicloFormativo(CicloFormativo cicloFormativo){
        this.cicloFormativo = cicloFormativo;
        return cicloFormativo;
    }



    public String getCodigo() {
        return codigo;
    }

    // Valida y asigna el código (debe ser un número de 4 dígitos)
    private void setCodigo(String codigo) {
        if (codigo == null || !codigo.matches(ER_CODIGO)) {
            throw new IllegalArgumentException("El código debe ser un número de 4 dígitos.");
        }
        this.codigo = codigo;
    }



    public String getNombre() {
        return nombre;
    }

    // Valida y asigna el nombre de la asignatura
    public void setNombre(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre no puede ser nulo.");
        }
        this.nombre = nombre;
    }





    public int getHorasAnuales() {
        return horasAnuales;
    }

    // Valida y asigna las horas anuales de la asignatura
    public void setHorasAnuales(int horasAnuales) {
        if(horasAnuales <= 0 || horasAnuales > MAXIMO_NUMERO_HORAS_ANUALES) {
            throw new IllegalArgumentException("Las horas anuales deben estar entre 1 y " + MAXIMO_NUMERO_HORAS_ANUALES + ".");
        }
        this.horasAnuales = horasAnuales;
    }



    public Curso getCurso() {
        return curso;
    }

    // Valida y asigna el curso (nivel educativo) de la asignatura
    public void setCurso(Curso curso) {
        if(curso == null) {
            throw new IllegalArgumentException("El curso no puede ser nulo");
        }
        this.curso = curso;
    }


    public int getHorasDesdoble() {
        return horasDesdoble;
    }

    // Valida y asigna las horas de desdoble de la asignatura
    public void setHorasDesdoble(int horasDesdoble) {
        if(horasDesdoble < 0 || horasDesdoble > MAXIMO_NUMERO_HORAS_DESDOBLES) {
            throw new IllegalArgumentException("Las horas de desdoble deben estar entre 0 y " + MAXIMO_NUMERO_HORAS_DESDOBLES + ".");
        }
        this.horasDesdoble = horasDesdoble;
    }



    public EspecialidadProfesorado getEspecialidadProfesorado() {
        return especialidadProfesorado;
    }


    // Valida y asigna la especialidad del profesorado necesario para la asignatura
    public void setEspecialidadProfesorado(EspecialidadProfesorado especialidadProfesorado) {
        if (especialidadProfesorado == null) {
            throw new IllegalArgumentException("La especialidad del profesorado no puede ser nula.");
        }
        this.especialidadProfesorado = especialidadProfesorado;
    }




    // Método equals para comparar dos objetos Asignatura
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Si son la misma instancia
        if (obj == null || getClass() != obj.getClass()) return false; // Si son de clases diferentes o el objeto es nulo
        Asignatura asignatura = (Asignatura) obj;
        return Objects.equals(codigo, asignatura.codigo); // Compara por el código de la asignatura
    }

    // Método hashCode para generar un valor hash único basado en el código
    @Override
    public int hashCode() {
        return Objects.hash(codigo); // Calcula el hash solo usando el código
    }

    public String imprimir() {
        return String.format("%d.- %s (%d horas)", getCodigo(), getNombre(), getHorasAnuales());
    }

    @Override
    public String toString() {
        return String.format(
                "Código=%s, nombre=%s, horas anuales=%d, curso=%s, horas desdoble=%d, ciclo formativo=%s, especialidad profesorado=%s",
                getCodigo(),
                getNombre(),
                getHorasAnuales(),
                (getCurso() != null ? getCurso() : "Sin curso"),
                getHorasDesdoble(),
                (getCicloFormativo() != null ? getCicloFormativo().getNombre() : "Sin ciclo formativo"),
                (getEspecialidadProfesorado() != null ? getEspecialidadProfesorado() : "Sin especialidad")
        );
    }




}

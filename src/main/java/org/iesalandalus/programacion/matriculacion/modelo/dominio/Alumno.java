package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Alumno {

    private static final String ER_TELEFONO = "^[6789]\\d{8}$";
    private static final String ER_CORREO = ".+@[a-zA-Z]+\\.[a-zA-Z]+";
    private static final String ER_DNI = "^(\\d{8})([A-HJ-NP-TV-Z])$";
    private static final String ER_NIA = "^[a-z]{4}\\d{3}$";
    public static final String FORMATO_FECHA = "dd/MM/YYYY";
    private static final int MIN_EDAD_ALUMNADO = 16;

    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private String nia;
    private LocalDate fechaNacimiento;


    public Alumno(String nombre, String telefono, String correo, String dni, LocalDate fechaNacimiento) {
        setNombre(nombre);
        setTelefono(telefono);
        setCorreo(correo);
        setDni(dni);
        setFechaNacimiento(fechaNacimiento);
    }

    public Alumno(Alumno alumno) {
        if (alumno == null) {
            throw new IllegalArgumentException("No se puede copiar un alumno nulo.");
        }
        setNombre(alumno.getNombre());
        setTelefono(alumno.getTelefono());
        setCorreo(alumno.getCorreo());
        setDni(alumno.getDni());
        setFechaNacimiento(alumno.getFechaNacimiento());
    }


    public String getNia() {
        return nia;
    }

    private void setNia() {
        String primerosCuatro = nombre.substring(0, 4).toLowerCase();
        String ultimosTresDni = dni.substring(dni.length() - 3);
        this.nia = primerosCuatro + ultimosTresDni;
    }

    private void setNia(String nia) {
        this.nia = nia;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre no puede ser nulo.");
        }
        if(nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre no puede ser vacío.");
        }
        this.nombre = formateaNombre(nombre);
    }

    private String formateaNombre(String nombre)
    {
        String resultado="";
        String palabra="";

        if (nombre==null)
            throw new NullPointerException("ERROR: No puede formatearse un nombre nulo.");

        if (nombre.trim().isEmpty())
            throw new IllegalArgumentException("ERROR: No puede formatearse un nombre vacío.");

        String [] palabras=nombre.trim().toLowerCase().split("\\s+");

        for(int i=0;i<palabras.length;i++)
        {
            palabra=palabras[i].trim();

            String letra=palabra.charAt(0)+"";

            resultado=resultado+letra.toUpperCase() + palabra.substring(1,palabra.length())+" ";
        }

        resultado=resultado.trim();

        return resultado;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono != null && (telefono.isEmpty() || !telefono.matches(ER_TELEFONO))) {
            throw new IllegalArgumentException("ERROR: El teléfono debe tener 9 dígitos y comenzar por 6, 7 o 9.");
        }
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null) {
            throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
        }
        if (!correo.matches(ER_CORREO)) {
            throw new IllegalArgumentException("ERROR: El correo del profesor no es válido.");
        }
        this.correo = correo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni == null || !dni.matches(ER_DNI) || !comprobarLetraDni(dni)) {
            throw new IllegalArgumentException("DNI no válido");
        }
        this.dni = dni;
        setNia();
    }

    private boolean comprobarLetraDni(String dni) {
        Pattern pattern = Pattern.compile(ER_DNI);
        Matcher matcher = pattern.matcher(dni);
        if (!matcher.matches()) {
            return false;
        }
        String numero = matcher.group(1);
        String letra = matcher.group(2);
        int num = Integer.parseInt(numero);
        final char[] letrasDni = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        char letraCorrecta = letrasDni[num % 23];
        return letraCorrecta == letra.charAt(0);
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    private void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula.");
        }
        if (Period.between(fechaNacimiento, LocalDate.now()).getYears() < MIN_EDAD_ALUMNADO) {
            throw new IllegalArgumentException("El alumno debe tener al menos " + MIN_EDAD_ALUMNADO + " años");
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    private String getIniciales() {
        String[] palabras = nombre.split(" ");
        StringBuilder iniciales = new StringBuilder();
        for (String palabra : palabras) {
            iniciales.append(palabra.charAt(0));
        }
        return iniciales.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Alumno alumno = (Alumno) obj;
        return dni.equals(alumno.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }


    public String imprimir() {
        return String.format("0.- %s (%s, %s)", this.getNombre(), this.getDni(), this.getFechaNacimiento());
    }


    @Override
    public String toString() {
        return String.format("Alumno [nombre=%s, telefono=%s, correo=%s, dni=%s, nia=%s, fechaNacimiento=%s]",
                nombre, telefono, correo, dni, nia, fechaNacimiento.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)));
    }


}
















package org.iesalandalus.programacion.matriculacion.modelo.dominio;



public abstract class Grado {
    protected String nombre;
    protected String iniciales;
    protected int numAnios;

    public Grado(String nombre) {
        setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    protected void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
        setIniciales();
    }

    private void setIniciales() {
        String[] palabras = nombre.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                sb.append(Character.toUpperCase(palabra.charAt(0)));
            }
        }
        this.iniciales = sb.toString() + "-" + getClass().getSimpleName();
    }

    public int getNumAnios() {
        return numAnios;
    }

    public abstract void setNumAnios(int numAnios);

    @Override
    public String toString() {
        return String.format("(%s) - %s", iniciales, nombre);
    }
}

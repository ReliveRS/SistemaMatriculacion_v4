package org.iesalandalus.programacion.matriculacion;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.vista.*;

public class MainApp {


    public static void main(String[] args) {
        try {
            System.out.println("Programa para la gestión del sistema de matriculación del IES Al-Andalus");

            // Inicialización del modelo, vista y controlador
            Modelo modelo = new Modelo();
            Vista vista = new Vista();
            Controlador controlador = new Controlador(modelo, vista);

            // Iniciar la aplicación
            controlador.comenzar();

        } catch (IllegalArgumentException e) {
            // Captura excepciones relacionadas con argumentos inválidos
            System.err.println("Error de argumento: " + e.getMessage());
        } catch (NullPointerException e) {
            // Captura excepciones relacionadas con objetos nulos
            System.err.println("Error de referencia nula: " + e.getMessage());
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada
            System.err.println("Error inesperado: " + e.getMessage());
        } finally {
            // Bloque finally para mensaje de finalización
            System.out.println("La aplicación ha finalizado.");
        }
    }


























}







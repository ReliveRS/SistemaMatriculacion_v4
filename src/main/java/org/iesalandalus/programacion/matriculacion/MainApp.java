package org.iesalandalus.programacion.matriculacion;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.vista.Vista;
import org.iesalandalus.programacion.utilidades.Entrada;

public class MainApp {

    /**
     * Procesa los argumentos para determinar la fuente de datos y crea el modelo correspondiente.
     * @param args Argumentos pasados a la aplicación.
     * @return Modelo configurado según la fuente de datos especificada.
     */


    public static Modelo procesarArgumentosFuenteDatos(String[] args) {
        // Si no se pasan argumentos, solicita al usuario que elija una opción
        if (args == null || args.length == 0) {
            System.out.println("No se han proporcionado argumentos.");
            System.out.println("Por favor, elija la fuente de datos:");
            System.out.println("- Escriba '-fdmemoria' para usar la fuente de datos en memoria.");
            System.out.println("- Escriba '-fdmysql' para usar la fuente de datos MySQL.");

            // Leer la entrada del usuario desde la consola
            String opcion = Entrada.cadena();

            // Procesar la opción ingresada por el usuario
            if ("-fdmemoria".equalsIgnoreCase(opcion)) {
                return new Modelo(FactoriaFuenteDatos.MEMORIA);
            } else if ("-fdmysql".equalsIgnoreCase(opcion)) {
                return new Modelo(FactoriaFuenteDatos.MYSQL);
            } else {
                throw new IllegalArgumentException("ERROR: Opción no válida. Por favor, reinicie el programa e ingrese una opción válida.");
            }
        }

        // Si se pasan argumentos, procesarlos como antes
        for (String arg : args) {
            if ("-fdmemoria".equalsIgnoreCase(arg)) {
                return new Modelo(FactoriaFuenteDatos.MEMORIA);
            } else if ("-fdmysql".equalsIgnoreCase(arg)) {
                return new Modelo(FactoriaFuenteDatos.MYSQL);
            }
        }

        throw new IllegalArgumentException("ERROR: No se ha proporcionado un argumento válido para la fuente de datos.");
    }


    public static void main(String[] args) {
        try {
            System.out.println("Programa para la gestión del sistema de matriculación del IES Al-Andalus");

            // Procesar los argumentos y crear el modelo correspondiente
            Modelo modelo = procesarArgumentosFuenteDatos(args);

            // Inicializar vista y controlador
            Vista vista = new Vista();
            Controlador controlador = new Controlador(modelo, vista);

            // Iniciar la aplicación
            controlador.comenzar();

        } catch (IllegalArgumentException e) {
            System.err.println("Error de argumento: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Error de referencia nula: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        } finally {
            System.out.println("La aplicación ha finalizado.");
        }
    }

}

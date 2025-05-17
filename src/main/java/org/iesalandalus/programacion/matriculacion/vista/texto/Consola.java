package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class Consola {

    private Consola() {
        //Evito que se cree el constructor por defecto
    }
    public static void mostrarMenu() {
        System.out.println("Opciones disponibles:");
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion.ordinal() + ". " + opcion.name());
        }
    }

    public static Opcion elegirOpcion() {
        int opcionElegida;
        while (true) {
            try {
                System.out.print("Elige una opcion: ");
                opcionElegida = Integer.parseInt(Entrada.cadena());
                if (opcionElegida < 0 || opcionElegida >= Opcion.values().length) {
                    throw new IllegalArgumentException("Opcion no valida.");
                }
                return Opcion.values()[opcionElegida];
            } catch (IllegalArgumentException e) {
                System.out.println("Opcion invalida, intenta de nuevo.");
            }
        }
    }

    public static Alumno leerAlumno() {
        try {
            System.out.print("Introduce el nombre del alumno: ");
            String nombre = Entrada.cadena();
            System.out.print("Introduce el telefono del alumno: ");
            String telefono = Entrada.cadena();
            System.out.print("Introduce el correo del alumno: ");
            String correo = Entrada.cadena();
            System.out.print("Introduce el DNI del alumno: ");
            String dni = Entrada.cadena();
            System.out.print("Introduce la fecha de nacimiento del alumno (dd/MM/YYYY): ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaCadena = (Entrada.cadena());
            // Convertir la cadena a LocalDate utilizando el formato especificado
            LocalDate fechaNacimiento = LocalDate.parse(fechaCadena, formatter);
            // Mostrar la fecha de nacimiento
            System.out.println("Fecha de nacimiento: " + fechaNacimiento);

            // Validar los datos aquí
            if (dni.length() != 9 || !dni.substring(0, 8).matches("\\d{8}") || !Character.isLetter(dni.charAt(8))) {
                throw new IllegalArgumentException("DNI invalido.");
            }

            return new Alumno(nombre, telefono, correo, dni,fechaNacimiento); // Asegúrate de que Alumno tenga el constructor adecuado
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al leer el alumno: " + e.getMessage());
        }
    }

    public static String getDniAlumno() throws Exception {
        System.out.print("Introduce el DNI del alumno: ");
        String dni = Entrada.cadena().trim();

        // Validar que el DNI no sea nulo o vacío
        if (dni == null || dni.isEmpty()) {
            throw new Exception("ERROR: El DNI del alumno no puede ser nulo ni vacío.");
        }

        // Validar el formato del DNI (8 dígitos + 1 letra)
        if (!dni.matches("\\d{8}[A-Z]")) {
            throw new Exception("ERROR: El DNI del alumno no tiene el formato correcto.");
        }

        return dni; // Devolver el DNI validado
    }

    public static LocalDate leerFecha() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            try {
                System.out.print("Introduce una fecha (dd/MM/yyyy): ");
                String fechaStr = Entrada.cadena();
                return LocalDate.parse(fechaStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha inválida, intenta de nuevo.");
            }
        }
    }



    public static TiposDeGrado leerTipoDeGrado() {
        System.out.println("Selecciona el tipo de grado(GRADOD o GRADOE):");
        String tipoDeGrado = Entrada.cadena();
        return TiposDeGrado.valueOf(tipoDeGrado.toUpperCase());
    }

    public static Modalidad leerModalidad() {
        System.out.println("Selecciona la modalidad (Presencial o Semi-presencial):");
        String modalidad = Entrada.cadena();
        return Modalidad.valueOf(modalidad.toUpperCase());
    }

    public static CicloFormativo leerCicloFormativo() {
        try {
            System.out.print("Introduce el codigo del ciclo formativo: ");
            int codigo = Entrada.entero();
            System.out.print("Introduce la familia profesional: ");
            String familiaProfesional = Entrada.cadena();
            Grado grado = leerGrado();
            System.out.print("Introduce el nombre del ciclo formativo: ");
            String nombre = Entrada.cadena();
            System.out.print("Introduce el número de horas: ");
            int horas = Integer.parseInt(Entrada.cadena());

            return new CicloFormativo(codigo, familiaProfesional, grado, nombre, horas);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al leer el ciclo formativo: " + e.getMessage());
        }
    }

    public static Grado leerGrado() {
        System.out.println("Ingrese el nombre del grado:");
        String nombre = Entrada.cadena();
        TiposDeGrado tipo = leerTipoDeGrado();
        if (tipo == TiposDeGrado.GRADOD) {
            Modalidad modalidad = leerModalidad();
            System.out.println("Ingrese el numero de años:2 o 3:");
            int numAnios = Entrada.entero();
            return new GradoD(nombre, numAnios, modalidad);
        } else {
            System.out.println("Ingrese el numero de ediciones:");
            int numEdiciones = Entrada.entero();
            return new GradoE(nombre,1, numEdiciones);
        }

    }

    public static void mostrarCiclosFormativos(List<CicloFormativo> ciclosFormativos) {
        if (ciclosFormativos == null || ciclosFormativos.isEmpty()) {
            System.out.println("No hay ciclos formativos para mostrar.");
            return;
        }

        System.out.println("=== Lista de Ciclos Formativos ===");
        for (CicloFormativo ciclo : ciclosFormativos) {
            System.out.println("----------------------------------------------");
            System.out.println("  - Código: " + ciclo.getCodigo());
            System.out.println("  - Nombre: " + ciclo.getNombre());
            System.out.println("  - Familia Profesional: " + ciclo.getFamiliaProfesional());
            System.out.println("  - Grado: " + ciclo.getGrado());
            System.out.println("  - Horas: " + ciclo.getHoras());
            System.out.println("----------------------------------------------");
        }
    }

    public static CicloFormativo getCicloFormativoPorCodigo() throws IllegalArgumentException {
        System.out.print("Introduce el código del ciclo formativo: ");
        int codigo = Entrada.entero();
        if (codigo == 0) {
            throw new IllegalArgumentException("La colección de ciclos formativos no puede estar vacía.");
        }
        Grado gradoFicticio = new GradoD("Grado Ficticio", 2, Modalidad.PRESENCIAL);
        return new CicloFormativo(codigo, "Familia Ficticia", gradoFicticio, "Nombre Ficticio", 100);    }


    public static Curso leerCurso() {
        System.out.print("Introduce el curso (Primero o Segundo): ");
        while (true) {
            try {
                String entrada = Entrada.cadena().trim().toUpperCase();
                for (Curso curso : Curso.values()) {
                    if (curso.name().equalsIgnoreCase(entrada)) {
                        return curso;
                    }
                }
                System.out.print("Curso no válido. Introduce Primero o Segundo: ");
            } catch (Exception e) {
                System.out.print("Error al leer el curso. Introduce Primero o Segundo: ");
            }
        }
    }

    public static EspecialidadProfesorado leerEspecialidadProfesorado() {
        System.out.print("Introduce la especialidad del profesorado (Informática, Sistemas y Aplicaciones Informáticas, Formación y Orientación Laboral): ");
        while (true) {
            try {
                String entrada = Entrada.cadena().trim().toUpperCase();
                for (EspecialidadProfesorado especialidad : EspecialidadProfesorado.values()) {
                    if (especialidad.name().equalsIgnoreCase(entrada)) {
                        return especialidad;
                    }
                }
                System.out.print("Especialidad no válida. Inténtalo de nuevo: ");
            } catch (Exception e) {
                System.out.print("Error al leer la especialidad. Inténtalo de nuevo: ");
            }
        }
    }


    public static Asignatura leerAsignatura(CicloFormativo cicloFormativo) {
        try {
            if (cicloFormativo == null) {
                throw new IllegalArgumentException("ERROR: El ciclo formativo no puede ser nulo.");
            }

            System.out.print("Introduce el código de la asignatura: ");
            String codigo = Entrada.cadena();
            if (codigo == null || codigo.trim().isEmpty()) {
                throw new IllegalArgumentException("ERROR: El código no puede estar vacío.");
            }

            System.out.print("Introduce el nombre de la asignatura: ");
            String nombre = Entrada.cadena();
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new IllegalArgumentException("ERROR: El nombre no puede estar vacío.");
            }

            System.out.print("Introduce las horas anuales de la asignatura: ");
            int horasAnuales = Integer.parseInt(Entrada.cadena());
            if (horasAnuales <= 0) {
                throw new IllegalArgumentException("ERROR: Las horas anuales deben ser mayores a 0.");
            }

            Curso curso = leerCurso();

            System.out.print("Introduce las horas de desdoble (0-6): ");
            int horasDesdoble = Integer.parseInt(Entrada.cadena());
            if (horasDesdoble < 0 || horasDesdoble > 6) {
                throw new IllegalArgumentException("ERROR: Las horas de desdoble deben estar entre 0 y 6.");
            }

            EspecialidadProfesorado especialidadProfesorado = leerEspecialidadProfesorado();

            return new Asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, especialidadProfesorado, cicloFormativo);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ERROR: Formato numérico inválido. " + e.getMessage());
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR: No se pudo leer la asignatura. " + e.getMessage());
        }
    }

    public static Asignatura getAsignaturaPorCodigo(List<Asignatura> asignaturas, String codigo) {
        for (Asignatura asignatura : asignaturas) {
            if (asignatura.getCodigo().equalsIgnoreCase(codigo)) {
                return asignatura;
            }
        }
        return null; // Si no se encuentra la asignatura
    }

    private static void mostrarAsignaturas(List<Asignatura> asignaturas) {
        System.out.println("Asignaturas disponibles:");
        for (Asignatura asignatura : asignaturas) {
            System.out.println(asignatura.getCodigo() + " - " + asignatura.getNombre());
        }
    }

    private static boolean asignaturaYaMatriculada(List<Asignatura> asignaturasMatricula, Asignatura asignatura) {
        return asignaturasMatricula.contains(asignatura);
    }


    public static Matricula leerMatricula(Alumno alumno, List<Asignatura> asignaturas) {
        try {
            // Solicitar el curso académico
            System.out.print("Introduce el curso académico (dd-dd): ");
            String cursoAcademico = Entrada.cadena().trim();
            if (cursoAcademico.isEmpty()) {
                throw new IllegalArgumentException("ERROR: El curso académico no puede estar vacío.");
            }

            // Solicitar la fecha de matriculación
            System.out.print("Introduce la fecha de matriculación (dd/MM/yyyy): ");
            LocalDate fechaMatriculacion = leerFecha();

            // Generar un ID de matrícula aleatorio
            int idMatricula = (int) (Math.random() * 100000);

            // Crear y devolver la matrícula
            return new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumno, asignaturas);
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR: No se pudo leer la matrícula. " + e.getMessage());
        }
    }

    public static List<Asignatura> elegirAsignaturasMatricula(List<Asignatura> asignaturas) {
        /*Este metodo tiene que servir para elegir las asignaturas que va a matricular un alumno.
        Va a devolver una lista de asignaturas Elegidas por el alumno.
        El alumno puede elegir 3 asignaturas o 1.
        Cada vez que eliga una asignatura se va a mostrar la lista de asignaturas disponibles.

        */
        // Esto sirve por si le pasas al metodo una lista vacia o nulla
        if (asignaturas == null || asignaturas.isEmpty()) {
            throw new IllegalArgumentException("ERROR: La lista de asignaturas no puede ser nula o vacía.");
        }
        //Creamos una lista de asignaturas donde vamos a guardar las asignaturas elegidas
        List<Asignatura> asignaturasMatricula = new ArrayList<>(); // Usamos List para almacenar las asignaturas seleccionadas
        int maxAsignaturas = 3; // Número máximo de asignaturas permitidas

        while (asignaturasMatricula.size() < maxAsignaturas ) {  // Se va hacer este bucle hasta que complete las 3 asignaturas
            mostrarAsignaturas(asignaturas); // Mostrar las asignaturas disponibles
            System.out.print("Selecciona la asignatura " + (asignaturasMatricula.size() + 1 + " o escribe \"fin\" para terminar" ) + " (Codigo): ");
            String codigo = Entrada.cadena().trim();

            // Verificar si el usuario quiere terminar la selección
            if (codigo.equalsIgnoreCase("fin")) {
                if (asignaturasMatricula.size() >= 1) {
                    break; // Salir del bucle si el alumno ha seleccionado al menos una asignatura
                } else {
                    System.out.println("Debes seleccionar al menos una asignatura.");
                    continue;
                }
            }

            // Buscar la asignatura por código
            Asignatura asignatura = getAsignaturaPorCodigo(asignaturas, codigo);

            if (asignatura != null) {
                if (asignaturaYaMatriculada(asignaturasMatricula, asignatura)) {
                    System.out.println("La asignatura ya estaba matriculada.");
                } else {
                    asignaturasMatricula.add(asignatura); // Añadir la asignatura a la lista
                }
            } else {
                System.out.println("ERROR: No se encontró una asignatura con el código proporcionado.");
            }
        }

        return asignaturasMatricula; // Devolver la lista de asignaturas seleccionadas
    }

    public static Matricula getMatriculaPorIdentificador() {
        try {
            // Solicitar el identificador de la matrícula
            System.out.print("Introduce el identificador de la matrícula: ");
            int idMatricula = Integer.parseInt(Entrada.cadena());

            if (idMatricula <= 0) {
                throw new IllegalArgumentException("ERROR: El identificador debe ser un número positivo.");
            }

            // Datos ficticios para la matrícula
            LocalDate fechaMatriculacion = LocalDate.now();
            String cursoAcademico = "23-24";

            // Crear un alumno ficticio
            Alumno alumnoFicticio = new Alumno("Alumno Ficticio", "650225588", "correo@ficticio.com", "41194392N", LocalDate.of(2005, 1, 1));

            // Crear un grado ficticio
            Grado gradoFicticio;
            gradoFicticio = new GradoD("Grado Ficticio D", 2, Modalidad.PRESENCIAL); // 2 años, modalidad presencial

            // Crear un ciclo formativo ficticio
            CicloFormativo cicloFormativoFicticio = new CicloFormativo(
                    1011, "Informática", gradoFicticio, "Desarrollo de Aplicaciones Web", 2000
            );

            // Crear una lista de asignaturas ficticias
            List<Asignatura> coleccionAsignaturas = new ArrayList<>();
            coleccionAsignaturas.add(new Asignatura("1111", "Matemáticas", 150, Curso.PRIMERO, 5, EspecialidadProfesorado.FOL, cicloFormativoFicticio));


            // Crear y devolver la matrícula
            return new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumnoFicticio, coleccionAsignaturas);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ERROR: El identificador debe ser un número válido. " + e.getMessage());
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR: No se pudo obtener la matrícula. " + e.getMessage());
        }
    }






}

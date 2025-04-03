package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class Vista {
    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        if (controlador == null) {
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        }
        this.controlador = controlador;
    }

    public void comenzar() {
        int ordinalOpcion;
        do {
            Consola.mostrarMenu();
            ordinalOpcion = Consola.elegirOpcion().ordinal();
            Opcion opcion = Opcion.values()[ordinalOpcion];
            ejecutarOpcion(opcion);
        } while (ordinalOpcion != Opcion.SALIR.ordinal());
    }

    public void terminar() {
        controlador.terminar();
    }

    public void ejecutarOpcion(Opcion opcion) {
        switch (opcion) {
            case INSERTAR_ALUMNO:
                insertarAlumno();
                break;
            case BUSCAR_ALUMNO:
                buscarAlumno();
                break;
            case BORRAR_ALUMNO:
                borrarAlumno();
                break;
            case MOSTRAR_ALUMNOS:
                mostrarAlumnos();
                break;
            case INSERTAR_ASIGNATURA:
                insertarAsignatura();
                break;
            case BUSCAR_ASIGNATURA:
                buscarAsignatura();
                break;
            case BORRAR_ASIGNATURA:
                borrarAsignatura();
                break;
            case MOSTRAR_ASIGNATURAS:
                mostrarAsignaturas();
                break;
            case INSERTAR_CICLO_FORMATIVO:
                insertarCicloFormativo();
                break;
            case BUSCAR_CICLO_FORMATIVO:
                buscarCicloFormativo();
                break;
            case BORRAR_CICLO_FORMATIVO:
                borrarCicloFormativo();
                break;
            case MOSTRAR_CICLOS_FORMATIVOS:
                mostrarCiclosFormativos();
                break;
            case INSERTAR_MATRICULA:
                insertarMatricula();
                break;
            case BUSCAR_MATRICULA:
                buscarMatricula();
                break;
            case ANULAR_MATRICULA:
                anularMatricula();
                break;
            case MOSTRAR_MATRICULAS:
                mostrarMatriculas();
                break;
            case MOSTRAR_MATRICULAS_ALUMNO:
                mostrarMatriculasPorAlumno();
                break;
            case MOSTRAR_MATRICULAS_CICLO_FORMATIVO:
                mostrarMatriculasPorCicloFormativo();
                break;
            case MOSTRAR_MATRICULAS_CURSO_ACADEMICO:
                mostrarMatriculasPorCursoAcademico();
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void insertarAlumno() {
        System.out.println("Insertar Alumno");
        try {
            Alumno alumno = Consola.leerAlumno();
            controlador.insertar(alumno);
            System.out.println("Alumno insertado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al insertar el alumno: " + e.getMessage());
        }
    }

    private void buscarAlumno() {
        System.out.println("Buscar Alumno");
        try {
            String dni = Consola.getDniAlumno();
            Alumno alumno = controlador.buscar(new Alumno("Nombre Ficticio", "600112255", "correo@ficticio.com", dni, LocalDate.now().minusYears(16).minusDays(1))) ;// Buscar por DNI
            if (alumno != null) {
                System.out.println("Alumno encontrado: " + alumno);
            } else {
                System.out.println("No se encontró un alumno con el DNI proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el alumno: " + e.getMessage());
        }
    }

    private void borrarAlumno() {
        System.out.println("Borrar Alumno");
        try {
            String dni = Consola.getDniAlumno(); // Obtener el DNI validado
            controlador.borrar(new Alumno(dni, "Nombre Ficticio", "correo@ficticio.com", "123456789", LocalDate.now())); // Borrar por DNI
            System.out.println("Alumno borrado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al borrar el alumno: " + e.getMessage());
        }
    }

    private void mostrarAlumnos() {
        List<Alumno> alumnos = controlador.getAlumnos();
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
        } else {
            alumnos.sort(Comparator.comparing(Alumno::getDni));
            for (Alumno alumno : alumnos) {
                System.out.println(alumno);
            }
        }
    }

    private void insertarAsignatura() {
        System.out.println("Insertar Asignatura");
        CicloFormativo cicloFormativo = null;
        try {
            // Obtener la lista de ciclos formativos
            List<CicloFormativo> ciclosFormativos = controlador.getCiclosFormativos();

            if (ciclosFormativos.isEmpty()) {
                System.out.println("No hay ciclos formativos registrados. Debes crear uno nuevo.");
                cicloFormativo = Consola.leerCicloFormativo();
                controlador.insertar(cicloFormativo);
                System.out.println("Ciclo formativo creado correctamente.");
            } else {
                // Mostrar la lista de ciclos formativos
                System.out.println("Selecciona un ciclo formativo:");
                for (int i = 0; i < ciclosFormativos.size(); i++) {
                    System.out.println((i + 1) + ". " + ciclosFormativos.get(i));
                }

                // Pedir al usuario que seleccione un ciclo formativo
                System.out.print("Introduce el número del ciclo formativo (o 0 para crear uno nuevo): ");
                int indice;
                try {
                    indice = Integer.parseInt(Entrada.cadena()) - 1;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Entrada no válida. Debes ingresar un número.");
                }

                if (indice == -1) {
                    cicloFormativo = Consola.leerCicloFormativo();
                    controlador.insertar(cicloFormativo);
                    System.out.println("Ciclo formativo creado correctamente.");
                } else if (indice < 0 || indice >= ciclosFormativos.size()) {
                    throw new IllegalArgumentException("Índice no válido.");
                } else {
                    cicloFormativo = ciclosFormativos.get(indice);
                }
            }

            // Leer y crear la asignatura
            if (cicloFormativo != null) {
                Asignatura asignatura = Consola.leerAsignatura(cicloFormativo);
                controlador.insertar(asignatura);
                System.out.println("Asignatura insertada correctamente.");
            } else {
                System.out.println("No se pudo determinar un ciclo formativo válido.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al insertar la asignatura: " + e.getMessage());
        }
    }


    private void buscarAsignatura() {
        System.out.println("Buscar Asignatura");
        try {
            System.out.print("Introduce el código de la asignatura: ");
            String codigo = Entrada.cadena();
            Asignatura asignatura = controlador.buscar(new Asignatura(codigo, "", 0, null, 0, null, null));
            if (asignatura != null) {
                System.out.println("Asignatura encontrada: " + asignatura);
            } else {
                System.out.println("No se encontró una asignatura con el código proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar la asignatura: " + e.getMessage());
        }
    }

    private void borrarAsignatura() {
        System.out.println("Borrar Asignatura");
        try {
            System.out.print("Introduce el código de la asignatura a borrar: ");
            String codigo = Entrada.cadena();
            Asignatura asignatura = controlador.buscar(new Asignatura(codigo, "", 0, null, 0, null, null));
            if (asignatura != null) {
                controlador.borrar(asignatura);
                System.out.println("Asignatura borrada correctamente.");
            } else {
                System.out.println("No se encontró una asignatura con el código proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al borrar la asignatura: " + e.getMessage());
        }
    }

    private void mostrarAsignaturas() {
        List<Asignatura> asignaturas = controlador.getAsignaturas();
        if (asignaturas.isEmpty()) {
            System.out.println("No hay asignaturas registradas.");
        } else {
            asignaturas.sort(Comparator.comparing(Asignatura::getCodigo));
            System.out.println("Lista de asignaturas:");
            for (Asignatura asignatura : asignaturas) {
                System.out.println(asignatura);
            }
        }
    }

    private void insertarCicloFormativo() {
        System.out.println("Insertar Ciclo Formativo");
        try {
            CicloFormativo cicloFormativo = Consola.leerCicloFormativo();
            controlador.insertar(cicloFormativo);
            System.out.println("Ciclo formativo insertado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al insertar el ciclo formativo: " + e.getMessage());
        }
    }

    private void buscarCicloFormativo() {
        System.out.println("Buscar Ciclo Formativo");
        try {
            CicloFormativo cicloFormativo = Consola.leerCicloFormativo();
            cicloFormativo = controlador.buscar(cicloFormativo);
            if (cicloFormativo != null) {
                System.out.println("Ciclo formativo encontrado: " + cicloFormativo);
            } else {
                System.out.println("No se encontró un ciclo formativo con el código proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el ciclo formativo: " + e.getMessage());
        }
    }

    private void borrarCicloFormativo() {
        System.out.println("Borrar Ciclo Formativo");
        try {
            CicloFormativo cicloFormativo = Consola.leerCicloFormativo();
            controlador.borrar(cicloFormativo);
            System.out.println("Ciclo formativo borrado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al borrar el ciclo formativo: " + e.getMessage());
        }
    }

    private void mostrarCiclosFormativos() {
        List<CicloFormativo> ciclosFormativos = controlador.getCiclosFormativos();
        if (ciclosFormativos.isEmpty()) {
            System.out.println("No hay ciclos formativos registrados.");
        } else {
            ciclosFormativos.sort(Comparator.comparing(CicloFormativo::getCodigo));
            for (CicloFormativo ciclo : ciclosFormativos) {
                System.out.println(ciclo);
            }
        }
    }

    private void insertarMatricula() {
        System.out.println("Insertar Matrícula");

        try {
            // Solicitar el identificador de la matrícula
            System.out.print("Introduce el identificador de la matrícula: ");
            int idMatricula = Entrada.entero();
            if (idMatricula <= 0) {
                throw new IllegalArgumentException("El identificador de la matrícula debe ser un número positivo.");
            }

            // Solicitar el curso académico
            System.out.print("Introduce el curso académico (ej. 2023-2024): ");
            String cursoAcademico = Entrada.cadena().trim();
            if (cursoAcademico.isEmpty() || !cursoAcademico.matches("\\d{4}-\\d{4}")) {
                throw new IllegalArgumentException("El curso académico debe tener el formato 'YYYY-YYYY'.");
            }

            // Solicitar la fecha de matriculación
            System.out.print("Introduce la fecha de matriculación (YYYY-MM-DD): ");
            LocalDate fechaMatriculacion;
            try {
                fechaMatriculacion = LocalDate.parse(Entrada.cadena().trim());
            } catch (Exception e) {
                throw new IllegalArgumentException("Formato de fecha inválido. Debe ser 'YYYY-MM-DD'.");
            }

            // Solicitar los datos del alumno
            System.out.println("Introduce los datos del alumno:");
            Alumno alumno = Consola.leerAlumno();

            List<Asignatura> asignaturasDisponibles = controlador.getAsignaturas();

            // Solicitar las asignaturas
            System.out.println("Selecciona las asignaturas para la matrícula:");
            List<Asignatura> asignaturas = Consola.elegirAsignaturasMatricula(asignaturasDisponibles); // Pasar la lista de asignaturas disponibles

            // Crear la matrícula
            Matricula matricula = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumno, asignaturas);

            // Insertar la matrícula en el controlador
            controlador.insertar(matricula);
            System.out.println("Matrícula insertada correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al insertar la matrícula: " + e.getMessage());
        }
    }

    private void buscarMatricula() {
        System.out.println("Buscar Matrícula");
        try {
            System.out.print("Introduce el identificador de la matrícula:");
            int idMatricula = Entrada.entero();
            Matricula matricula = controlador.buscar(new Matricula(idMatricula, null, null, null, null));
            if (matricula != null) {
                System.out.println("Matrícula encontrada: " + matricula);
            } else {
                System.out.println("No se encontró una matrícula con el identificador proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar la matrícula: " + e.getMessage());
        }
    }

    private void anularMatricula() {
        System.out.println("Anular Matrícula");
        try {
            System.out.print("Introduce el identificador de la matrícula:");
            int idMatricula = Entrada.entero();
            Matricula matricula = controlador.buscar(new Matricula(idMatricula, null, null, null, null));

            if (matricula != null) {
                controlador.borrar(matricula);
                System.out.println("Matrícula anulada correctamente.");
            } else {
                System.out.println("No se encontró una matrícula con el identificador proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al anular la matrícula: " + e.getMessage());
        }
    }

    private void mostrarMatriculas() {
        List<Matricula> matriculas = controlador.getMatriculas();
        if (matriculas.isEmpty()) {
            System.out.println("No hay matrículas registradas.");
        } else {
            matriculas.sort(Comparator.comparing(Matricula::getIdMatricula));
            for (Matricula matricula : matriculas) {
                System.out.println(matricula);
            }
        }
    }

    private void mostrarMatriculasPorAlumno() {
        System.out.println("Mostrar Matrículas por Alumno");
        try {
            Alumno alumno = Consola.leerAlumno();
            List<Matricula> matriculas = controlador.getMatriculas(alumno);
            if (matriculas.isEmpty()) {
                System.out.println("No hay matrículas asociadas al alumno.");
            } else {
                for (Matricula matricula : matriculas) {
                    System.out.println(matricula);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar las matrículas del alumno: " + e.getMessage());
        }
    }

    private void mostrarMatriculasPorCicloFormativo() {
        System.out.println("Mostrar Matrículas por Ciclo Formativo");
        try {
            CicloFormativo cicloFormativo = Consola.leerCicloFormativo();
            List<Matricula> matriculas = controlador.getMatriculas(cicloFormativo);
            if (matriculas.isEmpty()) {
                System.out.println("No hay matrículas asociadas al ciclo formativo.");
            } else {
                for (Matricula matricula : matriculas) {
                    System.out.println(matricula);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar las matrículas por ciclo formativo: " + e.getMessage());
        }
    }

    public void mostrarMatriculasPorCursoAcademico() {
        try {
            // Solicitar el curso académico al usuario
            System.out.print("Introduce el curso académico (ej. 2023-2024): ");
            String cursoAcademico = Entrada.cadena().trim();

            // Validar el formato del curso académico
            if (cursoAcademico.isEmpty() || !cursoAcademico.matches("\\d{4}-\\d{4}")) {
                throw new IllegalArgumentException("El curso académico debe tener el formato 'YYYY-YYYY'.");
            }

            // Obtener las matrículas del curso académico desde el controlador
            List<Matricula> matriculas = controlador.getMatriculas(cursoAcademico);

            // Mostrar las matrículas
            if (matriculas.isEmpty()) {
                System.out.println("No se encontraron matrículas para el curso académico " + cursoAcademico + ".");
            } else {
                System.out.println("Matrículas para el curso académico " + cursoAcademico + ":");
                for (Matricula matricula : matriculas) {
                    System.out.println(matricula); // Suponiendo que Matricula tiene un método toString() implementado
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al mostrar las matrículas: " + e.getMessage());
        }
    }

}
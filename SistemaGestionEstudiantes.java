
 * ============================================================
 *  Programa por consola que permite:
 *   1. Agregar estudiantes
 *   2. Mostrar la lista de estudiantes
 *   3. Buscar un estudiante por ID
 *   4. Mostrar la lista en orden inverso
 *   5. Salir del sistema
 * ============================================================
 */

import java.util.Scanner;

public class SistemaGestionEstudiantes {

    // Tamano maximo del arreglo. Uso arreglos porque ya sabemos
    // cuantos estudiantes como maximo vamos a guardar.
    static final int MAX_ESTUDIANTES = 50;

    // Arreglos paralelos: la misma posicion guarda los datos
    // de un mismo estudiante (id, nombre y nota)
    static int[] ids = new int[MAX_ESTUDIANTES];
    static String[] nombres = new String[MAX_ESTUDIANTES];
    static double[] notas = new double[MAX_ESTUDIANTES];

    // Variable de CONTEO: lleva el total de estudiantes registrados
    // y tambien indica la siguiente posicion libre del arreglo
    static int totalEstudiantes = 0;

    // Scanner para leer lo que escribe el usuario
    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        // Variable BANDERA: controla cuando se termina el menu
        boolean banderaSalir = false;
        int opcion;

        System.out.println("==============================================");
        System.out.println("  SISTEMA DE GESTION DE ESTUDIANTES (SGE)");
        System.out.println("  Desarrollado por: Julian Andres Abril Restrepo");
        System.out.println("==============================================");

        // Repeticion condicionada al comienzo (while):
        // el menu se repite mientras el usuario no elija salir
        while (!banderaSalir) {

            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");

            // Decision multiple (switch): lleva el programa
            // a la opcion que el usuario escogio
            switch (opcion) {
                case 1:
                    agregarEstudiante();
                    break;
                case 2:
                    mostrarEstudiantes();
                    break;
                case 3:
                    buscarEstudiante();
                    break;
                case 4:
                    mostrarEstudiantesInverso();
                    break;
                case 5:
                    // Cambiamos la bandera para que el while termine
                    banderaSalir = true;
                    System.out.println("\nGracias por usar el SGE. Hasta pronto!");
                    break;
                default:
                    System.out.println("\nOpcion no valida. Debe escribir un numero del 1 al 5.");
            }
        }

        teclado.close();
    }

    // ------------------------------------------------------------
    // Muestra las opciones del menu principal
    // ------------------------------------------------------------
    public static void mostrarMenu() {
        System.out.println("\n----------- MENU PRINCIPAL -----------");
        System.out.println("1. Agregar estudiante");
        System.out.println("2. Mostrar lista de estudiantes");
        System.out.println("3. Buscar estudiante por ID");
        System.out.println("4. Mostrar lista en orden inverso");
        System.out.println("5. Salir");
        System.out.println("Estudiantes registrados: " + totalEstudiantes + " de " + MAX_ESTUDIANTES);
        System.out.println("--------------------------------------");
    }

    // ------------------------------------------------------------
    // Opcion 1: agrega un estudiante al arreglo
    // ------------------------------------------------------------
    public static void agregarEstudiante() {
        System.out.println("\n--- AGREGAR ESTUDIANTE ---");

        // Decision simple: primero se revisa que el arreglo no este lleno
        if (totalEstudiantes >= MAX_ESTUDIANTES) {
            System.out.println("No se pueden agregar mas estudiantes. El sistema esta lleno.");
        } else {
            int id = leerEntero("Ingrese el ID del estudiante: ");

            // Decisiones anidadas: se valida el ID antes de guardar
            if (id <= 0) {
                System.out.println("El ID debe ser un numero mayor que 0.");
            } else {
                if (buscarPosicion(id) != -1) {
                    System.out.println("Ya existe un estudiante con el ID " + id + ".");
                } else {
                    String nombre = leerTexto("Ingrese el nombre del estudiante: ");
                    double nota = leerNota("Ingrese la nota final (0.0 a 5.0): ");

                    // Se guarda en la siguiente posicion libre
                    ids[totalEstudiantes] = id;
                    nombres[totalEstudiantes] = nombre;
                    notas[totalEstudiantes] = nota;

                    // Conteo: se suma 1 al total de estudiantes
                    totalEstudiantes++;

                    System.out.println("Estudiante agregado correctamente.");
                }
            }
        }
    }

    // ------------------------------------------------------------
    // Opcion 2: muestra todos los estudiantes (orden ascendente)
    // ------------------------------------------------------------
    public static void mostrarEstudiantes() {
        System.out.println("\n--- LISTA DE ESTUDIANTES ---");

        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados todavia.");
        } else {
            double sumaNotas = 0;     // SUMATORIA de las notas
            int contadorAprobados = 0; // CONTEO de aprobados

            // Iteracion ascendente (for): desde el primero hasta el ultimo
            for (int i = 0; i < totalEstudiantes; i++) {
                mostrarEstudiante(i);
                sumaNotas = sumaNotas + notas[i];

                if (notas[i] >= 3.0) {
                    contadorAprobados++;
                }
            }

            double promedio = sumaNotas / totalEstudiantes;

            System.out.println("Total de estudiantes: " + totalEstudiantes);
            System.out.println("Estudiantes aprobados: " + contadorAprobados);
            System.out.println("Promedio del grupo: " + String.format("%.2f", promedio));
        }
    }

    // ------------------------------------------------------------
    // Opcion 3: busca un estudiante por su ID
    // ------------------------------------------------------------
    public static void buscarEstudiante() {
        System.out.println("\n--- BUSCAR ESTUDIANTE ---");

        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados para buscar.");
        } else {
            int idBuscado = leerEntero("Ingrese el ID a buscar: ");
            int posicion = buscarPosicion(idBuscado);

            // Decision simple: se valida si el estudiante fue encontrado
            if (posicion != -1) {
                System.out.println("Estudiante encontrado:");
                mostrarEstudiante(posicion);
            } else {
                System.out.println("No se encontro ningun estudiante con el ID " + idBuscado + ".");
            }
        }
    }

    // ------------------------------------------------------------
    // Opcion 4: muestra la lista del ultimo al primero
    // ------------------------------------------------------------
    public static void mostrarEstudiantesInverso() {
        System.out.println("\n--- LISTA EN ORDEN INVERSO ---");

        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados todavia.");
        } else {
            // Iteracion descendente (for): desde el ultimo hasta el primero
            for (int i = totalEstudiantes - 1; i >= 0; i--) {
                mostrarEstudiante(i);
            }
        }
    }

    // ------------------------------------------------------------
    // Funcion reutilizable: muestra los datos de UN estudiante
    // Se usa en mostrar, buscar y mostrar inverso
    // ------------------------------------------------------------
    public static void mostrarEstudiante(int posicion) {
        String estado;

        // Decision simple con else para saber si aprobo
        if (notas[posicion] >= 3.0) {
            estado = "Aprobado";
        } else {
            estado = "Reprobado";
        }

        System.out.println("ID: " + ids[posicion]
                + " | Nombre: " + nombres[posicion]
                + " | Nota: " + notas[posicion]
                + " | Estado: " + estado);
    }

    // ------------------------------------------------------------
    // Funcion reutilizable: busca un ID en el arreglo.
    // Devuelve la posicion donde esta o -1 si no existe.
    // ------------------------------------------------------------
    public static int buscarPosicion(int idBuscado) {
        boolean encontrado = false; // BANDERA para detener la busqueda
        int posicion = -1;
        int i = 0;

        // Se recorre el arreglo hasta encontrarlo o llegar al final
        while (i < totalEstudiantes && !encontrado) {
            if (ids[i] == idBuscado) {
                encontrado = true;
                posicion = i;
            }
            i++;
        }

        return posicion;
    }

    // ------------------------------------------------------------
    // Lee un numero entero y no deja que el programa se caiga
    // si el usuario escribe letras.
    // Repeticion condicionada al final (do-while): se pide el dato
    // al menos una vez y se repite mientras sea invalido.
    // ------------------------------------------------------------
    public static int leerEntero(String mensaje) {
        int numero = 0;
        boolean datoValido = false;

        do {
            System.out.print(mensaje);
            try {
                numero = Integer.parseInt(teclado.nextLine().trim());
                datoValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: debe escribir un numero entero.");
            }
        } while (!datoValido);

        return numero;
    }

    // ------------------------------------------------------------
    // Lee una nota entre 0.0 y 5.0 (acepta punto o coma)
    // ------------------------------------------------------------
    public static double leerNota(String mensaje) {
        double nota = 0;
        boolean datoValido = false;

        do {
            System.out.print(mensaje);
            try {
                String texto = teclado.nextLine().trim().replace(",", ".");
                nota = Double.parseDouble(texto);

                if (nota >= 0.0 && nota <= 5.0) {
                    datoValido = true;
                } else {
                    System.out.println("Error: la nota debe estar entre 0.0 y 5.0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: debe escribir un numero, por ejemplo 4.5");
            }
        } while (!datoValido);

        return nota;
    }

    // ------------------------------------------------------------
    // Lee un texto que no puede quedar vacio
    // Repeticion condicionada al comienzo (while)
    // ------------------------------------------------------------
    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String texto = teclado.nextLine().trim();

        while (texto.isEmpty()) {
            System.out.println("Error: este dato no puede quedar vacio.");
            System.out.print(mensaje);
            texto = teclado.nextLine().trim();
        }

        return texto;
    }
}

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Concesionario {
    static Scanner leer = new Scanner(System.in);
    static List<Coche> lista = new ArrayList<Coche>();


    public static void main(String[] args) {
        String path = "src/main/resources/coches.dat";
        leerFichero(path);
        int opcion = 0;

        do {
            System.out.println("Menu del concesionario:");
            System.out.println("1.Añadir un nuevo coche");
            System.out.println("2.Borrar un coche por id");
            System.out.println("3.Consulta de coche por id");
            System.out.println("4.Listado de coches");
            System.out.println("5.Exportar fichero .CSV");
            System.out.println("6.Exit");
            opcion = leer.nextInt();
            switch (opcion) {
                case 1:
                    addCoche();
                    break;
                case 2:
                    System.out.println("Introduzca el id a borrar:");
                    int idBorrar = leer.nextInt();
                    if (borrarCoche(idBorrar)) {
                        System.out.println("Coche " + idBorrar + " borrado con exito");
                    } else System.out.println("id no encontrado");
                    break;
                case 3:
                    System.out.println("Introduzca el id del coche:");
                    int idBuscar = leer.nextInt();
                    if (buscarId(idBuscar)) {
                        System.out.println("encontrado con el id: " + idBuscar);
                    } else System.out.println("id no encontrado");
                    break;
                case 4:
                    for (Coche c : lista) {
                        System.out.println(c.toString());
                    }
                    break;
                case 5:
                    exportarCSV("src/main/resources/coches.csv");
                    break;

                case 6:
                    break;

            }
        } while (opcion != 6);
        //cuando el usuario sale del programa (opcion 6), se ejecuta guardarListado, que guardará la lista de coches que
        //haya en memoria en el archivo .dat
        guardarListado();


    }

    // Metodo para exportar los coches a un fichero .csv
    private static void exportarCSV(String path) {
        File file = new File(path);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            // Escribir la cabecera del CSV
            writer.println("ID;Matricula;Marca;Modelo;Color");

            // Escribir los datos de cada coche
            for (Coche c : lista) {
                writer.println(c.getId() + ";" + c.getMatricula() + ";" + c.getMarca() + ";" + c.getModelo() + ";" + c.getColor());
            }
            System.out.println("Exportación de datos a csv realizada correctamente");
        } catch (IOException e) {
            System.out.println("Error al exportar a CSV");
        }
    }


    //Metodo para añadir un nuevo objeto Coche a la lista
    //Y que verifica que tanto el ID como la MATRICULA sean únicos
    private static void addCoche() {
        System.out.println("Introduzca el id del coche:");
        int id = leer.nextInt();
        leer.nextLine();// avanzar a la siguiente linea porque si no se salta
        System.out.println("Introduzca la matricula:");
        String matricula = leer.nextLine();
        System.out.println("Introduzca la marca:");
        String marca = leer.nextLine();
        System.out.println("Introduzca el modelo:");
        String modelo = leer.nextLine();
        System.out.println("Introduzca el color");
        String color = leer.nextLine();
        //llamamos al metro de buscar coche por ID, si ya existe decimos que no se puede

        if (!buscarId(id)) {
            // y despues llamamos a comprobar matricula
            if (!comprobarMatricula(matricula)) {
                lista.add(new Coche(id, matricula, marca, modelo, color));
                System.out.println("Coche añadido correctamente");
            } else {
                System.out.println("La matricula ya existe");

            }
        } else {
            System.out.println("el id =" + id + " ya existe. No se puede duplicar el id");
        }
    }

    //Metodo para comparar 2 matriculas
    private static boolean comprobarMatricula(String matricula) {
        for (Coche c : lista) {
            if (c.getMatricula().equals(matricula)) {
                return true;
            }
        }
        return false;
    }

    //Metodo que elimina un coche de la lista si el id coincide
    //devuelve true si lo encuentra y false si no
    private static boolean borrarCoche(int id) {
        for (Coche c : lista) {
            if (c.getId() == id) {
                lista.remove(c);
                return true;
            }
        }
        return false;
    }

    //Metodo que busca por id en la lista
    //devuelve true si lo encuentra y false si no
    private static boolean buscarId(int id) {
        for (Coche c : lista) {
            if (c.getId() == id) {
                System.out.println(c.toString());
                return true;
            }
        }
        return false;
    }

    //metodo para leer la lista de objetos coche del fichero coches.dat
    private static void leerFichero(String path) {
        File file = new File(path);
        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            //Este bucle while(true) es infinito pero se para en cuanto llega al final
            //de fichero EOFException, y hace el break;
            while (true) {
                try {
                    lista.add((Coche) objectInputStream.readObject());

                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero");
        } catch (ClassNotFoundException e) {
            System.out.println("Error en la clase");

        } finally {
            try {
                objectInputStream.close();
            } catch (IOException | NullPointerException e) {
                System.out.println("Error al cerrar el flujo de lectura");
            }
        }
    }

    //metodo para guardar la lista de objetos coche en el fichero coches.dat
    private static void guardarListado() {
        File file = new File("src/main/resources/coches.dat");
        ObjectOutputStream objectOutputStream = null;

        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            for (Coche c : lista) {
                objectOutputStream.writeObject(c);
            }
        } catch (IOException e) {
            System.out.println("Error de salida en el fichero");
        } finally {
            try {
                objectOutputStream.close();
            } catch (IOException | NullPointerException e) {
                System.out.println("Error al guardar");
            }
        }
    }
}

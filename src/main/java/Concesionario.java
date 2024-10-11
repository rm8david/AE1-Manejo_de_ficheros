import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Concesionario {
     static Scanner leer = new Scanner(System.in);
     static List<Coche> lista = new ArrayList<Coche>();
    public static void main(String[] args) {
        int opcion = 0;

        do{
            System.out.println("Menu del concesionario:");
            System.out.println("1.Añadir un nuevo coche");
            System.out.println("2.Borrar un coche por id");
            System.out.println("3.Consulta de coche por id");
            System.out.println("4.Listado de coches");
            System.out.println("5.Terminar el programa");
            opcion = leer.nextInt();
            switch (opcion){
                case 1:
                    addCoche();
                    break;
                case 2:
                    System.out.println("Introduzca el id a borrar:");
                    int idBorrar = leer.nextInt();
                    if(borrarCoche(idBorrar)){
                        System.out.println("Coche "+ idBorrar+ " borrado con exito");
                    }else System.out.println("id no encontrado");
                    break;
                case 3:
                    System.out.println("Introduzca el id del coche:");
                    int idBuscar = leer.nextInt();
                    if(buscarId(idBuscar)){
                        System.out.println("encontrado con el id: "+ idBuscar);
                    }else System.out.println("id no encontrado");
                    break;
                case 4:
                    for(Coche c:lista){
                        System.out.println(c.toString());
                    }
                    break;
                case 5:
                    break;
            }
        }while (opcion!=5);
        File file = new File("src/main/resources/coches.dat");
        ObjectOutputStream objectOutputStream = null;

        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            for(Coche c:lista){
                objectOutputStream.writeObject(c);
            }
        } catch (IOException e) {
            System.out.println("Error en el fichero");
        }finally {
            try {
                objectOutputStream.close();
            } catch (IOException|NullPointerException e) {
                System.out.println("Error al guardar");
            }
        }


    }
    private static void addCoche(){
        System.out.println("Introduzca el id del coche:");
        int id = leer.nextInt();
        leer.nextLine();// avanzar a la siguiente linea
        System.out.println("Introduzca la matricula:");
        String matricula = leer.nextLine();
        System.out.println("Introduzca la marca:");
        String marca = leer.nextLine();
        System.out.println("Introduzca el modelo:");
        String modelo = leer.nextLine();
        System.out.println("Introduzca el color");
        String color = leer.nextLine();
        lista.add(new Coche(id,matricula,marca,modelo,color));
        System.out.println("Coche añadido correctamente");
    }
    private static boolean borrarCoche(int id){
        for(Coche c:lista){
            if(c.getId()==id){
                lista.remove(c);
                return true;
            }
        }
        return false;
    }
    private static boolean buscarId(int id){
        for(Coche c:lista){
            if(c.getId()==id){
                System.out.println(c.toString());
                return true;
            }
        }
        return false;
    }
}

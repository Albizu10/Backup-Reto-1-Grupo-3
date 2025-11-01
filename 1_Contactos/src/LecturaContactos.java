import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LecturaContactos {
    //Restablecer el color:
    public static final String ANSI_RESET = "\u001B[0m";
    //Para imprimir los errores:
    public static final String ANSI_RED = "\u001B[31m";
    //Para imprimir los resultados:
    public static final String ANSI_GREEN = "\u001B[32m";
    //Para indicar que el formato de los datos introducidos no es correcto:
    public static final String ANSI_YELLOW = "\u001B[33m";
    //Para indicar los datos que tiene que introducir el usuario:
    public static final String ANSI_MAGENTA = "\u001B[35m";
    //Para imprimir los menus / enunciados:
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void main(String[] args) {
        //Try catch principal para manejar excepciones de lectura
        try {
            //Definir la ruta del archivo CSV que contiene los contactos
            File ruta = new File("contactos.csv");

            //Try with resources para cerrar los recursos automáticamente al finalizar
            try(FileReader fr = new FileReader(ruta)){
                //Inicializar recurso Buffered Reader
                BufferedReader br = new BufferedReader(fr);

                //Variable para almacenar cada línea leída del archivo
                String linea;

                // Saltar la primera línea porque es el encabezado
                br.readLine();

                //Mensaje inicial
                System.out.println(ANSI_CYAN + "\nLEYENDO CONTACTOS DEL FICHERO '" + ruta + "'" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "----------------------------------------------\n" + ANSI_RESET);

                //Leer el archivo línea a línea
                while ((linea = br.readLine()) != null) {
                    //Eliminar las comillas
                    String lineaSinSeparador = linea.replace("\"", "");

                    //Separar los campos que están separados por comas
                    String[] campos = lineaSinSeparador.split(",");

                    //Si la línea tiene los 5 campos, imprimir los datos
                    if (campos.length >= 5) {
                        //Asignar cada valor de X campo a su variable indibidual
                        String nombreCompleto = campos[0];
                        String correo = campos[1];
                        String telefono = campos[2];
                        String ciudad = campos[3];
                        String pais = campos[4];

                        //Imprimir los datos del contacto
                        System.out.println("Nombre completo: " + nombreCompleto);
                        System.out.println("Correo: " + correo);
                        System.out.println("Teléfono: " + telefono);
                        System.out.println("Ciudad: " + ciudad);
                        System.out.println("País: " + pais);
                        System.out.println(ANSI_GREEN + "\n-----------------------------------\n" + ANSI_RESET);
                    } else {
                    //SI NO
                        //Informar al usuario que la línea X tiene un formato incorrecto
                        System.out.println(ANSI_YELLOW + "Línea con formato incorrecto: \n" + ANSI_RESET + linea);
                        System.out.println(ANSI_YELLOW + "\n-----------------------------------\n" + ANSI_RESET);
                    }
                }
                //FileReader y Buffered Reader se cierran automáticamente
            }
        } catch (FileNotFoundException e) {
            //Mensaje de excepción si el archivo no existe / no lo encuentra
            System.err.println(ANSI_RED);
            e.printStackTrace();
            System.err.println(ANSI_RESET);
        } catch (IOException e) {
            //Mensaje de excepciones generales
            System.err.println(ANSI_RED);
            e.printStackTrace();
            System.err.println(ANSI_RESET);
        }

    }
}

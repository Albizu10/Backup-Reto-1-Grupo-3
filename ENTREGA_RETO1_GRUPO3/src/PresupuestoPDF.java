import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que permite leer o copiar un archivo PDF (en este caso, 'Presupuesto - S00007.pdf').
 * Combina las funcionalidades de LecturaPresupuesto y EscrituraPresupuesto en un único programa con menú.
 * @version 1.0
 */
public class PresupuestoPDF {
    /**Código ANSI para restablecer el color de la terminal */
    public static final String ANSI_RESET = "\u001B[0m";
    /**Código ANSI para imprimir los errores en color rojo en la terminal */
    public static final String ANSI_RED = "\u001B[31m";
    /**Código ANSI para imprimir los resultados en color verde en la terminal */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**Código ANSI para indicar que el formato de los datos introducidos no es correcto, color amarillo en la terminal */
    public static final String ANSI_YELLOW = "\u001B[33m";
    /**Código ANSI para indicar los datos que tiene que introducir el usuario en color magenta en la terminal */
    public static final String ANSI_MAGENTA = "\u001B[35m";
    /**Código ANSI para imprimir los menus / enunciados en color cyan en la terminal */
    public static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Método principal del programa.
     * Muestra un menú para leer el contenido del PDF, copiarlo o salir.
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println(ANSI_CYAN + "\nMENÚ PRINCIPAL\n------------------------------------" + ANSI_RESET);
            System.out.println("1) Leer presupuesto en formato hexadecimal");
            System.out.println("2) Copiar presupuesto (crear nueva copia)");
            System.out.println("3) Salir");
            System.out.print(ANSI_MAGENTA + "\nSeleccione una opción: " + ANSI_RESET);

            try {
                int opcion = sc.nextInt();
                sc.nextLine();

                if (opcion == 1) {
                    leerPresupuesto();
                } else if (opcion == 2) {
                    copiarPresupuesto();
                } else if (opcion == 3) {
                    System.out.println(ANSI_CYAN + "\n------------------------------------\nSaliendo del programa...\n" + ANSI_RESET);
                    salir = true;
                } else {
                    System.out.println(ANSI_YELLOW + "\nDebe introducir 1, 2 o 3.\n" + ANSI_RESET);
                }
            } catch (InputMismatchException e) {
                System.out.println(ANSI_YELLOW + "\nDebe introducir un número (1, 2 o 3).\n" + ANSI_RESET);
                sc.nextLine();
            }
        }

        sc.close();
    }

    /**
     * Método para LEER el PRESUPUESTO de un PDF, en este caso 'Presupuesto - S00007.pdf'
     */
    public static void leerPresupuesto() {
        //Definir ruta del archivo PDF del presupuesto
        File ruta = new File("Presupuesto - S00007.pdf");

        //Try with resources para abrir el archivo y cerrar automáticamente el FileInputStream 
        try (FileInputStream filein = new FileInputStream(ruta)) {
            //Mensaje inicial
            System.out.println(ANSI_CYAN + "\nLEYENDO PRESUPUESTO DEL FICHERO '" + ruta + "'" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "-------------------------------------------------------------\n" + ANSI_RESET);

            //Variable para guardar el byte leído
            int byteLeido;

            //Leer e imprimir el archivo byte a byte
            while ((byteLeido = filein.read()) != -1) {
                //Imprimir cada byte en hexadecimal (contiene imagenes)
                System.out.printf("%02X ", byteLeido);
            }

            System.out.println(ANSI_CYAN + "\n--------------------------------------\nLectura finalizada" + ANSI_RESET);
            //File Input Stream se cierra automáticamente al salir del try

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

    /**
     * Método para COPIAR el PRESUPUESTO de un PDF, en este caso 'Presupuesto - S00007_copia.pdf'
     */
    public static void copiarPresupuesto() {
        //Definir las rutas de los archivos de lectura y escritura
        File rutaLectura = new File("Presupuesto - S00007.pdf");
        File rutaEscritura = new File("Presupuesto - S00007_copia.pdf");

        //Mensaje inicial
        System.out.println(ANSI_CYAN + "\nCOPIANDO FICHERO '" + rutaLectura + "' A '" + rutaEscritura + "'" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "\n------------------------------------------------------------------------------------\n" + ANSI_RESET);

        System.out.println(ANSI_MAGENTA + "Contenido que se escribirá en la copia del fichero '" + rutaLectura + "':" + ANSI_RESET);
        System.out.println(ANSI_MAGENTA + "\n------------------------------------------------------------------------------------\n" + ANSI_RESET);

        //Try with resources para leer e imprimir el archivo
        try (FileInputStream filein = new FileInputStream(rutaLectura)) {
            int byteLeido;
            //Leer y mostrar el contenido en formato hexadecimal
            while ((byteLeido = filein.read()) != -1) {
                System.out.printf("%02X ", byteLeido);
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

        //Iniciar la copia
        try (FileInputStream filein = new FileInputStream(rutaLectura);
             FileOutputStream fileout = new FileOutputStream(rutaEscritura)) {

            int byteLeido;

            //Leer del fichero de origen y escribir en el nuevo fichero byte a byte
            while ((byteLeido = filein.read()) != -1) {
                fileout.write(byteLeido);
            }

            System.out.println(ANSI_GREEN + "\n------------------------------------------------------------------------------------\n\nLa copia del presupuesto '" + rutaLectura + "' se ha realizado correctamente." + ANSI_RESET);
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Clase que lee y copia un archivo PDF (en este caso, 'Presupuesto - S00007.pdf').
 * Muestra su contenido en formato hexadecimal, ya que se trata de un archivo binario que contiene imagenes.
 * @version 1.0
 */
public class EscrituraPresupuesto {
    /**Código ANSI para restablecer el color de la terminal */
    public static final String ANSI_RESET = "\u001B[0m";
    /**Código ANSI para imprimir los errores en color rojo en la terminal */
    public static final String ANSI_RED = "\u001B[31m";
    /**Código ANSI para imprimir los resultados en color verde en la terminal */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**Código ANSI para indicar que el formato de los datos introducidos no es correcto, color amarillo en la terminal*/
    public static final String ANSI_YELLOW = "\u001B[33m";
    /**Código ANSI para indicar los datos que tiene que introducir el usuario en color magenta en la terminal */
    public static final String ANSI_MAGENTA = "\u001B[35m";
    /**Código ANSI para imprimir los menus / enunciados en color cyan en la terminal */
    public static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Método principal para copiar un archivo PDF a otro y mostrar su contenido en formato hexadecimal
     * @param args
     */
    public static void main(String[] args) {
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
        try (FileInputStream filein = new FileInputStream(rutaLectura); FileOutputStream fileout = new FileOutputStream(rutaEscritura)) {
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
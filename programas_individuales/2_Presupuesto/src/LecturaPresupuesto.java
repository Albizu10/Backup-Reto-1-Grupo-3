import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Clase que permite leer un archivo PDF (en este caso, 'Presupuesto - S00007.pdf')
 * y mostrar su contenido en formato hexadecimal, ya que se trata de un archivo binario que contiene imagenes.
 * @version 1.0
 */
public class LecturaPresupuesto {
    /**Código ANSI para restablecer el color de la terminal */
    public static final String ANSI_RESET = "\u001B[0m";
    /**Código ANSI para imprimir los errores en color rojo en la terminal */
    public static final String ANSI_RED = "\u001B[31m";
    /**Código ANSI para imprimir los menus / enunciados en color cyan en la terminal */
    public static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Método principal del programa que permite leer un archivo PDF y mostrar su contenido en formato hexadecimal.
     * @param args
     */
    public static void main(String[] args) {
        //Definir ruta del archivo PDF del presupuesto
        File ruta = new File("Presupuesto - S00007.pdf");

        //Try with resources para abrir el archivo y cerrar automáticamente el FileInputStream 
        try (FileInputStream filein = new FileInputStream(ruta)){
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
}
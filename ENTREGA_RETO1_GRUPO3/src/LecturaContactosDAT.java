import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Clase que permite leer los contactos de un archivo binario DAT, en este caso, 'contactos.dat'
 * @version 1.0
 */
public class LecturaContactosDAT {
    /** Longitudes específicas para cada campo */
    private static final int LONG_NOMBRECOMPLETO = 35;
    private static final int LONG_CORREO = 35;
    private static final int LONG_TELEFONO = 15;
    private static final int LONG_CIUDAD = 25;
    private static final int LONG_PAIS = 20;

    /** Código ANSI para restablecer el color de la terminal */
    public static final String ANSI_RESET = "\u001B[0m";
    /** Código ANSI para imprimir los errores en color rojo en la terminal */
    public static final String ANSI_RED = "\u001B[31m";
    /** Código ANSI para imprimir los resultados en color verde en la terminal */
    public static final String ANSI_GREEN = "\u001B[32m";
    /** Código ANSI para imprimir los menús / enunciados en color cyan en la terminal */
    public static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Método principal del programa. 
     * Permite al usuario leer contactos de un archivo DAT.
     * @param args
     */
    public static void main(String[] args) {   
        // Definir la ruta del archivo DAT que contiene los contactos
        File ruta = new File("contactos.dat");

        // Try with resources para cerrar los recursos automáticamente al finalizar
        try (RandomAccessFile rndmAF = new RandomAccessFile(ruta, "r")) {
            // Mensaje inicial
            System.out.println(ANSI_CYAN + "\nLEYENDO CONTACTOS DEL FICHERO '" + ruta + "'" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "----------------------------------------------\n" + ANSI_RESET);

            // Número de registros a leer (en este caso se asume que el archivo tiene registros completos)
            int numRegistros = (int) (rndmAF.length() / (LONG_NOMBRECOMPLETO + LONG_CORREO + LONG_TELEFONO + LONG_CIUDAD + LONG_PAIS) / 2);

            // Leer el archivo contacto por contacto
            for (int i = 0; i < numRegistros; i++) {
                // Calcular la posición del registro a leer
                long posicion = i * (LONG_NOMBRECOMPLETO + LONG_CORREO + LONG_TELEFONO + LONG_CIUDAD + LONG_PAIS) * 2; // 2 porque cada char ocupa 2 bytes
                
                // Posicionarse en el registro correspondiente
                rndmAF.seek(posicion);

                // Leer los campos de cada contacto
                String nombreCompleto = leerCampo(rndmAF, LONG_NOMBRECOMPLETO);
                String correo = leerCampo(rndmAF, LONG_CORREO);
                String telefono = leerCampo(rndmAF, LONG_TELEFONO);
                String ciudad = leerCampo(rndmAF, LONG_CIUDAD);
                String pais = leerCampo(rndmAF, LONG_PAIS);

                // Imprimir los datos del contacto
                System.out.println("Nombre completo: " + nombreCompleto);
                System.out.println("Correo: " + correo);
                System.out.println("Teléfono: " + telefono);
                System.out.println("Ciudad: " + ciudad);
                System.out.println("País: " + pais);
                System.out.println(ANSI_GREEN + "\n-----------------------------------\n" + ANSI_RESET);
            }

            System.out.println(ANSI_CYAN + "-----------------------------------\nLectura finalizada" + ANSI_RESET);

        } catch (FileNotFoundException e) {
            // Mensaje de excepción si el archivo no existe / no lo encuentra
            System.err.println(ANSI_RED);
            e.printStackTrace();
            System.err.println(ANSI_RESET);
        } catch (IOException e) {
            // Mensaje de excepciones generales
            System.err.println(ANSI_RED);
            e.printStackTrace();
            System.err.println(ANSI_RESET);
        }
    }

    /**
     * Función para leer un campo específico de tipo String con longitud fija
     * 
     * @param rndmAF El archivo donde se leerán los datos
     * @param longitud La longitud del campo a leer
     * @return El valor del campo leído
     * @throws IOException
     */
    private static String leerCampo(RandomAccessFile rndmAF, int longitud) throws IOException {
        char[] buffer = new char[longitud];
        for (int i = 0; i < longitud; i++) {
            buffer[i] = rndmAF.readChar(); // Leer un carácter
        }
        return new String(buffer).trim(); // Convertir a String y eliminar espacios extra
    }
}

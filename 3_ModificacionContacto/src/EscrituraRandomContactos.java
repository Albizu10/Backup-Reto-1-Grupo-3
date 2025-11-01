import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Clase que permite escribir nuevos contactos en un archivo DAT, en este caso, 'contactos.dat'.
 * Permite escribir más de un contacto a la vez y contiene validaciones de correo y teléfono.
 * 
 * @version 1.0
 */
public class EscrituraRandomContactos {
    /**Longitud específica para cada campo*/
    private static final int LONG_NOMBRECOMPLETO = 35;
    private static final int LONG_CORREO = 35;
    private static final int LONG_TELEFONO = 15;
    private static final int LONG_CIUDAD = 25;
    private static final int LONG_PAIS = 20;

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
     * 
     * Método principal del programa. Permite al usuario introducir nuevos contactos
     * y guardarlos en un archivo DAT, con validaciones de correo y teléfono.
     * @param args
     */
    public static void main(String[] args) {
        //Definir ruta del archivo donde se guardan los contactos
        File ruta = new File("contactos.dat");

        String[] arrayNomCompl = { "Ada Martinez", "Administrator", "Carla Jimenez", "Comercializadora Ruiz S.A.", "Juan Martinez", "Laura Hernandez" };
        String[] arrayCorreos = { "adamartinez@techsolutuion.com", "ikmssaid24@lhusurbil.eus", "carlajimenez@techsolutuion.com", "ruiz@gmail.com", "juanmartinez@techsolutuion.com", "laurahernandez@gmail.com" };
        String[] arrayTelefonos = { "555-0101", "245-345", "555-0102", "5664-7631", "555-0103", "6234-8909" };
        String[] arrayCiudades = { "Madrid", "Madrid", "Santander", "Quito", "Soria", "Albacete" };
        String[] arrayPaises = { "España", "España", "España", "España", "España", "España" };

        //Try with resources para que los recursos se cierren automaticamente
        try (RandomAccessFile rndmAF = new RandomAccessFile(ruta, "rw")) {
            //Mensaje inicial
            System.out.println(ANSI_CYAN + "\nESCRITURA DE NUEVOS CONTACTOS \n------------------------------------\n" + ANSI_RESET);

            // Situarse al final del archivo (para no sobrescribir si ya existen datos)
            rndmAF.seek(rndmAF.length());

            for(int i=0; i<arrayNomCompl.length; i++){
                escribirContacto(rndmAF, arrayNomCompl[i], arrayCorreos[i], arrayTelefonos[i], arrayCiudades[i], arrayPaises[i]);
                    
                System.out.println("Escribiendo los datos del contacto: " + arrayNomCompl[i] + ANSI_GREEN + "\n\n---------------------------------------------------------------------\n" + ANSI_RESET);
            }

            System.out.println(ANSI_CYAN + "\n------------------------------------\nSe han guardado " + arrayNomCompl.length + " contactos");
        } catch (FileNotFoundException e) {
            //Mostrar mensaje de excepción si el archivo no existe
            System.err.println(ANSI_RED);
            e.printStackTrace();
            System.err.println(ANSI_RESET);
        } catch (IOException e) {
            //Mostrar mensaje de excepciónes generales
            System.err.println(ANSI_RED);
            e.printStackTrace();
            System.err.println(ANSI_RESET);
        }
    
    }   

    /**
     * Función para escribir un contacto en el archivo con longitudes fijas
     * 
     * @param rndmAF El archivo donde se guardarán los datos
     * @param nombre El nombre completo del contacto
     * @param correo El correo electrónico del contacto
     * @param telefono El teléfono del contacto
     * @param ciudad La ciudad del contacto
     * @param pais El país del contacto
     */
    private static void escribirContacto(RandomAccessFile rndmAF, String nombre, String correo, String telefono, String ciudad, String pais) throws IOException {
        // Convertir las cadenas a StringBuffer de longitud fija
        StringBuffer bufferNombre = new StringBuffer(nombre);
        bufferNombre.setLength(LONG_NOMBRECOMPLETO);

        StringBuffer bufferCorreo = new StringBuffer(correo);
        bufferCorreo.setLength(LONG_CORREO);

        StringBuffer bufferTelefono = new StringBuffer(telefono);
        bufferTelefono.setLength(LONG_TELEFONO);

        StringBuffer bufferCiudad = new StringBuffer(ciudad);
        bufferCiudad.setLength(LONG_CIUDAD);

        StringBuffer bufferPais = new StringBuffer(pais);
        bufferPais.setLength(LONG_PAIS);

        // Escribir los campos en el archivo binario
        rndmAF.writeChars(bufferNombre.toString());
        rndmAF.writeChars(bufferCorreo.toString());
        rndmAF.writeChars(bufferTelefono.toString());
        rndmAF.writeChars(bufferCiudad.toString());
        rndmAF.writeChars(bufferPais.toString());
    }
}
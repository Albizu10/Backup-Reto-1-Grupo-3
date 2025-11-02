import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que permite acceder aleatoriamente al archivo 'contactos.dat'
 * y modificar los datos del tercer contacto registrado en el fichero.
 * 
 * El usuario podrá decidir, campo por campo, qué datos desea actualizar,
 * y el programa validará que los formatos introducidos sean correctos.
 * 
 * @version 1.0
 */
public class ModificacionContacto3DAT {
    /**Longitud específica para cada campo*/
    private static final int LONG_NOMBRECOMPLETO = 35;
    private static final int LONG_CORREO = 35;
    private static final int LONG_TELEFONO = 15;
    private static final int LONG_CIUDAD = 25;
    private static final int LONG_PAIS = 20;

    /**Longitud total de un registro */
    private static final int LONG_REGISTRO = (LONG_NOMBRECOMPLETO + LONG_CORREO + LONG_TELEFONO + LONG_CIUDAD + LONG_PAIS) * 2;

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
     * Método principal del programa. Permite modificar los datos del tercer contacto
     * almacenado en el archivo 'contactos.dat'. El usuario podrá elegir qué campos desea
     * cambiar y se realizarán validaciones de formato antes de sobrescribir los datos.
     * 
     * @param args Argumentos de línea de comandos (no utilizados en este programa)
     */
    public static void main(String[] args) {
        //Ruta del archivo .dat para modificar el contacto
        File ruta = new File("contactos.dat");

        //Try with resources para cerrar automáticamente el RAC y el SC
        try (RandomAccessFile rndmAF = new RandomAccessFile(ruta, "rw"); Scanner sc = new Scanner(System.in)) {
            int id = 3; // tercer contacto
            long posicion = (id - 1) * LONG_REGISTRO; //calcular posición en bytes

            //Posixionarse en el registro que se desea modificar
            rndmAF.seek(posicion);

            //Mensaje inicial
            System.out.println(ANSI_CYAN + "\nMODIFICACIÓN DEL TERCER CONTACTO \n------------------------------------\n" + ANSI_RESET);

            // Comprobar si el archivo tiene al menos tres registros
            if (rndmAF.length() < posicion + LONG_REGISTRO) {
                System.out.println(ANSI_RED + "No existe un tercer contacto en el archivo para poder modificar.\n" + ANSI_RESET);
                return; //Salir del programa
            }

            //Definir los campos y las longitudes
            String[] campos = {"Nombre completo", "Correo electrónico", "Teléfono", "Ciudad", "País"};
            int[] longitudes = {LONG_NOMBRECOMPLETO, LONG_CORREO, LONG_TELEFONO, LONG_CIUDAD, LONG_PAIS};
            String[] valores = new String[campos.length];

            // Leer y mostrar los valores actuales del contacto
            leerYMostrarContacto(rndmAF, id, campos, longitudes, valores);

            System.out.println(ANSI_CYAN + "\nDecida qué datos desea cambiar. Responda 's' para sí y 'n' para no." + ANSI_RESET);

            //Pregunta si desea modificar campo por campo
            for (int i = 0; i < campos.length; i++) {
                String opcion;
                //Verificar si la respuesta del usuario es un 's' o 'n'
                do {
                    System.out.print(ANSI_MAGENTA + "\n¿Cambiar " + campos[i].toLowerCase() + "? (s/n): " + ANSI_RESET);
                    opcion = sc.nextLine().trim().toLowerCase();
                    if (!opcion.equals("s") && !opcion.equals("n")) {
                        System.out.println(ANSI_YELLOW + "\nEl dato introducido no es correcto, escriba 's' o 'n'" + ANSI_RESET);
                    }
                } while (!opcion.equals("s") && !opcion.equals("n"));

                //SÍ
                if (opcion.equals("s")) {
                    //Variable para guardar el nuevo valor
                    String nuevoValor;
                    do {
                        System.out.print(ANSI_MAGENTA + "\nNuevo " + campos[i].toLowerCase() + ": " + ANSI_RESET);
                        nuevoValor = sc.nextLine().trim();

                        if (nuevoValor.isEmpty()) {
                            System.out.println(ANSI_YELLOW + "El campo no puede estar vacío. Inténtelo de nuevo." + ANSI_RESET);
                        } else if (campos[i].equals("Correo electrónico") && !validarCorreo(nuevoValor)) {
                            System.out.println(ANSI_YELLOW + "\nFormato de correo no válido. Inténtelo de nuevo." + ANSI_RESET);
                        } else if (campos[i].equals("Teléfono") && !validarTelefono(nuevoValor)) {
                            System.out.println(ANSI_YELLOW + "\nFormato de teléfono no válido. Inténtelo de nuevo." + ANSI_RESET);
                        } else {
                            valores[i] = nuevoValor;
                            break; // salir del bucle solo si todo está bien
                        }
                    } while (true);
                } else {
                //NO    
                    System.out.println(ANSI_CYAN + "\nNo se modificará el campo '" + campos[i].toLowerCase() + "'" + ANSI_RESET);
                }
            }

            // Reposicionar el puntero al inicio del registro
            rndmAF.seek(posicion);

            // Escribir los valores actualizados
            for (int i = 0; i < campos.length; i++) {
                rndmAF.writeChars(ajustarString(valores[i], longitudes[i]));
            }
            
            System.out.println(ANSI_GREEN + "\n------------------------------------\nContacto actualizado correctamente. \n" + ANSI_RESET);

            // Reposicionar el puntero al inicio del registro antes de leer los datos modificados
            rndmAF.seek(posicion);

            leerYMostrarContacto(rndmAF, id, campos, longitudes, valores);

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
     * Función para leer y mostrar los datos del contacto actual
     * 
     * @param rndmAF Random Acces File
     * @param id ID del contacto que se desea leer y mostrar
     * @param campos Campos del contacto
     * @param longitudes Longitud de cada dato
     * @param valores Valores del contacto
     * @throws IOException
     */
    public static void leerYMostrarContacto(RandomAccessFile rndmAF, int id, String[] campos, int[] longitudes, String[] valores) throws IOException {
        //Leer los valores actuales
        for (int i = 0; i < campos.length; i++) {
            char[] buffer = new char[longitudes[i]];
            for (int j = 0; j < longitudes[i]; j++) {
                buffer[j] = rndmAF.readChar();
            }
            valores[i] = new String(buffer).trim();
        }

        // Mostrar los valores actuales
        System.out.println("Datos actuales del contacto " + id + ":\n------------------------------------\n");
        for (int i = 0; i < campos.length; i++) {
            System.out.println(campos[i] + ": " + valores[i]);
        }
    }

    /**
     * Función para ajustar la longitud de los valores del contacto
     * 
     * @param texto Dato que introduce el usuario
     * @param longitud La longitud que deseamos que tenga cada valor
     * @return Devuelve el texto con la longitud deseada
     */
    private static String ajustarString(String texto, int longitud) {
        StringBuilder sb = new StringBuilder(texto);
        if (sb.length() > longitud) {
            sb.setLength(longitud);
        } else {
            while (sb.length() < longitud) {
                sb.append(' '); // rellena con espacios si es más corto
            }
        }
        return sb.toString();
    }

    /**
     * Función para validar que el formato del correo electrónico es correcto mediante una expresión regular
     *
     * @param correo Dato que introduce el usuario
     * @return Si el formato es correcto devuelve true
     */
    public static Boolean validarCorreo(String correo) {
        //Definir el patrón: números / letras / guiones + @ + números / letras + . + 2-6 letras
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        //Verificar que el dato introducido por el usuario coincide con el patrón
        Matcher matcher = pattern.matcher(correo);
        //Devolver el resultado (true si cumple el patrón)
        return matcher.matches();
    }

    /**
     * Función para validar que el formato del teléfono es correcto mediante una expresión regular
     * 
     * @param telefono Dato que introduce el usuario
     * @return Si el formato es correcto devuelve true
     */
    public static boolean validarTelefono(String telefono) {
        //Permitir +, dígitos, espacios y guiones
        Pattern pattern = Pattern.compile("^[+]?[0-9\\-\\s]+$");
        //Verificar que el dato introducido por el usuario coincide con el patrón
        Matcher matcher = pattern.matcher(telefono);
        //Devolver el resultado (true si el teléfono cumple el patrón)
        return matcher.matches();
    }   
}
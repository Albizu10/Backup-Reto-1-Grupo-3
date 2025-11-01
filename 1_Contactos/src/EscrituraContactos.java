import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

/**
 * Clase que permite escribir nuevos contactos en un archivo CSV, en este caso, 'contactos.csv'.
 * Permite escribir más de un contacto a la vez y contiene validaciones de correo y teléfono.
 * 
 * @version 1.0
 */
public class EscrituraContactos {
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
     * y guardarlos en un archivo CSV, con validaciones de correo y teléfono.
     * @param args
     */
    public static void main(String[] args) {
        //Definir ruta del archivo donde se guardan los contactos
        File ruta = new File("contactos.csv");

        //Try with resources para que los recursos se cierren automaticamente
        try (FileWriter fr = new FileWriter(ruta, true)) {
            //Inicializar Buffered Writer
            BufferedWriter bw = new BufferedWriter(fr);

            //Inicializar Scanner
            Scanner sc = new Scanner(System.in);

            //Inicializar variables
            String nombre, apellido, correo, telefono, ciudad, pais;
            boolean seguirEscribiendo = true;

            //Mensaje inicial
            System.out.println(ANSI_CYAN + "\nESCRITURA DE NUEVOS CONTACTOS \n------------------------------------\n" + ANSI_RESET);

            //Bucle para escribir contactos hasta que el usuario decida salie
            while (seguirEscribiendo == true) {
                //Menu
                System.out.println(ANSI_CYAN + "1) Escribir \n2) Salir\n" + ANSI_RESET);

                try {
                    //Leer opción del usuario
                    int opcion = sc.nextInt();
                    sc.nextLine(); //Limpiar tras leer un int

                    
                    if (opcion == 1) {
                    //1) ESCRIBIR
                        //Solicitar datos de contacto y verificar que no están vacios
                        nombre = solicitarDato(sc, "Nombre");

                        apellido = solicitarDato(sc, "Apellido");

                        //Solicitar y validar correo electrónico hasta introducir un formato correcto
                        do {
                            System.out.println(ANSI_MAGENTA + "\nCorreo electrónico: " + ANSI_RESET);
                            correo = sc.nextLine();
                            if (correo.isEmpty()) {
                                System.out.println(ANSI_YELLOW + "El correo no puede estar vacío. Vuelva a intentarlo." + ANSI_RESET);
                            } else if (!validarCorreo(correo)) {
                                System.out.println(ANSI_YELLOW + "\nEl formato del correo electrónico introducido no es correcto, vuelva a escribirlo" + ANSI_RESET);
                            }
                        } while (correo.isEmpty() || !validarCorreo(correo));

                        //Solicitar y validar teléfono hasta introducir un formato correcto
                        do {
                            System.out.println(ANSI_MAGENTA + "\nTeléfono: " + ANSI_RESET);
                            telefono = sc.nextLine();
                            if (telefono.isEmpty()) {
                                System.out.println(ANSI_YELLOW + "El teléfono no puede estar vacío. Vuelva a intentarlo." + ANSI_RESET);
                            } else if (!validarTelefono(telefono)) {
                                System.out.println(ANSI_YELLOW + "\nEl formato del teléfono introducido no es correcto. Use solo dígitos, espacios, guiones o el símbolo '+'\n" + ANSI_RESET);
                            }
                        } while (telefono.isEmpty() || !validarTelefono(telefono));

                        ciudad = solicitarDato(sc, "Ciudad");

                        pais = solicitarDato(sc, "Pais");

                        //Escribir datos en el archivo CSV siguiendo la estructura (comillas y comas)
                        bw.write("\n\"" + nombre + " " + apellido + "\",\"" + correo.toLowerCase() + "\",\"" + telefono + "\",\"" + ciudad + "\",\"" + pais + "\"");
                        bw.flush(); //Para escribir inmediatamente

                        //Mensaje de confirmación para el usuario
                        System.out.println(ANSI_GREEN + "\n---------------------------------------------------------------------\n\nEscribiendo los datos del contacto: " + nombre + " " + apellido + " \n\n" + ANSI_RESET);
                    } else if (opcion == 2) {
                    //2) SALIR
                        //Salir del programa e informar al usuario
                        seguirEscribiendo = false;
                        System.out.println(
                                ANSI_CYAN + "\n------------------------------------\nSaliendo...\n" + ANSI_RESET);
                    } else {
                    //NO 1 / 2
                        //Informar al usuario que debe introducir el número 1 o 2
                        System.out.println(ANSI_YELLOW + "\nEl número introducido no es correcto, escriba 1 o 2 para indicar la acción que quiere realizar\n" + ANSI_RESET);
                    }
                } catch (InputMismatchException e) {
                //NO NÚMERO
                    //Informar al usuario que debe introducir un número
                    System.out.println(ANSI_YELLOW + "\nEl formato de dato introducido no es correcto, escriba un número, por favor\n"+ ANSI_RESET);
                    sc.nextLine(); //Limpiar para evitar bucles infinitos
                }
            }
            sc.close();
            //File Writer y BufferedWriter se cierran automáticamente
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
     * Función para solicitar un dato y asegurarse de que no está vacío.
     * 
     * @param sc Scanner para leer la entrada del usuario
     * @param campo Nombre del campo que se está solicitando
     * @return El valor introducido por el usuario
     */
    private static String solicitarDato(Scanner sc, String campo) {
        //Variable para guardar el dato que introduce el usuario
        String dato;
        
        //Solicita el dato hasta que el usuario lo introduzca
        do {
            System.out.println(ANSI_MAGENTA + "\n" + campo + ": " + ANSI_RESET);
            dato = sc.nextLine();
            if (dato.isEmpty()) {
                System.out.println(ANSI_YELLOW + "El " + campo.toLowerCase() + " no puede estar vacío, vuelva a intentarlo." + ANSI_RESET);
            }
        } while (dato.isEmpty());

        //Devuelve el dato
        return dato;
    }    

    /**
     * Función para validar que el formato del correo electrónico es correcto mediante una expresión regular
     *
     * @param correo Dato que introduce el usuario
     * @return Si el formato es correcto devuelve true
     */
    private static Boolean validarCorreo(String correo) {
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
    private static boolean validarTelefono(String telefono) {
        //Permitir +, dígitos, espacios y guiones
        Pattern pattern = Pattern.compile("^[+]?[0-9\\-\\s]+$");
        //Verificar que el dato introducido por el usuario coincide con el patrón
        Matcher matcher = pattern.matcher(telefono);
        //Devolver el resultado (true si el teléfono cumple el patrón)
        return matcher.matches();
    }    
}

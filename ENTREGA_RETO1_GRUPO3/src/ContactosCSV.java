import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que permite leer o escribir contactos en un archivo CSV, en este caso, 'contactos.csv'
 * Combina la lectura y la escritura en un solo programa con un menú principal.
 * @version 1.0
 */
public class ContactosCSV {
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
     * Método principal del programa. 
     * Permite al usuario leer o escribir contactos de un CSV, o salir del programa.
     * @param args
     */
    public static void main(String[] args) {
        //Inicializar Scanner
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        //MENÚ PRINCIPAL
        while (!salir) {
            System.out.println(ANSI_CYAN + "\nMENÚ PRINCIPAL\n------------------------------------");
            System.out.println("1) Leer contactos");
            System.out.println("2) Escribir contactos");
            System.out.println("3) Salir"  + ANSI_RESET);
            System.out.print(ANSI_MAGENTA + "\nSeleccione una opción: " + ANSI_RESET);

            try {
                int opcion = sc.nextInt();
                sc.nextLine(); //Limpiar el buffer

                if (opcion == 1) {
                    //1) LEER CONTACTOS
                    leerContactos();
                } else if (opcion == 2) {
                    //2) ESCRIBIR CONTACTOS
                    escribirContactos(sc);
                } else if (opcion == 3) {
                    //3) SALIR
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
     * Método para LEER CONTACTOS desde el archivo CSV
     */
    public static void leerContactos() {   
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
                if (campos.length == 5) {
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

            System.out.println(ANSI_CYAN + "-----------------------------------\nLectura finalizada" + ANSI_RESET);
            //FileReader y Buffered Reader se cierran automáticamente
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
     * Método para ESCRIBIR CONTACTOS en el archivo CSV
     */
    public static void escribirContactos(Scanner sc) {
        //Definir ruta del archivo donde se guardan los contactos
        File ruta = new File("contactos.csv");

        //Try with resources para que los recursos se cierren automaticamente
        try (FileWriter fr = new FileWriter(ruta, true)) {
            //Inicializar Buffered Writer
            BufferedWriter bw = new BufferedWriter(fr);

            //Inicializar variables
            String nombre, apellido, correo, telefono, ciudad, pais;
            boolean seguirEscribiendo = true;

            //Mensaje inicial
            System.out.println(ANSI_CYAN + "\nESCRITURA DE NUEVOS CONTACTOS \n------------------------------------\n" + ANSI_RESET);

            //Bucle para escribir contactos hasta que el usuario decida salir
            while (seguirEscribiendo == true) {
                //Menu
                System.out.println(ANSI_CYAN + "1) Escribir \n2) Volver al menú principal\n" + ANSI_RESET);

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
                                ANSI_CYAN + "\n------------------------------------\nVolviendo al menú principal...\n" + ANSI_RESET);
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
    public static String solicitarDato(Scanner sc, String campo) {
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

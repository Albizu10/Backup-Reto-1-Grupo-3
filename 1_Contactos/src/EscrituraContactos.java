import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

public class EscrituraContactos {
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
        //Try catch para manejar escepciones
        try {
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
                            //Solicitar datos de contacto
                            System.out.println(ANSI_MAGENTA + "\nNombre: " + ANSI_RESET);
                            nombre = sc.nextLine();

                            System.out.println(ANSI_MAGENTA + "\nApellido: " + ANSI_RESET);
                            apellido = sc.nextLine();

                            //Solicitar y validar correo electrónico hasta introducir un formato correcto
                            do {
                                System.out.println(ANSI_MAGENTA + "\nCorreo electrónico: " + ANSI_RESET);
                                correo = sc.nextLine();
                                if (!validarCorreo(correo)) {
                                    System.out.println(ANSI_YELLOW + "\nEl formato del correo electrónico introducido no es correcto, vuelva a escribirlo" + ANSI_RESET);
                                }
                            } while (!validarCorreo(correo));

                            //Solicitar y validar teléfono hasta introducir un formato correcto
                            do {
                                System.out.println(ANSI_MAGENTA + "\nTeléfono: " + ANSI_RESET);
                                telefono = sc.nextLine();
                                if (!validarTelefono(telefono)) {
                                    System.out.println(ANSI_YELLOW + "\nEl formato del teléfono introducido no es correcto. Use solo dígitos, espacios, guiones o el símbolo '+'\n" + ANSI_RESET);
                                }
                            } while (!validarTelefono(telefono));

                            System.out.println(ANSI_MAGENTA + "\nCiudad: " + ANSI_RESET);
                            ciudad = sc.nextLine();

                            System.out.println(ANSI_MAGENTA + "\nPaís:" + ANSI_RESET);
                            pais = sc.nextLine();

                            //Escribir datos en el archivo CSV siguiendo la estructura (comillas y comas)
                            bw.write("\n\"" + nombre + " " + apellido + "\",\"" + correo.toLowerCase() + "\",\"" + telefono + "\",\"" + ciudad + "\",\"" + pais + "\"");

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
                //Se cierran File Writer,  BufferedWriter y Scanner    
            }

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

    //Función para validar que el formato del correo electrónico es correcto
    private static Boolean validarCorreo(String correo) {
        //Definir el patrón: números / letras / guiones + @ + números / letras + . + 2-6 letras
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        //Verificar que el dato introducido por el usuario coincide con el patrón
        Matcher matcher = pattern.matcher(correo);
        //Devolver el resultado (true si cumple el patrón)
        return matcher.matches();
    }

    //Función para validar que el formato del teléfono es correcto
    private static boolean validarTelefono(String telefono) {
        //Permitir +, dígitos, espacios y guiones
        Pattern pattern = Pattern.compile("^[+]?[0-9\\-\\s]+$");
        //Verificar que el dato introducido por el usuario coincide con el patrón
        Matcher matcher = pattern.matcher(telefono);
        //Devolver el resultado (true si el teléfono cumple el patrón)
        return matcher.matches();
    }    
}

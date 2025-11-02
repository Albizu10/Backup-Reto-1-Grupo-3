import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Clase que permite añadir nuevos contactos manualmente al archivo JSON 'contactos.json'.
 * Incluye validación de correo electrónico y mantiene los datos en local.
 * 
 * @version 1.0
 */
public class NuevoContactoJSON {
    /**Código ANSI para restablecer el color de la terminal */
    public static final String ANSI_RESET = "\u001B[0m";
    /**Código ANSI para imprimir los errores en color rojo en la terminal */
    public static final String ANSI_RED = "\u001B[31m";
    /**Código ANSI para imprimir los resultados en color verde en la terminal */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**Código ANSI para indicar avisos o advertencias en color amarillo en la terminal */
    public static final String ANSI_YELLOW = "\u001B[33m";
    /**Código ANSI para imprimir los menús en color cyan en la terminal */
    public static final String ANSI_CYAN = "\u001B[36m";
    /**Código ANSI para indicar qué datos debe introducir el usuario en color magenta */
    public static final String ANSI_MAGENTA = "\u001B[35m";

    /**
     * Método principal del programa. Permite escribir nuevos contactos en el fichero JSON.
     * @param args
     */
    public static void main(String[] args) {
        //Ruta del archivo JSON para guardar los contactos
        File ruta = new File("contactos.json");
        //Creación del Scanner para leer los datos que introduce el usuario
        Scanner sc = new Scanner(System.in);

        //Array JSON para almacenar los contactos
        JsonArray contactos = new JsonArray();

        //Leer el archivo existente si ya existe
        if (ruta.exists()) { 
            //Abrir el archivo para lectura
            try (FileReader fr = new FileReader(ruta)) { 
                //Parsea el contenido a formato JSON
                JsonElement jsonElem = JsonParser.parseReader(fr); 
                //Si es un array
                if (jsonElem.isJsonArray()) {
                    //Guarda los contactos existentes
                    contactos = jsonElem.getAsJsonArray(); 
                }
            } catch (IOException e) {
                System.err.println(ANSI_RED);
                e.printStackTrace();
                System.err.println(ANSI_RESET);
            }
        }

        System.out.println(ANSI_CYAN + "\nESCRITURA DE NUEVOS CONTACTOS \n------------------------------------\n" + ANSI_RESET);

        //Variable para controlar el bucle del menú
        boolean seguirEscribiendo = true;

        //Bucle principal del menú
        while (seguirEscribiendo) {
            //Opciones del menú
            System.out.println(ANSI_CYAN + "1) Escribir nuevo contacto \n2) Salir\n" + ANSI_RESET);
            try {
                //Lee la opción del usuario
                int opcion = sc.nextInt();
                sc.nextLine();

                //OPCIÓN 1: Escribir nuevo contacto
                if (opcion == 1) {
                    //Variables de los datos de contacto
                    String nombre, correo, telefono, ciudad, pais;

                    //Solicita los datos usando, verifica que no esté vacío mediante la función
                    nombre = solicitarDato(sc, "Nombre completo");

                    //Validar formato de correo electrónico
                    do {
                        correo = solicitarDato(sc, "Correo electrónico");
                        if (!validarCorreo(correo)) {
                            System.out.println(ANSI_YELLOW + "Formato de correo no válido. Vuelva a intentarlo.\n" + ANSI_RESET);
                        }
                    } while (!validarCorreo(correo));

                    //Validar formato de teléfono
                    do {
                        telefono = solicitarDato(sc, "Teléfono");
                        if (!validarTelefono(telefono)) {
                            System.out.println(ANSI_YELLOW + "Formato de teléfono no válido. Solo se permiten dígitos, espacios, guiones o '+'.\n" + ANSI_RESET);
                        }
                    } while (!validarTelefono(telefono));

                    ciudad = solicitarDato(sc, "Ciudad");
                    pais = solicitarDato(sc, "País");

                    //Generar automáticamente el nuevo ID
                    int nuevoID = generarNuevoID(contactos);

                    //Crear nuevo objeto JSON con los datos del contacto
                    JsonObject contacto = new JsonObject();
                    contacto.addProperty("ID", nuevoID);
                    contacto.addProperty("nombre completo", nombre);
                    contacto.addProperty("correo", correo);
                    contacto.addProperty("telefono", telefono);
                    contacto.addProperty("ciudad", ciudad);
                    contacto.addProperty("pais", pais);

                    //Añadir el nuevo contacto al array
                    contactos.add(contacto);

                    //Guardar en el archivo usando Gson
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    //Abrir el archivo para escritura
                    try (FileWriter fw = new FileWriter(ruta)) { 
                        //Escribe el contenido en formato JSON
                        gson.toJson(contactos, fw); 
                        System.out.println(ANSI_GREEN + "\n------------------------------------\nContacto añadido correctamente: " + nombre + "\n" + ANSI_RESET);
                    } catch (IOException e) { 
                        //Gestión de excepciones
                        System.err.println(ANSI_RED);
                        e.printStackTrace();
                        System.err.println(ANSI_RESET);
                    }
                } else if (opcion == 2) { 
                    //SALIR
                    seguirEscribiendo = false;
                    System.out.println(ANSI_CYAN + "\nSaliendo del programa...\n" + ANSI_RESET);
                } else {
                    //Opción no válida
                    System.out.println(ANSI_YELLOW + "Debe elegir 1 o 2.\n" + ANSI_RESET);
                }

            } catch (InputMismatchException e) { 
                //Si introduce algo que no sea número
                System.err.println(ANSI_RED);
                e.printStackTrace();
                System.err.println(ANSI_RESET);
            }
        }
        //Cerrar el Scanner
        sc.close();
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
        } while (dato.isEmpty()); //Repite mientras esté vacío

        //Devuelve el dato
        return dato;
    }

    /**
     * Función para validar que el formato del correo electrónico es correcto mediante una expresión regular.
     *
     * @param correo Dato que introduce el usuario
     * @return Si el formato es correcto devuelve true
     */
    public static Boolean validarCorreo(String correo) {
        //Definir el patrón: números / letras / guiones + @ + dominio + . + 2-6 letras
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        //Verificar que el dato introducido por el usuario coincide con el patrón
        Matcher matcher = pattern.matcher(correo);
        //Devolver el resultado (true si cumple el patrón)
        return matcher.matches();
    }

    /**
     * Función para validar que el formato del teléfono es correcto mediante una expresión regular.
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

    /**
     * Función para generar automáticamente el nuevo ID basado en el último registro del array JSON.
     * 
     * @param contactos Array JSON con los contactos existentes
     * @return El siguiente ID (último ID + 1)
     */
    public static int generarNuevoID(JsonArray contactos) {
        //Si no hay contactos, el primer ID será 1
        if (contactos.size() == 0) {
            return 1;
        } else {
            //Obtener el último elemento del array
            JsonObject ultimoContacto = contactos.get(contactos.size() - 1).getAsJsonObject();
            //Extraer el valor del campo "ID" y sumarle 1
            int ultimoID = ultimoContacto.get("ID").getAsInt();
            return ultimoID + 1;
        }
    }
}
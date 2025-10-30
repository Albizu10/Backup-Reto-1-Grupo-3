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

public class NuevoContactoJSON {
    public static String ANSI_BLUE = "\u001B[34m";
    public static String ANSI_RED = "\u001B[33m";
    public static String ANSI_GREEN = "\u001B[32m";
    public static String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        File ruta = new File("contactos.json");
        Scanner sc = new Scanner(System.in);

        JsonArray contactos = new JsonArray();

        if(ruta.exists()) {
            try (FileReader fr = new FileReader(ruta)) {
                JsonElement jsonElem = JsonParser.parseReader(fr);
                if (jsonElem.isJsonArray()) {
                    contactos = jsonElem.getAsJsonArray();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boolean seguirEscribiendo = true;

        System.out.println(ANSI_BLUE + "\nESCRITURA DE NUEVOS CONTACTOS \n------------------------------------\n"
                            + ANSI_RESET);


        // ID-A GEHITZEKO BALIDAZIOA TXERTATU
                        

        while (seguirEscribiendo == true) {
            System.out.println(ANSI_BLUE + "1) Escribir \n2) Salir\n" + ANSI_RESET);

            try {
                int opcion = sc.nextInt();
                sc.nextLine();

                if (opcion == 1) {
                    String id, nombre, apellido, correo, telefono, ciudad, pais;

                    System.out.println("\nID");
                    id = sc.nextLine();

                    System.out.println("\nNombre: ");
                    nombre = sc.nextLine();

                    System.out.println("\nApellido: ");
                    apellido = sc.nextLine();

                    do {
                        System.out.println("\nCorreo electrónico: ");
                        correo = sc.nextLine();
                        if (!validarEmail(correo)) {
                            System.out.println(ANSI_RED
                                    + "\nEl formato del correo electrónico introducido no es correcto, vuelva a escribirlo" + ANSI_RESET);
                        }
                    } while (!validarEmail(correo));

                    System.out.println("\nTeléfono: ");
                    telefono = sc.nextLine();

                    System.out.println("\nCiudad: ");
                    ciudad = sc.nextLine();

                    System.out.println("\nPaís:");
                    pais = sc.nextLine();


                    JsonObject contacto = new JsonObject();
                    contacto.addProperty("ID", id);
                    contacto.addProperty("nombre completo", nombre);
                    contacto.addProperty("correo", correo);
                    contacto.addProperty("telefono", telefono);
                    contacto.addProperty("ciudad", ciudad);
                    contacto.addProperty("pais", pais);
                
                    contactos.add(contacto);

                    Gson gson = new GsonBuilder().setPrettyPrinting().create();

                    try (FileWriter fw = new FileWriter(ruta)) {
                        gson.toJson(contactos, fw);
                        System.out.println(ANSI_GREEN
                            + "\n---------------------------------------------------------------------\n\nEscribiendo los datos del contacto: "
                            + nombre + " " + apellido
                            + " \n\n"
                            + ANSI_RESET);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (opcion == 2) {
                    seguirEscribiendo = false;
                    System.out.println(
                            ANSI_BLUE + "\n------------------------------------\nSaliendo...\n" + ANSI_RESET);
                } else {
                    System.out.println(
                            ANSI_RED + "\nEl número introducido no es correcto, escriba 1 o 2 para indicar la acción que quiere realizar\n"
                                    + ANSI_RESET);
                }

                sc.close();

            } catch (InputMismatchException e) {
                System.out.println(
                        ANSI_RED + "\nEl formato de dato introducido no es correcto, escriba un número, por favor\n"
                                + ANSI_RESET);
                sc.nextLine();
            }
        }
    }

    private static Boolean validarEmail(String correo) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
}

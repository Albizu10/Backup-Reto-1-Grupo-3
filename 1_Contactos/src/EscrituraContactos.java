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
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static Boolean validarEmail(String correo) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    public static void main(String[] args) {
        try {
            File ruta = new File("contactos.csv");
            FileWriter fr = new FileWriter(ruta, true);
            BufferedWriter bfw = new BufferedWriter(fr);

            Scanner sc = new Scanner(System.in);

            String nombre, apellido, correo, ciudad, pais;
            int telefono;
            boolean seguirEscribiendo = true;

            System.out.println(ANSI_BLUE + "\nESCRITURA DE NUEVOS CONTACTOS \n------------------------------------\n"
                    + ANSI_RESET);

            while (seguirEscribiendo == true) {
                System.out.println(ANSI_BLUE + "1) Escribir \n2) Salir\n" + ANSI_RESET);

                try {
                    int opcion = sc.nextInt();
                    sc.nextLine();

                    if (opcion == 1) {
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
                        telefono = sc.nextInt();
                        sc.nextLine();

                        System.out.println("\nCiudad: ");
                        ciudad = sc.nextLine();

                        System.out.println("\nPaís:");
                        pais = sc.nextLine();

                        bfw.write("\n\"" + nombre + " " + apellido + "\",\"" + correo + "\",\"" + telefono + "\",\""
                                + ciudad + "\",\"" + pais + "\"");

                        System.out.println(ANSI_GREEN
                                + "\n----------------------------------------------------\n\nLa escritura del contacto "
                                + nombre + " " + apellido
                                + "se ha realizado correctamente. \n\n----------------------------------------------------\n"
                                + ANSI_RESET);
                    } else if (opcion == 2) {
                        seguirEscribiendo = false;
                        System.out.println(
                                ANSI_BLUE + "\n------------------------------------\nSaliendo...\n" + ANSI_RESET);
                    } else {
                        System.out.println(
                                ANSI_RED + "\nEl número introducido no es correcto, escriba 1 o 2 para indicar la acción que quiere realizar\n"
                                        + ANSI_RESET);
                    }
                } catch (InputMismatchException e) {
                    System.out.println(
                            ANSI_RED + "\nEl formato de dato introducido no es correcto, escriba un número, por favor\n"
                                    + ANSI_RESET);
                    sc.nextLine();
                }
            }
            bfw.close();
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
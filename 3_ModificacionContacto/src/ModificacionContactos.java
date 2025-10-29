import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModificacionContactos {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    private static final int LONG_NOMBRECOMPLETO = 45;
    private static final int LONG_CORREO = 45;
    private static final int LONG_TELEFONO = 15;
    private static final int LONG_CIUDAD = 25;
    private static final int LONG_PAIS = 25;
    private static final int LONG_REGISTRO = (LONG_NOMBRECOMPLETO + LONG_CORREO + LONG_TELEFONO + LONG_CIUDAD + LONG_PAIS) * 2;

    public static void main(String[] args) {
        try {
            File ruta = new File("contactosDAT.dat");
            Scanner sc = new Scanner(System.in);

            boolean seguir = true;

            try (RandomAccessFile rndmAF = new RandomAccessFile(ruta, "rw")) {
                int id = 3;
                long posicion = (id - 1) * LONG_REGISTRO;

                rndmAF.seek(posicion);

                System.out.println(ANSI_BLUE + "\nESCRITURA RANDOM DE NUEVOS CONTACTOS \n------------------------------------\n" + ANSI_RESET);

                while (seguir) {

                    System.out.println(ANSI_BLUE + "1) Escribir \n2) Guardar y salir\n" + ANSI_RESET);

                    try {
                        int opcion = sc.nextInt();
                        sc.nextLine();

                        if (opcion == 1) {
                            System.out.print("Nombre completo: ");
                            String nombreCompleto = sc.nextLine();

                            String correo;
                            do {
                                System.out.print("Correo electrónico: ");
                                correo = sc.nextLine();
                                if (!validarEmail(correo)) {
                                    System.out.println(
                                            ANSI_RED + "Formato de correo no válido. Inténtelo de nuevo." + ANSI_RESET);
                                }
                            } while (!validarEmail(correo));

                            System.out.print("Teléfono: ");
                            String telefono = sc.nextLine();

                            System.out.print("Ciudad: ");
                            String ciudad = sc.nextLine();

                            System.out.print("País: ");
                            String pais = sc.nextLine();

                            escribirContacto(rndmAF, nombreCompleto, correo, telefono, ciudad, pais);

                            System.out.println(ANSI_GREEN +
                                    "\n----------------------------------------------------\n" +
                                    "El contacto '" + nombreCompleto + "' se ha guardado correctamente.\n" + ANSI_RESET);

                        } else if (opcion == 2) {
                            seguir = false;
                            System.out.println(ANSI_BLUE + "\n------------------------------------\nSaliendo..." + ANSI_RESET);
                        } else {
                            System.out.println(ANSI_RED + "Por favor, elija 1 o 2." + ANSI_RESET);
                        }

                    } catch (InputMismatchException e) {
                        System.out.println(ANSI_RED + "Error: Debe introducir un número." + ANSI_RESET);
                        sc.nextLine();
                    }
                }
                sc.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Boolean validarEmail(String correo) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    private static String ajustarString(String texto, int longitud) {
        StringBuilder sb = new StringBuilder(texto);
        sb.setLength(longitud);
        return sb.toString();
    }

    private static void escribirContacto(RandomAccessFile rndmAF, String nombreCompleto, String correo, String telefono, String ciudad, String pais) throws IOException {
        rndmAF.writeChars(ajustarString(nombreCompleto, LONG_NOMBRECOMPLETO));
        rndmAF.writeChars(ajustarString(correo, LONG_CORREO));
        rndmAF.writeChars(ajustarString(telefono, LONG_TELEFONO));
        rndmAF.writeChars(ajustarString(ciudad, LONG_CIUDAD));
        rndmAF.writeChars(ajustarString(pais, LONG_PAIS));
    }
}

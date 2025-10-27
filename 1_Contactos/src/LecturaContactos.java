import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LecturaContactos {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        try {
            File ruta = new File("contactos.csv");
            FileReader fr = new FileReader(ruta);
            BufferedReader bfr = new BufferedReader(fr);

            String linea;

            // Saltar la primera línea porque es el encabezado
            bfr.readLine();

            System.out.println(ANSI_BLUE + "\nLEYENDO CONTACTOS DEL FICHERO '" + ruta + "'" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "----------------------------------------------\n" + ANSI_RESET);

            while ((linea = bfr.readLine()) != null) {
                String lineaSinSeparador = linea.replace("\"", "");

                String[] campos = lineaSinSeparador.split(",");

                if (campos.length >= 5) {
                    String nombreCompleto = campos[0];
                    String correo = campos[1];
                    String telefono = campos[2];
                    String ciudad = campos[3];
                    String pais = campos[4];

                    System.out.println("Nombre completo: " + nombreCompleto);
                    System.out.println("Correo: " + correo);
                    System.out.println("Teléfono: " + telefono);
                    System.out.println("Ciudad: " + ciudad);
                    System.out.println("País: " + pais);
                    System.out.println(ANSI_GREEN + "\n-----------------------------------\n" + ANSI_RESET);

                } else {
                    System.out.println(ANSI_RED + "Línea con formato incorrecto: \n" + linea + ANSI_RESET);
                    System.out.println(ANSI_RED + "\n-----------------------------------\n" + ANSI_RESET);
                }

            }
            bfr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EscrituraPresupuesto {
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        try {
            File rutaLectura = new File("Presupuesto - S00007.pdf");
            File rutaEscritura = new File("Presupuesto - S00007_copia.pdf");

            System.out.println(
                    ANSI_BLUE + "\nCOPIANDO FICHERO '" + rutaLectura + "' A '" + rutaEscritura + "'" + ANSI_RESET);
            System.out.println(ANSI_BLUE
                    + "\n------------------------------------------------------------------------------------\n"
                    + ANSI_RESET);

            System.out.println(ANSI_YELLOW + "Contenido que se escribirá en la copia del fichero '" + rutaLectura + "':"
                    + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "\n------------------------------------------------------------------------------------\n" + ANSI_RESET);

            try (FileInputStream filein = new FileInputStream(rutaLectura)) {
                int byteLeido;
                while ((byteLeido = filein.read()) != -1) {
                    System.out.printf("%02X ", byteLeido);
                }

                filein.close();
            }

            try (FileInputStream filein = new FileInputStream(rutaLectura);
                    FileOutputStream fileout = new FileOutputStream(rutaEscritura)) {

                int byteLeido;
                while ((byteLeido = filein.read()) != -1) {
                    fileout.write(byteLeido);
                }
                filein.close();
                fileout.close();

                System.out.println(ANSI_GREEN
                    + "\n------------------------------------------------------------------------------------\n\nLa copia del presupuesto '"
                    + rutaLectura + "' se ha realizado correctamente." + ANSI_RESET);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
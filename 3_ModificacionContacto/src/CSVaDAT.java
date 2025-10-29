import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CSVaDAT {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    // Longitudes fijas
    private static final int LONG_NOMBRECOMPLETO = 45;
    private static final int LONG_CORREO = 45;
    private static final int LONG_TELEFONO = 15;
    private static final int LONG_CIUDAD = 25;
    private static final int LONG_PAIS = 25;

    public static void main(String[] args) {
        File rutaCSV = new File("contactos.csv");
        File rutaDAT = new File("contactosDAT.dat");

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV));
             RandomAccessFile raf = new RandomAccessFile(rutaDAT, "rw")) {

            System.out.println(ANSI_BLUE + "\nCONVERSIÓN DE CSV A DAT\n--------------------------------------------\n" + ANSI_RESET);
            String linea;
            int cont = 0;

            // Saltar la cabecera
            br.readLine();

            while ((linea = br.readLine()) != null) {
                // Eliminamos las comillas y dividimos por coma
                String[] campos = linea.replace("\"\"", "[sin registro]").replace("\"", "").split(",");

                if (campos.length >= 5) {
                    String nombreCompleto = campos[0].trim();
                    String correo = campos[1].trim();
                    String telefono = campos[2].trim();
                    String ciudad = campos[3].trim();
                    String pais = campos[4].trim();

                    escribirContacto(raf, nombreCompleto, correo, telefono, ciudad, pais);

                    cont++;
                } else {
                    System.out.println(ANSI_RED + "Línea con formato incorrecto: " + linea + ANSI_RESET);
                }
            }

            System.out.println("Total contactos copiados: " + cont);

            System.out.println(ANSI_GREEN + "\n--------------------------------------------" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "Copia finalizada." + ANSI_RESET);

        } catch (FileNotFoundException e) {
            System.out.println(ANSI_RED + "No se encontró el archivo CSV: " + rutaCSV + ANSI_RESET);
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error al leer o escribir el archivo." + ANSI_RESET);
            e.printStackTrace();
        }
    }

    private static void escribirContacto(RandomAccessFile raf, String nombre, String correo, String telefono, String ciudad, String pais) throws IOException {
        raf.writeChars(ajustarString(nombre, LONG_NOMBRECOMPLETO));
        raf.writeChars(ajustarString(correo, LONG_CORREO));
        raf.writeChars(ajustarString(telefono, LONG_TELEFONO));
        raf.writeChars(ajustarString(ciudad, LONG_CIUDAD));
        raf.writeChars(ajustarString(pais, LONG_PAIS));
    }

    // Ajustar longitud de cadena (rellena o corta)
    private static String ajustarString(String texto, int longitud) {
        StringBuilder sb = new StringBuilder(texto);
        sb.setLength(longitud);
        return sb.toString();
    }
}

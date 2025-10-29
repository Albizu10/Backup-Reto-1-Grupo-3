import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LecturaPresupuesto {
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        try {
            File ruta = new File("Presupuesto - S00007.pdf");
            FileInputStream filein = new FileInputStream(ruta);

            System.out.println(ANSI_BLUE + "\nLEYENDO PRESUPUESTO DEL FICHERO '" + ruta + "'" + ANSI_RESET);
            System.out.println(
                    ANSI_BLUE + "-------------------------------------------------------------\n" + ANSI_RESET);

            int byteLeido;

            while ((byteLeido = filein.read()) != -1) {
                //Escribir en hexadecimal (contiene imagenes)
                System.out.printf("%02X", byteLeido);
            }

            filein.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

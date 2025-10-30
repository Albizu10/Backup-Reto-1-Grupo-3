import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class EscrituraContactosRandom {
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
        RandomAccessFile rndmAF = null;
        File ruta = null;

        try{
            ruta = new File("contactosEscrituraRandom.dat");
            rndmAF = new RandomAccessFile(ruta, "rw");

            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            try {
                if(rndmAF != null){
                    rndmAF.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
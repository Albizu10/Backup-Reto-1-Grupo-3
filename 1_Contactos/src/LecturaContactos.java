import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LecturaContactos {
    public static void main(String[] args) {
        try {
            File ruta = new File("contactos.csv");
            FileReader fr = new FileReader(ruta);
            BufferedReader bfr = new BufferedReader(fr);

            String linea;

            while ((linea = bfr.readLine()) != null) {
                System.out.println(linea);
            }

            bfr.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }


    }
}

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.Map;
import java.util.Iterator;

public class LecturaJSON {
    public static String ANSI_GREEN = "\u001B[32m";
    public static String ANSI_BLUE = "\u001B[34m";
    public static String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        FileReader fr = null;

        try {
            File ruta = new File("contactos.json");
            fr = new FileReader(ruta);
            JsonElement jsonElem = JsonParser.parseReader(fr);
            JsonArray jsonArray = jsonElem.getAsJsonArray();
            Iterator<JsonElement> iterator = jsonArray.iterator();

            System.out.println(ANSI_BLUE + "\nLEYENDO CONTACTOS DEL FICHERO '" + ruta + "'" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "----------------------------------------------\n" + ANSI_RESET);

            while (iterator.hasNext()) {
                JsonElement jsonElem2 = iterator.next();
                JsonObject jsonObj = jsonElem2.getAsJsonObject();
                Iterator<Map.Entry<String, JsonElement>> iterator2 = jsonObj.entrySet().iterator();

                System.out.println("ID: " + iterator2.next().getValue());
                System.out.println("Nombre completo: " + iterator2.next().getValue());
                System.out.println("Correo: " + iterator2.next().getValue());
                System.out.println("Teléfono: " + iterator2.next().getValue());
                System.out.println("Ciudad: " + iterator2.next().getValue());
                System.out.println("País: " + iterator2.next().getValue());
                System.out.println(ANSI_GREEN + "\n-----------------------------------\n" + ANSI_RESET);

                
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (IllegalStateException e){
            e.printStackTrace();
        } finally{
            try {
                if (fr != null){
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;

/**
 * Clase que permite leer y mostrar en consola los contactos almacenados en el archivo JSON 'contactos.json'.
 * Muestra los datos con formato claro y legible en la terminal.
 * 
 * @version 1.0
 */
public class LecturaContactosJSON {
    /**Código ANSI para restablecer el color de la terminal */
    public static final String ANSI_RESET = "\u001B[0m";
    /**Código ANSI para imprimir los resultados en color verde en la terminal */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**Código ANSI para imprimir mensajes de cabecera en color cyan en la terminal */
    public static final String ANSI_CYAN = "\u001B[36m";
    /**Código ANSI para imprimir errores en color rojo en la terminal */
    public static final String ANSI_RED = "\u001B[31m";

    /**
     * Método principal del programa. 
     * Permite leer el archivo JSON y mostrar cada contacto en formato estructurado.
     * @param args
     */
    public static void main(String[] args) {
        FileReader fr = null;

        try {
            File ruta = new File("contactos.json");
            fr = new FileReader(ruta);
            JsonElement jsonElem = JsonParser.parseReader(fr);
            JsonArray jsonArray = jsonElem.getAsJsonArray();
            Iterator<JsonElement> iterator = jsonArray.iterator();

            System.out.println(ANSI_CYAN + "\nLEYENDO CONTACTOS DEL FICHERO '" + ruta + "'" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "----------------------------------------------\n" + ANSI_RESET);

            //Recorrer todos los contactos y mostrarlos en consola
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
            
         System.out.println(ANSI_CYAN + "\n-----------------------------------\nLectura finalizada\n" + ANSI_RESET);
        } catch (FileNotFoundException e) {
            System.err.println(ANSI_RED);
            e.printStackTrace();
            System.err.println(ANSI_RESET);
        } catch (JsonSyntaxException | IllegalStateException e) {
            System.err.println(ANSI_RED);
            e.printStackTrace();
            System.err.println(ANSI_RESET);
        } finally {
            try {
                if (fr != null) fr.close();
            } catch (IOException e) {
                System.err.println(ANSI_RED);
                e.printStackTrace();
                System.err.println(ANSI_RESET);
            }
        }
    }
}


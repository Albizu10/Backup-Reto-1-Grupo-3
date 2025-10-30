import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class EscrituraJSON {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";    
    public static void main(String[] args) {
        JsonArray contactos = new JsonArray();

        String[] arrayNomCompl = { "Ada Martinez", "Administrator", "Carla Jimenez", "Comercializadora Ruiz S.A.", "Juan Martinez", "Laura Hernandez" };
        String[] arrayCorreos = { "adamartinez@techsolutuion.com", "ikmssaid24@lhusurbil.eus", "carlajimenez@techsolutuion.com", "ruiz@gmail.com", "juanmartinez@techsolutuion.com", "laurahernandez@gmail.com" };
        String[] arrayTelefonos = { "555-0101", "245-345", "555-0102", "5664-7631", "555-0103", "6234-8909" };
        String[] arrayCiudades = { "Madrid", "Madrid", "Santander", "Quito", "Soria", "Albacete" };
        String[] arrayPaises = { "España", "España", "España", "España", "España", "España" };

        for(int i=0; i < arrayNomCompl.length; i++){
            JsonObject contacto = new JsonObject();
            
            contacto.addProperty("ID", i+1);
            contacto.addProperty("nombre completo", arrayNomCompl[i]);
            contacto.addProperty("correo", arrayCorreos[i]);
            contacto.addProperty("telefono", arrayTelefonos[i]);
            contacto.addProperty("ciudad", arrayCiudades[i]);
            contacto.addProperty("pais", arrayPaises[i]);
        
            contactos.add(contacto);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File ruta = new File("contactos.json");

        try (FileWriter fw = new FileWriter(ruta)) {
            gson.toJson(contactos, fw);
            System.out.println(ANSI_GREEN + "\n-------------------------------------------------------------------\n" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "Se ha generado el archivo '" + ruta + "' con " + arrayNomCompl.length + " datos de contacto\n" + ANSI_RESET);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

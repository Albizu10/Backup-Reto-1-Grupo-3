import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Clase que permite generar un fichero JSON con los contactos almacenados en Odoo.
 * Además, añade un nuevo contacto correspondiente a INCIBE (Instituto Nacional de Ciberseguridad).
 * El archivo resultante se genera en local y no afecta a Odoo.
 * 
 * @version 1.0
 */
public class EscrituraContactosJSON {
    /**Código ANSI para restablecer el color de la terminal */
    public static final String ANSI_RESET = "\u001B[0m";
    /**Código ANSI para imprimir los resultados en color verde en la terminal */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**Código ANSI para imprimir mensajes de cabecera en color cyan en la terminal */
    public static final String ANSI_CYAN = "\u001B[36m";
    /**Código ANSI para imprimir mensajes de advertencia o errores en color rojo en la terminal */
    public static final String ANSI_RED = "\u001B[31m";

    /**
     * Método principal del programa.
     * Crea un archivo JSON llamado 'contactos.json' con los contactos de Odoo y añade el contacto de INCIBE.
     * @param args
     */
    public static void main(String[] args) {
        //Inicializar el array JSON donde se almacenarán los contactos
        JsonArray contactos = new JsonArray();

        //Datos base que simulan los contactos de Odoo
        String[] arrayNomCompl = { "Ada Martinez", "Administrator", "Carla Jimenez", "Comercializadora Ruiz S.A.", "Juan Martinez", "Laura Hernandez" };
        String[] arrayCorreos = { "adamartinez@techsolutuion.com", "ikmssaid24@lhusurbil.eus", "carlajimenez@techsolutuion.com", "ruiz@gmail.com", "juanmartinez@techsolutuion.com", "laurahernandez@gmail.com" };
        String[] arrayTelefonos = { "555-0101", "245-345", "555-0102", "5664-7631", "555-0103", "6234-8909" };
        String[] arrayCiudades = { "Madrid", "Madrid", "Santander", "Quito", "Soria", "Albacete" };
        String[] arrayPaises = { "España", "España", "España", "España", "España", "España" };

        //Recorrer los arrays y construir objetos JSON para cada contacto
        for(int i = 0; i < arrayNomCompl.length; i++){
            JsonObject contacto = new JsonObject();
            contacto.addProperty("ID", i + 1);
            contacto.addProperty("nombre completo", arrayNomCompl[i]);
            contacto.addProperty("correo", arrayCorreos[i]);
            contacto.addProperty("telefono", arrayTelefonos[i]);
            contacto.addProperty("ciudad", arrayCiudades[i]);
            contacto.addProperty("pais", arrayPaises[i]);
            contactos.add(contacto);
        }

        //Crear el objeto Gson para escribir con formato bonito
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //Ruta del fichero
        File ruta = new File("contactos.json");

        //Escribir el fichero JSON
        try (FileWriter fw = new FileWriter(ruta)) {
            gson.toJson(contactos, fw);
            System.out.println(ANSI_CYAN + "\nESCRIBIENDO CONTACTOS EN '" + ruta + "'" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "-------------------------------------------------------------------\n" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "Se ha generado el archivo '" + ruta + "' con " + contactos.size() + "\n" + ANSI_RESET);
        } catch (IOException e) {
            System.err.println(ANSI_RED);
            e.printStackTrace();
            System.err.println(ANSI_RESET);
        }
    }
}

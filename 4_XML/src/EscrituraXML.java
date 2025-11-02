import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;

/**
 * Clase que permite escribir contactos en formato XML en el archivo 'contactos.xml'.
 * Cada contacto tiene un nombre completo, correo, teléfono, ciudad y país.
 * 
 * @version 1.0
 */
public class EscrituraXML {
    /**Código ANSI para restablecer el color de la terminal */
    public static final String ANSI_RESET = "\u001B[0m";
    /**Código ANSI para imprimir los errores en color rojo en la terminal */
    public static final String ANSI_RED = "\u001B[31m";
    /**Código ANSI para imprimir los resultados en color verde en la terminal */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**Código ANSI para imprimir los menus / enunciados en color cyan en la terminal */
    public static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Método principal del programa.
     * Crea un archivo XML con los contactos y los guarda en el archivo 'contactos.xml'.
     * 
     * @param args Argumentos de línea de comandos (no utilizados en este caso)
     */
    public static void main(String[] args) {
        // Definir los datos de los contactos
        String[] arrayNomCompl = { "Ada Martinez", "Administrator", "Carla Jimenez", "Comercializadora Ruiz S.A.", "Juan Martinez", "Laura Hernandez" };
        String[] arrayCorreos = { "adamartinez@techsolutuion.com", "ikmssaid24@lhusurbil.eus", "carlajimenez@techsolutuion.com", "ruiz@gmail.com", "juanmartinez@techsolutuion.com", "laurahernandez@gmail.com" };
        String[] arrayTelefonos = { "555-0101", "245-345", "555-0102", "5664-7631", "555-0103", "6234-8909" };
        String[] arrayCiudades = { "Madrid", "Madrid", "Santander", "Quito", "Soria", "Albacete" };
        String[] arrayPaises = { "España", "España", "España", "España", "España", "España" };

        // Mensaje inicial informando que estamos escribiendo en un archivo XML
        System.out.println(ANSI_CYAN + "\nESCRITURA DE NUEVOS CONTACTOS EN XML\n------------------------------------\n" + ANSI_RESET);

        //Try with resources para manejar los recursos de manera automática
        try {
            // Crear una instancia de DocumentBuilderFactory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // Crear el DocumentBuilder que construirá el documento XML
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Crear un nuevo documento XML
            Document doc = db.newDocument();

            // Definir la ruta donde se guardará el archivo XML
            File ruta = new File("contactos.xml");

            // Preparar la salida para escribir en el archivo
            StreamResult sr = new StreamResult(ruta);

            // Crear el elemento raíz del XML, que será <Contactos>
            Element contactos = doc.createElement("Contactos");
            doc.appendChild(contactos);

            // Iterar sobre cada contacto para escribirlo en el XML
            for (int i = 0; i < arrayNomCompl.length; i++) {
                // Crear el elemento <Contacto> para cada contacto
                Element contacto = doc.createElement("Contacto");

                // Asignar un ID único al contacto (usamos el índice + 1)
                Attr id = doc.createAttribute("id");
                contacto.setAttribute("id", String.valueOf(i + 1));
                contactos.appendChild(contacto);

                // Crear y agregar el campo NombreCompleto al XML
                Element nombreCompleto = doc.createElement("NombreCompleto");
                contacto.appendChild(nombreCompleto);
                nombreCompleto.setTextContent(arrayNomCompl[i]);

                // Crear y agregar el campo CorreoElectronico al XML
                Element correoElectronico = doc.createElement("CorreoElectronico");
                contacto.appendChild(correoElectronico);
                correoElectronico.setTextContent(arrayCorreos[i]);

                // Crear y agregar el campo Teléfono al XML
                Element telefono = doc.createElement("Telefono");
                contacto.appendChild(telefono);
                telefono.setTextContent(arrayTelefonos[i]);

                // Crear y agregar el campo Ciudad al XML
                Element ciudad = doc.createElement("Ciudad");
                contacto.appendChild(ciudad);
                ciudad.setTextContent(arrayCiudades[i]);

                // Crear y agregar el campo País al XML
                Element pais = doc.createElement("Pais");
                contacto.appendChild(pais);
                pais.setTextContent(arrayPaises[i]);
            }

            // Crear un Transformer para convertir el documento DOM a un archivo XML
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transf = tf.newTransformer();

            // Crear una fuente DOM que se convertirá en un archivo XML
            DOMSource doms = new DOMSource(doc);

            // Realizar la transformación y escribir el documento en el archivo XML
            transf.transform(doms, sr);

            // Mensaje final indicando que la operación se ha completado con éxito
            System.out.println(ANSI_GREEN + "\n------------------------------------\n" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "Se ha generado el archivo '" + ruta + "' con " + arrayNomCompl.length + " datos de contacto\n" + ANSI_RESET);

        } catch (ParserConfigurationException | TransformerException e){
            System.err.println(ANSI_RED);
            e.printStackTrace();
            System.err.println(ANSI_RESET);
        }
    }
}

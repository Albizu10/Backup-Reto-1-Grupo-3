import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.io.IOException;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Clase que permite leer los contactos de un archivo XML, en este caso, 'contactos.xml'
 * @version 1.0
 */
public class LecturaXML {
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
     * Permite leer contactos de un archivo XML y mostrarlos por consola.
     * @param args
     */
    public static void main(String[] args) {
        // Definir la ruta del archivo XML que contiene los contactos
        File ruta = new File("contactos.xml");

        // Try-with-resources para asegurar el cierre automático de recursos
        try {
            // Configuración del parser XML para leer el archivo
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            
            // Parseo del archivo XML
            Document doc = db.parse(ruta);

            // Obtener todos los nodos "Contacto" del documento XML
            NodeList nl = doc.getElementsByTagName("Contacto");

            // Mensaje inicial indicando que se está leyendo el archivo
            System.out.println(ANSI_CYAN + "\nLEYENDO CONTACTOS DEL FICHERO '" + ruta + "'" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "----------------------------------------------\n" + ANSI_RESET);

            // Recorrer todos los contactos en el archivo XML
            for (int i = 0; i < nl.getLength(); i++) {
                Node nContacto = nl.item(i);

                // Verificar que el nodo es de tipo Elemento (nodo de contacto)
                if (nContacto.getNodeType() == Node.ELEMENT_NODE) {
                    Element contacto = (Element) nContacto;
                    
                    // Obtener los atributos y elementos del nodo
                    String id = contacto.getAttribute("id");
                    String nombreCompleto = contacto.getElementsByTagName("NombreCompleto").item(0).getTextContent();
                    String correo = contacto.getElementsByTagName("CorreoElectronico").item(0).getTextContent();
                    String telefono = contacto.getElementsByTagName("Telefono").item(0).getTextContent();
                    String ciudad = contacto.getElementsByTagName("Ciudad").item(0).getTextContent();
                    String pais = contacto.getElementsByTagName("Pais").item(0).getTextContent();

                    // Imprimir los datos del contacto
                    System.out.println("ID: " + id);
                    System.out.println("Nombre completo: " + nombreCompleto);
                    System.out.println("Correo: " + correo);
                    System.out.println("Teléfono: " + telefono);
                    System.out.println("Ciudad: " + ciudad);
                    System.out.println("País: " + pais);
                    System.out.println(ANSI_GREEN + "\n-----------------------------------\n" + ANSI_RESET);
                }
            }

            System.out.println(ANSI_CYAN + "-----------------------------------\nLectura finalizada" + ANSI_RESET);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            //Manejo de excepciones (generales y específicas)
            System.err.println(ANSI_RED);
            e.printStackTrace();
            System.err.println(ANSI_RESET);
        }
    }
}

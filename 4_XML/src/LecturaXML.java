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

public class LecturaXML {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        try {
            File ruta = new File("contactos.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(ruta);

            NodeList nl = doc.getElementsByTagName("Contacto");

            System.out.println(ANSI_BLUE + "\nLEYENDO CONTACTOS DEL FICHERO '" + ruta + "'" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "----------------------------------------------\n" + ANSI_RESET);

            for(int i=0; i<nl.getLength(); i++){
                
                Node nContacto = nl.item(i);

                if (nContacto.getNodeType() == Node.ELEMENT_NODE) {
                    Element contacto = (Element) nContacto;

                    String nombreCompleto = contacto.getElementsByTagName("NombreCompleto").item(0).getTextContent();
                    String correo = contacto.getElementsByTagName("CorreoElectronico").item(0).getTextContent();
                    String telefono = contacto.getElementsByTagName("Telefono").item(0).getTextContent();
                    String ciudad = contacto.getElementsByTagName("Ciudad").item(0).getTextContent();
                    String pais = contacto.getElementsByTagName("Pais").item(0).getTextContent();

                    System.out.println("Nombre completo: " + nombreCompleto);
                    System.out.println("Correo: " + correo);
                    System.out.println("Teléfono: " + telefono);
                    System.out.println("Ciudad: " + ciudad);
                    System.out.println("País: " + pais);
                    System.out.println(ANSI_GREEN + "\n-----------------------------------\n" + ANSI_RESET);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}

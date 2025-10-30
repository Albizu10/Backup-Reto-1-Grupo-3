//Document
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;

//XPath
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

//Node
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;


//Exception
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.IOException;
import javax.xml.xpath.XPathExpressionException;

public class LecturaXMLconXPath {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            String ruta = "contactos.xml";
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(ruta);
            XPath xpath = XPathFactory.newInstance().newXPath();

            String expresion = "Contactos/Contacto";

            NodeList nContactos = (NodeList) xpath.evaluate(expresion, doc, XPathConstants.NODESET);

            System.out.println(ANSI_BLUE + "\nLEYENDO CONTACTOS DEL FICHERO '" + ruta + "'" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "----------------------------------------------\n" + ANSI_RESET);

            for(int i=0; i<nContactos.getLength(); i++){
                
                Node nContacto = nContactos.item(i);

                if (nContacto.getNodeType() == Node.ELEMENT_NODE) {
                    Element contacto = (Element) nContacto;

                    String id = contacto.getAttribute("id");
                    String nombreCompleto = contacto.getElementsByTagName("NombreCompleto").item(0).getTextContent();
                    String correo = contacto.getElementsByTagName("CorreoElectronico").item(0).getTextContent();
                    String telefono = contacto.getElementsByTagName("Telefono").item(0).getTextContent();
                    String ciudad = contacto.getElementsByTagName("Ciudad").item(0).getTextContent();
                    String pais = contacto.getElementsByTagName("Pais").item(0).getTextContent();

                    System.out.println("ID: " + id);
                    System.out.println("Nombre completo: " + nombreCompleto);
                    System.out.println("Correo: " + correo);
                    System.out.println("Teléfono: " + telefono);
                    System.out.println("Ciudad: " + ciudad);
                    System.out.println("País: " + pais);
                    System.out.println(ANSI_GREEN + "\n-----------------------------------\n" + ANSI_RESET);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        } catch (XPathExpressionException e){
            e.printStackTrace();
        }
    }
}

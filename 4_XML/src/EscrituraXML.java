import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;

public class EscrituraXML {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            File ruta = new File("contactosEscritura.xml");
            StreamResult sr = new StreamResult(ruta);

            String[] arrayNomCompl = { "Ada Martinez", "Administrator", "Carla Jimenez", "Comercializadora Ruiz S.A.", "Juan Martinez", "Laura Hernandez" };
            String[] arrayCorreos = { "adamartinez@techsolutuion.com", "ikmssaid24@lhusurbil.eus", "carlajimenez@techsolutuion.com", "ruiz@gmail.com", "juanmartinez@techsolutuion.com", "laurahernandez@gmail.com" };
            String[] arrayTelefonos = { "555-0101", "245-345", "555-0102", "5664-7631", "555-0103", "6234-8909" };
            String[] arrayCiudades = { "Madrid", "Madrid", "Santander", "Quito", "Soria", "Albacete" };
            String[] arrayPaises = { "España", "España", "España", "España", "España", "España" };

            System.out.println("\nGenerando XML con datos de contacto...");

            Element contactos = doc.createElement("Contactos");
            doc.appendChild(contactos);

            for (int i = 0; i < arrayNomCompl.length; i++) {
                Element contacto = doc.createElement("Contacto");
                Attr id = doc.createAttribute("id");
                contacto.setAttribute("id", String.valueOf(i + 1));
                contactos.appendChild(contacto);

                Element nombreCompleto = doc.createElement("NombreCompleto");
                contacto.appendChild(nombreCompleto);
                nombreCompleto.setTextContent(arrayNomCompl[i]);

                Element correoElectronico = doc.createElement("correoElectronico");
                contacto.appendChild(correoElectronico);
                correoElectronico.setTextContent(arrayCorreos[i]);

                Element telefono = doc.createElement("Telefono");
                contacto.appendChild(telefono);
                telefono.setTextContent(arrayTelefonos[i]);

                Element ciudad = doc.createElement("Ciudad");
                contacto.appendChild(ciudad);
                ciudad.setTextContent(arrayCiudades[i]);

                Element pais = doc.createElement("Pais");
                contacto.appendChild(pais);
                pais.setTextContent(arrayPaises[i]);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transf = tf.newTransformer();
            DOMSource doms = new DOMSource(doc);

            transf.transform(doms, sr);

            System.out.println(ANSI_GREEN + "\n-----------------------------------\n" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "Se ha generado el archivo '" + ruta + "' con " + arrayNomCompl.length + " datos de contacto\n" + ANSI_RESET);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}

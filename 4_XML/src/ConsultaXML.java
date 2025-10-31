import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.io.IOException;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Scanner;

public class ConsultaXML {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        try {
            File ruta = new File("contactos.xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(ruta);

            Scanner sc = new Scanner(System.in);

            System.out.println("\nCONSULTA DE CONTACTOS DEL FICHERO '" + ruta + "'");
            System.out.println("-----------------------------------------------------\n");

            boolean seguir = true;
            int opcion;

            while (seguir == true){
                System.out.println(ANSI_YELLOW + "\n\t\t\tMENU" + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "-----------------------------------------------------\n" + ANSI_RESET);

                System.out.println(ANSI_YELLOW + "1) Obtener teléfono a partir de id \n" + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "2) Obtener nombre a partir del correo electrónico \n" + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "3) Obtener registros de los contactos que son de X ciudad \n" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "4) Salir \n" + ANSI_RESET);

                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    //CONSULTA 1
                    case 1:
                        System.out.println("Escriba el id del contacto del que desea obtener el teléfono: \n");
                        String idBuscado = sc.nextLine();

                        consulta1(doc, idBuscado);

                        break;
                
                    //CONSULTA 2
                    case 2:
                        System.out.println("Escriba el correo del contacto del que desea obtener el nombre: \n");
                        String correoBuscado = sc.nextLine();

                        consulta2(doc, correoBuscado);

                        break;

                    //CONSULTA 3
                    case 3:
                        System.out.println("Escriba la ciudad a la que perteneces los contactos que desea buscar: \n");
                        String ciudadBuscada = sc.nextLine();

                        consulta3(doc, ciudadBuscada);

                        break;

                    case 4:
                        System.out.println(ANSI_BLUE + "\n------------------------------------\nSaliendo...\n" + ANSI_RESET);
                        seguir = false;
                        break; 

                    default:
                        System.out.println(ANSI_RED + "Escriba una opción válida (1-4)" + ANSI_RESET);
                        break;
                }
            }   
            
            sc.close();
        } catch (ParserConfigurationException | SAXException |IOException e) {
            e.printStackTrace();
        }
    }

    //Obtener telefono a partir de id
    private static void consulta1(Document doc, String idBuscado){
        NodeList contactos = doc.getElementsByTagName("Contacto");
        boolean encontrado = false;

        for(int i=0; i < contactos.getLength(); i++){
            Element contacto = (Element) contactos.item(i);
            String id = contacto.getAttribute("id");

            if(id.equals(idBuscado)){
                String telefono = contacto.getElementsByTagName("Telefono").item(0).getTextContent();

                System.out.println(ANSI_GREEN + "\n¡CONTACTO ENCONTRADO!" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "\n------------------------------------" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "\nTeléfono del contacto con ID " + id + ": " + telefono + "\n" + ANSI_RESET);

                encontrado = true;
            } 
        }

        if (encontrado == false){
            System.out.println(ANSI_RED + "\nNo existe ningún contacto con el ID " + idBuscado + " en nuestro registro" + ANSI_RESET);
        }
    }

    //Obtener nombre a partir del correo                        
    private static void consulta2(Document doc, String correoBuscado){
        NodeList contactos = doc.getElementsByTagName("Contacto");
        boolean encontrado = false;

        for(int i=0; i < contactos.getLength(); i++){
            Element contacto = (Element) contactos.item(i);
            String correo = contacto.getElementsByTagName("CorreoElectronico").item(0).getTextContent();

            if(correo.equalsIgnoreCase(correoBuscado)){
                String nombreCompleto = contacto.getElementsByTagName("NombreCompleto").item(0).getTextContent();

                System.out.println(ANSI_GREEN + "\n¡CONTACTO ENCONTRADO!" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "\n------------------------------------" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "\nNombre completo del contacto con correo '" + correoBuscado + "'': " + nombreCompleto + "\n" + ANSI_RESET);

                encontrado = true;
            } 
        }

        if (encontrado == false){
            System.out.println(ANSI_RED + "\nNo existe ningún contacto con el correo '" + correoBuscado + "' en nuestro registro" + ANSI_RESET);
        }
    }
    
    //Obtener registro de los contactos que son de X ciudad
    private static void consulta3(Document doc, String ciudadBuscada){
        NodeList contactos = doc.getElementsByTagName("Contacto");
        boolean encontrado = false;

        for(int i=0; i < contactos.getLength(); i++){
            Element contacto = (Element) contactos.item(i);
            String ciudades = contacto.getElementsByTagName("Ciudad").item(0).getTextContent();

            int cont=1;

            if(ciudades.equalsIgnoreCase(ciudadBuscada)){
                cont++;

                if(!encontrado){
                    System.out.println(ANSI_GREEN + "\n¡CONTACTO ENCONTRADO!" + ANSI_RESET);
                    System.out.println(ANSI_GREEN + "\n------------------------------------" + ANSI_RESET);
                    System.out.println(ANSI_GREEN + "\nDatos de los " + cont + " contactos de " + ciudadBuscada + ": \n" + ANSI_RESET);
                }
                
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

                encontrado = true;
            } 
        }

        if (encontrado == false){
            System.out.println(ANSI_RED + "\nNo existe ningún contacto de " + ciudadBuscada + " en nuestro registro" + ANSI_RESET);
        }
    }
}

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.io.IOException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que permite realizar consultas sobre un archivo XML, en este caso, 'contactos.xml'
 * 
 * @version 1.0
 */
public class ConsultaContactosXMLXPath {
    /** Código ANSI para restablecer el color de la terminal */
    public static final String ANSI_RESET = "\u001B[0m";
    /** Código ANSI para imprimir los errores en color rojo en la terminal */
    public static final String ANSI_RED = "\u001B[31m";
    /** Código ANSI para imprimir los resultados en color verde en la terminal */
    public static final String ANSI_GREEN = "\u001B[32m";
    /** Código ANSI para indicar que el formato de los datos introducidos no es correcto, color amarillo en la terminal */
    public static final String ANSI_YELLOW = "\u001B[33m";
    /** Código ANSI para indicar los datos que tiene que introducir el usuario en color magenta en la terminal */
    public static final String ANSI_MAGENTA = "\u001B[35m";
    /** Código ANSI para imprimir los menus/enunciados en color cyan en la terminal */
    public static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Método principal del programa. Permite al usuario realizar consultas respecto a los contactos del XML.
     * @param args
     */
    public static void main(String[] args) {
        //Iniciar instancia de DBF
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            //Ruta del archivo XML
            File ruta = new File("contactos.xml");

            //Crear DB para procesar el XML
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(ruta);
            XPath xpath = XPathFactory.newInstance().newXPath();

            //Iniciar Scanner
            Scanner sc = new Scanner(System.in);

            //Mensaje inicial
            System.out.println(ANSI_CYAN + "\nCONSULTA DE CONTACTOS DEL FICHERO '" + ruta + "'");
            System.out.println("-----------------------------------------------------\n" + ANSI_RESET);

            //Variables para controlar las interacciones del usuario
            boolean seguir = true;
            int opcion;

            while (seguir) {
                //Menu de opciones posibles
                System.out.println(ANSI_CYAN + "\n\t\t\tMENU");
                System.out.println("-----------------------------------------------------\n" + ANSI_RESET);

                System.out.println(ANSI_CYAN + "1) Obtener teléfono a partir de id \n" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "2) Obtener nombre a partir del correo electrónico \n" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "3) Obtener registros de los contactos que son de X ciudad \n" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "4) Salir \n" + ANSI_RESET);

                //Leer la opción del usuario
                try {
                    opcion = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException e) {
                    sc.nextLine(); // Para salir del bucle
                    opcion = -1;   // Asignar un valor no válido para que entre en default
                }


                switch (opcion) {
                    // CONSULTA 1: Obtener teléfono a partir de id
                    case 1:
                        //Solicidar ID al usuario, no puede estar vacío
                        String idBuscado = solicitarDato(sc, "ID");
                        //Llamar a la consulta
                        consulta1(doc, xpath, idBuscado);
                        break;

                    // CONSULTA 2: Obtener nombre a partir del correo
                    case 2:
                        String correoBuscado;
                        //Solicitar correo hasta que el usuario introduzca un dato válido
                        do {
                            correoBuscado = solicitarDato(sc, "Correo electrónico");
                            if (!validarCorreo(correoBuscado)) {
                                System.out.println(ANSI_YELLOW + "\nEl formato del correo electrónico introducido no es correcto, vuelva a escribirlo" + ANSI_RESET);
                            }
                        } while (!validarCorreo(correoBuscado));
                        //Llamar a la consulta
                        consulta2(doc, xpath, correoBuscado);
                        break;

                    // CONSULTA 3: Obtener registros de los contactos que son de X ciudad
                    case 3:
                        //Solicitar ciudad, no puede estar vacío
                        String ciudadBuscada = solicitarDato(sc, "Ciudad");
                        //Llamar a la consulta
                        consulta3(doc, xpath, ciudadBuscada);
                        break;

                    case 4:
                        //Salir del programa
                        System.out.println(ANSI_CYAN + "\n------------------------------------\nSaliendo...\n" + ANSI_RESET);
                        seguir = false; //Salir del bucle
                        break;

                    default:
                        //Si el usuario no introduce un número entre el 1 y el 4
                        System.out.println(ANSI_YELLOW + "\nEscriba una opción válida (1-4)" + ANSI_RESET);
                        break;
                }
            }
            //Cerrar el Scanner
            sc.close();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // Manejo de excepciones si ocurre un error al procesar el XML
            System.err.println(ANSI_RED);
            e.printStackTrace();
            System.err.println(ANSI_RESET);
        } catch (XPathExpressionException e) {
            // Manejo de excepciones si ocurre un error al realizar una consulta XPath
            System.err.println(ANSI_RED);
            e.printStackTrace();
            System.err.println(ANSI_RESET);
        }
    }

    // CONSULTA 1: Obtener teléfono a partir de id
    private static void consulta1(Document doc, XPath xpath, String idBuscado) throws XPathExpressionException {
        //Definir la expresión XPath para buscar un contacto por su ID
        String expresion = "Contactos/Contacto[@id='" + idBuscado + "']";
        Node contacto = (Node) xpath.evaluate(expresion, doc, XPathConstants.NODE);

        //Verifica si el contacto existe
        if(contacto != null){
            //Obtiene el teléfono del contacto
            String telefono = xpath.evaluate("Telefono", contacto);
            //Imprimir los resultados
            System.out.println(ANSI_GREEN + "\n¡CONTACTO ENCONTRADO!" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "\n------------------------------------" + ANSI_RESET);
            System.out.println("\nTeléfono del contacto con ID " + idBuscado + ": " + telefono + "\n");
        } else {
            //Mensaje si no se encuentra ningún contacto
            System.out.println(ANSI_RED + "\nNo existe ningún contacto con el ID " + idBuscado + " en nuestro registro" + ANSI_RESET);
        }
    }

    // CONSULTA 2: Obtener nombre a partir del correo    
    private static void consulta2(Document doc, XPath xpath, String correoBuscado) throws XPathExpressionException {
        //Definir la expresión XPath para buscar un contacto por su correo
        String expresion = "/Contactos/Contacto[CorreoElectronico='" + correoBuscado + "']"; // XPath para buscar por correo electrónico
        Node contacto = (Node) xpath.evaluate(expresion, doc, XPathConstants.NODE);
        //Verificar si el contacto existe
        if (contacto != null) {
            //Obtener nombre completo del contacto e imprimirlo
            String nombreCompleto = xpath.evaluate("NombreCompleto", contacto);
            System.out.println(ANSI_GREEN + "\n¡CONTACTO ENCONTRADO!" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "\n------------------------------------" + ANSI_RESET);
            System.out.println("\nNombre completo del contacto con correo '" + correoBuscado + "': " + nombreCompleto + "\n");
        } else {
            //Mensaje si el contacto no existe
            System.out.println(ANSI_RED + "\nNo existe ningún contacto con el correo '" + correoBuscado + "' en nuestro registro" + ANSI_RESET);
        }
    }

    // CONSULTA 3: Obtener registros de los contactos que son de X ciudad
    private static void consulta3(Document doc, XPath xpath, String ciudadBuscada) throws XPathExpressionException {
        //Expresión para buscar los contactos de X ciudad
        String expresion = "Contactos/Contacto[Ciudad='" + ciudadBuscada + "']";
        NodeList contactos = (NodeList) xpath.evaluate(expresion, doc, XPathConstants.NODESET);

        //Verificar si se han encontrado contactos
        if (contactos.getLength() > 0) {
            System.out.println(ANSI_GREEN + "\n¡CONTACTO(S) ENCONTRADO(S)!" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "\n------------------------------------" + ANSI_RESET);

            //Iterar sobre los contactos
            for (int i = 0; i < contactos.getLength(); i++) {
                Node contacto = contactos.item(i);
                //Obtener los datos
                String id = xpath.evaluate("@id", contacto);
                String nombreCompleto = xpath.evaluate("NombreCompleto", contacto);
                String correo = xpath.evaluate("CorreoElectronico", contacto);
                String telefono = xpath.evaluate("Telefono", contacto);
                String ciudad = xpath.evaluate("Ciudad", contacto);
                String pais = xpath.evaluate("Pais", contacto);
                //Imprimirlos
                System.out.println("ID: " + id);
                System.out.println("Nombre completo: " + nombreCompleto);
                System.out.println("Correo: " + correo);
                System.out.println("Teléfono: " + telefono);
                System.out.println("Ciudad: " + ciudad);
                System.out.println("País: " + pais);
                System.out.println(ANSI_GREEN + "\n-----------------------------------\n" + ANSI_RESET);
            }
        } else {
            //Mensaje si no existen contactos
            System.out.println(ANSI_RED + "\nNo existe ningún contacto de " + ciudadBuscada + " en nuestro registro" + ANSI_RESET);
        }
    }

    /**
     * Función para validar que el formato del correo electrónico es correcto mediante una expresión regular
     *
     * @param correo Dato que introduce el usuario
     * @return Si el formato es correcto devuelve true
     */
    public static Boolean validarCorreo(String correo) {
        // Definir el patrón para la validación del correo
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        // Verificar que el dato introducido por el usuario coincide con el patrón
        Matcher matcher = pattern.matcher(correo);
        // Devolver el resultado (true si cumple el patrón)
        return matcher.matches();
    }

    /**
     * Función para solicitar un dato y asegurarse de que no está vacío.
     * 
     * @param sc Scanner para leer la entrada del usuario
     * @param campo Nombre del campo que se está solicitando
     * @return El valor introducido por el usuario
     */
    public static String solicitarDato(Scanner sc, String campo) {
        //Variable para guardar el dato que introduce el usuario
        String dato;
        
        //Solicita el dato hasta que el usuario lo introduzca
        do {
            System.out.println(ANSI_MAGENTA + "\n" + campo + ": " + ANSI_RESET);
            dato = sc.nextLine();
            if (dato.isEmpty()) {
                System.out.println(ANSI_YELLOW + "El " + campo.toLowerCase() + " no puede estar vacío, vuelva a intentarlo." + ANSI_RESET);
            }
        } while (dato.isEmpty());

        //Devuelve el dato
        return dato;
    } 
}

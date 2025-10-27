import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.IOException;

public class EscrituraContactos {
    public static void main(String[] args) {
        try {
            File ruta = new File("contactos.csv");
            FileWriter fr = new FileWriter(ruta, true);
            BufferedWriter bfw = new BufferedWriter(fr);

            Scanner sc = new Scanner(System.in);

            String nombre, apellido, correo, ciudad, pais;
            int telefono;

            System.out.println("Nombre: ");
            nombre = sc.nextLine();

            System.out.println("Apellido: ");
            apellido = sc.nextLine();

            System.out.println("Correo electrónico: ");
            correo = sc.nextLine();

            System.out.println("Teléfono: ");
            telefono = sc.nextInt();
            sc.nextLine();

            System.out.println("Ciudad: ");
            ciudad = sc.nextLine();

            System.out.println("País:");
            pais = sc.nextLine();

            bfw.write("\n\"" + nombre + " " + apellido + "\",\"" + correo + "\",\"" + telefono +  "\",\"" + ciudad + "\",\"" + pais + "\"");
            
            bfw.close();
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
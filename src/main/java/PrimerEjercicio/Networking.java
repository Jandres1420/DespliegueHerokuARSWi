package PrimerEjercicio;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Ejercicio numero 1
 */
public class Networking {

    public static void main(String args[]){
        try {
            URL url = new URL("http://www.hackthebox.com");
            System.out.println("Puerto "+ url.getPort());
            System.out.println("Protocolo "+ url.getProtocol());
            System.out.println("Host "+url.getHost());
            System.out.println("Authority "+url.getAuthority());
            System.out.println("Path "+url.getPath());
            System.out.println("Query "+url.getQuery());
            System.out.println("File" + url.getFile());
            System.out.println("Ref :" + url.getRef());

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}

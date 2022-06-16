package SegundoEjercicio;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class URLReader {
    private static String console;
    private String reader;
    private URLConnection urlConnection;
    private URL url;

    /**
     * Metodo main que pide la url por consola y llama a un objeto SegundoEjercicio.URLReader para comenzar la transcripción a HTML
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        console = scanner.nextLine();
        URLReader urlReader = new URLReader(console);
    }

    /**
     * Constructor con el parametro de la consola
     * @param console
     * @throws IOException
     */
    public URLReader(String console) throws IOException {
        checkingString(console);
    }

    /**
     * Verificando que la url ingresada por consola no este vacia y si es así poner como default
     * google.com
     * @param console
     * @throws IOException
     */
    public void checkingString(String console) throws IOException {
        if(console.isEmpty()){
            console = "http://www.google.com";
            url = new URL(console);
        }else{
            url = new URL(console);
        }
        createPage(url);
    }

    /**
     * Se crea la conexión con la pagina ingresada
     * @param url
     * @throws IOException
     */
    public void createPage(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        lectura(urlConnection);
    }

    /**
     * Lectura con la conexión por URLConnection
     * y  poniendo toda la información de la pagina en resultado.html
     * @param urlConnection
     */
    public void lectura(URLConnection urlConnection){
        //foundingHeader(urlConnection);
        System.out.println("Entra a lectura");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine = null;
            String htmlFile = "";
            while ((inputLine = reader.readLine()) != null) {
                //System.out.println(inputLine);
                htmlFile+=inputLine;
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("resultado.html"));
            System.out.println("ESTE ES EL HTML \n" + htmlFile);
            writer.write(htmlFile);
            writer.close();
        } catch (IOException x) {
            System.err.println(x);
        }
    }
    /**
    public void foundingHeader(URLConnection urlConnection){
        Set<Map.Entry<String, List<String>>> convertingHeader = urlConnection.getHeaderFields().entrySet();
        for(Map.Entry<String,List<String>> convert : convertingHeader){
            String header = convert.getKey();
            if(header!= null){
             //   System.out.println(header + " Este es el header\n");
            }
            List<String> lista = convert.getValue();
            for(String value:lista){
             //   System.out.println(value + " Valor de la lista\n");
            }
        }
    }**/
}


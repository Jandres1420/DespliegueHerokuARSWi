package CuartoEjercicio.sockets;

import java.net.*;
import java.io.*;

public class HttpServer {
    private static HttpServer _instance = new HttpServer();
    public static HttpServer get_instance() {
        return _instance;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean flags = true;
        while (flags) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            String path = "";
            Boolean primeraLinea = true;
            while ((inputLine = in.readLine()) != null) {
                if(primeraLinea){
                    path = inputLine.split(" ")[1];
                    URI resourse  = new URI(path);
                    System.out.println("Path : " + resourse.getPath());
                    primeraLinea = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + ReadFile.Readfile(new File("Networking/src/resources/index.html"))
                   ;

            out.println(outputLine);

            out.close();

            in.close();            clientSocket.close();
        }
        serverSocket.close();
    }
    private static int getPort(){
        if(System.getenv("PORT") !=null) return Integer.parseInt(System.getenv("PORT")) ;
        return 35000;
    }
}

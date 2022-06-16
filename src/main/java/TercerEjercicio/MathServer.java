package TercerEjercicio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MathServer {
    private static HashMap radianes;

    /**
     * Clase que abre la conexion a localhost por el puerto 35000
     * Recibe la información de ClientMath y dependiendo de la entrada
     * calcula las razones trigonometricas
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        radianes = new HashMap<String,Double>();

        try {
            serverSocket = new ServerSocket(35000);
        }catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        String comprobar = "cos";
        while ((inputLine = in.readLine()) != null ) {
            if(inputLine.equals("fun:sin")){
                comprobar = "sin";

            }else if(inputLine.equals("fun:tan")){
                comprobar ="tan";
            }else{
                double temp = 0;
                if(comprobar.equals("cos") || inputLine.contains("π")){
                    try {
                        String replace  = inputLine.replace("π",String.valueOf(Math.PI));
                        temp = Double.parseDouble(replace);
                        System.out.println("El valor de coseno es: "+ cos(temp));
                    } catch (NumberFormatException e) {
                        e.getStackTrace();
                    }
                }else if(comprobar.equals("tan")){
                    try {
                        String replace  = inputLine.replace("π",String.valueOf(Math.PI));
                        temp = Double.parseDouble(replace);
                        System.out.println("El valor de tangente es: "+ tan(temp));
                    } catch (NumberFormatException e) {
                        e.getStackTrace();
                    }
                }else if(comprobar.equals("sin")){
                    try {
                        String replace  = inputLine.replace("π",String.valueOf(Math.PI));
                        temp = Double.parseDouble(replace);
                        System.out.println("El valor de seno es: "+ sin(temp));
                    } catch (NumberFormatException e) {
                        e.getStackTrace();
                    }
                }
            }
            outputLine = "Respuesta" + inputLine ;
            out.println(outputLine);
            System.out.println("Este es mio : " + outputLine);
            if (outputLine.equals("Bye"))
                break;
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    /**
     * Calcula el coseno
     * @param valor
     * @return
     */
    public static double cos(double valor){
        return Math.cos(valor);
    }

    public void cambio(double valor){
        radianes.put("π/2",Math.PI/2);
        radianes.put("π/4",Math.PI/4);
        radianes.put("π",Math.PI);
        radianes.put("π/3",Math.PI/3);
        radianes.put("π/6",Math.PI/6);
    }

    /**
     * Calcula el seno
     * @param valor
     * @return
     */
    public static double sin(double valor){
        return Math.sin(valor);
    }

    /**
     * Calcula la tangente
     * @param valor
     * @return
     */
    public static double tan(double valor){
        return Math.tan(valor);
    }

}



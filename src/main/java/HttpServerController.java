import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HttpServerController {
    private List<String> tipoTexto;
    private List<String> tipoImg;

    private String file, fileType, outputLine;
    private Socket clientSocket;
    private PrintWriter out;

    /**
     * Constructor que se encarga de llenar las listas con los respectivos tipos de los archivos
     * EJ : .png, .html,.jss
     */
    public HttpServerController() {
        fillingLists();
    }


    /**
     * Llenando las listas
     */
    private void fillingLists() {
        tipoTexto = new ArrayList<String>();
        tipoImg = new ArrayList<String>();
        tipoTexto.add(".html");
        tipoTexto.add(".css");
        tipoTexto.add(".js");
        tipoImg.add(".jpg");
        tipoImg.add(".png");
    }

    /**
     * Dependiendo del ClientSocket pasado se comprueba el tipo de archivo que sea mandado por el path
     * si no se llega a encontrar ningun path se manda un error 404
     * @param clientSocket
     * @param path
     * @throws IOException
     */
    public void writingFile(Socket clientSocket, String path) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        try{
            file = path;
            this.clientSocket = clientSocket;
            boolean flag = true;
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            for (String l : tipoTexto) {
                if (file.contains(l)) {
                    fileType = file.split("\\.")[1];
                    System.out.println("ACAAAA");
                    System.out.println("ACAAAA");
                    System.out.println("ACAAAA");
                    System.out.println("ACAAAA");
                    System.out.println(fileType);
                    try {
                        creatingText(clientSocket);
                    } catch (Exception e) {
                        stringBuffer = error404(stringBuffer);
                        System.out.println(e);
                    }
                    out.println();
                    out.println(stringBuffer.toString());
                }
            }
            for (String i : tipoImg) {
                if (file.contains(i)) {
                    fileType = file.split("\\.")[1];
                    try{
                        creatingImg();
                    }catch (Exception e){
                        stringBuffer = error404(stringBuffer);
                        System.out.println(e);
                    }
                }
            }
        }catch (Exception e){
            error404(stringBuffer);
            System.out.println(e);
        }

    }

    /**
     * Creacion web de tipo texto
     * @param clientSocket
     * @throws IOException
     */
    public void creatingText(Socket clientSocket) throws IOException {
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        outputLine = "HTTP/1.1 200 OK\r\n";
        outputLine += "Content-Type: text/" + fileType + "\r\n";
        outputLine += "\r\n";
        outputLine += new String(Files.readAllBytes(Paths.get("resources" + file)), StandardCharsets.UTF_8);
        out.println(outputLine);
        out.close();
        clientSocket.close();
    }

    /**
     * Creación web tipo img
     */
    public void creatingImg() {
        try {
            DataOutputStream binary = new DataOutputStream(clientSocket.getOutputStream());
            File image = new File("resources" + file);
            FileInputStream filein = new FileInputStream(image);
            byte[] imageData = new byte[(int) image.length()];
            filein.read(imageData);
            filein.close();
            binary.writeBytes("HTTP/1.0 200 OK\r\n");
            binary.writeBytes("Content-Type: image/" + fileType + "\r\n");
            binary.writeBytes("Content-Length: " + imageData.length);
            binary.writeBytes("\r\n\r\n");
            binary.write(imageData);
            binary.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Error 404 lanzado si no se encuentra ninguna pagina.
     * @param stringBuffer
     * @return
     */
    public StringBuffer error404(StringBuffer stringBuffer) {

        outputLine = "HTTP/1.1 404 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Title of the document</title>\n" + "</head>"
                + "<body>"
                + "ERROR 404"
                + "</body>"
                + "</html>";
        stringBuffer = new StringBuffer();
        stringBuffer.append(outputLine);
        return stringBuffer;
    }

}

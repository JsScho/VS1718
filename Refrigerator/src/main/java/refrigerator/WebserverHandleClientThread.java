
package refrigerator;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class WebserverHandleClientThread implements Runnable{
    
    private Refrigerator refrigerator;
    private Socket clientSocket;
    
    public WebserverHandleClientThread(Refrigerator refrigerator,Socket socket){
        this.refrigerator = refrigerator;
        this.clientSocket = socket;
    }
    
    @Override
    public void run(){
        try{
            HttpRequestHeader header = new HttpRequestHeader();
            header.readHeader(clientSocket.getInputStream());
            header.printHeader();
            analyseHeader( header );
        } catch (NoSuchElementException e){
            this.sendResponse(400, null);
        } catch (IOException e){
            System.out.println(e.getLocalizedMessage());
        }
    }
    
    public void analyseHeader(HttpRequestHeader header){
        switch (header.getHttpMethod()){
            case "GET":
                if(header.getField("Accept").contains("text/html") || header.getField("Accept").contains("text/*")){
                    if (header.getFilePath().equals("/") || header.getFilePath().equals("/Refrigerator.html")){
                        sendResponse(200,refrigerator.generateHtml());
                    }
                    else 
                        sendResponse(404,null);
                }
                else{
                    sendResponse(406,null);
                } 
                break;
            default:
                sendResponse(405,null);
                break;
        } 
    }

    public void sendResponse(Integer StatusCode, String body){
        try{
            OutputStream out = clientSocket.getOutputStream();
            switch(StatusCode){
                case 200:
                    out.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
                    out.write(("Content-Length: "+String.valueOf(body.length())+"\r\n").getBytes("UTF-8"));
                    out.write("Content-Type: text/html\r\n\r\n".getBytes("UTF-8"));
                    out.write(body.getBytes());
                    break;
                case 400:
                    out.write("HTTP/1.1 400 Bad Request\r\n".getBytes("UTF-8"));
                    break;
                case 404:
                    out.write("HTTP/1.1 404 Not Found\r\n".getBytes("UTF-8"));
                    break;
                case 405:
                    out.write("HTTP/1.1 405 Method not allowed\r\n".getBytes("UTF-8"));
                    out.write("Allow: GET\r\n".getBytes("UTF-8"));
                    break;
                case 406:
                    out.write("HTTP/1.1 406 Not Acceptable\r\n\r\n".getBytes("UTF-8"));
                    break;
            }
            clientSocket.shutdownOutput();
                
        } catch(IOException e){
            System.out.println(e.getLocalizedMessage());
        }
    }
}

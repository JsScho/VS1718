
package refrigerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


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
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream out = null;
            String toSend = refrigerator.generateHtml();
            out = clientSocket.getOutputStream();
            out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes("UTF-8"));
            out.write(toSend.getBytes());
            //out.flush();
            //out.close();
            clientSocket.shutdownOutput();
            //tcpClientSocket.close();
        } catch(IOException e){
             System.out.println(e.getLocalizedMessage());
        }         /*finally {
                if (connectionSocket != null) {
                    try {
                        connectionSocket.close();
                        LOGGER.debug("Connection socket closed.");
                    } catch (IOException e) {
                        // Do nothing.
                    }
                }
        */
    }
}

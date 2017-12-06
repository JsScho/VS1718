
package refrigerator;

import java.io.*;
import java.net.*;


public class WebserverDelegateClientsThread implements Runnable {

    private ServerSocket tcpServerSocket;
    private Refrigerator refrigerator;

    
    public WebserverDelegateClientsThread(Refrigerator refrigerator){
        this.refrigerator = refrigerator;
        try{
            tcpServerSocket = new ServerSocket(3142);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        while(true){
            try{
                Socket tcpClientSocket = null;
                tcpClientSocket = tcpServerSocket.accept();
                new Thread(new WebserverHandleClientThread(refrigerator, tcpClientSocket)).start();

            } catch (IOException e) {
                System.out.println("TCPSocketServer could not accept incoming connection.\n" + e.getLocalizedMessage());
            } 
        }
    }
    
    private void printInputStream(BufferedReader bufferedReader) {
        try {
            System.out.println("Received some information: " + bufferedReader.readLine());
        } catch (IOException e) {
            System.out.println("Could not read from buffered reader.");
        }
    }
}


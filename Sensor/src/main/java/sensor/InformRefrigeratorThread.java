
package sensor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

public class InformRefrigeratorThread implements Runnable {
    
    private final String dataToSend;
    private final InetAddress refrigeratorIP;
    private final static int PORT = 3141;
    private DatagramSocket udpSocket;
    private byte[] buf = new byte [256];
    
    public InformRefrigeratorThread(String data,InetAddress refrigIP){
        dataToSend = data;
        refrigeratorIP = refrigIP;
    }
    public void sendMsg(String msg,InetAddress destIP)throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf,buf.length, destIP, PORT);
        udpSocket.send(packet);
    }
    
    @Override
    public void run(){ 
        try{
            udpSocket = new DatagramSocket();
        } catch (IOException e){
            System.out.println("Failed to create UDP socket "+e.getLocalizedMessage());
        }
        try {
            sendMsg(dataToSend,refrigeratorIP);
        } catch (IOException e){
            System.out.println("Failed to send UDP packge to refrigerator"+e.getLocalizedMessage());
        }
    }
}


package refrigerator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class SensorServerThread implements Runnable {
    
    private Refrigerator refrigerator;
    private int port;
    private DatagramSocket udpSocket;
    private byte[] buf = new byte[256];
    
    public SensorServerThread(int port, Refrigerator zentrale) throws IOException {
        this.refrigerator = zentrale;
        this.port = port;
        udpSocket = new DatagramSocket( port );
    }
    @Override
    public void run(){
        while(true){
            try{
                DatagramPacket udpPacket = new DatagramPacket(buf,buf.length);
                udpSocket.receive(udpPacket);
                printPacketData(udpPacket);
                refrigerator.parseCommand(new String(udpPacket.getData()));
            } catch(IOException e){
                System.out.println("Failed to receive package or to inform Refrigerator.");
            }
        }
    }
    public void printPacketData(DatagramPacket udpPacket){
        InetAddress address = udpPacket.getAddress();
        int port = udpPacket.getPort();
        int length = udpPacket.getLength();
        String payload = new String (udpPacket.getData());
        System.out.println("Received a packet: IP:Port: "+address+":"+port+", length: "+
                length+", payload: "+payload+"\n");
    }
}

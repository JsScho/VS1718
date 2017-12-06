
package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.InetAddress;

public class Client {

    private final InetAddress refrigeratorIP;
        
    public static void main(String[] args) {
        try{
            Client client = new Client(InetAddress.getByName(args[0]));
            client.run();
        } catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    
    public Client(InetAddress address){
        refrigeratorIP = address;
    }
    
    public void run(){
        
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        System.out.println("--------------- Client ---------------");
        System.out.println("type \"info\" to make refrigerator print content information (on its own terminal) \n");
        int wrongCounter = 0;
        String input = new String ("");
        while (true){
            input = "";
            System.out.print("> ");
            try{
                input = br.readLine();
            } catch(IOException e){
                System.out.println("Failed to read user input. Type \"info\" for refrigerator content information.");
            }
            
            if ("info".equals(input)){
                System.out.println("A request is being sent to the refrigerator ... ");
                requestInfo();
                wrongCounter = 0;
            } else {
                ++ wrongCounter;
                if (wrongCounter >= 2){
                    System.out.println("Need help? Type \"info\" for refrigerator content information.");
                    wrongCounter = 0;
                }
            }
        }
    }
    public synchronized void requestInfo(){
        Runnable inform = new InformRefrigeratorThread(new String("info;"),refrigeratorIP);
        new Thread(inform).start();
    }
}

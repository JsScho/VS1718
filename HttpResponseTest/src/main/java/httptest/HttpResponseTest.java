
package httptest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class HttpResponseTest {
    
    private String refrigeratorIP;
    private Long successfulResponse;
    private Long wrongResponse;
    private Long testDuration;
    
    public static void main(String[] args) {
        HttpResponseTest test = new HttpResponseTest(args[0],args[1]);
        test.run();
    }
    
    public HttpResponseTest(String ip,String testDurationInMs){
        try{
            testDuration = Long.valueOf(testDurationInMs);
            refrigeratorIP = ip;
            successfulResponse = Long.valueOf(0);
            wrongResponse = Long.valueOf(0);
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
    
    public void run(){
        responseQuality();
        responseQuantity();
    }
    public void responseQuantity(){
        try {
            
            Runnable r1 = new SpamRequestsThread(this,testDuration,refrigeratorIP);

            Thread t1 =new Thread(r1);
            t1.start();
            Thread t2 =new Thread(r1);
            t2.start();
            Thread t3 =new Thread(r1);
            t3.start();
            Thread t4 =new Thread(r1);
            t4.start();
            Thread t5 =new Thread(r1);
            t5.start();        

            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            
            System.out.println("Correct responses: "+String.valueOf(this.successfulResponse));
            System.out.println("Wrong responses: "+String.valueOf(this.wrongResponse));            
            
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
    public void responseQuality(){
        //Syntax errors
        checkResponse("GET / HTTP/1.1\r\n","400"); //no accepted formats listed
        checkResponse("GET / \r\n","400");
        checkResponse("GET / HTTP/1.1\r\nAccept: text/html","400"); // missing newline at the end
        checkResponse("GET / HTTP/1.1\r\n Accept: text/html","400");
        
        //allowed
        checkResponse("GET / HTTP/1.1\r\nAccept: text/html\r\n","200");
        checkResponse("GET / HTTP/1.1\r\nAccept: text/*\r\n","200");
        checkResponse("GET / HTTP/1.1\r\nAccept: text/*,text/html\r\n","200");
        checkResponse("GET /Refrigerator.html HTTP/1.1\r\nAccept: text/html\r\n","200");
        
        //other errors
        checkResponse("GET /index.html HTTP/1.1\r\nAccept: text/html\r\n","404");
        checkResponse("GET / HTTP/1.1\r\nAccept: image/jpeg\r\n","406");
        checkResponse("POST / HTTP/1.1\r\n","405");
    }
    public void checkResponse(String msg, String expectedCode){
        try{
            Socket socket = new Socket(refrigeratorIP,3142);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                       
            OutputStream out = socket.getOutputStream();
            out.write(msg.getBytes("UTF-8"));
            
            String statusLine = inFromServer.readLine();
            Scanner scanner = new Scanner(statusLine);
            String http = scanner.next();
            String code = scanner.next();
            
            System.out.println(msg+" ; expected= "+expectedCode+"; actual= "+code+"\n\n");
            socket.shutdownOutput();
            
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    };
    
    public synchronized void addToFail(Long toAdd){
        this.wrongResponse += toAdd;
    }
    public synchronized void addToSuccess(Long toAdd){
        this.successfulResponse += (toAdd);
    }
}

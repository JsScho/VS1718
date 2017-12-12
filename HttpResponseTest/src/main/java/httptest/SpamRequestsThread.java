/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httptest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author scurr
 */
public class SpamRequestsThread implements Runnable{
    
    private HttpResponseTest test;
    private Long testDuration;
    private String refrigeratorIP;
    
    public SpamRequestsThread(HttpResponseTest newtest, Long dur,String ip){
        this.test = newtest;
        this.testDuration = dur;
        this.refrigeratorIP = ip;
    }
    
    @Override
    public void run(){
        try{
            Long success = Long.valueOf(0);
            Long fail = Long.valueOf(0);
            Long startTime = System.currentTimeMillis();
            while(System.currentTimeMillis()-startTime < testDuration){ 
                Socket socket = new Socket(refrigeratorIP,3142);
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                OutputStream out = socket.getOutputStream();
                out.write("GET / HTTP/1.1\r\nAccept: text/html\r\n".getBytes("UTF-8"));

                String statusLine = inFromServer.readLine();
                if (statusLine.equals("HTTP/1.1 200 OK")){
                    ++success;
                }
                else {
                    ++fail;
                }
                socket.shutdownOutput();
            }
            test.addToFail(fail);
            test.addToSuccess(success);
            
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
}

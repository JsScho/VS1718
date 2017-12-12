
package refrigerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class HttpRequestHeader {
    private String httpMethod;
    private String filePath;
    private String httpVersion;
    private HashMap<String,String> fields = new HashMap<> ();
    
    public void readHeader(InputStream input) throws NoSuchElementException {
        try{
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(input));
            if (!inFromClient.ready()){
                throw new NoSuchElementException ();
            }
            String statusLine = readLine(inFromClient);
            Scanner sc = new Scanner(statusLine);
            httpMethod = sc.next();
            filePath = sc.next();
            httpVersion = sc.next();
            
            String tmp = null;
            while (inFromClient.ready() ){
                tmp = readLine(inFromClient);
                fields.put( tmp.substring(0,tmp.indexOf(':')) , tmp.substring(tmp.indexOf(':')+2) );
            }
            
        } catch(IOException e){
            System.out.println("Could not read http Request Header");
            System.out.println(e.getLocalizedMessage());
        } catch(IndexOutOfBoundsException e){
            return;  
        } catch (Exception e){
            return;
        }
    }
 
    //alternatively to readLine from bufferedReader.
    //this doesnt block if no newline character or carraige return is found and instead returns null.
    //reads characters until \n , \r\n or \r is found. if stream ends before that -> null String is returned
 
    public String readLine(BufferedReader in){
        try{
            String line = "";
            while(in.ready()){
                Character nextChar = (char) in.read();
                if (nextChar.equals('\n')){
                    return line;
                }
                if (nextChar.equals('\r')){
                    if (in.ready()){
                        in.mark(1);
                        Character next = (char) in.read();
                        if (next.equals('\n')){
                            return line;
                        }
                        else{ 
                            in.reset();
                            return line;
                        }
                    }
                    else return line;
                }
                line = line.concat(nextChar.toString());
            }
            return null;
        
        } catch(Exception e){
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }
    
    public void printHeader(){
        try{
            System.out.println(httpMethod+" "+filePath+" "+httpVersion);
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                System.out.println(entry.getKey() +": "+ entry.getValue());
            }
        } catch (NullPointerException e){
            return;
        }
    }
    
    public String getHttpVersion(){
        return httpVersion;
    }
    public String getHttpMethod(){
        return httpMethod;
    }
    public String getFilePath(){
        return filePath;
    }
    public String getField(String key){
        return fields.get(key);
    }
}

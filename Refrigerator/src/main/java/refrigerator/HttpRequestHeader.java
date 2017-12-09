
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
            
            String statusLine = inFromClient.readLine();
            Scanner sc = new Scanner(statusLine);
            httpMethod = sc.next();
            filePath = sc.next();
            httpVersion = sc.next();
            
            String tmp = null;
            while ((tmp = inFromClient.readLine()) != null){
                fields.put( tmp.substring(0,tmp.indexOf(':')) , tmp.substring(tmp.indexOf(':')+2) );
            }
            
        } catch(IOException e){
            System.out.println("Could not read http Request Header");
            System.out.println(e.getLocalizedMessage());
        } catch(IndexOutOfBoundsException e){
            return;  
        }
    }
    
    public void printHeader(){
    System.out.println(httpMethod+" "+filePath+" "+httpVersion);
    for (Map.Entry<String, String> entry : fields.entrySet()) {
            System.out.println(entry.getKey() +": "+ entry.getValue());
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


package refrigerator;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Refrigerator {

    private final List<Food> content = new ArrayList<> ();
    private final Integer historyEntriesPrinted;
    private ReadWriteLock rwlock = new ReentrantReadWriteLock(true);
    
    public static void main(String[] args){    
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException();
            }
            Refrigerator refrigerator = new Refrigerator(Integer.valueOf(args[0]));
            refrigerator.startUDPSocketServer();
            refrigerator.startTCPSocketServer();
        
        } catch (IllegalArgumentException e){
            System.out.println(e.toString());
            System.out.println("1 parameter expected, "+ String.valueOf(args.length)+" received");
            System.out.println("parameter is \"historyEntriesPrinted\" ");
            System.out.println("Example: \"java -jar client.jar 20\"  "
                    + "->  would print maximum the last 20 entries for each food");
        } catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }        
    }
    
    public Refrigerator(Integer entriesShown) {
        this.historyEntriesPrinted = entriesShown;
    }
    
    public void startUDPSocketServer(){
        try {
            Runnable r1 = new SensorServerThread(3141,this);
            new Thread(r1).start();
        } catch(IOException e){
            System.out.println("class refrigerator: Failed to create UDPSocketServer.");
        } 
    }
    public void startTCPSocketServer(){
        Runnable r1 = new WebserverDelegateClientsThread(this);
        new Thread(r1).start();            
    }
 
    public void parseCommand( String mes ) {
        try {
            Scanner sc = new Scanner(mes).useDelimiter(";");
            String type = sc.next();
            switch(type){
                case "sensor":{
                    addHistoryEntry(sc.next(),sc.nextInt(),sc.nextLong());
                    break;
                }
            }
        } catch (Exception e){
            System.out.println("Received a command but failed to execute it.");
        }
    }
    
    public String generateHtml(){
        rwlock.readLock().lock();
        try{
            StringBuilder builder = new StringBuilder();
            builder.append("<!DOCTYPE html><html><head><title>Refrigerator</title></head>");
            builder.append("<body>");
            builder.append("<section>");
            builder.append("<h1> Refrigerator Information </h1>");
            builder.append("<h2> Current content </h2>");
            for (Food f : content ){
                builder.append("<p>"+f.getName()+": "+f.getCurrentLevel()+"</p>");
            }
            builder.append("<h2> Content History </h2>");
            for (Food f : content ){
                List<HistoryEntry> history = f.getFillHistory();
                int entriesPrinted = history.size();
                if (entriesPrinted > historyEntriesPrinted){
                    entriesPrinted = historyEntriesPrinted;
                }   
                builder.append("<p> Last "+entriesPrinted+" log entries for item \""+f.getName()+"\"</p>");
                for(int i=0; i<entriesPrinted; ++i){
                    builder.append("<p>"+history.get(history.size()-1-i).toString()+"</p>");
                }
                builder.append("<br>");
            }
            builder.append("</section>");
            builder.append("</body>");
            builder.append("</html>");
            return builder.toString();
        } finally {
            rwlock.readLock().unlock();
        }
    }
    
    public void addHistoryEntry(String foodname,Integer amount,Long time){
        rwlock.writeLock().lock();
        try{
            for(Food f : content){
                if(f.getName().equals(foodname)){
                    HistoryEntry previousEntry = f.getFillHistory().get(f.getFillHistory().size()-1);
                    if (!previousEntry.getAmount().equals( amount ) && 
                            !(Long.valueOf(previousEntry.getTimestamp()) >= (time))){ //checkDuplicate
                        f.addHistoryEntry(time, amount);
                    }
                    return;
                }
            };
            Food food = new Food(new String(foodname));
            food.addHistoryEntry(time, amount);
            content.add(food);
        }
        finally{
            rwlock.writeLock().unlock();
        }
    }
    
    /*
    public void printHistory(){
        rwlock.readLock().lock();
        try{
            for (Food f : content ){
                f.printHistory(this.historyEntriesPrinted);
            }
            System.out.println();
        } finally{
            rwlock.readLock().unlock();
        }
    }
    
    public void printContent(){
        rwlock.readLock().lock();
        try{
            System.out.println("Current Content: ");
            for (Food f : content ){
                System.out.println( f.getName()+": "+f.getCurrentLevel());
            }
            System.out.println();
        } finally{
            rwlock.readLock().unlock();
        }
    }    
    */
    
    private ReadWriteLock ReentrantReadWriteLock() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Food> getContent(){
        return content;
    };
}

package sensor;

import java.io.IOException;
import java.net.InetAddress;

public class Sensor {
    
    private final String name;
    private Integer currentAmount;
    private final Integer maxAmount;
    private final InetAddress refrigeratorIP;
    
    public static void main(String[] args) {
        try {
            if (args.length != 6) {
                throw new IllegalArgumentException();
            }            
            Sensor sensor = new Sensor(args[0],Integer.valueOf(args[1]),Integer.valueOf(args[2]),InetAddress.getByName(args[5]));
            sensor.informRefrigerator();
            sensor.changeOverTime(Integer.valueOf(args[3]),Integer.valueOf(args[4]));
            sensor.listenToUDPCommands();
        
        } catch (IllegalArgumentException e){
            System.out.println(e.toString());
            System.out.println("6 parameters expected, "+ String.valueOf(args.length)+" received");
            System.out.println("parameters are \"name\" \"initAmount\" \"maxAmount\" "
                    + "\"changeIntervalInSeconds\" \"changeAmount\" \"refrigeratorIP\" ");
            System.out.println("Example: java -jar sensor.jar Milch 200 1000 10 -15 192.168.0.35");
        } catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    
    public Sensor(String name,Integer initAmount, Integer maxAmount,InetAddress refrigip) {
        this.name = name;
        this.currentAmount = initAmount;
        this.maxAmount = maxAmount;
        this.refrigeratorIP = refrigip;
    }
    
    public synchronized void changeCurrentAmount(int diff ){
        if (currentAmount + diff < 0){
            currentAmount = 0;
        } else if (currentAmount + diff > maxAmount){
            currentAmount = maxAmount;
        } else { 
            currentAmount += diff;
        }
        System.out.println("current amount changed to "+String.valueOf(currentAmount));
        informRefrigerator();
    }
    
    public void changeOverTime(Integer interval, Integer amount){
        Runnable consume = new ChangeOverTimeThread(this, interval,amount);
        new Thread(consume).start();
    }
    
    public synchronized void informRefrigerator(){
        String update = new String ( "sensor;" + name + ";"+String.valueOf(currentAmount)+";"+
                String.valueOf(System.currentTimeMillis())+";");
        Runnable inform = new InformRefrigeratorThread(update,this.refrigeratorIP);
        new Thread(inform).start();
    }

    public void listenToUDPCommands(){
        
        while (true){ 
            // Port aufmachen um Befüllung des Kühlschranks entgegenzunehmen + zu verarbeiten
        }
    }
}
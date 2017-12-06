
package sensor;

public class ChangeOverTimeThread implements Runnable {
    private final Sensor sensor;
    private final int consumeInterval; // in seconds
    private final int consumeAmount;
    
    public ChangeOverTimeThread( Sensor sensor, Integer interval, Integer amount ){
        this.sensor = sensor;
        this.consumeInterval = interval;
        this.consumeAmount = amount;
    }
    @Override
    public void run(){
         do { 
            try{
                Thread.sleep(consumeInterval * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            sensor.changeCurrentAmount(consumeAmount);
        } while (true);
    }
}

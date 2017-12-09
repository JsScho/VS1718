
package refrigerator;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryEntry {
    private final Long timestamp;
    private final Integer amount;
    
    public HistoryEntry(long time, int amount){
        this.timestamp = time;
        this.amount = amount;
    }
    public Integer getAmount() {
        return amount;
    }
    public Long getTimestamp() {
        return timestamp;
    }
    public void print(){
        Format format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date(timestamp);
        System.out.println(String.valueOf(amount)+"; \t"+format.format(date));
    }
    public String toString(){
        Format format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date(timestamp);
        return new String(String.valueOf(amount)+"\t"+format.format(date));
    }
}

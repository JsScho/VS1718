
package refrigerator;

import java.util.List;
import java.util.ArrayList;

public class Food {
    private final String name;
    private final List<HistoryEntry> fillHistory = new ArrayList<> ();
    
    public Food(String newname){
        name = newname;
    }
    public Integer getCurrentLevel(){
        return fillHistory.get(fillHistory.size()-1).getAmount();
    } 
    public String getName() {
        return name;
    }
    public void addHistoryEntry(long timestamp, Integer newAmount){
        fillHistory.add(new HistoryEntry(timestamp,newAmount));
    }
    public List<HistoryEntry> getFillHistory(){
        return fillHistory;
    }
    public void printHistory(Integer maxEntriesPrinted){
        int entriesPrinted = fillHistory.size();
        if (entriesPrinted > maxEntriesPrinted){
            entriesPrinted = maxEntriesPrinted;
        }   
        System.out.println("Last "+entriesPrinted+" log entries for item \""+name+"\"");
        for (int i=0; i<entriesPrinted; ++i){
            fillHistory.get(fillHistory.size()-1-i).print();
        }
        System.out.println();
    }
}

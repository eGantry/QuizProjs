/*
 * TotalTracker class, by Ron Ruffin
 */
package keytotaler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Ron Ruffin
 */
public class TotalTracker {
    private HashMap<String, Integer> totals;

    public TotalTracker() {
        totals = new HashMap();
    }

    public void addToTotal (String key, int amountToAdd) {
        //Add the passed-in amount to the indicated total based on the passed-in key.
        
        Integer value = totals.get(key);
        
        if (value != null) {
            value += amountToAdd;
        }
        else {
            value = amountToAdd;
        }
        totals.put(key, value);
    }
    
    public void printOutTotals() {
        //Loop through the set of totals, and print out each total.
        Set totalSet = totals.entrySet();
        
        Iterator it = totalSet.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> eachEntry = (Map.Entry<String, Integer>)it.next();
            System.out.println("The total for " + eachEntry.getKey() + " is " + eachEntry.getValue() + ".");
        }
        
    }
    
    public Set<String> getKeys() {
        return totals.keySet();
    }
}

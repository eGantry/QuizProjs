package countingBoxes;
import java.util.Scanner;

/**
 *
 * @author Ron Ruffin
 */
public class CountingBoxes {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Get user's # items entry.
        Scanner in = new Scanner(System.in);
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.print("Type in an item count (or 0 to end program):  ");
            int itemCount = in.nextInt();
            if (itemCount > 0) {
                figureOutBoxes(itemCount);
            }
            else {
                keepGoing = false;
            }
        }
    }
    
    static int largeBoxCount = 0;
    static int smallBoxCount = 0;
    static int totalShippingCost = 0;
    static final int SMALL_SHIPPING_COST = 100;     //Simulates small box cost
    static final int LARGE_SHIPPING_COST = 150;     //Simulates large box cost, more than 1 small box, and less than 2 small boxes.
    
    private static void resetValues() {
        largeBoxCount= 0;
        smallBoxCount = 0;
        totalShippingCost = 0;
    }
    
    private static void figureOutBoxes(int itemCount) {
        /*
        RULES:
        Small box holds 1 item.
        Large box holds 4 to 6 items, and never less than 4 items.
        Small box cost < large box cost < 2 small boxes.
        */
        resetValues();
        int remainingItems = itemCount;
        int sixes = remainingItems / 6;
        remainingItems = remainingItems % 6;
        largeBoxCount += sixes;
        
        if ((remainingItems == 5) || (remainingItems == 4)) {
            largeBoxCount += 1;
            remainingItems = 0;
        }
        else if (remainingItems == 1) {
            smallBoxCount += remainingItems;
            remainingItems = 0;
        }
        else if (remainingItems > 0) {
            if (largeBoxCount > 0) {
                largeBoxCount += 1;
            }
            else {
                smallBoxCount += remainingItems;
            }
            remainingItems = 0;
        }

        totalShippingCost = (largeBoxCount * LARGE_SHIPPING_COST) + (smallBoxCount * SMALL_SHIPPING_COST);
        
        StringBuilder sbTotals = new StringBuilder();
        sbTotals.append("For " + itemCount + " items:  " + largeBoxCount + " large boxes + " + smallBoxCount + " small boxes \n");
        sbTotals.append("Total cost:  $" + totalShippingCost + ".\n\n");
        System.out.println(sbTotals.toString());
        
    }
    
}

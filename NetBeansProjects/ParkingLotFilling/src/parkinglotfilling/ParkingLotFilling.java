/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkinglotfilling;

/**
 *
 * @author quizz
 */
public class ParkingLotFilling {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        boolean found = false;
        final int lotAStart = 93;
        final int lotBStart = 44;
        final int lotAIncrement = 31;
        final int lotBIncrement = 16;
        
        int lotATotal = lotAStart;
        int lotBTotal = lotBStart;
        
        System.out.println("Lot A starts out with " + lotATotal + " cars, and lot B starts out with " + lotBTotal + " cars.");
        for (int hoursPassed = 1; !found && hoursPassed <= 24; hoursPassed++) {
            lotATotal += lotAIncrement;
            lotBTotal += lotBIncrement;
            
            System.out.println("After " + hoursPassed + " hours, lot A has " + lotATotal + " cars, and lot B has " + lotBTotal + " cars.");
            
            if (lotBTotal != 0) {
                if (lotATotal == lotBTotal * 2) {
                    found = true;
                    break;
                }
            }
        }
        
        if (found) {
            System.out.println("Found solution!");
        }
        else {
            System.out.println("Did not find solution!");
        }
        
        
        
    }
    
}

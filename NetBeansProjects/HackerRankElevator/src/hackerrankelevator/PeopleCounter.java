/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackerrankelevator;

/**
 *
 * @author quizz
 */
public class PeopleCounter {
    /*  
    This class counts people going into, or out of, the elevator, and reports on the stats.
    */
    
    private int peopleCount = 0;
    
    
    public void incrementCount() {
        peopleCount += 1;
    }
    
    public void resetCount() {
        peopleCount = 0;
    }
    
    public void reportCount(String activity, int floor) {
        String folksWord = "people";
        String verbPlural = "";
        String whichWay;
        
        if (activity.equals("enter")) {
            whichWay = "from";
        }
        else {
            whichWay = "onto";
        }
        
        if (peopleCount == 1) {
            folksWord = "person";
            verbPlural = "s";
        }
        
        if (peopleCount > 0) {
            if (floor == 0) {
                System.out.println(peopleCount + " " + folksWord + " " + activity + verbPlural + " the elevator " + whichWay + " Lobby.");
             }
            else {
                System.out.println(peopleCount + " " + folksWord + " " + activity + verbPlural + " the elevator " + whichWay + " floor " + floor + ".");
            }
        }
    }
}

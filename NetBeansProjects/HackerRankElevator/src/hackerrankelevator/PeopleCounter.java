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
    
    public void reportCount(String activity) {
        String folksWord = "people";
        String verbPlural = "";
        
        if (peopleCount == 1) {
            folksWord = "person";
            verbPlural = "s";
        }
        
        if (peopleCount > 0) {
            System.out.println(peopleCount + " " + folksWord + " " + activity + verbPlural + " the elevator.");
        }
    }
}

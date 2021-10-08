/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author sillyrabbit
 */
public class JavaApplication12 {

    enum name {
        SMITH("Smith"),
        JONES("Jones"),
        JOHNSON("Johnson"),
        HARVEY("Harvey");
        String name;
        name(String nameParm) {
            name = nameParm;
        }
        String getName() {
            return name;
        }
    }
    
    static final String[] smithYes = {"Smith", "Yes"};
    static final String[] smithNo = {"Smith", "No"};
    static final String[] jonesYes = {"Jones", "Yes"};
    static final String[] jonesNo = {"Jones", "No"};
    static final String[] taylorYes = {"Taylor", "Yes"};
    static final String[] taylorNo = {"Taylor", "No"};
    static final String[] harveyYes = {"Harvey", "Yes"};
    static final String[] harveyNo = {"Harvey", "No"};
            
            
    static ArrayList<String[]> oldStuff = new ArrayList<String[]>();
    static ArrayList<String[]> newExact = new ArrayList<String[]>();
    static ArrayList<String[]> newSuperset = new ArrayList<String[]>();
    static ArrayList<String[]> newSubset = new ArrayList<String[]>();
    static ArrayList<String[]> newDifferent = new ArrayList<String[]>();
    
    private static void setupStuff() {
        
        oldStuff.add(smithYes);
        oldStuff.add(jonesNo);
        oldStuff.add(taylorNo);
        oldStuff.add(harveyYes);

        newExact.add(smithYes);
        newExact.add(jonesNo);
        newExact.add(taylorNo);
        newExact.add(harveyYes);
        
        newSuperset.add(smithYes);
        newSuperset.add(jonesYes);
        newSuperset.add(taylorNo);
        newSuperset.add(harveyYes);
        
        newSubset.add(smithYes);
        newSubset.add(jonesNo);
        newSubset.add(taylorNo);
        newSubset.add(harveyNo);
        
        newDifferent.add(smithNo);
        newDifferent.add(jonesYes);
        newDifferent.add(taylorYes);
        newDifferent.add(harveyNo);

    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        setupStuff();
        compareStuff();
    }
    
    private static boolean compareStuff() {
        boolean result = false;
        
        ArrayList<String[]> newStuff;
        
        for (int whichSet = 0; whichSet < 4; whichSet++) {
            int oldYes = 0;
            int newYes = 0;
            int matches = 0;
            String whichCompare = null;
            boolean match = false;
            
            switch (whichSet) {
                case 0:
                    newStuff = newSubset;
                    whichCompare = "newSubset";
                    break;
                case 1:
                    newStuff = newSuperset;
                    whichCompare = "newSuperset";
                    break;
                case 2:
                    newStuff = newDifferent;
                    whichCompare = "newDifferent";
                    break;
                default:
                    newStuff = newExact;
                    whichCompare = "newExact";
                    break;
                
            }

            for (int whichRow = 0; whichRow < oldStuff.size(); whichRow++) {
                String[] eachOldRow = oldStuff.get(whichRow);
                String[] eachNewRow = newStuff.get(whichRow);
                
                if (eachOldRow[1].equals("Yes")) {
                    oldYes++;
                }
                if (eachNewRow[1].equals("Yes")) {
                    newYes++;
                }
                if (((eachOldRow[0].equals(eachNewRow[0])) && (eachOldRow[1].equals(eachNewRow[1]) && eachNewRow[1].equals("Yes")))) {
                    matches++;
                }
                
            }
            
            if ((matches == oldYes) && (oldYes == newYes)) {
                match = true;
            }
            
            System.out.println(whichCompare + " matches:  " + match);
            
        }

       
        return result;
    }
    
    
    
}

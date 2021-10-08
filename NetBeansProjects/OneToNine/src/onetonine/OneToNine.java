/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onetonine;

import java.util.Random;

/**
 *
 * @author sillyrabbit
 */
public class OneToNine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Random rand = new Random();
        int[] segTotals = {-1,-1,-1};
        int howManyTries = 0;
        
        while ((segTotals[0] != 21) || (segTotals[1] != 21) || (segTotals[2] != 21)) {
            Integer[] newSequence = getRandomValues(rand);
            Integer[][] segment = populateSegments(newSequence);
            String sequenceStr = "";

            for (int whichVal = 0; whichVal < 9; whichVal++) {
                sequenceStr += newSequence[whichVal].toString();
                if (whichVal < 8) {
                    sequenceStr += ", ";
                }
                else {
                    sequenceStr += ".";
                }
            }

            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(sequenceStr);
            for (int whichSegment = 0; whichSegment < 3; whichSegment++) {
                String segmentStr = "";
                int segTotal = 0;
                segTotals[whichSegment] = 0;
                for (int whichVal = 0; whichVal < 4; whichVal++) {
                    segTotal += segment[whichSegment][whichVal];
                    segTotals[whichSegment] = segTotal;
                    segmentStr += segment[whichSegment][whichVal].toString();
                    if (whichVal < 3) {
                        segmentStr += ", ";
                    }
                    else {
                        segmentStr += ":  ";
                    }
                }    
                segmentStr += segTotal + ".";
                System.out.println(segmentStr);
            }
            howManyTries++;
        }
        if (howManyTries == 1) {
            System.out.println("Found it in only " + howManyTries + " try!!");
        }
        else {
            System.out.println("Found it in only " + howManyTries + " tries!!");
        }
        
        
        
    }
    /*
    The purpose of this project is to solve a puzzle which presents the following problem.
    Generate a sequence of nine numbers valued 1 through 9.
    
    Three values are already placed for you as shown.
    
    c1, 9, s1, c2, s2, 6, c3, s3, 4, c1
    
    The values can be segmented as shown.
    
    c1, 9, s1, c2 == 21
    c2, 6, s2, c3 == 21 
    c3, s3, 4, c1 == 21
    
    Each segment must total 21.
    Each value in the sequence can only be used once.
    
    */
    
    static Integer[] getRandomValues(Random rand) {
        Integer[] result = {-1,9,-1,-1,-1,6,-1,-1,4,-2};
        
        int whichValue = -1;
        for (Integer eachValue : result) {
            whichValue++;
            if (eachValue == -1) {
                Integer newValue = rand.nextInt((9 - 1) + 1) + 1;
                while (arrayContains(result, newValue)) {
                    newValue = rand.nextInt((9 - 1) + 1) + 1;
                }
                result[whichValue] = newValue;
            }
        }
        result[9] = result[0];
        
        return result;
    }
    
    static boolean arrayContains(Integer[] array, int value) {
        boolean result = false;
        
        for (int eachValue : array) {
            if (eachValue == value) {
                result = true;
                break;
            }
        }
        return result;
    }
    
    static Integer[][] populateSegments(Integer[] sequence) {
        Integer[][] result = {{-1,-1,-1,-1},{-1,-1,-1,-1},{-1,-1,-1,-1}};
        
        for (int whichSegment = 0; whichSegment < 3; whichSegment++) {
            for (int whichPosition = 0; whichPosition < 4; whichPosition++) {
                result [whichSegment][whichPosition] = sequence[(whichSegment * 3) + whichPosition];
            }
        }
        return result;
    }
    
}

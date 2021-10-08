/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edittheiliad;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RRuffin
 */
public class DigitProcessor {
        /*
        Find the combination of digit values corresponding to the following digit-letter-names:
        E, T, H, L, D, I, and A,
        which make the following math assertion true:
        "EDIT+THE=ILIAD", and which conform to the following rules:
        The values of "E" "T" and "I" must be non-zero.
        All letters must have different digit values.
        */

    private List<Integer> allLegalCombos = new ArrayList<Integer>();
    
    private Integer E, T, H, L, D, I, A;  //Digit letters.
    
    public void findAllLegalCombos() {
        /*
        Loop through all values between 0 and 10,000,000, each representing the combination of digits which form each value.
        For each digit combination, break out digit values for each letter.
        if a digit combination conforms to the rules above, add it to the set.
        */
        for (int eachValue = 0; eachValue < 10000000; eachValue++) {
            breakOutDigits(eachValue);
            if (!anyMatchingDigits()) {
                if (E != 0 && T != 0 && I != 0) {
                    allLegalCombos.add(eachValue);
                }
            }
        }
        
        System.out.println("Found " + allLegalCombos.size() + " legal digit combinations.");
        
        
    }
    
    public void testLegalCombos() {
        /*
        Loop through all legal digit value combinations.
        Assemble the mathmatic assertion using the letters' assigned digit-values and their positions as decimal places.
        If the math expression holds true, output the digits' values.
        */
        for (Integer eachLegalCombo : allLegalCombos) {
            breakOutDigits(eachLegalCombo);
            Integer EDIT = (E*1000) + (D*100) + (I*10) + (T*1);
            Integer THE = (T*100) + (H*10) + (E*1);
            Integer ILIAD = (I*10000) + (L*1000) + (I*100) + (A*10) + (D*1);
            if (EDIT + THE == ILIAD) {
                System.out.println("EDIT + THE = ILIAD:  True, for values below:");
                System.out.println("EDIT = " + EDIT);
                System.out.println("THE = " + THE);
                System.out.println("ILIAD = " + ILIAD);
                
                System.out.println("E = " + E);
                System.out.println("T = " + T);
                System.out.println("H = " + H);
                System.out.println("L = " + L);
                System.out.println("D = " + D);
                System.out.println("I = " + I);
                System.out.println("A = " + A);
            }
        }
    }
    
    
    private void breakOutDigits(int numericValue) {
        int remainingValue = numericValue;
        if (remainingValue < 10000000) {
            E = remainingValue/1000000;
            remainingValue -= E*1000000;
            T = remainingValue/100000;
            remainingValue -= T*100000;
            H = remainingValue/10000;
            remainingValue -= H*10000;
            L = remainingValue/1000;
            remainingValue -= L*1000;
            D = remainingValue/100;
            remainingValue -= D*100;
            I = remainingValue/10;
            remainingValue -= I*10;
            A = remainingValue;
            remainingValue -= A;
            //remainingValue should now equal zero.
        }
    }
    
    private boolean anyMatchingDigits() {
        boolean result = false;
        List<Integer> digitValues = new ArrayList<Integer>();
        List<Integer> seenValues = new ArrayList<Integer>();
        
        digitValues.add(E);
        digitValues.add(T);
        digitValues.add(H);
        digitValues.add(L);
        digitValues.add(D);
        digitValues.add(I);
        digitValues.add(A);
        
        for (Integer eachValue : digitValues) {
            if (seenValues.contains(eachValue)) {
                result = true;
                break;
            }
            else {
                seenValues.add(eachValue);
            }
        }
        
        return result;
    }
    
}
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
public class EditTheIliad {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        /*
        "EDIT+THE=ILIAD"
        Find the combination of digit values corresponding to the letter-names shown above,
        E, T, H, L, D, I, and A,
        which make the above math assertion true.
        The values of "E" "T" and "I" must be non-zero.
        All letters must have different digit values.
        */
        DigitProcessor dp = new DigitProcessor();
        dp.findAllLegalCombos();
        dp.testLegalCombos();
    }
    
    
    
    
}
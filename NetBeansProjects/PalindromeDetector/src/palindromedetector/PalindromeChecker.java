/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palindromedetector;

/**
 *
 * @author sillyrabbit
 */
public class PalindromeChecker {
    public PalindromeChecker() {
        
    }
    
    public boolean isPalindrome(String stringToCheck) {
        boolean result = false;
        StringBuilder sb = new StringBuilder(stringToCheck);
        
        String backwards = sb.reverse().toString();
        
        if (backwards.equalsIgnoreCase(stringToCheck)) {
            result = true;
        }
        
        return result;
    }
    
}

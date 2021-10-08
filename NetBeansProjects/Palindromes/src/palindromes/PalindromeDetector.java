/*
 * PalindromeDetector class, by Ron Ruffin
 */
package palindromes;

/**
 *
 * @author Ron Ruffin
 */
public class PalindromeDetector {
    public PalindromeDetector() {
        
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

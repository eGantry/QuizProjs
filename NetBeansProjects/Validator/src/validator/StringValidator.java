/*
 * StringValidator class, by Ron Ruffin
 */
package validator;

import java.util.ArrayList;

/**
 *
 * @author Ron Ruffin
 */
public abstract class StringValidator {

    protected enum StringStatus {
        VALID,
        INVALID
    }
    
    abstract protected void processRows(ArrayList<String> linesToProcess);

    abstract protected StringStatus processString(String stringToProcess);
    
    protected String stripOutSpaces(String stringToStrip) {
        return stripOutChars(stringToStrip, ' ');
    }
    
    protected String stripOutChars(String stringToStrip, char charToStrip) {
        //Remove any embedded spaces from the passed-in string.
        StringBuilder sb = new StringBuilder();
        String result;
        
        char[] charsInStr = stringToStrip.toCharArray();
        
        for (char eachChar : charsInStr) {
            if (eachChar != charToStrip) {
                sb.append(eachChar);
            }
        }
        result = sb.toString();
        return result;
    }
    
    public boolean isUsable(String str) {
        return isUsable(str, false);
    }


    public boolean isUsable(String str, boolean checkNumericAlso) {
        //Determine whether passed-in string is 100% comprised of printable characters.
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (isAsciiPrintable(str.charAt(i)) == false) {
                return false;
            }
            else if (isNumeral(str.charAt(i)) && (checkNumericAlso)) {
                return false;
            }
        }
        return true;
    }
    
    protected boolean isAsciiPrintable(char ch) {
        return ch >= 32 && ch < 127;
    }
    
    protected boolean isNumeral(char ch) {
        return Character.isDigit(ch);
    }    
}

/*
 * PalindromeValidator class, by Ron Ruffin
 */
package palindromes;

import java.util.ArrayList;
import java.util.List;
import validator.FileIO;
import validator.StringValidator;

/**
 *
 * @author Ron Ruffin
 */
public final class PalindromeValidator extends StringValidator {

    private final PalindromeDetector palindromeDetector;
    
    public PalindromeValidator() {
        palindromeDetector = new PalindromeDetector();
        String filePath = FileIO.selectFile();
        
        if (filePath != null) {
            ArrayList<String> linesToProcess = FileIO.readStuffIn(filePath);
            processRows(linesToProcess);
        }

    }

    @Override
    protected void processRows(ArrayList<String> linesToProcess) {
        linesToProcess.stream().forEach((eachLine) -> {
            StringStatus status = processString(eachLine);
            if (status == StringStatus.INVALID) {
                System.err.println("Problem with input string '" + eachLine + "'.");
            }
        });
    }

    @Override
    protected StringStatus processString(String stringToProcess) {
        //Process each string, and determine whether it qualifies as a palindrome.
        JazzySpellChecker speller = new JazzySpellChecker();
        //Result determines whether input string is a valid candidate to be a palindrome.
        StringStatus result = StringStatus.INVALID;
        if (stringToProcess != null) {
            //Rule out any input string containing non-printable characters or digits.
            if (isUsable(stringToProcess, true)) {
                //If there are any special chars or spaces
                String expressionToCheck = null;
                if (hasMultipleWords(stringToProcess)) {
                    //Treat as a sentence or phrase.
                    String specialsRemoved = resolveSpecialChars(stringToProcess);
                    String singleSpaces = trimExtraSpaces(specialsRemoved);
                    List<String> misspelledWords = speller.getMisspelledWords(singleSpaces);
                    if (misspelledWords.isEmpty()) {
                        expressionToCheck = stripOutSpaces(singleSpaces);
                        expressionToCheck = stripOutChars(expressionToCheck, '\'');
                    }
                }
                else {
                    //Treat as a single word.
                    List<String> misspelledWords = speller.getMisspelledWords(stringToProcess);
                    if (misspelledWords.isEmpty()) {
                        expressionToCheck = stringToProcess;
                    }
                }
                
                if (expressionToCheck != null) {
                    if (expressionToCheck.length() > 0) {
                        //If expressionToCheck is populated, stringToProcess is a valid candidate to be a palindrome.
                        result = StringStatus.VALID;
                        //Determine whether expressionToCheck is a palindrome, and report on the result.
                        boolean isPalindrome = palindromeDetector.isPalindrome(expressionToCheck);
                        if (isPalindrome) {
                            System.out.println("'" + stringToProcess + "' is a palindrome.");
                        }
                        else {
                            System.out.println("'" + stringToProcess + "' is NOT a palindrome.");
                        }
                    }
                }
            }
        }
        
        return result;
    }
    
    private String resolveSpecialChars(String stringToProcess) {
        //Convert special characters to spaces.
        StringBuilder sb = new StringBuilder();
        
        boolean isSpecialChar;
        for (char eachChar : stringToProcess.toCharArray()) {
            isSpecialChar = false;
            if (eachChar == ' ') {}
            else if (eachChar == '\'') {}
            else if (Character.isDigit(eachChar)) {}
            else if (Character.isLetter(eachChar)) {}
            else {
                isSpecialChar = true;
            }
            if (isSpecialChar) {
                sb.append(' ');
            }
            else {
                sb.append(eachChar);
            }
        }
        return sb.toString();
    }
    
    private String trimExtraSpaces(String stringToProcess) {
        //Resolve multiple adjacent spaces to a single space.
        StringBuilder sb = new StringBuilder();
        
        char prevChar = ' ';
        for (char eachChar : stringToProcess.toCharArray()) {
            if (((eachChar == ' ') && (prevChar != ' ')) || (eachChar != ' ')) {
                sb.append(eachChar);
            }
            
            prevChar = eachChar;
        }
        return sb.toString();
    }
    
    
    private boolean hasMultipleWords(String stringToTry) {
        //Return true if passed-in string contains any characters typically used as word seperators.
        //Treat apostrophies as part of the word.
        char[] charsInStr = stringToTry.toCharArray();
        
        for (char eachChar : charsInStr) {
            if (eachChar == ' ') {
                return true;
            }
            else if (Character.isDigit(eachChar)) {}
            else if (Character.isLetter(eachChar)) {}
            else if (Character.isLetter(eachChar)) {}
            else {
                return true;
            }
        }
        
        return false;
    }
  
}

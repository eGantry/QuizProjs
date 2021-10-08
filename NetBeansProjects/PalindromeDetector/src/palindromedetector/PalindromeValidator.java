/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palindromedetector;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

/**
 *
 * @author sillyrabbit
 */
public class PalindromeValidator extends StringValidator {
    
    private enum StringStatus {
        VALID,
        INVALID
    }
/*
    
    Write palindrome detector, using Jazzy spell check example to determine real words 

    1. Eliminate entries with non printable characters, or numbers 

    2. Look for special characters or spaces.
        
        2a. If none, consider as single word.
    
    3. Convert special characters to spaces. 

    4. Resolve multiple spaces to single spaces. 

    5. If any spaces, split on spaces to separate word strings. 

    6. Determine whether each word string is an actual word. 

    7. Reject entries containing non word strings. 

    8. Remove spaces, then determine whether phrase is a palindrome.    
    
    */
    private PalindromeChecker palindromeDetector;
    
    public PalindromeValidator() {
        palindromeDetector = new PalindromeChecker();
        String filePath = selectFile();

        ArrayList<String> linesToProcess = readStuffIn(filePath);
        processRows(linesToProcess);
        

    }

    private String selectFile() {
        JFileChooser chooser = new JFileChooser("c:\\");
        chooser.setSelectedFile(null);
        chooser.showOpenDialog(null);
        File curFile = chooser.getSelectedFile();            
        String result = curFile.getAbsolutePath();        
        
        return result;
    }
    
    private void processRows(ArrayList<String> linesToProcess) {
        for (String eachLine : linesToProcess) {
            StringStatus status = processString(eachLine);
            if (status == StringStatus.INVALID) {
                System.err.println("Problem with input string '" + eachLine + "'.");
            }
        }
    }

    private StringStatus processString(String stringToProcess) {
        //Process each string, which SHOULD contain data in the following form:  "x,y" e.g. "Roger,4"
        JazzySpellChecker speller = new JazzySpellChecker();
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
                    result = StringStatus.VALID;
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
        boolean result = false;
        
        int blankCount = 0;
        int digitCount = 0;
        int letterCount = 0;
        int specialCharCount = 0;
        
        
        char[] charsInStr = stringToTry.toCharArray();
        
        for (char eachChar : charsInStr) {
            if (eachChar == ' ') {
                blankCount++;
            }
            else if (Character.isDigit(eachChar)) {
                digitCount++;
            }
            else if (Character.isLetter(eachChar)) {
                letterCount++;
            }
            else {
                specialCharCount++;
            }
        }
        
        result = ((blankCount > 0) || (specialCharCount > 0));
        
        return result;
    }
    


  
}

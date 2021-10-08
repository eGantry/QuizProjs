/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palindromedetector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author sillyrabbit
 */
public class StringValidator {
    protected ArrayList<String> readStuffIn(String fileName) {
        //Read in file contents, and store into ArrayList.
        ArrayList<String> result = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            try {
                StringBuilder sb = new StringBuilder();

                for (String line = br.readLine(); line != null; ) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    result.add(line);
                    line = br.readLine();
                }
                br.close();
            } 
            catch (IOException e) {
                //IO failed.
            }

        }
        catch(FileNotFoundException e) {
            //File wasn't found.
            System.err.println("Could not find file: '" + fileName + "'.");
        }
        finally {
            return result;
        }

    }    

    protected String stripOutSpaces(String stringToStrip) {
        //Remove any embedded spaces from the passed-in string.
        StringBuilder sb = new StringBuilder();
        String result;
        
        char[] charsInStr = stringToStrip.toCharArray();
        
        for (char eachChar : charsInStr) {
            if (eachChar != ' ') {
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

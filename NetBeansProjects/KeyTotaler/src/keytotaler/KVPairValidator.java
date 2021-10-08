/*
 * KVPairValidator class, by Ron Ruffin
 */
package keytotaler;

import java.util.ArrayList;
import java.util.Set;
import validator.FileIO;
import validator.StringValidator;

/**
 *
 * @author Ron Ruffin
 */
public class KVPairValidator extends StringValidator {
    
    private TotalTracker totalTracker;
    
    public KVPairValidator() {
        totalTracker = new TotalTracker();
        String filePath = FileIO.selectFile();

        ArrayList<String> linesToProcess = FileIO.readStuffIn(filePath);
        processRows(linesToProcess);
        totalTracker.printOutTotals();

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
        //Process each string, which SHOULD contain data in the following form:  "x,y" e.g. "Roger,4"
        StringStatus result = StringStatus.INVALID;
        if (stringToProcess != null) {
            //Rule out any input string containing non-printable characters.
            if (isUsable(stringToProcess)) {
                //Allow for string containing embedded spaces, like a space after the comma.
                String stringScrubbed = stripOutSpaces(stringToProcess);
                if (stringScrubbed != null) {
                    //Only process if string conforms to "x,y" format. 
                    if (stringScrubbed.contains(",")) {
                        //Break string into a key and value pair of types String and Integer.
                        String[] parts = stringScrubbed.split(",");
                        //Eliminate any string with more than or less than two sections.
                        if (parts.length == 2) {
                            String soughtKey = parts[0];
                            String strValue = parts[1];
                            try {
                                //Only process if second part parses successfully to an Integer. 
                                Integer value = Integer.parseInt(strValue);
                                //Find existing key based on sought key value, regardless of case spelling.
                                String key = findKey(soughtKey);
                                //Accumulate existing total, or save value to new total.
                                totalTracker.addToTotal(key, value);
                                result = StringStatus.VALID;
                            }
                            catch (NumberFormatException e) {
                                //Integer parse failed.  Do nothing.
                            }
                        }
                    }
                }
            }
        }
        
        return result;
    }
    
    private String findKey (String soughtKey) {
        //Find the matching key, based on the passed-in key string, regardless of case spelling.
        String result = null;
        Set<String> keys = totalTracker.getKeys();

        for (String eachKey : keys) {
            if (eachKey.equalsIgnoreCase(soughtKey)) {
                result = eachKey;
                break;
            }
        }
        
        if (result == null) {
            result = soughtKey;
        }
        
        return result;
    }
    
}

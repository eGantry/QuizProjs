/*
 * FileIO class, by Ron Ruffin
 */
package validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author Ron Ruffin
 */
public class FileIO {
    public static ArrayList<String> readStuffIn(String fileName) {
        //Read in file contents, and return resulting ArrayList.
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
                return result;
            } 
            catch (IOException e) {
                //IO failed.
            }

        }
        catch(FileNotFoundException e) {
            //File wasn't found.
            System.err.println("Could not find file: '" + fileName + "'.");
        }
        return result;

    }
    
    public static String selectFile() {
        String result = null;
        JFileChooser chooser = new JFileChooser("c:\\");
        chooser.setSelectedFile(null);
        chooser.showOpenDialog(null);
        File curFile = chooser.getSelectedFile();            
        if (curFile != null) {
            result = curFile.getAbsolutePath();        
        }
        
        return result;
    }    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectiondemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author sillyrabbit
 */
public class Greeter {
    public void sayJim(String numParm) {
        try {
            //Convert numParm to an int, then add 4 to it.
            int intValue = Integer.parseInt(numParm);
            System.out.println("Hello there, Jim!  " + (intValue) + " + 4 = " + (intValue + 4) + ".");
        }
        catch (Exception e) {
            System.out.println("Jim's int parse failed, '" + numParm + "' is not a valid int string.");
        }
        
    }
    public void sayHank(String parm) {
        System.out.println("Hello there, Hank!  Word of the day:  '" + parm + "'.");
    }
    public void sayTed(String dateParm) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            //Convert dateParm to a date, then add 5 days to it.
            Date date = sdf.parse(dateParm);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            gc.add(GregorianCalendar.DAY_OF_MONTH, 5);
            Date newDate = gc.getTime();
            System.out.println("Hello there, Ted!!  '" + sdf.format(date) + "' + 5 days = " + sdf.format(newDate) + ".");
        }
        catch (Exception e) {
            System.out.println("Ted's date parse failed, '" + dateParm + "' is not a valid date string.");
        }
   }
    public void sayKevin(String parm) {
        System.out.println("Hello there, Kevin!  Word of the day:  '" + parm + "'.");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectiondemo;

import java.lang.reflect.Method;

/**
 *
 * @author sillyrabbit
 */
public class ReflectionDemo {

    static Class<Greeter> greeterClass = Greeter.class;
    
    static Greeter greeter = new Greeter();
    
    static String[] methodNames = {"sayJim", "sayHank", "sayTed", "sayKevin"};

    static String[] methodArgs = {"4", "logistics", "2/4/2017", "heuristics"};
//    static String[] methodArgs = {"barn yard", "logistics", "hay stack", "heuristics"};

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        for (int whichHi = 0; whichHi < 5; whichHi++) {
            sayHi(whichHi);
        }
    }
    
    private static void sayHi(int whichHi) {
        //This method uses Reflection, to select a method to call from Greeter, based on the passed-in index, and the associated method name.
        try {
            Method method = greeterClass.getMethod(methodNames[whichHi], String.class);
            if (method != null) {
                //Call the selected method.
                method.invoke(greeter, methodArgs[whichHi]);
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong!  Error was '" + e.toString() + "'.");
        }
    }
    
}

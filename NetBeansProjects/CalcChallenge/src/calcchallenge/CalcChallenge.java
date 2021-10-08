/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcchallenge;

/**
 *
 * @author sillyrabbit
 */
public class CalcChallenge {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Calculator calc = new Calculator();
        //Calculator calc = new Calculator(true);
        double sum = calc.add(1, 3);
        double difference = calc.subtract(10, 3);
        double product = calc.multiply(12, 2);
        double quotient = calc.divide(80, 4);
        
        
        
        System.out.println("Sum = " + sum + ".");
        System.out.println("Difference = " + difference + ".");
        System.out.println("Product = " + product + ".");
        System.out.println("Quotient = " + quotient + ".");

        try {
            double exp = calc.pow(2, 3);
            System.out.println("Pow = " + exp + ".");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            double[] value1 = {2, 3};
            double[] value2 = {2, 4};
            double multiplyExps = calc.multiplyExps(value1, value2);
            System.out.println("multiplyExps = " + multiplyExps + ".");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            double[] value1 = {2, 3};
            double[] value2 = {2, 5};
            double divideExps = calc.divideExps(value1, value2);
            System.out.println("divideExps = " + divideExps + ".");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        
        
    
    }
    
}

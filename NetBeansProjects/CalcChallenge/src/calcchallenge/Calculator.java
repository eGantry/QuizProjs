/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcchallenge;

//import java.lang.IllegalArgumentException;


/**
 *
 * @author sillyrabbit
 */
public class Calculator {
    ExponentHandler exponents = null;
    
    public Calculator() {
        this(false);
    }
    
    public Calculator(boolean handleExponents) {
        if (handleExponents) {
            exponents = new ExponentHandler();
        }
    }
    
    
    public double add(double firstVal, double secondVal) {
        double result = firstVal + secondVal;
        return result;
    }
    
    public double subtract(double firstVal, double secondVal) {
        double result = firstVal - secondVal;
        return result;
    }

    public double multiply(double firstVal, double secondVal) {
        double result = firstVal * secondVal;
        return result;
    }

    public double divide(double firstVal, double secondVal) {
        if (secondVal != 0) {
            double result = firstVal / secondVal;
            return result;
        }
        else throw new IllegalArgumentException("Cannot divide by zero.");
    }
    
    public double pow(double firstValue, double secondValue) throws Exception {
        if (exponents != null) {
            double result = exponents.pow(firstValue, secondValue);
            return result;
        }
        else {
            throw new Exception("ExponentHandler not instantiated.");
        }
    }
    
    public double multiplyExps(double[] firstValue, double[] secondValue) throws Exception {
        if (exponents != null) {
            try {
                double result = exponents.multiplyExps(firstValue, secondValue);
                return result;
            }
            catch (IllegalArgumentException e) {
                throw e;
            }
        }
        else {
            throw new Exception("ExponentHandler not instantiated.");
        }
    }
    
    public double divideExps(double[] firstValue, double[] secondValue) throws Exception {
        if (exponents != null) {
            try {
                double result = exponents.divideExps(firstValue, secondValue);
                return result;
            }
            catch (IllegalArgumentException e) {
                throw e;
            }
        }
        else {
            throw new Exception("ExponentHandler not instantiated.");
        }
    }
    
    
    
}

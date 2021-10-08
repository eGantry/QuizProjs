/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chasechallenges;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author sillyrabbit
 */
public class ChaseChallenges {

    //For each group of three digits, we use the ones names in the hundreds place and the ones place,
    //we use the tens names in the tens place,
    //and if the tens value is "1", we use the teens names in the ones place.
    static String[][] DIGIT_NAMES = {{"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine" }, 
        {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"}, 
        {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"}};

    static final int SINGLES = 0;
    static final int TEENS = 1;
    static final int TENS = 2;
    
    
    /**
     * @param args the amount line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.print("Type 'MaxGains', 'MoneyNames' or 'Exit':  ");
            String command = in.nextLine();
            if (!command.toLowerCase().equals("exit")) {
                //Do Stuff
                if (command.equalsIgnoreCase("MaxGains")) {
                    maxGains();
                }
                else if (command.equalsIgnoreCase("MoneyNames")) {
                    moneyNames();
                }
            }
            else {
                keepGoing = false;
            }
        }
        System.out.println("Exiting.");
        
    }
    
    private static void maxGains() {
        Scanner in = new Scanner(System.in);
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.print("Max Gains:  Type in 'Done', or a value list:  ");
            String valueList = in.nextLine();
            if (!valueList.equalsIgnoreCase("done")) {
                //Do Stuff
                String highestValue = processValueList(valueList);
                System.out.println(highestValue);
            }
            else {
                keepGoing = false;
            }
        }
        System.out.println("Max Gains:  Done.");
    }
    
    
    private static String processValueList(String valueList) {
        StringBuilder result = new StringBuilder();
        
        String valuesStr[] = valueList.split(" ");
        if (valuesStr.length > 1) {
            try {
                int valueCount = Integer.parseInt(valuesStr[0]);
                if (valueCount >= valuesStr.length) {
                    valueCount = (valuesStr.length - 1);
                }
                int highestSum = 0;
                int bestStartValue = -1;
                int bestEndValue = -1;
                //Loop through all possible start values in the list...
                for (int startValueCtr = 1; startValueCtr <= valueCount; startValueCtr++) {
                    if (startValueCtr > 0) {
                        //...and all possible end values in the list...
                        for (int endValueCtr = startValueCtr; endValueCtr <= valueCount; endValueCtr++) {
                            int currentSum = 0;
                            //Add up the number range from each start value, to each end value...
                            for (int sumValuesCtr = startValueCtr; sumValuesCtr <= endValueCtr; sumValuesCtr++) {
                                int eachValue = Integer.parseInt(valuesStr[sumValuesCtr]);
                                currentSum += eachValue;
                            }
                            //Capture the current sum if it's higher than the mark to beat.
                            if (currentSum > highestSum) {
                                highestSum = currentSum;
                                bestStartValue = startValueCtr;
                                bestEndValue = endValueCtr;
                            }
                        }
                    }
                }
                //We should now have the highest sum, the the start and end positions of the range which produced that sum.
                result.append(highestSum);
//                result.append(highestSum + ", from day #" + bestStartValue + " to day #" + bestEndValue);
            }
            catch(NumberFormatException e) {
                result.append("Invalid Entry!!");
            }
            
        }
        else {
            result.append("Invalid Entry!!");
        }

        return result.toString();
    }
    
    
    
    
    private static void moneyNames() {
        Scanner in = new Scanner(System.in);
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.print("Money Names:  Type in 'Done', or a money amount < 1000000000:  ");
            String amount = in.nextLine();
            if (!amount.equalsIgnoreCase("done")) {
                //Do Stuff
                String moneyAsText = moneyName(amount);
                System.out.println(moneyAsText);
            }
            else {
                keepGoing = false;
            }
        }
        System.out.println("Money Names:  Done.");
    }
    
    private static String moneyName(String dollarAmountStr) {
        String result = "ZeroDollars";
        
        try {
            long dollarAmount = Long.parseLong(dollarAmountStr);
            if (dollarAmount < 1000000000) {
                result = textFromNumbers(dollarAmount);
            }
            else {
                result = "Invalid entry:  Amount must be under 1 billion!!";
            }
        }
        catch(NumberFormatException e) {
            result = "Invalid Entry!!";
        }
        
        return result;
    }
    
    
    private static String textFromNumbers(long value) {
        StringBuilder result = new StringBuilder();
        
        /*
        Rules
        
        Digits have single values, tens values, and teens values.
        Digits are grouped in sets of three within each of three levels.
        The levels are, Singles, Thousands, and Millions.
        The sets of three are ones, tens, and hundreds.
        If a tens value is "1", value in the ones column is interpreted using teens names.
        */
        
        //Break up the total amount into groups of three digits, as millions, thousands, and singles.
        long millions = value / 1000000;
        long thousands = (value % 1000000) / 1000;
        long singles = (value % 1000000) % 1000;
        
        //Assign each millions digit its own value within its group.
        int mill_hundreds = (int)millions / 100;
        int mill_tens = (int)(millions % 100) / 10;
        int mill_ones = (int)(millions % 100) % 10;
        
        //Construct millions string, hundreds first.
        if (millions > 0) {
            if (mill_hundreds > 0) {
                result.append(DIGIT_NAMES[SINGLES][mill_hundreds] + "Hundred");
            }
            if (mill_tens > 0) {
                result.append(DIGIT_NAMES[TENS][mill_tens]);
            }
            if (mill_ones >= 0) {
                if (mill_tens == 1) {
                    result.append(DIGIT_NAMES[TEENS][mill_ones]);
                }
                else {
                    result.append(DIGIT_NAMES[SINGLES][mill_ones]);
                }
            }
            
            result.append("Million");
        }
        
        //Assign each thousands digit its own value within its group.
        int thous_hundreds = (int)thousands / 100;
        int thous_tens = (int)(thousands % 100) / 10;
        int thous_ones = (int)(thousands % 100) % 10;
        
        //Construct thousands string, hundreds first.
        
        if (thousands > 0) {
            if (thous_hundreds > 0) {
                result.append(DIGIT_NAMES[SINGLES][thous_hundreds] + "Hundred");
            }
            
            if (thous_tens > 0) {
                result.append(DIGIT_NAMES[TENS][thous_tens]);
            }                
            if (thous_ones >= 0) {
                if (thous_tens == 1) {
                    result.append(DIGIT_NAMES[TEENS][thous_ones]);
                }
                else {
                    result.append(DIGIT_NAMES[SINGLES][thous_ones]);
                }
            }
            result.append("Thousand");
        }

        //Assign each singles digit its own value within its group.
        int sing_hundreds = (int)singles / 100;
        int sing_tens = (int)(singles % 100) / 10;
        int sing_ones = (int)(singles % 100) % 10;

        
        //Construct singles string, hundreds first.
        if (singles > 0) {
            if (sing_hundreds > 0) {
                result.append(DIGIT_NAMES[SINGLES][sing_hundreds] + "Hundred");
            }
            if (sing_tens > 0) {
                result.append(DIGIT_NAMES[TENS][sing_tens]);
            }
            if (sing_ones >= 0) {
                if (sing_tens == 1) {
                    result.append(DIGIT_NAMES[TEENS][sing_ones]);
                }
                else {
                    result.append(DIGIT_NAMES[SINGLES][sing_ones]);
                }
            }
        }
        if (value == 1) {
            result.append("Dollar");
        }
        else if (value == 0) {
            result.append("ZeroDollars");
        }
        else {
            result.append("Dollars");
        }
        
        return result.toString();
        
    }
    
    
}

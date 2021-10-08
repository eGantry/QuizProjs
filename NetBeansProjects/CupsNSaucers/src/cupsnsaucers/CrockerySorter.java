/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cupsnsaucers;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author quizz
 */
public class CrockerySorter {
    //Find all combinations using all five values.
    
    List<List<Crockery>> allCups;
    List<List<Crockery>> allSaucers;
    
    List<List<Crockery>> noMatchCupsOnce;
    
    
    
    
    CrockerySorter() {
        allCups = new ArrayList<List<Crockery>>();
        allSaucers = new ArrayList<List<Crockery>>();
        noMatchCupsOnce = new ArrayList<List<Crockery>>();
        createAllSaucerCombos();
        createAllCupCombos();
        List<Crockery> firstSaucers = allSaucers.get(0);
        createNoMatchCombos(firstSaucers);
    }
    
    
    void createNoMatchCombos(List<Crockery> saucerSet) {
        System.out.println("Creating all possible 'No Match' cup arrangements for one saucer arrangement.");
        System.out.println();
        
        for (List<Crockery> eachCupSet : allCups) {
            if (!anyMatches(eachCupSet, saucerSet)) {
                noMatchCupsOnce.add(eachCupSet);
                System.out.println();
                displayVals(eachCupSet);
                displayVals(saucerSet);
            }
        }
        
        System.out.println();
        System.out.println(noMatchCupsOnce.size() + " 'No Match' cup arrangements for one saucer arrangement.");
    }
    
    void createAllSaucerCombos() {
        createAllCombos(Crockery.CROCKERY_TYPE.SAUCER);
    }
    
    void createAllCupCombos() {
        createAllCombos(Crockery.CROCKERY_TYPE.CUP);
        
//        for (List<Crockery> eachSaucerSet : allSaucers) {
//            displayVals(eachSaucerSet);
//        }
//        System.out.println(allSaucers.size() + " Saucer Sets");
//        
//        for (List<Crockery> eachCupSet : allCups) {
//            displayVals(eachCupSet);
//        }
//        System.out.println(allCups.size() + " Cup Sets");
        
        
    }
    
    void createAllCombos(Crockery.CROCKERY_TYPE whichType) {
        
        int[] loopVals = new int[5];
        
        for (loopVals[0] = 0; loopVals[0] < 5; loopVals[0]++) {
            for (loopVals[1] = 0; loopVals[1] < 5; loopVals[1]++) {
                for (loopVals[2] = 0; loopVals[2] < 5; loopVals[2]++) {
                    for (loopVals[3] = 0; loopVals[3] < 5; loopVals[3]++) {
                        for (loopVals[4] = 0; loopVals[4] < 5; loopVals[4]++) {
                            
                            if (hasAllFiveValues(loopVals)) {
                                List<Crockery> crockSet = createSet(whichType, loopVals);
                                if (whichType == Crockery.CROCKERY_TYPE.CUP) {
                                    allCups.add(crockSet);
                                }
                                else {
                                    allSaucers.add(crockSet);
                                }
                            }
                        }
                    }
                }
            }
        }

    }
    
    private boolean hasAllFiveValues(int[] numberSet) {
        boolean result = true;
        
        boolean hasValue[] = new boolean[5];
        
        for (int eachValue : numberSet) {
            hasValue[numberSet[eachValue]] = true;
        }
        
        for (boolean eachBool : hasValue) {
            if (!eachBool) {
                result = false;
                break;
            }
        }
        
        return result;
    }
    

    public static List<Crockery> createSet(Crockery.CROCKERY_TYPE crockType, int colorValues[]) {
        List<Crockery> result = new ArrayList<Crockery>();
        
        for (int whichCrock = 0; whichCrock <= 4; whichCrock++) {
            Crockery crock = new Crockery(crockType, colorValues[whichCrock]);
            result.add(crock);
        }
        
        return result;
    }
  
    
    private void displayVals(List<Crockery> crockSet) {
        StringBuilder sbValues = new StringBuilder();
        boolean firstTime = true;
        for (Crockery eachCrock : crockSet) {
            if (firstTime) {
                sbValues.append(eachCrock.crockType.name);
                sbValues.append("S:");
                sbValues.append('\t');
                if (eachCrock.crockType == Crockery.CROCKERY_TYPE.CUP) {
                    sbValues.append('\t');
                }
                firstTime = false;
             }
            sbValues.append(eachCrock.crockColor.name);
            sbValues.append('\t');
        }
        System.out.println(sbValues.toString());
    }
    
    
    private boolean anyMatches(List<Crockery> cupSet, List<Crockery> saucerSet) {
        boolean result = false;
        
        for (int whichPair = 0; whichPair <5; whichPair++) {
            Crockery pairedCup = cupSet.get(whichPair);
            Crockery pairedSaucer = saucerSet.get(whichPair);
            if (pairedCup.crockColor == pairedSaucer.crockColor) {
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    
    
    
}

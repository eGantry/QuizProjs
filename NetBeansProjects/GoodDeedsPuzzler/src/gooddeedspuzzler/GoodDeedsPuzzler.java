/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gooddeedspuzzler;

/**
 *
 * @author quizz
 */
public class GoodDeedsPuzzler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        /*
        Assign digit values from 0 through 9 to the letters used below
        so that the following equation is true:
        DID + GOOD = DEEDS
        Observe the following rules:
        None of the values begins with a zero digit.
        All the digit values must be unique.
        */
        
        int D = -1;
        int G = -1;
        int I = -1;
        int O = -1;
        int E = -1;
        int S = -1;
        
        int validCombosCtr = 0;
        int untilFoundCtr = 0;
        boolean matchFound = false;
        
        for (D = 1; D <= 9; D++) {
            if (D==G || D==I || D==O || D==E || D==S) {
                continue;
            }
            for (G = 1; G <= 9; G++) {
                if (G==D || G==I || G==O || G==E || G==S) {
                    continue;
                }
                for (I = 0; I <= 9; I++) {
                    if (I==D || I==G || I==O || I==E || I==S) {
                        continue;
                    }
                    for (O = 0; O <= 9; O++) {
                        if (O==D || O==G || O==I || O==E || O==S) {
                            continue;
                        }
                        for (E = 0; E <= 9; E++) {
                            if (E==D || E==G || E==I || E==O || E==S) {
                                continue;
                            }
                            for (S = 0; S <= 9; S++) {
                                if (S==D || S==G || S==I || S==O || S==E) {
                                    continue;
                                }
                                validCombosCtr += 1;
                                if (!matchFound) {
                                    untilFoundCtr +=1;
                                }
                                int DID = ((D*100) + (I*10) + (D*1));
                                int GOOD = ((G*1000) + (O*100) + (O*10) + (D*1));
                                int DEEDS = ((D*10000) + (E*1000) + (E*100) + (D*10) + (S*1));

                                if (DID + GOOD == DEEDS) {
                                    matchFound = true;
                                    
                                    System.out.println("DID = " + DID);
                                    System.out.println("GOOD = " + GOOD);
                                    System.out.println("DEEDS = " + DEEDS);
                                    System.out.println();
                                    System.out.println("D = " + D);
                                    System.out.println("G = " + G);
                                    System.out.println("I = " + I);
                                    System.out.println("O = " + O);
                                    System.out.println("E = " + E);
                                    System.out.println("S = " + S);

                                }
                            
                            }
                            
                        }

                    }

                }

            }
            
        }
        
        System.out.println("Compared " + validCombosCtr + " valid value combinations.");
        if (matchFound) {
            System.out.println("Found a match after trying " + untilFoundCtr + " valid value combinations.");
        }
        
    }
    
}

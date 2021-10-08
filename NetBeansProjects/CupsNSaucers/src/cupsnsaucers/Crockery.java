/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cupsnsaucers;

/**
 *
 * @author quizz
 */
public class Crockery {
    /*
    A piece of crockery can be a cup or a saucer.
    
    It can have one of five colors.
    
    RED, GREEN, BLUE, YELLOW, PURPLE.
    
    CUP, SAUCER.
    
    */
    
    COLOR crockColor;
    CROCKERY_TYPE crockType;
    
    
    public enum COLOR {
        RED(0, "RED"), GREEN(1, "GREEN"), BLUE(2, "BLUE"), YELLOW(3, "YELLOW"), PURPLE(4, "PURPLE");
        int value;
        String name;
        COLOR(int myValue, String myName) {
            value = myValue;
            name = myName;
        }
    }
    
    public enum CROCKERY_TYPE {
        CUP("CUP"), SAUCER("SAUCER");
        String name;
        CROCKERY_TYPE(String myName) {
            name = myName;
        }
    }
    
    Crockery(CROCKERY_TYPE myType, int colorValue) {
        crockType = myType;
        for (COLOR eachColor : COLOR.values()) {
            if (eachColor.value == colorValue) {
                crockColor = eachColor;
                break;
            }
        }
    }
    
    
    
    
    
    
    
}

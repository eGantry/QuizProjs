/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;


interface A
{
    void show();
}

class Xyz implements A
{
    public void show() {
        System.out.println("Hello");
    }
}

/**
 *
 * @author sillyrabbit
 */
public class LambdaTest {
    public static void main(String[] artgs) {
        A obj = new Xyz();
        
        obj.show();
        
    }
}

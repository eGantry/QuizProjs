/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambdaTest;

/**
 *
 * @author sillyrabbit
 */
public class LambdaTest {

    /**
     * @param args the command line arguments
     */


interface TestLambda
{
    void sayHi(String who);

}    
    
    public static void main(String[] args) {
        
//        TestLambda obj;
//        obj = new TestLambda(){
//            public void show() {
                  //Anonymous inner class
//                System.out.println("Hello");
//            }
//        };

//        If there's only one method in the interface, a Lambda expression eliminates the need for an inner class.
//        obj = () -> 
//        {
//            System.out.println("Hello there Mr. Lambda!!");
//        };

//        If there's only one line in the method, the Lambda expression can be shortened like this.
//        obj = (String name) -> System.out.println("Hello there, " + name + "!!");

        //If there's one parm, the expression needn't include the parm's type.
//        obj = (name) -> System.out.println("Hello there, " + name + "!!");

        //...or the surrounding parentheses.  The parn also doens't have to be named the same as it is in the interface declaration.
        TestLambda obj = name -> System.out.println("Hello there, " + name + "!!");
        
        
        obj.sayHi("Mr. Lambda");
       
   }
   
}

//class Xyz implements TestLambda
//{
//    public void sayHi() {
//        System.out.println("Hello");
//    }
//}



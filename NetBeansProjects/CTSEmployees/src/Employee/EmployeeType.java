/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

/**
 *
 * @author sillyrabbit
 */
public class EmployeeType {
    public enum Employee_Type {
        MANAGER("Manager", 300),
        QA("QA", 500),
        DEVELOPER("Developer", 1000);
        private final String empTitle;
        private final int empBudget;
        Employee_Type(String title, int budget) {
            empTitle = title;
            empBudget = budget;
        }
        String myTitle() {
            return empTitle;
        }
        int myBudget() {
            return empBudget;
        }
    };

}

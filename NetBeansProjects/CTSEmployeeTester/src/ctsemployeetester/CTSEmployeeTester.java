/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctsemployeetester;
import Employee.*;

/**
 *
 * @author sillyrabbit
 */
public class CTSEmployeeTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Manager managerA = (Manager)HR.createEmployee("managerA", EmployeeType.Employee_Type.MANAGER);
        Manager managerB = (Manager)HR.createEmployee("managerB", EmployeeType.Employee_Type.MANAGER);
        Manager managerG = (Manager)HR.createEmployee("managerG", EmployeeType.Employee_Type.MANAGER);
        Manager managerH = (Manager)HR.createEmployee("managerH", EmployeeType.Employee_Type.MANAGER);
        Employee developer = HR.createEmployee("developer", EmployeeType.Employee_Type.DEVELOPER);
        Employee qaTester = HR.createEmployee("qaTester", EmployeeType.Employee_Type.QA);
        managerB.addEmployee(developer);
        managerB.addEmployee(qaTester);
        managerA.addEmployee(managerB);
        //managerG.addEmployee(managerB);
        //managerH.addEmployee(managerB);
       
        System.out.println("Manager A's budget is:  $" + managerA.getBudget() + ".");
        System.out.println("Manager B's budget is:  $" + managerB.getBudget() + ".");
        System.out.println("Manager G's budget is:  $" + managerG.getBudget() + ".");
        System.out.println("Manager H's budget is:  $" + managerH.getBudget() + ".");

    }
}

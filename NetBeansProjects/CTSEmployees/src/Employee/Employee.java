/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;
    import Employee.EmployeeType.Employee_Type;
/**
 *
 * @author sillyrabbit
 */
public class Employee {
    //Define class with commonalities here.
    protected final String employeeName;
    protected final String employeeTitle;
    protected final int employeeBudget;
    //protected final Employee_Type employeeType;
            
    protected Employee(String empName, Employee_Type empType) {
        employeeName = empName;
        employeeTitle = empType.myTitle();
        employeeBudget = empType.myBudget();
    }
    
    public int getBudget() {
        //Return this employee's buedget.
        return employeeBudget;
    }
    
}

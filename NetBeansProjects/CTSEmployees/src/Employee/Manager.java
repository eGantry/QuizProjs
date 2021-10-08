/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sillyrabbit
 */
public class Manager extends Employee {
    protected List<Employee> employees;
    Manager(String empName, EmployeeType.Employee_Type empType) {
        super(empName, empType);
        employees = new ArrayList();
    }
    public void addEmployee(Employee newEmp) {
        HR.addEmpToMgrsTeam(this, newEmp);
    }
    public int getBudget() {
        //Return this employee's budget, combined with those of all employees under him/her, including indirect employees.
        int totalBudget = employeeBudget;
        for (Employee eachEmp : employees) {
            totalBudget += eachEmp.getBudget();
        }
        return totalBudget;
    }
}

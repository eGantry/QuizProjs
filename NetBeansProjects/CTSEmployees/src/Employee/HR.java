/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * The purpose of this class is to create employees and managers, 
 * and to enforce a rule that an employee may only have one direct manager.
 * @author sillyrabbit
 */
public class HR {
    static List<Manager> managers = new ArrayList();
    
    public static Employee createEmployee(String empName, EmployeeType.Employee_Type empType) {
        if (empType == EmployeeType.Employee_Type.MANAGER) {
            Manager newManager = new Manager(empName, empType);
            addToMgrGroup(newManager);
            return newManager;
        }
        else {
            return new Employee(empName, empType);
        }
    }

    public static void addEmpToMgrsTeam(Manager manager, Employee employee) {
        //Make the passed-in employee part of the passed-in Manager's team.
        //If employee is already on another manager's team, remove him/her.
        for (Manager eachManager : managers) {
            if (eachManager.employees.contains(employee)) {
                eachManager.employees.remove(employee);
            }
        }
        //Add the employee to the passed-in manager's team.
        manager.employees.add(employee);
    }
    
    private static void addToMgrGroup(Manager manager) {
        //Add the passed-in manager to the group of managers.
        managers.add(manager);
    }
    
}

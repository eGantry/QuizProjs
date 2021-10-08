/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ron.student.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sillyrabbit
 */
public abstract class StudentFactory {

    public static Student buildStudent(String idStr, String lastName, String firstName, String dateAdmitted, String department, String major) {
        Short id = Short.parseShort(idStr);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date dateAdm = null;
        try {
            dateAdm = sdf.parse(dateAdmitted);
        }
        catch (ParseException e) {
            //Report Parse Exception somehow.
        }

        Student newStudent = StudentFactory.buildStudent(id, lastName, firstName, dateAdm, department, major);
        return newStudent;
    }
    
    
    public static Student buildStudent(short id, String lastName, String firstName, Date admissionDate, String department, String major) {
        Student newStudent = new Student();
        StudentPK newStudentPK = new StudentPK();
        newStudentPK.setStudentId(id);
        newStudentPK.setLastName(lastName);
        newStudentPK.setFirstName(firstName);
        newStudent.setStudentPK(newStudentPK);
        newStudent.setAdmissionDate(admissionDate);
        newStudent.setDepartment(department);
        newStudent.setMajor(major);        
        return newStudent;
    }
}

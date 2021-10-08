/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ron.student.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author sillyrabbit
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    protected StudentPK studentPK;
    private Date admissionDate;
    private String department;
    private String major;

    public Student() {
    }

    public Student(StudentPK studentPK) {
        this.studentPK = studentPK;
    }

    public Student(StudentPK studentPK, Date admissionDate, String department) {
        this.studentPK = studentPK;
        this.admissionDate = admissionDate;
        this.department = department;
    }

    public Student(short studentId, String lastName, String firstName) {
        this.studentPK = new StudentPK(studentId, lastName, firstName);
    }

    public StudentPK getStudentPK() {
        return studentPK;
    }

    public void setStudentPK(StudentPK studentPK) {
        this.studentPK = studentPK;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentPK != null ? studentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.studentPK == null && other.studentPK != null) || (this.studentPK != null && !this.studentPK.equals(other.studentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.me.student.Student[ studentPK=" + studentPK + " ]";
    }
    
}

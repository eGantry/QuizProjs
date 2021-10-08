/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ron.student.model;

import java.io.Serializable;

/**
 *
 * @author sillyrabbit
 */
public class StudentPK implements Serializable {
    private short studentId;
    private String lastName;
    private String firstName;

    public StudentPK() {
    }

    public StudentPK(short studentId, String lastName, String firstName) {
        this.studentId = studentId;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public short getStudentId() {
        return studentId;
    }

    public void setStudentId(short studentId) {
        this.studentId = studentId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) studentId;
        hash += (lastName != null ? lastName.hashCode() : 0);
        hash += (firstName != null ? firstName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentPK)) {
            return false;
        }
        StudentPK other = (StudentPK) object;
        if (this.studentId != other.studentId) {
            return false;
        }
        if ((this.lastName == null && other.lastName != null) || (this.lastName != null && !this.lastName.equals(other.lastName))) {
            return false;
        }
        if ((this.firstName == null && other.firstName != null) || (this.firstName != null && !this.firstName.equals(other.firstName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.me.student.StudentPK[ studentId=" + studentId + ", lastName=" + lastName + ", firstName=" + firstName + " ]";
    }
    
}

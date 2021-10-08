/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ron.student.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author sillyrabbit
 */
public class StudentDBP {

    public StudentDBP() {

    }
    
    //Create a new Student.
    public short createNewStudent(String lastName, String firstName, String dateAdmitted, String department, String major) {
        //Returns the new id if the Add succeeded, or -1 if it failed.
        short result = -1;
        short id = getNextID();
        if (id != -1) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date dateAdmDt = null;
            try {
                dateAdmDt = sdf.parse(dateAdmitted);
                result = addNewStudent(id, lastName, firstName, dateAdmDt, department, major);
            }
            catch (ParseException e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }
    
    private short getNextID() {
        //Find the max id value, add 1 to it, and return the result.
        short result = -1;
        
        String sql = "select student_id from student order by student_id desc";
        
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                result = rs.getShort("student_id");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            
        }        
        
        if (result != -1) {
            result++;
        }
        
        return result;
    }
    
    private short addNewStudent(short id, String lastName, String firstName, Date dateAdmitted, String department, String major) {
        int success = -1;
        String sql = "insert into student (student_id, last_name, first_name, department, major, admission_date) values (?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql); 
            stmt.setShort(1, id);
            stmt.setString(2, lastName);
            stmt.setString(3, firstName);
            stmt.setString(4, department);
            stmt.setString(5, major);
            stmt.setDate(6, new java.sql.Date(dateAdmitted.getTime()));
            
            //Do this stuff to execute the statement.
            success = stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            
        }
                
        if (success > 0) {
            return id;
        }
        else {
            return -1;
        } 
    }
    
    //Read a Student.  Return the student(s) to the caller.
    public List<Student> getStudentById(int id) {
        List<Student> result = findById(id);
        return result;
    }
    
    public List<Student> getStudentByName(String lastName, String firstName) {
        if (lastName == null) {
            lastName = "";
        }
        if (firstName == null) {
            firstName = "";
        }
        List<Student> result = findByName(lastName, firstName);
        
        return result;
    }    
    
    private List<Student> findByName(String lastName, String firstName) {
        List<Student> result = new ArrayList<>();
        
        String sqlLastName = "select * from student where last_name = ?";
        String sqlFirstName = "select * from student where first_name = ?";
        String sqlWholeName = "select * from student where last_name = ? and first_name = ?";
        String sql = null;
        
        if ((lastName.trim().length() > 0) && (firstName.trim().length() > 0)) {
            sql = sqlWholeName;
        }
        else if (lastName.trim().length() > 0) {
            sql = sqlLastName;
        }
        else if (firstName.trim().length() > 0) {
            sql = sqlFirstName;
        }
        
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            if ((sql.equals(sqlWholeName)) || (sql.equals(sqlLastName))) {
                stmt.setString(1, lastName);
                if (sql.equals(sqlWholeName)) {
                    stmt.setString(2, firstName);
                }
            }
            else if (sql.equals(sqlFirstName)) {
                stmt.setString(1, firstName);
            }
            
            result = retrieveStudents(stmt);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return result;
    }
    
    //Find any students matching passed-in id.
    private List<Student> findById(int id) {
        List<Student> result = new ArrayList<>();
        String sql = "select * from student where student_id = ?";
        
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            result = retrieveStudents(stmt);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return result;
    }
    
    //Get all students, and return them to the caller.
    public List<Student> getAllStudents() {
        List<Student> result = findAllStudents();
        return result;
    }
    
    //Find all students.
    private List<Student> findAllStudents() {
        List<Student> result = new ArrayList<>();
        String sql = "select * from student";
        
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            result = retrieveStudents(stmt);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return result;
    }
    
    private List<Student> retrieveStudents(PreparedStatement stmt) {
        List<Student> result = new ArrayList<>();
        try {
            Connection conn = getConnection();
            ResultSet foundRows = stmt.executeQuery();
            while (foundRows.next()) {
                Student eachStudent = StudentFactory.buildStudent(foundRows.getShort("student_id"), foundRows.getString("last_name"), 
                        foundRows.getString("first_name"), foundRows.getDate("admission_date"), foundRows.getString("department"), 
                        foundRows.getString("major"));
                result.add(eachStudent);
            }
            conn.close();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return result;
    }
    
    //Update a Student.
    public boolean updateStudent(Student origStudent, Student editedStudent) {
        boolean success = false;
        String sql = buildUpdateSQL(origStudent, editedStudent);
        
        if (sql != null) {
            if (sql.length() > 0) {
                success = editStudent(sql);
            }
        }
        return success;
    }
    
    private String buildUpdateSQL(Student origStudent, Student editedStudent) {
        String result = "";
        boolean fieldsChanged = false;
        
        if ((origStudent != null) && (editedStudent != null)) {
            StudentPK oldStuPK = origStudent.getStudentPK();
            StudentPK newStuPK = editedStudent.getStudentPK();
            if ((oldStuPK != null) && (newStuPK != null)) {
                if (oldStuPK.getStudentId() == newStuPK.getStudentId()) {
                    if (oldStuPK.getLastName().equals(newStuPK.getLastName())) {
                        if (oldStuPK.getFirstName().equals(newStuPK.getFirstName())) {
                            //The primary keys match.  Both Student objects are referring to the same student.
                            String idStr = Short.toString(oldStuPK.getStudentId());
                            String lastName = oldStuPK.getLastName();
                            String firstName = oldStuPK.getFirstName();
                            String whereBlurb = " where student_id = " + idStr 
                                    + " and last_name = '" + lastName 
                                    + "' and first_name = '" + firstName + "'";
                            result = "update student set ";
                            
                            String oldDepartment = origStudent.getDepartment();
                            String oldMajor = origStudent.getMajor();
                            java.util.Date oldAdmDate = origStudent.getAdmissionDate();
                            
                            String newDepartment = editedStudent.getDepartment();
                            String newMajor = editedStudent.getMajor();
                            java.util.Date newAdmDate = editedStudent.getAdmissionDate();
                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                            String newAdmDateStr = sdf.format(newAdmDate);
                            
                            if (newDepartment != null) {
                                if (newDepartment.length() > 0) {
                                    if (!newDepartment.equals(oldDepartment)) {
                                        fieldsChanged = true;
                                        result += "department = '" + newDepartment + "'";
                                    }
                                }
                            }
                            
                            if (newMajor != null) {
                                if (newMajor.length() > 0) {
                                    if (!newMajor.equals(oldMajor)) {
                                        if (fieldsChanged) {
                                            result += ", ";
                                        }
                                        fieldsChanged = true;
                                        result += "major = '" + newMajor + "'";
                                    }
                                }
                            }
                            
                            if (newAdmDate != null) {
                                if (newAdmDate.compareTo(oldAdmDate) != 0) {
                                    if (fieldsChanged) {
                                        result += ", ";
                                    }
                                    fieldsChanged = true;
                                    result += "admission_date = '" + newAdmDateStr + "'";
                                    
                                }
                            }

                            result += whereBlurb;
                        }                        
                    }
                }
            }
            
        }
        
        if (!fieldsChanged) {
            return "";
        }
        else {
            return result;
        }
        
    }
    
    private boolean editStudent(String sql) {
        boolean success = false;
        //Find the student using the id, last name and first name, then update whichever ofthe other fields are different from the current values.
        //This calls for dynamically assembling the SQL statement.

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement(); 
            
            //Do this stuff to execute the statement.
            int rowsAffected = stmt.executeUpdate(sql);
            if (rowsAffected >= 1) {
                success = true;
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            
        }
                
        return success;
    }
    
    //Delete a Student.
    public boolean deleteStudent(String id, String lastName, String firstName) {
        short idShort = Short.parseShort(id);
        boolean succeeded = removeStudent(idShort, lastName, firstName);

        return succeeded;
                
    }
    
    private boolean removeStudent(short id, String lastName, String firstName) {
        boolean success = false;
        
        String sql = "delete from student where student_id = ? and last_name = ? and first_name = ?";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql); 
            stmt.setInt(1, id);
            stmt.setString(2, lastName);
            stmt.setString(3, firstName);
            
            //Do this stuff to execute the statement.
            int rowsRemoved = stmt.executeUpdate();
            if (rowsRemoved > 0) {
                success = true;
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            
        }
                
        return success;        
    }
    
    private Connection getConnection() {
        
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("schema", "SCHOLAR");
        connectionProps.put("user", "scholar");
        connectionProps.put("password", "scholar");
        connectionProps.put("create", "true");
        String connStr = "jdbc:derby://localhost:1527/NGRStudentDB";

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            conn = DriverManager.getConnection(connStr, connectionProps);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }    

}

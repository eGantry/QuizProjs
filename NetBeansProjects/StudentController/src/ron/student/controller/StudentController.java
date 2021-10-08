/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ron.student.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ron.student.model.Student;
import ron.student.model.StudentPK;
import ron.student.model.StudentDBP;
import ron.student.model.StudentFactory;


/**
 *
 * @author sillyrabbit
 */
public class StudentController {
    //When a command comes in from the caller, this object responds by taking the appropriate action.
    //When the action is complete, this object updates display modes, field values and its status message.
    private String statusMsg;
    private List<String> enabledCommands = new ArrayList<>();
    private List<String> enabledFields = new ArrayList<>();
    private List<Student> students;
    private int currentStudent;
    private final StudentDBP dbp;
    
    private enum DISPLAY_MODE {
        VIEW_ONE,
        VIEW_LAST,
        VIEW_FIRST,
        VIEW_NONE,
        VIEW_NORMAL,
        ADD,
        EDIT,
        SEARCH,
        DELETE
    }
    
    private DISPLAY_MODE displayMode;
    
    public enum EDIT_FIELD {
        ID(0, "ID"),
        LAST_NAME(1, "Last Name"),
        FIRST_NAME(2, "First Name"),
        DEPARTMENT(3, "Department"),
        MAJOR(4, "Major"),
        DATE_ADMITTED(5, "Date Admitted");
        
        final private int fieldOffset;
        final private String labelText;
        
        EDIT_FIELD(int offset, String text) {
            fieldOffset = offset;
            labelText = text + ":  ";
        }
        
        public int getOffset() {
            return fieldOffset;
        }
        
        public String getLabelText() {
            return labelText;
        }
        
    }
    
    
    public enum COMMAND {
        PREVIOUS(0, 0, "Prev"),
        NEXT(0, 1, "Next"),
        SEARCH(0, 2, "Search"),
        GO(0, 3, "Go"),
        BACK(0, 4, "Back"),
        ADD(1, 5, "Add"),
        EDIT(1, 6, "Edit"),
        SAVE(1, 7, "Save"),
        DELETE(1, 8, "Delete"),
        CONFIRM(1, 9, "Confirm"),
        CANCEL(1, 10, "Cancel");
        
        final private int rowOffset;
        final private int commandOffset;
        final private String commandText;
        
        COMMAND(int row, int offset, String text) {
            rowOffset = row;
            commandOffset = offset;
            commandText = text;
        }
        
        public int getRowOffset() {
            return rowOffset;
        }
        
        public int getCommandOffset() {
            return commandOffset;
        }
        
        public String getCommandText() {
            return commandText;
        }
        
    }
    
    public StudentController() {
        dbp = new StudentDBP();
        getAllStudents();
        setupStudentDisplay(0);
    }
    
    private void setupStudentDisplay(int whichStudent) {
        currentStudent = -1;
        if (students.size() > 0) {
            if ((whichStudent >= 0) && (whichStudent < students.size())) {
                currentStudent = whichStudent;
            }
            else {
                currentStudent = 0;
            }
            
            if (students.size() > 1) {
                if (currentStudent == 0) {
                    setDisplayMode(DISPLAY_MODE.VIEW_FIRST);
                }
                else if (currentStudent == students.size() - 1) {
                    setDisplayMode(DISPLAY_MODE.VIEW_LAST);
                }
                else {
                    setDisplayMode(DISPLAY_MODE.VIEW_NORMAL);
                }
                
            }
            else {
                setDisplayMode(DISPLAY_MODE.VIEW_ONE);
            }
        }
        else {
            setDisplayMode(DISPLAY_MODE.VIEW_NONE);
        }        
    }
    
    public List<String> getEnabledFields() {
        return enabledFields;
    }
    
    public List<String> getEnabledCommands() {
        return enabledCommands;
    }
    
    //Return the field info for the current student for display.
    public Map<EDIT_FIELD, String> getFieldValues() {
        Map<EDIT_FIELD, String> mapValues = new HashMap<>();
        
        mapValues.put(EDIT_FIELD.ID, "");
        mapValues.put(EDIT_FIELD.LAST_NAME, "");
        mapValues.put(EDIT_FIELD.FIRST_NAME, "");
        mapValues.put(EDIT_FIELD.DATE_ADMITTED, "");
        mapValues.put(EDIT_FIELD.DEPARTMENT, "");
        mapValues.put(EDIT_FIELD.MAJOR, "");
        
        if (students != null) {
            if ((currentStudent >= 0) && (currentStudent < students.size())) {
                Student stuCurrent = students.get(currentStudent);
                if (stuCurrent != null) {
                    if (stuCurrent.getStudentPK() != null) {
                        StudentPK stuCurrPK = stuCurrent.getStudentPK();
                        String studentId = Short.toString(stuCurrPK.getStudentId());
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        String dateAdmStr = sdf.format(stuCurrent.getAdmissionDate());
                        mapValues.put(EDIT_FIELD.ID, studentId);
                        mapValues.put(EDIT_FIELD.LAST_NAME, stuCurrPK.getLastName());
                        mapValues.put(EDIT_FIELD.FIRST_NAME, stuCurrPK.getFirstName());
                        mapValues.put(EDIT_FIELD.DATE_ADMITTED, dateAdmStr);
                        mapValues.put(EDIT_FIELD.DEPARTMENT, stuCurrent.getDepartment());
                        mapValues.put(EDIT_FIELD.MAJOR, stuCurrent.getMajor());
                    }
                }
            }
        }        
        
        return mapValues;
    }
    
    //Receive command, and edit field values, from the caller, then process the command as appropriate.
    public Map<EDIT_FIELD, String> processCommand(String command, Map<EDIT_FIELD, String> requestVals) {
        Map<EDIT_FIELD, String> responseVals = new HashMap<>();
        String idStr = requestVals.get(EDIT_FIELD.ID);
        String lastName = requestVals.get(EDIT_FIELD.LAST_NAME);
        String firstName = requestVals.get(EDIT_FIELD.FIRST_NAME);
        String dateAdmitted = requestVals.get(EDIT_FIELD.DATE_ADMITTED);        
        String department = requestVals.get(EDIT_FIELD.DEPARTMENT);
        String major = requestVals.get(EDIT_FIELD.MAJOR);
        if (command.equals(COMMAND.PREVIOUS.getCommandText())) {
            //Get Previous Student, and if first student, set display mode to view first.
            if (currentStudent > 0) {
                currentStudent--;
                if (currentStudent == 0) {
                    setDisplayMode(DISPLAY_MODE.VIEW_FIRST);
                }
                else {
                    setDisplayMode(DISPLAY_MODE.VIEW_NORMAL);
                }
                responseVals = getFieldValues();
            }
        }
        else if (command.equals(COMMAND.NEXT.getCommandText())) {
            //Get next Student, and if last student, set display mode to view last.
            if (currentStudent < students.size() - 1) {
                currentStudent++;
                if (currentStudent == students.size() - 1) {
                    setDisplayMode(DISPLAY_MODE.VIEW_LAST);
                }
                else {
                    setDisplayMode(DISPLAY_MODE.VIEW_NORMAL);
                }
                responseVals = getFieldValues();
            }
        }
        else if (command.equals(COMMAND.SEARCH.getCommandText())) {
            //Set display mode to search.
            students = new ArrayList<>();
            currentStudent = -1;
            setDisplayMode(DISPLAY_MODE.SEARCH);
            responseVals = getFieldValues();
        }
        else if (command.equals(COMMAND.GO.getCommandText())) {
            //Search for Student with entered field values, and depending on whether found,...
            //...set display mode back to View (normal, one, none, first, last as applicable)
            //If a name values were entered, search by name.
            if ((lastName.trim().length() > 0) || (firstName.trim().length() > 0)) {
                students = dbp.getStudentByName(lastName, firstName);
            }
            //If only an id was entered, search by id.
            else if (idStr.trim().length() > 0) {
                Integer intId = Integer.parseInt(idStr);
                if (intId != null) {
                    students = dbp.getStudentById(intId);
                }
            }
            //If all fields were left empty, search all students.
            else {
                students = dbp.getAllStudents();
            }
            setupStudentDisplay(0);
            responseVals = getFieldValues();
        }
        else if (command.equals(COMMAND.BACK.getCommandText())) {
            //Set display mode back to View (normal, one, none, first, last as applicable)
            students = dbp.getAllStudents();
            setupStudentDisplay(0);
            responseVals = getFieldValues();
        }
        else if (command.equals(COMMAND.ADD.getCommandText())) {
            //Set display mode to Add.
            students = new ArrayList<>();
            currentStudent = -1;
            setDisplayMode(DISPLAY_MODE.ADD);
        }
        else if (command.equals(COMMAND.EDIT.getCommandText())) {
            //Set display mode to Edit.
            setDisplayMode(DISPLAY_MODE.EDIT);
        }
        else if (command.equals(COMMAND.SAVE.getCommandText())) {
            //If display mode is Add, create a new Student from field entries.
            //Search all Students, then point to newly-entered Student.
            //Set display mode back to View (normal, one, none, first, last as applicable)
            if (displayMode == DISPLAY_MODE.ADD) {
                short newId = -1;
                newId = dbp.createNewStudent(lastName, firstName, dateAdmitted, department, major);
                if (newId != -1) {
                    students = dbp.getAllStudents();
                    pointToStudentId(newId);
                    setupStudentDisplay(currentStudent);
                    responseVals = getFieldValues();
                }
            }
            //If display mode is Edit, update existing Student from field entries.
            //Search all Students, then point to just-edited Student.
            //Set display mode back to View (normal, one, none, first, last as applicable)
            else if (displayMode == DISPLAY_MODE.EDIT) {
                Student stuChanges = StudentFactory.buildStudent(idStr, lastName, firstName, dateAdmitted, department, major);
                
                boolean success = dbp.updateStudent(students.get(currentStudent), stuChanges);
                students = dbp.getAllStudents();
                setupStudentDisplay(currentStudent);
                responseVals = getFieldValues();
                
            }
            setDisplayMode(DISPLAY_MODE.VIEW_NORMAL);
        }
        else if (command.equals(COMMAND.DELETE.getCommandText())) {
            //Set display mode to Delete.
            setDisplayMode(DISPLAY_MODE.DELETE);
        }
        else if (command.equals(COMMAND.CONFIRM.getCommandText())) {
            //Delete current Student, then search all students, and point to student before deleted student.
            //Set display mode back to View (normal, one, none, first, last as applicable)
            boolean success = dbp.deleteStudent(idStr, lastName, firstName);
            students = dbp.getAllStudents();
            setupStudentDisplay(0);
            responseVals = getFieldValues();
        }
        else if (command.equals(COMMAND.CANCEL.getCommandText())) {
            //Set display mode back to View (normal, one, none, first, last as applicable)
            students = dbp.getAllStudents();
            setupStudentDisplay(0);
            responseVals = getFieldValues();
        }
        responseVals = getFieldValues();
        return responseVals;
    }
    
    private void pointToStudentId(short soughtId) {
        int result = -1;
        int studentCtr = -1;
        for (Student eachStudent : students) {
            studentCtr++;
            if (eachStudent.getStudentPK().getStudentId() == soughtId) {
                result = studentCtr;
                break;
            }
        }
        if (result == -1) {
            currentStudent = 0;
        }
        else {
            currentStudent = result;
        }
    }
    
    public String getStatusMsg() {
        return statusMsg;
    }
    
    private void getAllStudents() {
        currentStudent = -1;
        students = dbp.getAllStudents();
        if (students.size() > 0) {
            currentStudent = 0;
        }
        else {
            currentStudent = -1;
        }
    }
    
    //Select edit fields and commands to enable based on the passed-in display mode.
    private void setDisplayMode(DISPLAY_MODE mode) {
        enabledCommands = new ArrayList<>();
        enabledFields = new ArrayList<>();
        statusMsg = "";
        if ((mode == DISPLAY_MODE.VIEW_ONE) || (mode == DISPLAY_MODE.VIEW_FIRST) || (mode == DISPLAY_MODE.VIEW_LAST)
                || (mode == DISPLAY_MODE.VIEW_NORMAL)) {
            enabledCommands.add(COMMAND.ADD.getCommandText());
            enabledCommands.add(COMMAND.EDIT.getCommandText());
            enabledCommands.add(COMMAND.SEARCH.getCommandText());
            enabledCommands.add(COMMAND.DELETE.getCommandText());
            if (mode == DISPLAY_MODE.VIEW_FIRST) {
                statusMsg = "First Student";
                enabledCommands.add(COMMAND.NEXT.getCommandText());
            }
            else if (mode == DISPLAY_MODE.VIEW_LAST) {
                statusMsg = "Last Student";
                enabledCommands.add(COMMAND.PREVIOUS.getCommandText());

            }
            else if (mode == DISPLAY_MODE.VIEW_NORMAL) {
                enabledCommands.add(COMMAND.NEXT.getCommandText());
                enabledCommands.add(COMMAND.PREVIOUS.getCommandText());
            }
        }
        else if (mode == DISPLAY_MODE.VIEW_NONE) {
            statusMsg = "No Students Found";
            enabledCommands.add(COMMAND.ADD.getCommandText());
            enabledCommands.add(COMMAND.SEARCH.getCommandText());
        }
        else if ((mode == DISPLAY_MODE.ADD) || (mode == DISPLAY_MODE.EDIT)) {
            enabledCommands.add(COMMAND.SAVE.getCommandText());
            enabledCommands.add(COMMAND.CANCEL.getCommandText());
            enabledFields.add(EDIT_FIELD.MAJOR.getLabelText());
            enabledFields.add(EDIT_FIELD.DEPARTMENT.getLabelText());
            if (mode == DISPLAY_MODE.ADD) {
                statusMsg = "Add a new Student.";
                enabledFields.add(EDIT_FIELD.LAST_NAME.getLabelText());
                enabledFields.add(EDIT_FIELD.FIRST_NAME.getLabelText());
                enabledFields.add(EDIT_FIELD.DATE_ADMITTED.getLabelText());
            }
            else if (mode == DISPLAY_MODE.EDIT) {
                statusMsg = "Edit an existing Student.";
                enabledFields.add(EDIT_FIELD.DATE_ADMITTED.getLabelText());
            }
        }
        else if (mode == DISPLAY_MODE.SEARCH) {
            statusMsg = "Search for a Student.";
            enabledCommands.add(COMMAND.GO.getCommandText());
            enabledCommands.add(COMMAND.BACK.getCommandText());
            enabledFields.add(EDIT_FIELD.ID.getLabelText());
            enabledFields.add(EDIT_FIELD.LAST_NAME.getLabelText());
            enabledFields.add(EDIT_FIELD.FIRST_NAME.getLabelText());
        }
        else if (mode == DISPLAY_MODE.DELETE) {
            statusMsg = "Delete this Student.  Are you sure?";
            enabledCommands.add(COMMAND.CONFIRM.getCommandText());
            enabledCommands.add(COMMAND.CANCEL.getCommandText());
        }
        displayMode = mode;
    }
    
}

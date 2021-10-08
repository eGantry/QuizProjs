/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ron.student.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import ron.student.controller.StudentController;

/**
 *
 * @author sillyrabbit
 */
public class StudentView extends JApplet {
    
    private static final int JFXPANEL_WIDTH_INT = 300;
    private static final int JFXPANEL_HEIGHT_INT = 250;
    private static JFXPanel fxContainer;
    private final StudentController controller = new StudentController();
    
    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {
                }
                
                JFrame frame = new JFrame("Students");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                JApplet applet = new StudentView();
                applet.init();
                
                frame.setContentPane(applet.getContentPane());
                
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
                applet.start();
            }
        });
    }
    
    @Override
    public void init() {
        fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(fxContainer, BorderLayout.CENTER);
        // create JavaFX scene
        Platform.runLater(new Runnable() {
            
            @Override
            public void run() {
                createScene();
            }
        });
    }
    
    private void createScene() {
        doStuff();
        VBox root = new VBox();
        List<HBox> lstBoxEdit = new ArrayList<>();
        List<Label> lstLblEdit = new ArrayList<>();
        List<Control> lstTxtEdit = new ArrayList<>();
        List<Button> lstBtnAction = new ArrayList<>();
        DatePicker datePicker = new DatePicker();
        
        for (StudentController.EDIT_FIELD eachField : StudentController.EDIT_FIELD.values()) {
            lstBoxEdit.add(new HBox());
            lstLblEdit.add(new Label(eachField.getLabelText()));
            lstTxtEdit.add(new TextField());
            
            lstBoxEdit.get(eachField.getOffset()).getChildren().add(lstLblEdit.get(eachField.getOffset()));
            
            if (eachField == StudentController.EDIT_FIELD.DATE_ADMITTED) {
                lstBoxEdit.get(eachField.getOffset()).getChildren().add(datePicker);
            }
            else {
                lstBoxEdit.get(eachField.getOffset()).getChildren().add(lstTxtEdit.get(eachField.getOffset()));
            }
            root.getChildren().add(lstBoxEdit.get(eachField.getOffset()));
        }
        
        //Status message, will display controller's status after performing command.
        Label lblStatusMsg = new Label();
        root.getChildren().add(lblStatusMsg);
        
        //Navigation and Action buttons go here.
        //Prev, Next, Add, Edit, Delete, Save, etc.
        List<HBox> boxButtons = new ArrayList<>();
        boxButtons.add(new HBox());
        boxButtons.add(new HBox());

        for (StudentController.COMMAND eachAction : StudentController.COMMAND.values()) {
            lstBtnAction.add(new Button(eachAction.getCommandText()));
            boxButtons.get(eachAction.getRowOffset()).getChildren().add(lstBtnAction.get(eachAction.getCommandOffset()));
            lstBtnAction.get(eachAction.getCommandOffset()).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                      sendCommand(eachAction, lstBoxEdit, lstTxtEdit, datePicker, lstBtnAction, lblStatusMsg);
                }
            });
        }
        root.getChildren().add(boxButtons.get(0));
        root.getChildren().add(boxButtons.get(1));

        Map<StudentController.EDIT_FIELD, String> responseVals = controller.getFieldValues();
        populateFields(responseVals, lstBoxEdit, lstTxtEdit, datePicker, lstBtnAction, lblStatusMsg);
        fxContainer.setScene(new Scene(root));
    }
    
    
    private void doStuff() {
        Control ctrl = new DatePicker();
        
        String classStr = ctrl.getClass().toString();
        
        System.out.println(classStr);
    }
    
    
    private void sendCommand(StudentController.COMMAND cmdAction, List<HBox> lstBoxEdit, List<Control> lstTxtEdit, 
            DatePicker datePicker, List<Button> lstBtnAction, Label lblStatusMsg) {
        //Send this button's command, and all the field values, to the controller.
        Map<StudentController.EDIT_FIELD, String> requestVals = new HashMap<>();
        for (StudentController.EDIT_FIELD eachField : StudentController.EDIT_FIELD.values()) {
            if (eachField == StudentController.EDIT_FIELD.DATE_ADMITTED) {
                requestVals.put(eachField, getDateStr(datePicker));
            }
            else {
                Control eachCtrl = lstTxtEdit.get(eachField.getOffset());
                String classStr = eachCtrl.getClass().toString();
                if (classStr.contains("TextField")) {
                    TextField txtField = (TextField)eachCtrl;
                    requestVals.put(eachField, txtField.getText());
                }
//                requestVals.put(eachField, lstTxtEdit.get(eachField.getOffset()).getText());
            }
        }
        //Send the requested command to the controller, then repopulate the edit requestVals, 
        //update the status, and re-enable the requestVals and controls.
        Map<StudentController.EDIT_FIELD, String> responseVals = controller.processCommand(cmdAction.getCommandText(), requestVals);
        populateFields(responseVals, lstBoxEdit, lstTxtEdit, datePicker, lstBtnAction, lblStatusMsg);
    }

    private String getDateStr(DatePicker datePicker) {
        SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfMDY = new SimpleDateFormat("MM/dd/yyyy");
        String result = "";
        try {
            LocalDate lDate = datePicker.getValue();
            if (lDate != null) {
                Date date = sdfYMD.parse(lDate.toString());
                String dateStr = sdfMDY.format(date);
                result = dateStr;
                System.out.println("Chosen Date is " + dateStr);
            }
        }
        catch (ParseException e) {
            //
             System.out.println("Date parsiong error.");
        }
        finally {
            return result;
        }
    }

    private void setDateValue(DatePicker datePicker, String dateStr) {
        SimpleDateFormat sdfMDY = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
        if (dateStr != null) {
            try {
                Date date = sdfMDY.parse(dateStr);
                String dateYMD = sdfYMD.format(date);
                LocalDate lDate = LocalDate.parse(dateYMD);
                datePicker.setValue(lDate);   
            }
            catch (ParseException e) {
                //
                System.out.println("Date parsiong error.");
            }
        }
        
     
    }
    
    
    void populateFields(Map<StudentController.EDIT_FIELD, String> responseVals, List<HBox> lstBoxEdit, List<Control> lstTxtEdit, 
            DatePicker datePicker, List<Button> lstBtnAction, Label lblStatusMsg) {
        //Populate edit fields with response values.
        for (StudentController.EDIT_FIELD eachField : StudentController.EDIT_FIELD.values()) {
            if (eachField == StudentController.EDIT_FIELD.DATE_ADMITTED) {
                setDateValue(datePicker, responseVals.get(eachField));
            }
            else {
                //lstTxtEdit.get(eachField.getOffset()).setText(responseVals.get(eachField));
                Control eachCtrl = lstTxtEdit.get(eachField.getOffset());
                String classStr = eachCtrl.getClass().toString();
                if (classStr.contains("TextField")) {
                    TextField txtField = (TextField)eachCtrl;
                    txtField.setText(responseVals.get(eachField));
                }
            }
            
        }                    

        lblStatusMsg.setText(controller.getStatusMsg());

        //Enable selected requestVals, and controls as determined by controller.
        datePicker.setEditable(false);
        datePicker.setDisable(true);
        for (Control eachField : lstTxtEdit) {
            String classStr = eachField.getClass().toString();
            if (classStr.contains("TextField")) {
                TextField txtField = (TextField)eachField;
                txtField.setEditable(false);
            }
            
        }

        //Enable edit fields based on controller's selected mode for each field.
        for (String eachFieldName : controller.getEnabledFields()) {
            if (eachFieldName.contains("Date")) {
                datePicker.setEditable(true);
                datePicker.setDisable(false);
            } 
            else {
                for (HBox boxEachField : lstBoxEdit) {
                    Label lblEach = (Label)boxEachField.getChildren().get(0);
                    if (lblEach.getText().equals(eachFieldName)) {
                        TextField txtEach = (TextField)boxEachField.getChildren().get(1);
                        txtEach.setEditable(true);
                    }
                }
            }

        }

        //Enable command buttons based on controller's selected mode for each button.
        for (Button eachAction : lstBtnAction) {
            eachAction.setDisable(true);
        }

        for (String eachCommand : controller.getEnabledCommands()) {
            for (Button btnEach : lstBtnAction) {
                if (btnEach.getText().equals(eachCommand)) {
                    btnEach.setDisable(false);
                }
            }
        }        
        
    }
    
}

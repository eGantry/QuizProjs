/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.calcsolver;

/**
 *
 * @author quizz
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RevisedUI {

    private JFrame mainPanel;
    private JTextArea display;
    private JPanel mainBtnPanel;
    private JPanel varBtnPanel;
    private boolean solved = false;

    public RevisedUI() {
        mainPanel = new JFrame("Algebrator");
        mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setSize(400, 600);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setLocationRelativeTo(null); // Center the calculator on the screen

        // Display Area
        display = new JTextArea(4, 30);
        display.setText("0");
        display.setFont(new Font("Arial", Font.PLAIN, 18));
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        display.setEditable(false);
        JScrollPane displayScroll = new JScrollPane(display);
        mainPanel.add(displayScroll, BorderLayout.NORTH);

        // Variable Button Panel
        varBtnPanel = new JPanel();
        varBtnPanel.setLayout(new GridLayout(10, 6, 3, 3)); // 5 rows, 6 columns
        addTopButtons();
        addVariableButtons();
        addFunctionButtons();
        mainPanel.add(varBtnPanel, BorderLayout.CENTER);

//        // Function Button Panel
//        funcBtnPanel = new JPanel();
//        funcBtnPanel.setLayout(new GridLayout(3, 6, 3, 3)); // 5 rows, 6 columns
//        addFunctionButtons();
//        mainPanel.add(funcBtnPanel, BorderLayout.CENTER);
        // Main Button Panel
        mainBtnPanel = new JPanel();
        mainBtnPanel.setLayout(new GridLayout(4, 4, 4, 4)); // 5 rows, 4 columns, with gaps
        addMainButtons();
        mainPanel.add(mainBtnPanel, BorderLayout.SOUTH);

        mainPanel.setVisible(true);
    }

    private void addMainButtons() {
        String[] buttons = {"7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "Solve", "+"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(this::handleButtonPress);
            mainBtnPanel.add(button);
        }
    }

    private void addVariableButtons() {
        for (char c = 'a'; c <= 'z'; c++) {
            JButton button = new JButton(String.valueOf(c));
            button.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font for variable buttons
            button.addActionListener(this::handleButtonPress);
            varBtnPanel.add(button);
        }
    }

    private void addTopButtons() {
        String[] buttons = {"", "", "", "←", "C"};

        for (String text : buttons) {
            if (!text.isEmpty()) {
                JButton button = new JButton(text);
                button.setFont(new Font("Arial", Font.PLAIN, 14));
                button.addActionListener(this::handleButtonPress);
                varBtnPanel.add(button);
            } else {
                JLabel label = new JLabel();
                varBtnPanel.add(label);
            }
        }

    }

    private void addFunctionButtons() {
        String[] buttons = {"^", "√", "^2", "2√", "^3", "3√",
            "<", "<=", ">=", ">", "!=", "==",
            "(", ")", ","};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.addActionListener(this::handleButtonPress);
            varBtnPanel.add(button);
        }

    }

    private void handleButtonPress(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String text = source.getText();

        if (null == text) {
            display.append(text);
        } else {
            String currentText = display.getText();
            switch (text) {
                case "C" -> {
                    //Clear the value from the display.
                    display.setText("0");
                    solved = false;
                }
                case "Solve" -> {
                    //Call the backend to solve the current expression
                }
                case "←" -> {
                    if (!currentText.isEmpty()) {
                        display.setText(currentText.substring(0, currentText.length() - 1));
                    }
                }
                case "2√" -> {
                    //Reverse square root
//                    display.setText(currentText + reverseText(text));
                    updateDisplay(reverseText(text));
                }
                case "3√" -> {
                    //Reverse cubed root
//                    display.setText(currentText + reverseText(text));
                    updateDisplay(reverseText(text));
                }
                case "=" -> {
                    //Decide whether to append the equal sign based on last byte in display.
                    if ((currentText.endsWith("<")) || (currentText.endsWith(">")) || (currentText.endsWith("!"))) {
//                        display.setText(currentText + text);
                        updateDisplay(text);
                    } else {
//                        display.setText(currentText + text + text);
                        updateDisplay(text + text);
                    }
                }
                default ->
                    updateDisplay(text);
            }
        }

    }

    private String reverseText(String textToReverse) {
        String result;

        StringBuilder sbText = new StringBuilder(textToReverse);
        sbText.reverse();
        result = sbText.toString();

        return result;
    }

    private void updateDisplay(String buttonText) {

        String displayText = display.getText();
        
        if (solved) {
            if (buttonText.matches("[0-9]") || buttonText.equals(".")) {
                display.setText(buttonText);
            } else {
                display.setText(displayText + buttonText);
            }
            solved = false;
        } else {
            if (solvedText(displayText)) {
                display.setText(buttonText);
            } else {
                display.setText(displayText + buttonText);
            }
        }

    }

    private boolean solvedText(String currentText) {
        boolean result = false;

        if ((currentText.equals("0")) || (currentText.contains("Result")) || (currentText.contains("Solutions"))) {
            result = true;
        }

        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RevisedUI::new);
    }
}

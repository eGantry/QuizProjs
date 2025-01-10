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
    private JPanel mathBtns;
    private JPanel algebraBtns;
    private CalculatorBackend backend;
    private boolean solved = false;

    public RevisedUI(CalculatorBackend backend) {
        this.backend = backend;
        mainPanel = new JFrame("The Algebrator");
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

        // Algebra & Equation Button Panel
        algebraBtns = new JPanel();
        algebraBtns.setLayout(new GridLayout(10, 6, 3, 3)); // 5 rows, 6 columns
        addTopButtons();
        addAlgebraButtons();
        addEquationButtons();
        mainPanel.add(algebraBtns, BorderLayout.CENTER);

        // Math Button Panel
        mathBtns = new JPanel();
        mathBtns.setLayout(new GridLayout(4, 4, 4, 4)); // 5 rows, 4 columns, with gaps
        addMathButtons();
        mainPanel.add(mathBtns, BorderLayout.SOUTH);

        mainPanel.setVisible(true);
    }

    private void addMathButtons() {
        String[] buttons = {"7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "Solve", "+"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(this::handleButtonPress);
            mathBtns.add(button);
        }
    }

    private void addAlgebraButtons() {
        for (char c = 'a'; c <= 'z'; c++) {
            JButton button = new JButton(String.valueOf(c));
            button.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font for variable buttons
            button.addActionListener(this::handleButtonPress);
            algebraBtns.add(button);
        }
    }

    private void addTopButtons() {
        String[] buttons = {"", "", "", "←", "C"};

        for (String text : buttons) {
            if (!text.isEmpty()) {
                JButton button = new JButton(text);
                button.setFont(new Font("Arial", Font.PLAIN, 14));
                button.addActionListener(this::handleButtonPress);
                algebraBtns.add(button);
            } else {
                JLabel label = new JLabel();
                algebraBtns.add(label);
            }
        }

    }

    private void addEquationButtons() {
        String[] buttons = {"^", "√", "^2", "2√", "^3", "3√",
            "<", "<=", ">=", ">", "!=", "==",
            "(", ")", ","};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.addActionListener(this::handleButtonPress);
            algebraBtns.add(button);
        }

    }

    private void handleButtonPress(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String buttonText = source.getText();

        if (null == buttonText) {
            display.append(buttonText);
        } else {
            String displayText = display.getText();
            switch (buttonText) {
                case "C" -> {
                    //Clear the value from the display.
                    display.setText("0");
                    solved = false;
                }
                case "Solve" -> {
                    //Call the backend to solve the current expression
                    if (!displayText.equals("0")) {
                        String result = backend.solveProblem(displayText);
                        display.setText(result);
                        solved = true;
                    }
                }
                case "←" -> {
                    if (!displayText.isEmpty()) {
                        display.setText(displayText.substring(0, displayText.length() - 1));
                    }
                }
                case "2√" -> {
                    //Reverse square root
                    updateDisplay(reverseText(buttonText));
                }
                case "3√" -> {
                    //Reverse cubed root
                    updateDisplay(reverseText(buttonText));
                }
                default ->
                    updateDisplay(buttonText);
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
        boolean isVariable = buttonText.length() == 1 && Character.isLowerCase(buttonText.charAt(0)) && Character.isLetter(buttonText.charAt(0));
        if (solved) {
            if (buttonText.matches("[0-9]") || buttonText.equals(".") || buttonText.equals("-") || isVariable) {
                display.setText(buttonText);
            } else {
                //Prevent user from entering an endless succession of operators.
                preventEndlessOps(buttonText);
            }
            solved = false;
        } else {
            if (stableText(displayText)) {
                display.setText(buttonText);
            } else {
                //Prevent user from entering an endless succession of operators.
                preventEndlessOps(buttonText);
            }
        }

    }

    private final String[] operators = {"+", "-", "*", "/", "^", "√", "<", "<=", ">=", ">", "!=", "=="};

    private void preventEndlessOps(String buttonText) {
        //Prevent user from submitting an endless succession of operators.
        String displayText = display.getText();

        if ((!buttonIsOperator(buttonText)) || (!displayEndsWOperator())) {
            if (!repeatingCommas(buttonText)) {
                display.setText(displayText + buttonText);
            }
        }
    }

    private boolean repeatingCommas(String buttonText) {
        //Prevent user from submitting an endless succession of commas or decimal points.
        boolean result = false;
        String displayText = display.getText();

        String[] noRepeats = {",", "."};

        for (String eachChar : noRepeats) {
            if ((buttonText.contains(eachChar)) && (displayText.endsWith(eachChar))) {
                result = true;
                break;
            }
        }

        return result;

    }

    private boolean buttonIsOperator(String btnText) {
        boolean result = false;

        for (String eachOperator : operators) {
            if (btnText.contains(eachOperator)) {
                result = true;
                break;
            }
        }

        return result;
    }

    private boolean displayEndsWOperator() {
        boolean result = false;

        String displayText = display.getText();

        for (String eachOperator : operators) {
            if (displayText.endsWith(eachOperator)) {
                result = true;
                break;
            }
        }

        return result;
    }

    private boolean stableText(String currentText) {
        boolean result = false;

        if ((currentText.equals("0")) || (currentText.contains("Solutions"))) {
            result = true;
        }

        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RevisedUI(new CalculatorBackend()));
    }
}

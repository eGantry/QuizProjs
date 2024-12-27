/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.calcsolver;

/**
 *
 * @author quizz
 */
// Calculator Back-End (Model)
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CalculatorBackend {

    // Method for basic math operations
    public double performOperation(double operand1, double operand2, String operator) {
        return switch (operator) {
            case "+" ->
                operand1 + operand2;
            case "-" ->
                operand1 - operand2;
            case "*" ->
                operand1 * operand2;
            case "/" ->
                operand2 != 0 ? operand1 / operand2 : Double.NaN;
            case "^" ->
                Math.pow(operand1, operand2);
            case "√" ->
                calculateRoot(operand1, operand2);
            default ->
                Double.NaN;
        };
    }

    // Method for calculating roots with a specified index
    public double calculateRoot(double radicand, double index) {
        return radicand >= 0 && index != 0 ? Math.pow(radicand, 1.0 / index) : Double.NaN;
    }

    // Method for solving algebraic equations
    public String solveEquation(String equation) {
        if (equation == null || equation.isEmpty()) {
            return "Invalid equation.";
        }

        // Split the equation into LHS and RHS
        String[] parts = equation.split("=");
        if (parts.length != 2) {
            return "Error: Invalid equation format.";
        }

        String lhs = parts[0].trim();
        String rhs = parts[1].trim();

        // Simplify both sides
        lhs = simplifyExpression(lhs);
        rhs = simplifyExpression(rhs);

        // Identify the variable
        Set<Character> variables = new HashSet<>();
        for (char c : lhs.toCharArray()) {
            if (Character.isLetter(c)) {
                variables.add(c);
            }
        }

        if (variables.size() != 1) {
            return "Error: Equation must have exactly one variable.";
        }

        char variable = variables.iterator().next();

        // Extract coefficients and constant from LHS
        double lhsCoefficient = extractCoefficient(lhs, variable);
        double lhsConstant = extractConstant(lhs);

        // Evaluate the RHS
        double rhsValue;
        try {
            rhsValue = evaluateExpression(rhs);
        } catch (Exception e) {
            return "Error in RHS evaluation.";
        }

        // Solve for the variable
        if (lhsCoefficient == 0) {
            return lhsConstant == rhsValue ? "Infinite solutions." : "No solution.";
        }

        double solution = (rhsValue - lhsConstant) / lhsCoefficient;

        return variable + " = " + solution;
    }

    // Simplifies an expression by normalizing it
    public String simplifyExpression(String expression) {
        expression = expression.replaceAll("\\s", ""); // Remove spaces

        // Normalize by inserting '*' between numbers and variables, and variables and variables
        expression = expression.replaceAll("(?<=[0-9])(?=[a-zA-Z])", "*"); // Between numbers and variables
        expression = expression.replaceAll("(?<=[a-zA-Z])(?=[a-zA-Z])", "*"); // Between variables (e.g., 'xy' -> 'x*y')
        expression = expression.replaceAll("(?<=[a-zA-Z])(?=[0-9])", "*"); // Between variables and numbers

        return expression;
    }

    // Extracts the coefficient of the given variable in an expression
    public double extractCoefficient(String expression, char variable) {
        expression = expression.replaceAll("\\s", ""); // Remove spaces
        double coefficient = 0.0;
        boolean found = false;

        String[] terms = expression.split("(?=[+-])");
        for (String term : terms) {
            if (term.indexOf(variable) != -1) {
                found = true;
                term = term.replace(String.valueOf(variable), "");

                if (term.isEmpty() || term.equals("+")) {
                    coefficient += 1;
                } else if (term.equals("-")) {
                    coefficient -= 1;
                } else {
                    coefficient += Double.parseDouble(term.replace("*", ""));
                }
            }
        }

        return found ? coefficient : 0.0;
    }

    // Extracts the constant from an expression
    public double extractConstant(String expression) {
        expression = expression.replaceAll("\\s", ""); // Remove spaces
        double constant = 0.0;

        String[] terms = expression.split("(?=[+-])");
        for (String term : terms) {
            if (!term.matches(".*[a-zA-Z].*")) {
                constant += Double.parseDouble(term);
            }
        }

        return constant;
    }

// Method for evaluating expressions with parentheses, exponents, and roots
    public double evaluateExpression(String expression) {
        try {
            // Convert infix to postfix for proper evaluation
            String postfix = infixToPostfix(expression);
            return evaluatePostfix(postfix);
        } catch (Exception e) {
            System.err.println("Error in expression evaluation: " + e.getMessage());
            return Double.NaN;
        }
    }

// Infix to Postfix Conversion
    private String infixToPostfix(String expression) {
        Stack<Character> operators = new Stack<>();
        StringBuilder postfix = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // If the character is a digit or '.', include it in the postfix string
            if (Character.isDigit(c) || c == '.') {
                postfix.append(c);
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                // Pop from stack until '(' is encountered
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postfix.append(' ').append(operators.pop());
                }
                operators.pop(); // Remove '('
            } else if (isOperator(c)) {
                // Add space before operator and pop higher precedence operators
                postfix.append(' ');
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    postfix.append(operators.pop()).append(' ');
                }
                operators.push(c);
            }
        }

        // Pop remaining operators
        while (!operators.isEmpty()) {
            postfix.append(' ').append(operators.pop());
        }

        return postfix.toString().trim();
    }

// Postfix Evaluation
    private double evaluatePostfix(String postfix) {
        Stack<Double> operands = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (isNumeric(token)) {
                operands.push(Double.parseDouble(token));
            } else {
                // Pop operands for operation
                double b = operands.pop();
                double a = operands.isEmpty() ? 0 : operands.pop(); // Handles unary operators
                operands.push(performOperation(a, b, token));
            }
        }

        return operands.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '√';
    }

    private int precedence(char operator) {
        return switch (operator) {
            case '+', '-' ->
                1;
            case '*', '/' ->
                2;
            case '^', '√' ->
                3;
            default ->
                -1;
        };
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

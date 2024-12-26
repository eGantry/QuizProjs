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
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
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

    public String solveEquation(String equation) {
        if (equation == null || equation.isEmpty()) {
            return "Invalid equation.";
        }

        // Check for variables and "=" sign
        if (!equation.contains("=")) {
            try {
                double result = evaluateExpression(equation);
                return String.valueOf(result);
            } catch (Exception e) {
                return "Error in expression.";
            }
        }

        // Split the equation into LHS and RHS
        String[] parts = equation.split("=");
        if (parts.length != 2) {
            return "Invalid equation format.";
        }

        String lhs = parts[0].trim();
        String rhs = parts[1].trim();

        // Extract variables
        Set<Character> variables = equation.chars()
                .filter(Character::isLetter)
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());

        if (variables.size() > 10) {
            return "Error: Too many variables. Maximum supported is 10.";
        }

        // If there's only one variable, attempt to isolate and solve it
        if (variables.size() == 1) {
            char variable = variables.iterator().next();
            try {
                return solveSingleVariableEquation(lhs, rhs, variable);
            } catch (Exception e) {
                return "Error solving equation.";
            }
        }

        return "Error: Cannot solve for multiple variables.";
    }

    private String solveSingleVariableEquation(String lhs, String rhs, char variable) {
        // Move all terms to LHS and rearrange
        String fullExpression = lhs + "-(" + rhs + ")";
        String simplified = simplifyExpression(fullExpression);

        // Isolate the variable
        double coefficient = extractCoefficient(simplified, variable);
        double constant = extractConstant(simplified);

        if (coefficient == 0) {
            return "No solution or infinite solutions.";
        }

        double solution = -constant / coefficient;
        return variable + " = " + solution;
    }

    private String simplifyExpression(String expression) {
        // Use evaluateExpression() or similar parsing logic
        // Placeholder for symbolic simplification logic
        return expression; // For now, return the raw expression
    }

    private double extractCoefficient(String expression, char variable) {
        // Parse the coefficient of the given variable
        // Placeholder logic
        return 1.0;
    }

    private double extractConstant(String expression) {
        // Parse the constant terms
        // Placeholder logic
        return 0.0;
    }

    public double evaluateExpression(String expression) {
        try {
            // Convert infix expression to postfix
            String postfix = infixToPostfix(expression);
            // Evaluate postfix expression
            return evaluatePostfix(postfix);
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    private String infixToPostfix(String expression) {
        StringBuilder output = new StringBuilder();
        Stack<Character> operators = new Stack<>();
        String operatorsList = "+-*/^√";

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                // Append numbers and decimals directly to the output
                output.append(c);
            } else if (Character.isLetter(c)) {
                // Append variables directly to the output
                output.append(c);
            } else if (c == '(') {
                // Push opening parenthesis onto the stack
                operators.push(c);
            } else if (c == ')') {
                // Pop from stack to output until opening parenthesis
                while (!operators.isEmpty() && operators.peek() != '(') {
                    output.append(' ').append(operators.pop());
                }
                if (!operators.isEmpty() && operators.peek() == '(') {
                    operators.pop(); // Remove the '('
                }
            } else if (operatorsList.indexOf(c) >= 0) {
                // Handle operators
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    output.append(' ').append(operators.pop());
                }
                output.append(' ');
                operators.push(c);
            }
        }

        // Append remaining operators
        while (!operators.isEmpty()) {
            output.append(' ').append(operators.pop());
        }

        return output.toString();
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

    private double evaluatePostfix(String postfix) {
        Stack<Double> operands = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                // Push numbers onto the stack
                operands.push(Double.parseDouble(token));
            } else if (token.matches("[A-Za-z]")) {
                // Handle variables (currently unsupported; placeholder)
                throw new UnsupportedOperationException("Variable evaluation not yet implemented.");
            } else {
                // Pop operands for operator evaluation
                double operand2 = operands.pop();
                double operand1 = operands.isEmpty() ? 0 : operands.pop(); // Handles unary operators
                double result = performOperation(operand1, operand2, token);
                operands.push(result);
            }
        }

        return operands.pop();
    }
}

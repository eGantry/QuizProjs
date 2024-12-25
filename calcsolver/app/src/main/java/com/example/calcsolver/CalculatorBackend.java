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
import java.util.Stack;

public class CalculatorBackend {

    // Method for basic math operations
    public double performOperation(double operand1, double operand2, String operator) {
        return switch (operator) {
            case "+" -> operand1 + operand2;
            case "-" -> operand1 - operand2;
            case "*" -> operand1 * operand2;
            case "/" -> operand2 != 0 ? operand1 / operand2 : Double.NaN;
            case "^" -> Math.pow(operand1, operand2);
            case "√" -> calculateRoot(operand1, operand2);
            default -> Double.NaN;
        };
    }

    // Method for calculating roots with a specified index
    public double calculateRoot(double radicand, double index) {
        return radicand >= 0 && index != 0 ? Math.pow(radicand, 1.0 / index) : Double.NaN;
    }

    // Method for solving algebraic equations
    public String solveEquation(String equation) {
        if (equation == null || equation.isEmpty()) return "Invalid equation.";

        long variableCount = equation.chars()
                .filter(Character::isLetter)
                .distinct()
                .count();

        if (variableCount > 10) {
            return "Error: Too many variables. Maximum supported is 10.";
        }

        if (equation.contains("=")) {
            // Placeholder: Process algebraic equation
            return "Solution logic not yet implemented.";
        } else {
            try {
                double result = evaluateExpression(equation);
                return String.valueOf(result);
            } catch (Exception e) {
                return "Error in expression.";
            }
        }
    }

    // Method for evaluating expressions with parentheses, exponents, and roots
    public double evaluateExpression(String expression) {
        try {
            // Placeholder for implementing infix-to-postfix evaluation with parentheses, exponents, and roots
            return Double.NaN;
        } catch (Exception e) {
            return Double.NaN;
        }
    }
}


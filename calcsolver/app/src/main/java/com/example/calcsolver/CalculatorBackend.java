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
import org.matheclipse.core.eval.EvalUtilities;
import org.matheclipse.core.interfaces.IExpr;

public class CalculatorBackend {

    private EvalUtilities evaluator;

    public CalculatorBackend() {
        // Initialize evaluator for solving expressions
        evaluator = new EvalUtilities(false, true);
    }

    // Solve a problem containing one or more equations or arithmetic expressions
    public String solveProblem(String problem) {
        try {
            if (!problem.contains("=")) {
                // Handle arithmetic expressions
                IExpr result = evaluator.evaluate(problem);
                return "Result: " + result.toString();
            }

            // Handle algebraic equations
            StringBuilder solveInput = new StringBuilder("Solve[{");

            solveInput.append(problem);
            solveInput.append("}, {}]");

            // Solve equations
            IExpr result = evaluator.evaluate(solveInput.toString());
            return "Solutions: " + result.toString();
        } catch (Exception e) {
            return "Error solving problem: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        CalculatorBackend backend = new CalculatorBackend();

        // Test cases
        System.out.println(backend.solveProblem("1+4")); // Arithmetic
        System.out.println(backend.solveProblem("n==m+1,m*n==56,m>0")); // Algebraic
        System.out.println(backend.solveProblem("((3/7)*n)*n==84,n>0")); // Quadratic-like
    }
}

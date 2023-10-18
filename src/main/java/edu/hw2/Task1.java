package edu.hw2;

public class Task1 {

    public sealed interface Expr {
        double evaluate();

        record Constant(double val) implements Expr {
            @Override
            public double evaluate() {
                return val;
            }
        }

        record Negate(Expr val) implements Expr {
            @Override
            public double evaluate() {
                return -val.evaluate();
            }
        }

        record Exponent(Expr basis, Expr degree) implements Expr {
            @Override
            public double evaluate() {
                return Math.pow(basis.evaluate(), degree.evaluate());
            }
        }

        record Addition(Expr num1, Expr num2) implements Expr {
            @Override
            public double evaluate() {
                return num1.evaluate() + num2.evaluate();
            }
        }

        record Multiplication(Expr num1, Expr num2) implements Expr {
            @Override
            public double evaluate() {
                return num1.evaluate() * num2.evaluate();
            }
        }
    }

}

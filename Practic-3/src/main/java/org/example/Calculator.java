package org.example;

public class Calculator {
    public double add(double a, double b){
        if (!Double.isFinite(a) || !Double.isFinite(b)) {
            throw new InvalidInputException("Потрібно ввести скінченні числа");
        }
        double add = a + b;
        return add;
    }
    public double subtract(double a, double b) {
        if (!Double.isFinite(a) || !Double.isFinite(b)) {
            throw new InvalidInputException("Потрібно ввести скінченні числа");
        }
        double subtract = a - b;
        return subtract;
    }
    public double multiply(double a, double b) {
        if (!Double.isFinite(a) || !Double.isFinite(b)) {
            throw new InvalidInputException("Потрібно ввести скінченні числа");
        }
        double multiply = a * b;
        return multiply;
    }
    public double divide(double a, double b) {
        if (!Double.isFinite(a) || !Double.isFinite(b)) {
            throw new InvalidInputException("Потрібно ввести скінченні числа");
        }
        if (b == 0) {
            throw new ArithmeticException("Ділення на 0 неможливе");
        }
        double divide = a / b;
        return divide;
    }
    public double sqrt(double a) {
        if (!Double.isFinite(a)) {
            throw new InvalidInputException("Потрібно ввести скінченні числа");
        }
        if (a < 0) {
            throw new InvalidInputException("Не можна обчислити квадратний корінь від’ємного числа");
        }
        double sqrt = Math.sqrt(a);
        return sqrt;
    }
}

package org.example;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {

    @Test
    void addShouldReturnSum() { // +
        Calculator calculator = new Calculator();
        double result = calculator.add(2, 8);

        assertEquals(10.0,result);
    }

    @Test
    void divideByZeroThrowException() { // Тест на 0
        Calculator calculator = new Calculator();

        assertThrows(ArithmeticException.class, () -> calculator.divide(20, 0));
    }

    @Test
    void sqrtNegativeNumberThrowException() { // Тест корня на -
        Calculator calculator = new Calculator();

        assertThrows(InvalidInputException.class, () -> calculator.sqrt(-9));
    }

    @Test
    void sqrtByZero() {
        Calculator calculator = new Calculator();
        double result = calculator.sqrt(0);

        assertEquals(0.0, result);
    }

    @Test
    void textInsteadOfNumber() {
        Scanner scanner = new Scanner("hello");

        assertThrows(InputMismatchException.class, () -> scanner.nextDouble());
    }

    @Test
    void addWithNaNShouldThrowException() {
        Calculator calculator = new Calculator();

        assertThrows(InvalidInputException.class, () -> calculator.add(Double.NaN, 5));
    }
}

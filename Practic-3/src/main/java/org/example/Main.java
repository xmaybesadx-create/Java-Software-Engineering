package org.example;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static void main() {
        Scanner scanner = new Scanner(System.in); // System.in - стандартний поток вводу
        Calculator calculator = new Calculator();

        while (true) {
            System.out.println("Яку операцію ви хочете виконати? '+, -, *, /, sqrt', stop");
            String operation = scanner.next();

            if (operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/") || operation.equals("sqrt") || operation.equals("stop")) {
            } else {
                System.out.println("Невідома операція"); //Проверка правильного ввода операции
                continue;
            }
            if (operation.equals("stop")) {
                break;
            } else {
                try {
                    System.out.println("Введіть перше число: ");
                    double a = scanner.nextDouble();

                    if (operation.equals("sqrt")) { // Стоит тут, что бы корень не просил второе число
                        double result = calculator.sqrt(a);
                        System.out.println(result);
                    } else {
                        System.out.println("Введіть друге число: ");
                        double b = scanner.nextDouble();

                        if (operation.equals("+")) {
                            double result = calculator.add(a, b);
                            System.out.println(result);
                        } else if (operation.equals("-")) {
                            double result = calculator.subtract(a, b);
                            System.out.println(result);
                        } else if (operation.equals("*")) {
                            double result = calculator.multiply(a, b);
                            System.out.println(result);
                        } else if (operation.equals("/")) {
                            double result = calculator.divide(a, b);
                            System.out.println(result);
                        } else {
                            System.out.println("Невідома операція :( ");
                        }
                    }

                } catch (ArithmeticException e) {
                    System.out.println(e.getMessage()); // Работа с ошибкой на 0 в Calculator
                } catch (InvalidInputException i) { // Робота с ошибкой на - в корне Calculator
                    System.out.println(i.getMessage());
                }catch (InputMismatchException m) { // Робота с ошибкой на другое вместо числа
                    System.out.println("Помилка: потрібно ввести число");
                    scanner.nextLine(); // Чистим неправильный ввод после ошибки
                }
                finally {
                    System.out.println("Обробку завершено");
                }
            }
        }


    }
}

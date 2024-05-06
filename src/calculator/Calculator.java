package calculator;

import java.util.Scanner;
import java.util.Stack;

public class Calculator {

    private static double calculate(String expression) {

        // 計算自然對數
        expression = expression.replaceAll("e", String.valueOf(Math.E));

        // 計算π
        expression = expression.replaceAll("pi", String.valueOf(Math.PI));


        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == ' ') {
                continue;
            } else if (ch == '(') {
                operators.push(ch);
            } else if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                i--;
                operands.push(Double.parseDouble(sb.toString()));
            } else if (ch == '-') {
                if (i == 0 || expression.charAt(i - 1) == '(') { // Check if '-' is a negative sign
                    StringBuilder sb = new StringBuilder("-");
                    i++;
                    while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                        sb.append(expression.charAt(i++));
                    }
                    i--;
                    operands.push(Double.parseDouble(sb.toString()));
                } else { // Treat '-' as a subtraction operator
                    while (!operators.isEmpty() && precedence(ch) <= precedence(operators.peek())) {
                        evaluate(operands, operators);
                    }
                    operators.push(ch);
                }
            } else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    evaluate(operands, operators);
                }
                operators.pop(); // Pop '('
            } else {
                while (!operators.isEmpty() && precedence(ch) <= precedence(operators.peek())) {
                    evaluate(operands, operators);
                }
                operators.push(ch);
            }
        }

        while (!operators.isEmpty()) {
            evaluate(operands, operators);
        }

        return operands.pop();
    }

    private static void evaluate(Stack<Double> operands, Stack<Character> operators) {
        double b = operands.pop();
        double a = operands.pop();
        char operator = operators.pop();
        switch (operator) {
            case '+':
                operands.push(a + b);
                break;
            case '-':
                operands.push(a - b);
                break;
            case '*':
                operands.push(a * b);
                break;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("除數不能為零");
                }

                operands.push(a / b);
                break;
            case '^':
                operands.push(Math.pow(a, b));
                break;

            default :
            	throw new ArithmeticException("Wrong inputed operator");
        }
    }

    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    public static void main(String[] args) {
//        String expression = "1.41421*2+2";
//        double result = calculate(expression);
//        System.out.println(expression + " = " + result);


        Scanner scanner = new Scanner(System.in);
        System.out.println("請輸入表達式：");
        String expression = scanner.nextLine();

        try {
            double result = calculate(expression);
            System.out.print("結果：" + result);
        } catch (Exception e) {
            System.out.println("錯誤：" + e.getMessage());
        }


    }
}



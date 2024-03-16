package calculator;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("請輸入表達式：");
        String expression = scanner.nextLine();

        try {
            double result = evaluateExpression(expression);
            System.out.print("結果：" + result);
        } catch (Exception e) {
            System.out.println("錯誤：" + e.getMessage());
        }
    }

    private static double evaluateExpression(String expression) {
        // 移除空白
        expression = expression.replaceAll("\\s+", "");

        // 計算括弧內的運算
        while (expression.contains("(")) {
            int startIndex = expression.lastIndexOf("(");
            int endIndex = expression.indexOf(")", startIndex);

            if (startIndex == -1 || endIndex == -1) {
                throw new IllegalArgumentException("括弧不匹配");
            }

            String subExpression = expression.substring(startIndex + 1, endIndex);
            double subResult = evaluateExpression(subExpression);

            expression = expression.substring(0, startIndex) + subResult + expression.substring(endIndex + 1);
        }

        // 計算次方根
        while (expression.contains("^")) {
            int index = expression.indexOf("^");
            double base = Double.parseDouble(expression.substring(index - 1, index));
            double exponent = Double.parseDouble(expression.substring(index + 1, index + 2));

            double result = Math.pow(base, exponent);
            expression = expression.substring(0, index - 1) + result + expression.substring(index + 2);
        }

        // 計算根號
        while (expression.contains("sqrt")) {
            int index = expression.indexOf("sqrt");
            double operand = Double.parseDouble(expression.substring(index + 4, index + 5));

            double result = Math.sqrt(operand);
            expression = expression.substring(0, index) + result + expression.substring(index + 5);
        }

        // 計算自然對數
        expression = expression.replaceAll("e", String.valueOf(Math.E));

        // 計算π
        expression = expression.replaceAll("pi", String.valueOf(Math.PI));

        // 乘除
        while (expression.contains("*") || expression.contains("/")) {
            int multiplyIndex = expression.indexOf("*");
            int divideIndex = expression.indexOf("/");

            if ((multiplyIndex != -1 && divideIndex == -1) || (multiplyIndex != -1 && multiplyIndex < divideIndex)) {
                performOperation(expression, multiplyIndex, "*");
            } else if ((divideIndex != -1 && multiplyIndex == -1) || (divideIndex != -1 && divideIndex < multiplyIndex)) {
                performOperation(expression, divideIndex, "/");
            }
        }

        // 加減
        while (expression.contains("+") || expression.contains("-")) {
            int addIndex = expression.indexOf("+");
            int subtractIndex = expression.indexOf("-");

            if ((addIndex != -1 && subtractIndex == -1) || (addIndex != -1 && addIndex < subtractIndex)) {
                performOperation(expression, addIndex, "+");
            } else if ((subtractIndex != -1 && addIndex == -1) || (subtractIndex != -1 && subtractIndex < addIndex)) {
                performOperation(expression, subtractIndex, "-");
            }
        }

        return Double.parseDouble(expression);
    }

    private static void performOperation(String expression, int operatorIndex, String operator) {
        double leftOperand = Double.parseDouble(expression.substring(operatorIndex - 1, operatorIndex));
        double rightOperand = Double.parseDouble(expression.substring(operatorIndex + 1, operatorIndex + 2));

        double result = 0;
        switch (operator) {
            case "+":
                result = leftOperand + rightOperand;
                break;
            case "-":
                result = leftOperand - rightOperand;
                break;
            case "*":
                result = leftOperand * rightOperand;
                break;
            case "/":
                if (rightOperand == 0) {
                    throw new ArithmeticException("除數不能為零");
                }
                result = leftOperand / rightOperand;
                break;
        }

        expression = expression.substring(0, operatorIndex - 1) + result + expression.substring(operatorIndex + 2);
    }
}

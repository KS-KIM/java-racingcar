package com.woowacourse.stringcalculator;

import java.util.LinkedList;
import java.util.Queue;

public class StringCalculatorUtil {

    private StringCalculatorUtil() {
    }

    public static StringCalculator parseCalculator(String input) {
        input = input.trim();
        checkIfExpressionEmpty(input);

        Queue<IntOperator> operatorQueue = new LinkedList<>();
        Queue<Integer> numberQueue = new LinkedList<>();
        String[] tokens = input.split(" ");

        numberQueue.add(Integer.parseInt(tokens[0]));
        int length = tokens.length;

        for (int i = 1; i < length; i += 2) {
            checkIfValidTokens(tokens, length, i);
            operatorQueue.add(IntOperator.valueOf(tokens[i].charAt(0)));
            numberQueue.add(Integer.parseInt(tokens[i + 1]));
        }

        return new StringCalculator(operatorQueue, numberQueue);
    }

    private static void checkIfExpressionEmpty(String expression) {
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("식이 비어 있습니다.");
        }
    }

    private static void checkIfValidTokens(String[] tokens, int length, int i) {
        if (!isOperator(tokens[i]) || i + 1 >= length || !isNumber(tokens[i + 1])) {
            throw new IllegalArgumentException("올바르지 않은 식입니다.");
        }
    }

    private static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String str) {
        if (str.length() == 0) {
            return false;
        }

        try {
            IntOperator.valueOf(str.charAt(0));
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
}
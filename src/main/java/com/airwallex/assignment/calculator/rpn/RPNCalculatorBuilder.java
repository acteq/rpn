package com.airwallex.assignment.calculator.rpn;

import com.airwallex.assignment.calculator.CalculatorBuilder;
import com.airwallex.assignment.util.BigBigMath;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author lx
 * @date 2018-11-25
 */

public class RPNCalculatorBuilder implements CalculatorBuilder<BigDecimal> {

    private RPNCalculator<BigDecimal> calculator;
    private int precision;

    @Override
    public RPNCalculator buildCalculator(int setPrecision) {
        calculator = new RPNCalculator<>(text -> new BigDecimal(text));
        precision = setPrecision;

        return calculator;
    }

    @Override
    public void buildArithmetic() {

        // those functions are called in the method eval of  Calculator class
        calculator.registerOperator("+", (num1, num2) -> num1.add(num2));
        calculator.registerOperator("-", (num1, num2) -> num1.subtract(num2));
        calculator.registerOperator("*", (num1, num2) -> num1.multiply(num2));

        //we have catched runtime exceptions in the method eval of  Calculator class
        calculator.registerOperator("/", (num1, num2) -> num1.divide(num2, Integer.max(precision, Integer.max(num1.scale(), num2.scale())), RoundingMode.HALF_UP));
        calculator.registerOperator("sqrt", (num) -> BigBigMath.sqrt(num, Integer.max(num.scale(), precision)));
    }

}

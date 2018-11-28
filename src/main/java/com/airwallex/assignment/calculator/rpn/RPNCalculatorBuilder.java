package com.airwallex.assignment.calculator.rpn;

import com.airwallex.assignment.calculator.CalculatorBuilder;
import com.airwallex.assignment.util.BigBigMath;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author lx
 * @date 2018-11-25
 */

public class RPNCalculatorBuilder implements CalculatorBuilder<BigDecimal> {

    private RPNCalculator<BigDecimal> calculator;
    private MathContext mathContext;

    @Override
    public RPNCalculator buildCalculator(int setPrecision) {
        mathContext = new MathContext(setPrecision);
        calculator = new RPNCalculator<>(text -> new BigDecimal(text, mathContext));

        return calculator;
    }

    @Override
    public void buildArithmetic() {

        // those functions are called in the method eval of  Calculator class
        calculator.registerOperator("+", (num1, num2) -> num1.add(num2, mathContext));
        calculator.registerOperator("-", (num1, num2) -> num1.subtract(num2, mathContext));
        calculator.registerOperator("*", (num1, num2) -> num1.multiply(num2, mathContext));

        //we have catched runtime exceptions in the method eval of  Calculator class
        calculator.registerOperator("/", (num1, num2) -> num1.divide(num2, mathContext));
        calculator.registerOperator("sqrt", (num) -> BigBigMath.sqrt(num, mathContext.getPrecision()));
    }

}

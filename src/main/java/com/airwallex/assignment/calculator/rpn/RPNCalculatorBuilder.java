package com.airwallex.assignment.calculator.rpn;

import com.airwallex.assignment.calculator.CalculatorBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

/**
 * @author lx
 * @date 2018-11-25
 */

public class RPNCalculatorBuilder implements CalculatorBuilder {

    private RPNCalculator<BigDecimal> calculator;
    private MathContext mathContext;


    @Override
    public RPNCalculator buildCalculator(int setPrecision) {
        mathContext = new MathContext(setPrecision);
        calculator = new RPNCalculator<>();
        calculator.setOperandConverter(text -> new BigDecimal(text, mathContext));

        calculator.registerCommand("clear", val -> calculator.clear());
        calculator.registerCommand("undo", val -> calculator.undo());
        return calculator;
    }

    @Override
    public void buildArithmetic() {

        calculator.registerOperator("+", (num1, num2) -> num1.add(num2, mathContext) );
        calculator.registerOperator("-", (num1, num2) -> num1.subtract(num2, mathContext) );
        calculator.registerOperator("*", (num1, num2) -> num1.multiply(num2, mathContext) );
        calculator.registerOperator("/", (num1, num2) -> {
            Optional<BigDecimal> num = Optional.ofNullable(num2)
                    .filter((val) -> val.compareTo(BigDecimal.ZERO) == 0);
            return num.map((val) -> num1.divide(val, mathContext)).get();
        } );
        //        com.airwallex.assignment.calculator.registerOperator("sqrt"
    }

}

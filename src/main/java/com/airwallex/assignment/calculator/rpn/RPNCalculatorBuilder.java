package com.airwallex.assignment.calculator.rpn;

import com.airwallex.assignment.util.BigMath;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 逆波兰计算器创建器，构建的计算器支持算术运算，精度定义
 * <br>undo, clear命令不在这里注册，可由RPNCalculator的使用者自行添加。
 * date 2018-11-25
 * @author lx
 * @version 0.0.1
 */

public class RPNCalculatorBuilder  {

    private RPNCalculator<BigDecimal> calculator;
    private int precision;

    /**
     * 构建器
     * <br>date: 2018-11-28
     * @author: lx
     * @param setPrecision the precision of calculator
     */
    public RPNCalculatorBuilder(int setPrecision) {
        precision = setPrecision;
        calculator = new RPNCalculator<>(text -> new BigDecimal(text));
    }

    /**
     * 构建计算器
     * <br>date: 2018-11-28
     * @author: lx
     * @return:  RPNCalculator
     */
    public RPNCalculator build() {
        return calculator;
    }

    /**
     * 构建计算器的算术运算符，目前支持加减乘除和开方
     * <br>不属于算术操作符的命令，如clear, undo 等，由另外的类通过 RPNCalculator 的registerCommand完成。
     * <br>date: 2018-11-28
     * @author: lx
     * @return self
     */
    public RPNCalculatorBuilder buildArithmetic() {

        // those functions are called in the method eval of  Calculator class
        calculator.registerOperator("+", (num1, num2) -> num1.add(num2));
        calculator.registerOperator("-", (num1, num2) -> num1.subtract(num2));
        calculator.registerOperator("*", (num1, num2) -> num1.multiply(num2));

        //we have catched runtime exceptions in the method eval of  Calculator class
        calculator.registerOperator("/", (num1, num2) -> num1.divide(num2, Integer.max(precision, Integer.max(num1.scale(), num2.scale())), RoundingMode.HALF_UP));
        calculator.registerOperator("sqrt", (num) -> BigMath.sqrt(num, Integer.max(num.scale(), precision)));

        return this;
    }

}

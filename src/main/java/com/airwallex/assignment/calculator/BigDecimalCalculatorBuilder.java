package com.airwallex.assignment.calculator;

import com.airwallex.assignment.calculator.impl.StackCalculatorImpl;
import com.airwallex.assignment.util.BigMath;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * 计算器创建器，构建的计算器支持算术运算，精度定义
 * <br>undo, clear命令不在这里注册，可由RPNCalculator的使用者自行添加。
 * @author lx
 * @version 0.0.2
 */

public class BigDecimalCalculatorBuilder {

    private ConcreteCalculator<BigDecimal> calculator;

    /**
     * 构建器
     * @author: lx
     * @param parser parse text to a list of tuple
     */
    public BigDecimalCalculatorBuilder(Function<String, List<Tuple<String, Integer>>> parser) {

        calculator = ConcreteCalculator.of(new StackCalculatorImpl(), text -> new BigDecimal(text), parser);
    }

    /**
     * 构建计算器
     * @author: lx
     * @return:  ConcreteCalculator
     */
    public ConcreteCalculator build() {
        return calculator;
    }

    /**
     * 构建计算器的算术运算符，目前支持加减乘除和开方
     * <br>不属于算术操作符的命令，如clear, undo 等，由另外的类通过 ConcreteCalculator 的registerCommand完成。
     * @author: lx
     * @param precision the precision of calculator
     * @return self
     */
    public BigDecimalCalculatorBuilder buildArithmetic(int precision) {

        // those functions are called in the method eval of  Calculator class
        calculator.registerOperator("+", (num1, num2) -> num1.add(num2));
        calculator.registerOperator("-", (num1, num2) -> num1.subtract(num2));
        calculator.registerOperator("*", (num1, num2) -> num1.multiply(num2));

        //we have catched runtime exceptions in the method eval of  Calculator class
        calculator.registerOperator("/", (num1, num2) -> num1.divide(num2, Integer.max(precision, Integer.max(num1.scale(), num2.scale())), RoundingMode.HALF_UP));
        calculator.registerOperator("sqrt", (num) -> BigMath.sqrt(num, Integer.max(num.scale(), precision)));

        return this;
    }

    public BigDecimalCalculatorBuilder setDisplayPrecision(int precision) {
        calculator.setDispalyFormat(num -> formatBigDecimal(num, precision));
        return this;
    }

    /**
     * 把BigDecimal 按设定的精度转换成String
     * @author lx
     * @param num 要转换的把BigDecimal
     * @param precision 精度
     * @return String
     */
    public static String formatBigDecimal(BigDecimal num, int precision) {
        return Optional.ofNullable(num)
                .map(val -> {
                    String numStr = num.stripTrailingZeros().toPlainString();

                    String[] strs = numStr.split("\\.");

                    if (strs.length > 1) {
                        strs[1] = strs[1].substring(0, Integer.min(precision, strs[1].length()));
                    }
                    return String.join(".", strs);

                }).orElse("");
    }

}

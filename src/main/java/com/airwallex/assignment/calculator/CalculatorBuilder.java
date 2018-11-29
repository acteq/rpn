package com.airwallex.assignment.calculator;

/**
 * 计算器生成器，帮助创建计算器
 * date 2018-11-25
 * @author lx
 */

public interface CalculatorBuilder<T extends Number> {

    /**
     * 构建计算器
     * <br>date: 2018-11-28
     * @author: lx
     * @param setPrecision int
     * @return:  Calculator
     */
    Calculator<T> buildCalculator(int setPrecision);

    /**
     * 构建计算器的算术运算符
     * <br>date: 2018-11-28
     * @author: lx
     */
    void buildArithmetic();
}

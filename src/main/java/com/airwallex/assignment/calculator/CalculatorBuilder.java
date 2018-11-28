package com.airwallex.assignment.calculator;

/**
 * @author lx
 * @date 2018-11-25
 */

public interface CalculatorBuilder<T extends Number> {
    Calculator<T> buildCalculator(int setPrecision);
    void buildArithmetic();
}

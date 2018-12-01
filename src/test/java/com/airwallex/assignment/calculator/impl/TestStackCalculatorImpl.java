package com.airwallex.assignment.calculator.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author lx
 * @date 2018-11-28
 */
@DisplayName("StackCalculatorImpl test case")
public class TestStackCalculatorImpl {

    @Test
    @DisplayName("test UnaryOperator method")
    public void testUnaryOperator() {

        StackCalculatorImpl<Double> calculator = new StackCalculatorImpl<>();
        calculator.compute(4.0);
        calculator.compute(num -> Math.sqrt(num));

        Object[] array = calculator.stream().toArray();
        assertEquals(array.length, 1);
        assertTrue(Math.abs(((Double)array[0]) - 2.0) < 0.00001);

    }

    @Test
    @DisplayName("test BinaryOperator method using '1 1 +'")
    public void testBinaryOperator() {
        StackCalculatorImpl<Integer> calculator = new StackCalculatorImpl<>();
        calculator.compute(1);
        calculator.compute(1);
        calculator.compute((num1, num2) -> num1 + num2);

        Object[] array = calculator.stream().toArray();
        assertEquals(array.length, 1);
        assertEquals( (Integer)array[0], new Integer(2));

    }

    @Test
    @DisplayName("test push operand method")
    public void testOperand() {
        StackCalculatorImpl<Integer> calculator = new StackCalculatorImpl<>();
        calculator.compute(1);

        Object[] array = calculator.stream().toArray();
        assertEquals(array.length, 1);
        assertEquals( (Integer)array[0], new Integer(1));
    }


}

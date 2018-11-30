package com.airwallex.assignment.calculator;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author lx
 * @date 2018-11-28
 */
@DisplayName("StackCalculator test case")
public class TestStackCalculator {


    @Test
    @DisplayName("test pushUnaryOperator method")
    public void testPushUnaryOperator() {

        StackCalculator<Double> calculator = new StackCalculator<>();
        calculator.pushOperand(4.0);
        calculator.pushOperator(num -> Math.sqrt(num));

        Object[] array = calculator.stream().toArray();
        assertEquals(array.length, 1);
        assertTrue(Math.abs(((Double)array[0]) - 2.0) < 0.00001);

    }

    @Test
    @DisplayName("test pushBinaryOperator method")
    public void testPushBinaryOperator() {
        StackCalculator<Integer> calculator = new StackCalculator<>();
        calculator.pushOperand(1);
        calculator.pushOperand(1);
        calculator.pushOperator((num1, num2) -> num1 + num2);

        Object[] array = calculator.stream().toArray();
        assertEquals(array.length, 1);
        assertEquals( (Integer)array[0], new Integer(2));

    }

    @Test
    @DisplayName("test pushOperand method")
    public void testPushOperand() {
        StackCalculator<Integer> calculator = new StackCalculator<>();
        calculator.pushOperand(1);

        Object[] array = calculator.stream().toArray();
        assertEquals(array.length, 1);
        assertEquals( (Integer)array[0], new Integer(1));
    }


}

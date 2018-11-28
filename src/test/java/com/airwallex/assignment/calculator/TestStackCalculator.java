package com.airwallex.assignment.calculator;


import org.junit.Assert;
import org.junit.Test;



/**
 * @author lx
 * @date 2018-11-28
 */

public class TestStackCalculator {



    @Test
    public void testPushUnaryOperator() {

        StackCalculator<Double> calculator = new StackCalculator<>();
        calculator.pushOperand(4.0);
        calculator.pushOperator(num -> Math.sqrt(num));

        Object[] array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 1);
        Assert.assertTrue(Math.abs(((Double)array[0]) - 2.0) < 0.00001);

    }

    @Test
    public void testPushBinaryOperator() {
        StackCalculator<Integer> calculator = new StackCalculator<>();
        calculator.pushOperand(1);
        calculator.pushOperand(1);
        calculator.pushOperator((num1, num2) -> num1 + num2);

        Object[] array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 1);
        Assert.assertEquals( (Integer)array[0], new Integer(2));

    }

    @Test
    public void testPushOperand() {
        StackCalculator<Integer> calculator = new StackCalculator<>();
        calculator.pushOperand(1);

        Object[] array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 1);
        Assert.assertEquals( (Integer)array[0], new Integer(1));
    }


}

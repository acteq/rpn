package com.airwallex.assignment.calculator.rpn;

import com.airwallex.assignment.calculator.Caretaker;
import com.airwallex.assignment.calculator.EvalException;
import com.airwallex.assignment.calculator.Momento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author lx
 * @date 2018-11-28
 */


@DisplayName("RPNCalculator test case")
public class TestRPNCalculator {

    private RPNCalculator calculator;
    private final int STORED_PRECISION = 15;
    private class AnCareTaker implements Caretaker{

        private Stack<Momento> momentos = new Stack<>();

        @Override
        public boolean add(Momento momento) {
            momentos.push(momento);
            return true;
        }
    }

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setupClass() {
        AnCareTaker anCareTaker = new AnCareTaker();

        calculator = new RPNCalculatorBuilder(STORED_PRECISION).buildArithmetic().build();
        calculator.setCaretaker(anCareTaker);
        // register command clear, undo
        calculator.registerCommand("clear", () -> {
            calculator.clear();
            anCareTaker.momentos.clear();
        });
        calculator.registerCommand("undo", () -> {
            //EmptyStackException
            try {
                Momento momento = anCareTaker.momentos.pop();
                calculator.setMemento(momento);
            } catch (EmptyStackException e) {
                //nothing to do
            }
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using an operand")
    public void testEvalPushOperand()throws EvalException{

        Stream result = calculator.eval("5");
        result.forEach(val ->{
            assertEquals(((BigDecimal)val).intValue(), 5, "'5' should be :");
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using operator sqrt")
    public void testEvalSqrt() throws EvalException{
        calculator.clear();
        Stream result = calculator.eval("4 sqrt");
        result.forEach(val ->{
            assertEquals(((BigDecimal)val).intValue(), 2, "'4 sqrt' should be:");
        });

        calculator.clear();
        result = calculator.eval("2 sqrt");
        result.forEach(val ->{
            assertTrue(Math.abs( ((BigDecimal)val).doubleValue() - 1.4142135623) < 0.000001, "'2 sqrt' should be:");
        });

    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using operator +")
    public void testEvalOperatorPlus() throws EvalException{

        calculator.clear();
        Stream result = calculator.eval("5 2 +");
        result.forEach(val ->{
            assertEquals(((BigDecimal)val).intValue(), 7, "'5 2 +' should be :");
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using operator -")
    public void testEvalOperatorSubtract() throws EvalException{

        calculator.clear();
        Stream result = calculator.eval("5 2 -");
        result.forEach(val ->{
            assertEquals(((BigDecimal)val).intValue(), 3, "eval '5 2 -' should be :");
        });

    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using operator *")
    public void testEvalOperatorMultiply() throws EvalException{

        calculator.clear();
        Stream result = calculator.eval("5 2 *");
        result.forEach(val ->{
            assertEquals(((BigDecimal)val).intValue(), 10, "'5 2 *' should be :");
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using operator /")
    public void testEvalOperatorDivide() throws EvalException {

        calculator.clear();
        Stream result = calculator.eval("6 2 /");
        result.forEach(val ->{
            assertEquals(((BigDecimal)val).intValue(), 3, "'6 2 +' should be :");
        });

    }

    //example
    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method when divided by zero")
    public void testEvalDividedByZero() {

        Object[] array = null;

        try {
            calculator.clear();
            calculator.eval("2 0 /");
            Assertions.fail("should catch exception when divided by zero");
        }catch (EvalException e){
            array = calculator.stream().toArray();
            assertEquals(array.length, 2);
            assertEquals(((BigDecimal)array[0]).intValue(), 2);
            assertEquals(((BigDecimal)array[1]).intValue(), 0);
        }
    }

    //example 1
    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using example 1")
    public void testEvalExample1()  throws EvalException{

        Object[] array = null;
        calculator.clear();
        Stream result = calculator.eval("5 2");
        array = result.toArray();
        assertEquals(array.length, 2);
        assertEquals(((BigDecimal)array[0]).intValue(), 5);
        assertEquals(((BigDecimal)array[1]).intValue(), 2);

    }

    //example 2
    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using example 2")
    public void testEvalExample2() throws EvalException{

        Object[] array = null;
        calculator.clear();

        Stream result = calculator.eval("2 sqrt");
        array = result.toArray();
        assertEquals(array.length, 1);
        assertTrue(Math.abs( ((BigDecimal)array[0]).doubleValue() - 1.4142135623) < 0.000001, "'2 sqrt' should be:");

        calculator.eval("clear 9 sqrt");
        array = calculator.stream().toArray();
        assertEquals(array.length, 1);
        assertEquals(((BigDecimal)array[0]).intValue(), 3);

    }

    //example 3
    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using example 3")
    public void testEvalExample3() throws EvalException{

        Object[] array = null;
        calculator.clear();

        Stream result = calculator.eval("5 2 -");
        array = result.toArray();
        Assertions.assertEquals(array.length, 1);
        Assertions.assertEquals(((BigDecimal)array[0]).intValue(), 3);

        result = calculator.eval("3 -");
        array = result.toArray();
        Assertions.assertEquals(array.length, 1);
        Assertions.assertEquals(((BigDecimal)array[0]).intValue(), 0);

        result = calculator.eval("clear");
        array = result.toArray();
        Assertions.assertEquals(array.length, 0);


    }

    //example 4
    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using example 4")
    public void testEvalExample4()  throws EvalException{

        Object[] array = null;
        calculator.clear();

        Stream result = calculator.eval("5 4 3 2");
        array = result.toArray();
        Assertions.assertEquals(array.length, 4);
        Assertions.assertEquals(((BigDecimal)array[0]).intValue(), 5);
        Assertions.assertEquals(((BigDecimal)array[1]).intValue(), 4);
        Assertions.assertEquals(((BigDecimal)array[2]).intValue(), 3);
        Assertions.assertEquals(((BigDecimal)array[3]).intValue(), 2);

        result = calculator.eval("undo undo *");
        array = result.toArray();
        Assertions.assertEquals(array.length, 1);
        Assertions.assertEquals(((BigDecimal)array[0]).intValue(), 20);

        result = calculator.eval("5 *");
        array = result.toArray();
        Assertions.assertEquals(array.length, 1);
        Assertions.assertEquals(((BigDecimal)array[0]).intValue(), 100);


        result = calculator.eval("undo");
        array = result.toArray();
        Assertions.assertEquals(array.length, 2);
        Assertions.assertEquals(((BigDecimal)array[0]).intValue(), 20);
        Assertions.assertEquals(((BigDecimal)array[1]).intValue(), 5);

    }

    //example 5
    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using example 5")
    public void testEvalExample5()  throws EvalException{

        Object[] array = null;
        calculator.clear();

        Stream result = calculator.eval("7 12 2 /");
        array = result.toArray();
        Assertions.assertEquals(array.length, 2);
        Assertions.assertEquals(((BigDecimal)array[0]).intValue(), 7);
        Assertions.assertEquals(((BigDecimal)array[1]).intValue(), 6);

        result = calculator.eval("*");
        array = result.toArray();
        Assertions.assertEquals(array.length, 1);
        Assertions.assertEquals(((BigDecimal)array[0]).intValue(), 42);

        result = calculator.eval("4 /");
        array = result.toArray();
        Assertions.assertEquals(array.length, 1);
        Assertions.assertTrue(((BigDecimal)array[0]).compareTo(new BigDecimal(10.5)) == 0 );

    }

    //example 6
    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using example 6")
    public void testEvalExample6()  throws EvalException{

        Object[] array = null;
        calculator.clear();

        Stream result = calculator.eval("1 2 3 4 5");
        array = result.toArray();
        Assertions.assertEquals(array.length, 5);
        Assertions.assertEquals(((BigDecimal)array[0]).intValue(), 1);
        Assertions.assertEquals(((BigDecimal)array[1]).intValue(), 2);
        Assertions.assertEquals(((BigDecimal)array[2]).intValue(), 3);
        Assertions.assertEquals(((BigDecimal)array[3]).intValue(), 4);
        Assertions.assertEquals(((BigDecimal)array[4]).intValue(), 5);

        result = calculator.eval("*");
        array = result.toArray();
        Assertions.assertEquals(array.length, 4);
        Assertions.assertEquals(((BigDecimal)array[0]).intValue(), 1);
        Assertions.assertEquals(((BigDecimal)array[1]).intValue(), 2);
        Assertions.assertEquals(((BigDecimal)array[2]).intValue(), 3);
        Assertions.assertEquals(((BigDecimal)array[3]).intValue(), 20);

        result = calculator.eval("clear 3 4 -");
        array = result.toArray();
        Assertions.assertEquals(array.length, 1);
        Assertions.assertEquals(((BigDecimal)array[0]).intValue(), -1);

    }

    //example 7
    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using example 7")
    public void testEvalExample7()  throws EvalException{

        Object[] array = null;
        calculator.clear();

        Stream result = calculator.eval("1 2 3 4 5");
        array = result.toArray();
        Assertions.assertEquals(array.length, 5);
        Assertions.assertEquals(((BigDecimal)array[0]).intValue(), 1);
        Assertions.assertEquals(((BigDecimal)array[1]).intValue(), 2);
        Assertions.assertEquals(((BigDecimal)array[2]).intValue(), 3);
        Assertions.assertEquals(((BigDecimal)array[3]).intValue(), 4);
        Assertions.assertEquals(((BigDecimal)array[4]).intValue(), 5);

        result = calculator.eval("* * * *");
        array = result.toArray();
        Assertions.assertEquals(array.length, 1);
        Assertions.assertEquals(((BigDecimal)array[0]).intValue(), 120);

    }

    //example 8
    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test eval method using example 8")
    public void testEvalExample8() {

        Object[] array = null;

        try {
            calculator.clear();
            calculator.eval("1 2 3 * 5 + * * 6 5");
            Assertions.fail("should catch an exception");

        }catch (EvalException e){
            array = calculator.stream().toArray();
            Assertions.assertEquals(array.length, 1);
            Assertions.assertEquals(((BigDecimal)array[0]).intValue(), 11);
        }
    }
}

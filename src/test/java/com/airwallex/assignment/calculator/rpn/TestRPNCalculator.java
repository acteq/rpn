package com.airwallex.assignment.calculator.rpn;

import com.airwallex.assignment.calculator.Caretaker;
import com.airwallex.assignment.calculator.EvalException;
import com.airwallex.assignment.calculator.Momento;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.math.BigDecimal;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @author lx
 * @date 2018-11-28
 */


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

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        AnCareTaker anCareTaker = new AnCareTaker();

        RPNCalculatorBuilder builder = new RPNCalculatorBuilder();
        calculator = builder.buildCalculator(STORED_PRECISION);
        builder.buildArithmetic();

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
    public void testEvalPushOperand()throws EvalException{
        calculator.clear();
        calculator.eval("5");
        calculator.getResult().forEach( val ->{
            Assert.assertEquals("'5' should be :", ((BigDecimal)val).intValue(), 5);
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testEvalSqrt() throws EvalException{
        calculator.clear();
        calculator.eval("4 sqrt");
        calculator.getResult().forEach( val ->{
            Assert.assertEquals("'4 sqrt' should be:",
                     ((BigDecimal)val).intValue(), 2);
        });

        calculator.clear();
        calculator.eval("2 sqrt");
        calculator.getResult().forEach( val ->{
            Assert.assertTrue("'2 sqrt' should be:",
                    Math.abs( ((BigDecimal)val).doubleValue() - 1.4142135623) < 0.000001);
        });

    }



    @Test
    @SuppressWarnings("unchecked")
    public void testEvalOperatorPlus() throws EvalException{

        calculator.clear();
        calculator.eval("5 2 +");
        calculator.getResult().forEach( val ->{
            Assert.assertEquals("'5 2 +' should be :",
                    ((BigDecimal)val).intValue(), 7);
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testEvalOperatorSubtract() throws EvalException{

        calculator.clear();
        calculator.eval("5 2 -");
        calculator.getResult().forEach( val ->{
            Assert.assertEquals("eval '5 2 -' should be :",
                    ((BigDecimal)val).intValue(), 3);
        });

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testEvalOperatorMultiply() throws EvalException{

        calculator.clear();
        calculator.eval("5 2 *");
        calculator.getResult().forEach( val ->{
            Assert.assertEquals("'5 2 *' should be :",
                    ((BigDecimal)val).intValue(), 10);
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testEvalOperatorDivide() throws EvalException {

        calculator.clear();
        calculator.eval("6 2 /");
        calculator.getResult().forEach( val ->{
            Assert.assertEquals("'6 2 +' should be :",
                    ((BigDecimal)val).intValue(), 3);
        });

    }

    //example
    @Test
    @SuppressWarnings("unchecked")
    public void testEvalDividByZero() {

        Object[] array = null;

        try {
            calculator.clear();
            calculator.eval("2 0 /");

        }catch (EvalException e){
            array = calculator.getResult().toArray();
            Assert.assertEquals(array.length, 2);
            Assert.assertEquals(((BigDecimal)array[0]).intValue(), 2);
            Assert.assertEquals(((BigDecimal)array[1]).intValue(), 0);
        }
    }
    //example 1
    @Test
    @SuppressWarnings("unchecked")
    public void testEvalExample1()  throws EvalException{

        Object[] array = null;
        calculator.clear();
        calculator.eval("5 2");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 2);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 5);
        Assert.assertEquals(((BigDecimal)array[1]).intValue(), 2);

    }

    //example 2
    @Test
    @SuppressWarnings("unchecked")
    public void testEvalExample2() throws EvalException{

        Object[] array = null;
        calculator.clear();

        calculator.eval("2 sqrt");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 1);
        Assert.assertTrue("'2 sqrt' should be:",
                Math.abs( ((BigDecimal)array[0]).doubleValue() - 1.4142135623) < 0.000001);

        calculator.eval("clear 9 sqrt");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 1);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 3);

    }

    //example 3
    @Test
    @SuppressWarnings("unchecked")
    public void testEvalExample3() throws EvalException{

        Object[] array = null;
        calculator.clear();

        calculator.eval("5 2 -");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 1);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 3);

        calculator.eval("3 -");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 1);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 0);

        calculator.eval("clear");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 0);


    }

    //example 4
    @Test
    @SuppressWarnings("unchecked")
    public void testEvalExample4()  throws EvalException{

        Object[] array = null;
        calculator.clear();

        calculator.eval("5 4 3 2");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 4);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 5);
        Assert.assertEquals(((BigDecimal)array[1]).intValue(), 4);
        Assert.assertEquals(((BigDecimal)array[2]).intValue(), 3);
        Assert.assertEquals(((BigDecimal)array[3]).intValue(), 2);

        calculator.eval("undo undo *");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 1);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 20);

        calculator.eval("5 *");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 1);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 100);


        calculator.eval("undo");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 2);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 20);
        Assert.assertEquals(((BigDecimal)array[1]).intValue(), 5);

    }

    //example 5
    @Test
    @SuppressWarnings("unchecked")
    public void testEvalExample5()  throws EvalException{

        Object[] array = null;
        calculator.clear();

        calculator.eval("7 12 2 /");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 2);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 7);
        Assert.assertEquals(((BigDecimal)array[1]).intValue(), 6);

        calculator.eval("*");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 1);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 42);

        calculator.eval("4 /");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 1);
        Assert.assertTrue(((BigDecimal)array[0]).compareTo(new BigDecimal(10.5)) == 0 );

    }

    //example 6
    @Test
    @SuppressWarnings("unchecked")
    public void testEvalExample6()  throws EvalException{

        Object[] array = null;
        calculator.clear();

        calculator.eval("1 2 3 4 5");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 5);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 1);
        Assert.assertEquals(((BigDecimal)array[1]).intValue(), 2);
        Assert.assertEquals(((BigDecimal)array[2]).intValue(), 3);
        Assert.assertEquals(((BigDecimal)array[3]).intValue(), 4);
        Assert.assertEquals(((BigDecimal)array[4]).intValue(), 5);

        calculator.eval("*");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 4);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 1);
        Assert.assertEquals(((BigDecimal)array[1]).intValue(), 2);
        Assert.assertEquals(((BigDecimal)array[2]).intValue(), 3);
        Assert.assertEquals(((BigDecimal)array[3]).intValue(), 20);

        calculator.eval("clear 3 4 -");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 1);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), -1);

    }

    //example 7
    @Test
    @SuppressWarnings("unchecked")
    public void testEvalExample7()  throws EvalException{

        Object[] array = null;
        calculator.clear();

        calculator.eval("1 2 3 4 5");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 5);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 1);
        Assert.assertEquals(((BigDecimal)array[1]).intValue(), 2);
        Assert.assertEquals(((BigDecimal)array[2]).intValue(), 3);
        Assert.assertEquals(((BigDecimal)array[3]).intValue(), 4);
        Assert.assertEquals(((BigDecimal)array[4]).intValue(), 5);

        calculator.eval("* * * *");
        array = calculator.getResult().toArray();
        Assert.assertEquals(array.length, 1);
        Assert.assertEquals(((BigDecimal)array[0]).intValue(), 120);

    }

    //example 8
    @Test
    @SuppressWarnings("unchecked")
    public void testEvalExample8() {

        Object[] array = null;

        try {

            calculator.clear();

            calculator.eval("1 2 3 * 5 + * * 6 5");

        }catch (EvalException e){
            array = calculator.getResult().toArray();
            Assert.assertEquals(array.length, 1);
            Assert.assertEquals(((BigDecimal)array[0]).intValue(), 11);
        }
    }


}

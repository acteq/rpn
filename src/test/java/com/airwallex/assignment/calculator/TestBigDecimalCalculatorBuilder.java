package com.airwallex.assignment.calculator;

/**
 * @author lx
 * @date 2018-11-30
 */


import com.airwallex.assignment.util.BigMath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("ConcreteCalculator test case")
public class TestBigDecimalCalculatorBuilder {

    private ConcreteCalculator calculator = new BigDecimalCalculatorBuilder(Parser::parse).buildArithmetic(15).build();
    private Random rand =new Random(25);

    @BeforeEach
    public void setup() {
        calculator.clear();
    }


    @Test
    @DisplayName("test build method")
    public void testBuild() {
        ConcreteCalculator calculator = new BigDecimalCalculatorBuilder(Parser::parse).build();
        Assertions.assertNotNull(calculator);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test if the generated calculator can push an 5")
    public void testEvalPushOperand()throws EvalException {
        Stream result = calculator.eval("5");
        Object[] array = result.toArray();
        assertEquals(array.length, 1);
        assertEquals(((BigDecimal)array[0]).intValue(), 5, "should have pushed 5");
    }

    @Test
    @DisplayName("test buildArithmetic method")
    public void testBuildArithmetic() {


    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test buildArithmetic method if it added operator '+' using '5 2 +'")
    public void testEvalOperatorPlus() throws EvalException {
        Stream result = calculator.eval("5 2 +");
        Object[] array = result.toArray();
        assertEquals(array.length, 1);
        assertEquals(((BigDecimal)array[0]).intValue(), 7, "'5 2 +' should be 7");

    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test buildArithmetic method if it added operator '-' using  5 2 - ")
    public void testEvalOperatorSubtract() throws EvalException{
        Stream result = calculator.eval("5 2 -");
        Object[] array = result.toArray();
        assertEquals(array.length, 1);
        assertEquals(((BigDecimal)array[0]).intValue(), 3, "'5 2 -' should be 3");
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test buildArithmetic method if it added operator '*' using '5 2 *' ")
    public void testEvalOperatorMultiply() throws EvalException{
        Stream result = calculator.eval("5 2 *");
        Object[] array = result.toArray();
        assertEquals(array.length, 1);
        assertEquals(((BigDecimal)array[0]).intValue(), 10, "'5 2 *' should be 10");

    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test buildArithmetic method if it added operator '/' using '6 2 /'")
    public void testEvalOperatorDivide() throws EvalException {

        Stream result = calculator.eval("6 2 /");
        Object[] array = result.toArray();
        assertEquals(array.length, 1);
        assertEquals(((BigDecimal)array[0]).intValue(), 3, "'6 2 /' should be 3");
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test buildArithmetic method if it added operator 'sqrt' using '4 sqrt'")
    public void testEvalSqrt() throws EvalException{
        Stream result = calculator.eval("4 sqrt");
        Object[] array = result.toArray();
        assertEquals(array.length, 1);
        assertEquals(((BigDecimal)array[0]).intValue(), 2, "'4 sqrt' should be 2");
    }


    @Test
    @DisplayName("test formatBigDecimal method")
    public void testFormatBigDecimal() {

        final int scale = 10;

        double origin = rand.nextDouble();

        BigDecimal val = BigMath.sqrt(new BigDecimal(origin*origin), 15);

        String result = BigDecimalCalculatorBuilder.formatBigDecimal(val, scale);
        String[] strs = result.split("\\.");

        if (strs.length > 1) {
            assertTrue(strs[1].length() <= scale, "("+val.toPlainString()+", " + scale + "), should be: " + result);
        }else{
            assertTrue(true, "("+ val.toPlainString()+", " + scale + "), should be: " + result);
        }
    }
}

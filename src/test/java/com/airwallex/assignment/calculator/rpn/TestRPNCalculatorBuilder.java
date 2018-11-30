package com.airwallex.assignment.calculator.rpn;

/**
 * @author lx
 * @date 2018-11-30
 */

import com.airwallex.assignment.calculator.EvalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("RPNCalculator test case")
public class TestRPNCalculatorBuilder {

    private RPNCalculator calculator;


    @BeforeEach
    public void setup() {
        calculator = new RPNCalculatorBuilder(15).buildArithmetic().build();
        calculator.clear();
    }


    @Test
    @DisplayName("test build method")
    public void testBuild() {
        RPNCalculator calculator = new RPNCalculatorBuilder(15).build();
        Assertions.assertNotNull(calculator);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test if the generated calculator can push an 5")
    public void testEvalPushOperand()throws EvalException{
        RPNCalculator calculator = new RPNCalculatorBuilder(15).build();
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

        calculator.clear();
        Stream result = calculator.eval("5 2 +");
        Object[] array = result.toArray();
        assertEquals(array.length, 1);
        assertEquals(((BigDecimal)array[0]).intValue(), 7, "'5 2 +' should be 7");

    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test buildArithmetic method if it added operator '-' using  5 2 - ")
    public void testEvalOperatorSubtract() throws EvalException{

        calculator.clear();
        Stream result = calculator.eval("5 2 -");
        Object[] array = result.toArray();
        assertEquals(array.length, 1);
        assertEquals(((BigDecimal)array[0]).intValue(), 3, "'5 2 -' should be 3");

    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test buildArithmetic method if it added operator '*' using '5 2 *' ")
    public void testEvalOperatorMultiply() throws EvalException{

        calculator.clear();
        Stream result = calculator.eval("5 2 *");
        Object[] array = result.toArray();
        assertEquals(array.length, 1);
        assertEquals(((BigDecimal)array[0]).intValue(), 10, "'5 2 *' should be 10");

    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test buildArithmetic method if it added operator '/' using '6 2 /'")
    public void testEvalOperatorDivide() throws EvalException {

        calculator.clear();
        Stream result = calculator.eval("6 2 /");
        Object[] array = result.toArray();
        assertEquals(array.length, 1);
        assertEquals(((BigDecimal)array[0]).intValue(), 3, "'6 2 /' should be 3");
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("test buildArithmetic method if it added operator 'sqrt' using '4 sqrt'")
    public void testEvalSqrt() throws EvalException{
        calculator.clear();
        Stream result = calculator.eval("4 sqrt");
        Object[] array = result.toArray();
        assertEquals(array.length, 1);
        assertEquals(((BigDecimal)array[0]).intValue(), 2, "'4 sqrt' should be 2");

    }
}

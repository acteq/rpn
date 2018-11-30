package com.airwallex.assignment.calculator.rpn;

/**
 * @author lx
 * @date 2018-11-30
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("RPNCalculator test case")
public class TestRPNCalculatorBuilder {

    @Test
    @DisplayName("test build method")
    public void testBuild() {
        RPNCalculator calculator = new RPNCalculatorBuilder(15).build();
        Assertions.assertNotNull(calculator);
    }

}

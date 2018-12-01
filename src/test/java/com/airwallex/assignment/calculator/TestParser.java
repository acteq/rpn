package com.airwallex.assignment.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;

/**
 * @author lx
 * @date 2018-12-01
 */

@DisplayName("Parser test case")
public class TestParser {
    @Test
    @DisplayName("test parse function")
    public void testParse(){
        List<Tuple<String, Integer>> result = Parser.parse("5 4 3 2 undo clear * /");


//        Tuple<String, Integer>[] array = result.toArray(new Tuple<String, Integer>[0]);
//        result.to.toArray();
//        assertEquals(array.length, 1);
//        assertEquals(((BigDecimal)array[0]).intValue(), 5, "should have pushed 5");
    }
}

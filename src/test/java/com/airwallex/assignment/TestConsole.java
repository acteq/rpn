package com.airwallex.assignment;

import com.airwallex.assignment.util.BigMath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author lx
 * @date 2018-11-28
 */

@DisplayName("Console test case")
public class TestConsole {


    Random rand =new Random(25);

    @Test
    @DisplayName("test formatBigDecimal")
    public void testFormatBigDecimal() {

        final int scale = 10;

        double origin = rand.nextDouble();

        BigDecimal val = BigMath.sqrt(new BigDecimal(origin*origin), 15);

        String result = Console.formatBigDecimal(val, scale);
        String[] strs = result.split("\\.");

        if (strs.length > 1) {
            assertTrue(strs[1].length() <= scale, "("+val.toPlainString()+", " + scale + "), should be: " + result);
        }else{
            assertTrue(true, "("+ val.toPlainString()+", " + scale + "), should be: " + result);
        }


    }

}

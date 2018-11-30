package com.airwallex.assignment.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author lx
 * @date 2018-11-28
 */
@DisplayName("BigMath test case")
public class TestBigMath {

    Random rand =new Random(25);

    @Test
    @DisplayName("test sqrt using an integer number")
    public void testSqrtWithInt() {
        int origin = rand.nextInt(200);

        BigDecimal originBig = new BigDecimal(origin);

        BigDecimal val = BigMath.sqrt(new BigDecimal(origin*origin), 15);

        assertEquals(val.compareTo(originBig) , 0, "(" + originBig.toPlainString() + "," + 15 + ") == " + val.toPlainString());

    }

    @Test
    @DisplayName("test sqrt using a long number")
    public void testSqrtWithLong() {
        long origin = rand.nextInt(200);

        BigDecimal originBig = new BigDecimal(origin);

        BigDecimal val = BigMath.sqrt(new BigDecimal(origin*origin), 15);

        assertEquals(val.compareTo(originBig), 0, "(" + originBig.toPlainString() + "," + 15 + ") == " + val.toPlainString());

    }

    @Test
    @DisplayName("test sqrt using a float number")
    public void testSqrtWithFloat() {
        float origin = rand.nextInt(200);

        BigDecimal orginBig = new BigDecimal(origin*origin);
        BigDecimal val = BigMath.sqrt(orginBig, 15);

        assertTrue(Math.abs(val.floatValue() - origin) < 0.000001, "(" + orginBig.toPlainString() + "," + 15 + ") == " + val.toPlainString());

    }

    @Test
    @DisplayName("test sqrt using a double number")
    public void testSqrtWithDouble() {
        double origin = rand.nextInt(200);

        BigDecimal orginBig = new BigDecimal(origin*origin);
        BigDecimal val = BigMath.sqrt(orginBig, 15);

        assertTrue(Math.abs(val.doubleValue() - origin) < 0.000001, "(" + orginBig.toPlainString() + "," + 15 + ") == " + val.toPlainString() );

    }


    @Test
    @DisplayName("test sqrt using a negative number")
    public void testSqrtWithNegativeNum() {

        try {
            BigDecimal val = BigMath.sqrt(new BigDecimal(-4), 15);
            fail("'-4 sqrt' should catch exception, not " + val.toPlainString());
        }catch (ArithmeticException e) {
            assertTrue(e.getMessage().contains("negative"));
        }
    }

}

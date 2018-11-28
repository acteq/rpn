package com.airwallex.assignment.util;

import org.junit.Assert;
import org.junit.Test;


import java.math.BigDecimal;
import java.util.Random;

/**
 * @author lx
 * @date 2018-11-28
 */

public class TestBigMath {



    Random rand =new Random(25);

    @Test
    public void testSqrtWithInt() {
        int origin = rand.nextInt();

        BigDecimal originBig = new BigDecimal(origin);

        BigDecimal val = BigMath.sqrt(new BigDecimal(origin*origin), 15);

        Assert.assertTrue("test sqrt(" + originBig.toPlainString() + "," + 15 + ") == " + val.toPlainString(),
                val.compareTo(originBig) ==0);

    }

    @Test
    public void testSqrtWithLong() {
        long origin = rand.nextLong();

        BigDecimal originBig = new BigDecimal(origin);

        BigDecimal val = BigMath.sqrt(new BigDecimal(origin*origin), 15);

        Assert.assertTrue("test sqrt(" + originBig.toPlainString() + "," + 15 + ") == " + val.toPlainString(),
                val.compareTo(originBig) ==0);

    }

    @Test
    public void testSqrtWithFloat() {
        float origin = rand.nextFloat();

        BigDecimal orginBig = new BigDecimal(origin*origin);
        BigDecimal val = BigMath.sqrt(orginBig, 15);

        Assert.assertTrue("test sqrt(" + orginBig.toPlainString() + "," + 15 + ") == " + val.toPlainString(),
                Math.abs(val.floatValue() - origin) < 0.000001 );

    }

    @Test
    public void testSqrtWithDouble() {
        double origin = rand.nextDouble();

        BigDecimal orginBig = new BigDecimal(origin*origin);
        BigDecimal val = BigMath.sqrt(orginBig, 15);

        Assert.assertTrue("test sqrt(" + orginBig.toPlainString() + "," + 15 + ") == " + val.toPlainString(),
                Math.abs(val.doubleValue() - origin) < 0.000001 );

    }


}

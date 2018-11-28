package com.airwallex.assignment;

import com.airwallex.assignment.util.BigMath;
import org.junit.Assert;
import org.junit.Test;


import java.math.BigDecimal;
import java.util.Random;

/**
 * @author lx
 * @date 2018-11-28
 */

public class TestConsole {


    Random rand =new Random(25);

    @Test
    public void testFormatBigDecimal() {

        final int scale = 10;

        double origin = rand.nextDouble();

        BigDecimal val = BigMath.sqrt(new BigDecimal(origin*origin), 15);

        String result = Console.formatBigDecimal(val, scale);
        String[] strs = result.split("\\.");

        if (strs.length > 1) {
            Assert.assertTrue("test formatBigDecimal("+val.toPlainString()+", " + scale + "), it should be: " + result,
                    strs[1].length() <= scale);
        }else{
            Assert.assertTrue("test formatBigDecimal("+ val.toPlainString()+", " + scale + "), it should be: " + result,true);
        }


    }

}

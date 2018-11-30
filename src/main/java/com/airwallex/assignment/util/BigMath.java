package com.airwallex.assignment.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * BigDecimal sqrt
 * date 2018-11-23
 * @author lx
 */

public class BigMath {

    private static final BigDecimal TWO = BigDecimal.valueOf(2);

    /** Estimate square root of val, used as start value for babylonian method*/
    private static BigInteger estimateSqrt(BigInteger val) {
        return val.shiftRight(val.bitLength() / 2);
    }

    /** Estimate square root of val, used as start value for babylonian method*/
    private static BigDecimal estimateSqrt(BigDecimal val) {
        return new BigDecimal(estimateSqrt(val.unscaledValue()), val.scale()/2);
    }

    /**
     * Babylonian square root method (Newton's method) to compute the square
     * root of the given big decimal number up to roughly the given scale.
     *
     * @param  num the BigDecimal
     * @param  scale scale of the BigDecimal square root to be returned
     * @return BigDecimal
     */
    public static BigDecimal sqrt(final BigDecimal num, final int scale) {
        if(num.compareTo(BigDecimal.ZERO) == -1)
            throw new ArithmeticException("don't support negative number");

        BigDecimal x0 = BigDecimal.ZERO;
        BigDecimal x1 = estimateSqrt(num);

        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = num.divide(x0, scale, RoundingMode.HALF_EVEN);
            x1 = x1.add(x0);
            x1 = x1.divide(TWO, scale, RoundingMode.HALF_EVEN);
        }

        return x1;
    }

}

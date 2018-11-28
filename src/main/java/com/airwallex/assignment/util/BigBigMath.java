package com.airwallex.assignment.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author lx
 * @date 2018-11-23
 */

public class BigDecimalSqrt {
    private static final BigDecimal SQRT_DIG = new BigDecimal(150);
    private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

    /**
     * compute the square root of a BigDecimal.
     *
     */
    public static BigDecimal sqrtNewtonRaphson (BigDecimal c, BigDecimal xn, BigDecimal precision){
        BigDecimal fx = xn.pow(2).add(c.negate());
        BigDecimal fpx = xn.multiply(new BigDecimal(2));
        BigDecimal xn1 = fx.divide(fpx,2*SQRT_DIG.intValue(),RoundingMode.HALF_DOWN);
        xn1 = xn.add(xn1.negate());
        BigDecimal currentSquare = xn1.pow(2);
        BigDecimal currentPrecision = currentSquare.subtract(c);
        currentPrecision = currentPrecision.abs();
        if (currentPrecision.compareTo(precision) <= -1){
            return xn1;
        }
        return sqrtNewtonRaphson(c, xn1, precision);
    }

    /**
     * Uses Newton Raphson to compute the square root of a BigDecimal.
     *
     * @author
     */
    public static BigDecimal sqrt(BigDecimal c){
        return sqrtNewtonRaphson(c,new BigDecimal(1),new BigDecimal(1).divide(SQRT_PRE));
    }


    /**
     * 标准差σ=sqrt(s^2)
     * 结果精度：scale
     * 牛顿迭代法求大数开方
     *
     * @param x
     * @param scale
     * @return
     */
    public static BigDecimal standardDeviation(BigDecimal x, int scale) {
        //方差
        BigDecimal variance = x;
        BigDecimal base2 = BigDecimal.valueOf(2.0);
        //计算精度
        int precision = 100;
        MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
        BigDecimal deviation = variance;
        int cnt = 0;
        while (cnt < 100) {
            deviation = (deviation.add(variance.divide(deviation, mc))).divide(base2, mc);
            cnt++;
        }
        return deviation.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

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
     * @param  scale scale of the <tt>BigDecimal</tt> square root to be returned
     * @return <tt>sqrt(num)</tt>
     */
    public static BigDecimal sqrt(final BigDecimal num, final int scale) {
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

package com.airwallex.assignment;

import org.junit.Test;

public class RPNArithmeticTest {
    @Test
    public void testIsNumeric() {
        double aa = -19162431.1254;
        String a = "-19162431.1254";
        String b = "-19162431a1254";
        String c = "中文";
        System.out.println(isNumeric(Double.toString(aa)));
        System.out.println(isNumeric(a));
        System.out.println(isNumeric(b));
        System.out.println(isNumeric(c));
    }
}

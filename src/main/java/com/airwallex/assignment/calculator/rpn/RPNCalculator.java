package com.airwallex.assignment.calculator.rpn;

import com.airwallex.assignment.calculator.EvalException;
import com.airwallex.assignment.calculator.StackCalculator;
import com.airwallex.assignment.node.Expression;



/**
 * @author lx acte@foxmail.com
 * @date 2018-11-22
 */

public class RPNCalculator<T extends Number> extends StackCalculator<T> {

    @Override
    public void eval(String text) throws EvalException {
        Expression expression = new Expression(text);
//          char[] chars = str.toCharArray();
    }

}

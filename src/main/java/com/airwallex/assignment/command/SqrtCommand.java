package com.airwallex.assignment.command;


import com.airwallex.assignment.util.BigDecimalSqrt;
import com.airwallex.assignment.util.Stack;

import java.math.BigDecimal;
import java.util.Comparator;


public class SqrtCommand implements Command {
    private Arithmetic<BigDecimal> arithmetic;
    private Stack<BigDecimal> stack;
    private BigDecimal num1;

    public SqrtCommand(Stack<BigDecimal> stack, Arithmetic<BigDecimal> arithmetic) {

        this.stack = stack;
        this.arithmetic = arithmetic;
    }

    Comparator dd;
    @Override
    public void execute(String content) {
        num1 = stack.pop();
        stack.push(BigDecimalSqrt.sqrt(num1));
    }

    @Override
    public void unexecute() {
        stack.push(num1);
    }

}
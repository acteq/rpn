package com.airwallex.assignment.command;


import com.airwallex.assignment.util.Stack;

import java.math.BigDecimal;

public class SubtractCommand implements Command {
    private Stack<BigDecimal> stack;
    private Arithmetic<BigDecimal> arithmetic;

    private BigDecimal num1, num2;

    public SubtractCommand(Stack<BigDecimal> stack, Arithmetic<BigDecimal> arithmetic) {
        this.stack = stack;
        this.arithmetic = arithmetic;
    }


    @Override
    public void execute(String content) {
        num1 = stack.pop();
        num2 = stack.pop();
        stack.push(arithmetic.subtract(num1, num2));
    }

    @Override
    public void unexecute() {
        stack.pop();
        stack.push(num2);
        stack.push(num1);
    }
}
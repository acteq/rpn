package com.airwallex.assignment.command;


import com.airwallex.assignment.util.Stack;

import java.math.BigDecimal;
import java.math.MathContext;

public class PushOperand implements Command {
    private Stack<BigDecimal> stack;
    private BigDecimal val;
    private MathContext mathContext;

    public PushOperand(Stack<BigDecimal> stack, MathContext mathContext) {
        this.stack = stack;
        this.mathContext = mathContext;
    }


    @Override
    public void execute(String val) {
        try {
            stack.push(new BigDecimal(val, mathContext));
//            history.push(operator); //clone
        }catch (Exception e){

        }

    }

    @Override
    public void unexecute() {
        stack.pop();
    }
}

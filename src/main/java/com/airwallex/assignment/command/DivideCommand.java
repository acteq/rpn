package com.airwallex.assignment.command;


import com.airwallex.assignment.calculator.EvalException;
import com.airwallex.assignment.util.Stack;

import java.math.BigDecimal;
import java.util.Optional;


public class DivideCommand implements Command {
    private Arithmetic<BigDecimal> arithmetic;
    private Stack<BigDecimal> stack;
    private BigDecimal num1 , num2;

    public DivideCommand(Stack<BigDecimal> stack, Arithmetic<BigDecimal> arithmetic){
        this.stack = stack;
        this.arithmetic = arithmetic;
    }

    @Override
    public void execute(String content) throws EvalException {
        num1 = stack.pop();
        num2 = stack.pop();

        Optional<BigDecimal> num = Optional.ofNullable(num2)
                .filter((val) -> val.compareTo(BigDecimal.ZERO) == 0);

        num.orElseThrow(()->new EvalException(""));
        num.map((val) -> num1.divide(val, mathContext)).ifPresent(stack::push);

    }

    @Override
    public void unexecute() {
        stack.push(num2);
        stack.push(num1);
    }

}

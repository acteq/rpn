package com.airwallex.assignment.command;


import com.airwallex.assignment.calculator.EvalException;

public interface Command {
    void execute(String content) throws EvalException;
    void unexecute();
}

package com.airwallex.assignment.calculator;

import java.util.stream.Stream;

/**
 * @author lx
 * @date 2018-11-24
 */

public interface Calculator<T extends Number> {
    void clear();
    void undo();
    void eval(String expression) throws EvalException;
    Stream<T> getResult();
}

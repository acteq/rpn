package com.airwallex.assignment.calculator.impl;

import com.airwallex.assignment.calculator.Caretaker;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Calculator Implementation interface
 * date 2018-12-01
 * @author lx
 * @version 0.0.2
 * @see StackCalculatorImpl
 */

public interface CalculatorImpl<T> {

    void clear();

    Stream<T> stream();

    T compute(BinaryOperator<T> operator);
    T compute(UnaryOperator<T> operator);
    T compute(T number);

    void setMemento(Momento momento);
    void setCaretaker(Caretaker caretaker);
}

package com.airwallex.assignment.calculator.impl;


import com.airwallex.assignment.calculator.Caretaker;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;


/**
 * 使用堆栈实现的计算器基类，负责操作数保存，算术运算，创建备忘录，恢复
 * <br>具体的表达式解析和附加命令由子类实现，支持波兰表达式，逆波兰表达式
 * <br>date 2018-11-24
 * @author lx
 * @version 0.0.2
 */

public class StackCalculatorImpl<T extends Number> implements CalculatorImpl<T> {

    private final Stack<T> linked = new Stack<>();
    private Caretaker caretaker;

    @Override
    public void clear() {
        linked.clear();
    }

    @Override
    public Stream<T> stream() {
        return linked.stream();
    }

    @Override
    public void setCaretaker(Caretaker caretaker) {
        this.caretaker = caretaker;
    }

    /**
     * 单元运算符入栈，计算，创建和保存备忘录
     * <br>date: 2018-11-28
     * @author: lx
     * @version 0.0.2
     * @param  operator the UnaryOperator
     */
    public T compute(UnaryOperator<T> operator) {
        return Optional.ofNullable(operator)
                .map(op-> {
                    T restore1 = null;
                    //EmptyStackException
                    try {
                        T num1 = restore1 = linked.pop();
                        T result = op.apply(num1);
                        linked.push(result);

                        Optional.ofNullable(caretaker)
                                .ifPresent(taker -> {
                                    T[] state = (T[]) new Number[1];
                                    state[0] = num1;

                                    Momento momento = new Momento();
                                    momento.setState(state);
                                    caretaker.add(momento);
                                });
                        return result;
                    }catch (EmptyStackException e){
                        Optional.ofNullable(restore1).ifPresent(num->linked.push(num));
                        throw e;
                    }catch (Exception e) {
                        Optional.ofNullable(restore1).ifPresent(num->linked.push(num));
                        throw e;
                    }
                }).orElse(null);

    }

    /**
     * 二元运算符入栈，计算，创建和保存备忘录
     * <br>date: 2018-11-28
     * @author: lx
     * @version 0.0.2
     * @param  operator the BinaryOperator
     */
    public T compute(BinaryOperator<T> operator) {
        return Optional.ofNullable(operator)
                .map(op->{
                    T restore1 = null;
                    T restore2 = null;
                    //EmptyStackException
                    try {
                        T num = restore1 = linked.pop();
                        T numPrev = restore2 = linked.pop();

                        T result = op.apply(numPrev, num);
                        linked.push(result);

                        Optional.ofNullable(caretaker)
                                .ifPresent( taker -> {
                                    T[] state = (T[]) new Number[2];
                                    state[0] = numPrev;
                                    state[1] = num;

                                    Momento momento = new Momento();
                                    momento.setState(state);
                                    caretaker.add(momento);
                                });
                        return result;
                    }catch (EmptyStackException e){
                        Optional.ofNullable(restore2).ifPresent(num->linked.push(num));
                        Optional.ofNullable(restore1).ifPresent(num->linked.push(num));
                        throw e;
                    }catch (Exception e) {
                        Optional.ofNullable(restore2).ifPresent(num->linked.push(num));
                        Optional.ofNullable(restore1).ifPresent(num->linked.push(num));
                        throw e;
                    }
                }).orElse(null);

    }

    /**
     * 运算符，创建和保存备忘录
     * <br>2018-11-28
     * @author lx
     * @version 0.0.2
     * @param number type T
     */
    public T compute(T number) {
        return Optional.ofNullable(number)
                .map(num -> {
                    linked.push(num);
                    Optional.ofNullable(caretaker)
                            .ifPresent(taker -> {
                                Momento momento = new Momento();
                                // 设置数组大小为0，恢复的时候就只会出栈，不会入栈
                                momento.setState(new Number[0]);
                                caretaker.add(momento);
                            });
                    return num;
                }).orElse(null);
    }

    /**
     * 备忘录恢复
     * <br>date: 2018-11-28
     * @author lx
     * @version 0.0.1
     * @param momento Momento
     */
    public void setMemento(Momento momento) {
        Optional.ofNullable(momento)
                .map(m -> (T[]) m.getState())
                .ifPresent( state ->{
                    //
                    try {
                        //如果备忘录中记载的状态数组为0，那么是操作数入栈 undo的时候还是需要出栈操作
                        linked.pop();
                        for (T num : state) {
                            linked.push(num);
                        }
                    }catch (EmptyStackException e){
                        //no need to process
                    }
                });

    }


}

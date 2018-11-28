package com.airwallex.assignment.calculator;


import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * @author lx
 * @date 2018-11-24
 */

public abstract class StackCalculator<T extends Number> implements Calculator<T> {

    private final Stack<T> linked = new Stack<>();
    private Caretaker caretaker = null;

    public void setCaretaker(Caretaker caretaker) {
        this.caretaker = caretaker;
    }

    @Override
    public void clear() {
        linked.clear();
    }

    @Override
    public Stream<T> getResult() {
        return linked.stream();
    }


    protected void pushOperator(UnaryOperator<T> operator) {
        Optional.ofNullable(operator)
                .ifPresent(op-> {
                    T restore1 = null;
                    //EmptyStackException
                    try {
                        T num1 = restore1 = linked.pop();
                        linked.push(op.apply(num1));
                        Optional.ofNullable(caretaker)
                                .ifPresent(taker -> {
                                    T[] state = (T[]) new Number[1];
                                    state[0] = num1;

                                    Momento momento = new Momento();
                                    momento.setState(state);
                                    caretaker.add(momento);
                                });
                    }catch (EmptyStackException e){
                        Optional.ofNullable(restore1).ifPresent(num->linked.push(num));
                        throw e;
                    }catch (Exception e) {
                        Optional.ofNullable(restore1).ifPresent(num->linked.push(num));
                        throw e;
                    }
                });

    }

    protected void pushOperator(BinaryOperator<T> operator) {
        Optional.ofNullable(operator)
                .ifPresent(op->{
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

                    }catch (EmptyStackException e){
                        Optional.ofNullable(restore2).ifPresent(num->linked.push(num));
                        Optional.ofNullable(restore1).ifPresent(num->linked.push(num));
                        throw e;
                    }catch (Exception e) {
                        Optional.ofNullable(restore2).ifPresent(num->linked.push(num));
                        Optional.ofNullable(restore1).ifPresent(num->linked.push(num));
                        throw e;
                    }
                });

    }


    protected void pushOperand(T number) {
        Optional.ofNullable(number)
                .ifPresent(num -> {
                    linked.push(num);
                    Optional.ofNullable(caretaker)
                            .ifPresent(taker -> {
                                Momento momento = new Momento();
                                // 设置数组大小为0，恢复的时候就只会出栈，不会入栈
                                momento.setState(new Number[0]);
                                caretaker.add(momento);
                            });
                });
    }


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

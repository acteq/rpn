package com.airwallex.assignment.calculator;

/**
 * @author lx
 * @date 2018-12-01
 */

public class Tuple<F, S> {
    public final F first;
    public final S second;

    public Tuple(F first, S second){
        this.first = first;
        this.second = second;
    }
}

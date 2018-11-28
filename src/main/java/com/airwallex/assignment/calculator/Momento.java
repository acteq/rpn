package com.airwallex.assignment.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lx
 * @date 2018-11-26
 */

public class Memento<T extends Number> {

    private List<T> state = new ArrayList<>();

    List<T> getState(){
        return state;
    }

    private void setState(List<T> state){
        this.state = state;
    }
    private List<T> getState(List<T> state){
        return this.state;
    }

}

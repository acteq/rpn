package com.airwallex.assignment.calculator;


/**
 * @author lx
 * @date 2018-11-26
 */

public class Momento {
    private Object state;

    Momento() {

    }

    void setState(Object state){
        this.state = state;
    }
    Object getState(){
        return this.state;
    }

}

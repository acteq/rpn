package com.airwallex.assignment.calculator;


/**
 * 备忘录
 * <br>date 2018-11-26
 * @author lx
 * @version 0.0.1
 */

public class Momento {
    private Object state;

    Momento() {

    }

    /**
     * 保存状态到备忘录
     * <br>date: 2018-11-28
     * @author: lx
     * @param  state Object
     * @return void
     */
    void setState(Object state){
        this.state = state;
    }

    /**
     * 获取备忘录内保存的状态
     * <br>date: 2018-11-28
     * @author: lx
     * @return Object
     */
    Object getState(){
        return this.state;
    }

}

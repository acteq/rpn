package com.airwallex.assignment.calculator;

/**
 * 备忘录保管者接口
 * date 2018-11-27
 * @author lx
 */

public interface Caretaker {
    /**
     * 加入备忘录
     * <br>date: 2018-11-28
     * @author: lx
     * @param  momento Momento
     * @return boolean
     */
    boolean add(Momento momento);
}

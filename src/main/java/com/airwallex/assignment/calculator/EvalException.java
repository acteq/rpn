package com.airwallex.assignment.calculator;

import java.util.List;

/**
 * 表达式解析抛出的异常类
 * <br>date 2018-11-23
 * @author lx
 */

public class EvalException extends Exception {

    private List<String> list = null;

    public EvalException(String msg){
        super(msg);
    }


    public EvalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 在异常中保存表达式未执行部分
     * <br>date: 2018-11-28
     * @author lx
     * @param  list list of string
     */
    public void setUnhanledList(List<String>   list) {
        this.list = list;
    }

    /**
     * 获取保存的未执行的部分表达式
     * <br>date 2018-11-28
     * @author lx
     * @return list of string
     */
    public List<String> getUnhanledList() {
        return list;
    }
}

package com.airwallex.assignment.calculator;

import java.util.List;

/**
 * @author lx
 * @date 2018-11-23
 */

public class EvalException extends Exception {

    private List<String> list = null;

    public EvalException(String msg){
        super(msg);
    }


    public EvalException(String message, Throwable cause) {
        super(message, cause);
    }

    public void setUnhanledList(List<String> list) {
        this.list = list;
    }

    public List<String> getUnhanledList() {
        return list;
    }
}

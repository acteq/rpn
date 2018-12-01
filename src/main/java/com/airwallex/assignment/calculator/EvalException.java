package com.airwallex.assignment.calculator;


/**
 * 表达式解析抛出的异常类
 * <br>date 2018-11-23
 * @author lx
 */

public class EvalException extends Exception {

    private String msg = null;

    public EvalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 在异常中保存额外消息
     * <br>date: 2018-11-28
     * @author lx
     * @param  msg string
     */
    public void setExtraMessage(String  msg) {
        this.msg = msg;
    }

    /**
     * 获取额外消息
     * <br>date 2018-11-28
     * @author lx
     * @return string
     */
    public String getExtraMessage() {
        return msg;
    }
}

package com.airwallex.assignment.calculator;


import java.util.stream.Stream;

/**
 * 计算器接口
 * <br>date 2018-11-24
 * @author lx
 * @version 0.0.1
 */

public interface Calculator<T extends Number> {
    /**
     * 清除计算器所有数据
     * <br>2018-11-28
     * @author lx
     */
    void clear();

    /**
     * 计算表达式
     * <br>date 2018-11-30
     * @author lx
     * @param  text String
     * @return 计算结果的流
     * @throws EvalException 表达式解析异常
     */
     Stream<T> eval(String text) throws EvalException;

    /**
     * 返回计算器状态
     * <br>date 2018-11-28
     * @author lx
     * @return Stream
     */
    Stream<T> stream();
}

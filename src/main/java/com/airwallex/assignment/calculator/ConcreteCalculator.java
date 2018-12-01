package com.airwallex.assignment.calculator;

import com.airwallex.assignment.calculator.impl.CalculatorImpl;
import com.airwallex.assignment.calculator.impl.Momento;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


/**
 * 具体计算器，负责命令和算术运算符注册，表达式解析
 * date 2018-12-01
 * @author lx acte@foxmail.com
 * @version 0.0.1
 */

public class ConcreteCalculator<T extends Number> implements Calculator<T> {

    private Map<String, Runnable > commandMap = new HashMap<>();
    private Map<String, UnaryOperator<T> > unaryOperatorMap = new HashMap<>();
    private Map<String, BinaryOperator<T> > binaryOperatorHashMap = new HashMap<>();

    private Function<String, T> toNumber;
    private Function<T, String> toText = null;

    private CalculatorImpl<T> impl;
    private Function<String, List<Tuple<String, Integer>>> parser;

    private ConcreteCalculator(){};

    /**
     * @author lx
     * @param impl a CalculatorImpl, @see CalculatorImpl
     * @param toNumber  to convert string to type T
     * @param expression  to parse text to a list of tuple
     * @param <T>   type of number
     * @return Calculator
     */
    public static <T> ConcreteCalculator of(CalculatorImpl<T> impl,
                                            Function<String, T> toNumber,
                                            Function<String, List<Tuple<String, Integer>>> expression) {

        ConcreteCalculator calculator = new ConcreteCalculator();

        calculator.impl = Optional.ofNullable(impl).get();
        calculator.toNumber = Optional.ofNullable(toNumber).get();
        calculator.parser = Optional.ofNullable(expression).get();
        return calculator;
    }

    @Override
    public void clear() {
        impl.clear();
    }

    public void setCaretaker(Caretaker caretaker) {
        impl.setCaretaker(caretaker);
    }


    /**
     * create a custom Exception
     * @author lx
     * @param  e Exception
     * @param  list a list of expression tuple
     * @param  i index of current tuple
     * @param  template Exception
     * @return EvalException
     */
    private  EvalException createEvalException(Exception e, List<Tuple<String,Integer>> list, int i, String template ) {

        Tuple<String, Integer> tuple = list.get(i);

        String msg = String.format(template, tuple.first, tuple.second);
        EvalException ex = new EvalException(msg, e);

        try {
            List<Tuple<String, Integer>> unhandled = list.subList(i + 1, list.size());
            if (unhandled.size() > 0) {
                List<String> temp = unhandled.stream()
                        .map(val -> val.first)
                        .collect(Collectors.toList());

                ex.setExtraMessage("(the " + String.join(",", temp)
                        + " were not pushed on to the stack due to the previous error)");
            }
        }catch(IndexOutOfBoundsException exception){
            //
        }


        return ex;
    }

    /**
     * 计算表达式
     * <br>date 2018-11-28
     * @author lx
     * @param  text String
     * @return result stream
     * @throws EvalException 表达式解析异常
     */
    @Override
    public Stream<T> eval(String text) throws EvalException {

        List<Tuple<String,Integer>> list =  Optional.ofNullable(parser.apply(text)).orElse(new ArrayList<>());

        final String messageTemplate = "%s (position: %s)";

        for(int i=0; i< list.size(); i++ ) {
            Tuple<String, Integer> tuple = list.get(i);

            String node = tuple.first;

            try {
                // 依次检验是否 命令，单元运算符，二元运算符，操作数，做相应动作
                Boolean isOperator = Optional.ofNullable(commandMap.get(node))
                        .map(command -> {
                            command.run();
                            return true;
                        })
                        .orElse(false)
                        || Optional.ofNullable(unaryOperatorMap.get(node))
                        .map(operator -> {
                            impl.compute(operator);
                            return true;
                        })
                        .orElse(false)
                        || Optional.ofNullable(binaryOperatorHashMap.get(node))
                        .map(operator -> {
                            impl.compute(operator);
                            return true;
                        })
                        .orElse(false);

                if (isOperator) {
                    continue;
                }
            }catch (ArithmeticException e) {
                throw createEvalException(e, list, i, "operator "+ messageTemplate + e.getMessage());
            }catch (EmptyStackException e) {
                throw createEvalException(e, list, i, "operator "+ messageTemplate + " insufficient params");
            }catch (Exception e) {
                throw createEvalException(e, list, i, "operator "+ messageTemplate + e.getMessage());
            }

            try{
                T num = toNumber.apply(node);
                impl.compute(num);
            }catch (NumberFormatException e) {
                throw createEvalException(e, list, i, messageTemplate + "unsupported operand or operator");
            }catch (ArithmeticException e){
                throw createEvalException(e, list, i, "operand "+ messageTemplate + e.getMessage());
            }
        }


        return stream();

    }


    /**
     * 注册命令和对应的函数接口
     * <br>date 2018-11-28
     * @author lx
     * @param  key String, 如"undo", "clear"
     * @param  command Runnable接口
     */
    public void registerCommand(String key, Runnable command) {
        commandMap.put(key, command);
    }


    /**
     * 注册一元运算符和对应的函数接口
     * <br>date 2018-11-28
     * @author lx
     * @param  key String 如 "sqrt"
     * @param  operator UnaryOperator接口
     */
    public void registerOperator(String key, UnaryOperator<T> operator) {
        unaryOperatorMap.put(key, operator);
    }

    /**
     * 注册二元运算符和对应的函数接口
     * @author lx
     * @param  key String 如"+", "-", "/", "*"
     * @param  operator BinaryOperator接口
     */
    public void registerOperator(String key, BinaryOperator<T> operator) {
        binaryOperatorHashMap.put(key, operator);
    }


    @Override
    public Stream<T> stream() {
        return impl.stream();
    }

    /**
     * 设定显示精度
     * @author lx
     * @param  toText to convert type T to a string
     */
    public void setDispalyFormat(Function<T, String> toText){
        this.toText = toText;
    }

    /**
     * 按设定的显示精度输出Stack
     * @author lx
     * @return Stack content
     */
    @Override
    public String toString() {
        Function<T, String> func = toText;

        Stream<String> results = stream()
                .map( val -> func == null ? val.toString() : func.apply(val));
        return "stack: " + String.join(" ", results.collect(toList()));
    }


    /**
     * 备忘录恢复
     * <br>date: 2018-12-01
     * @author lx
     * @version 0.0.2
     * @param momento Momento
     */
    public void setMemento(Momento momento) {
        impl.setMemento(momento);
    }


}

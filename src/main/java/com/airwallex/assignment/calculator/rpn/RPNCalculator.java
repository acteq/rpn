package com.airwallex.assignment.calculator.rpn;

import com.airwallex.assignment.calculator.EvalException;
import com.airwallex.assignment.calculator.StackCalculator;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


/**
 * 逆波兰计算器，继承自StackCalculator，负责命令和算术运算符注册，表达式解析
 * date 2018-11-22
 * @author lx acte@foxmail.com
 * @version 0.0.1
 */

public class RPNCalculator<T extends Number> extends StackCalculator<T>  {

    private Map<String, Runnable > commandMap = new HashMap<>();
    private Map<String, UnaryOperator<T> > unaryOperatorMap = new HashMap<>();
    private Map<String, BinaryOperator<T> > binaryOperatorHashMap = new HashMap<>();

    private Function<String, T> toNumber;
    private Function<T, String> toText = null;

    public RPNCalculator(Function<String, T> toNumber) {
        super();
        this.toNumber = toNumber;
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

        Pattern pattern = Pattern.compile("\\S+");
        Matcher m = pattern.matcher(text);

        // lambda表达式作用：当要解析text发现错误时，提取剩余未解析部分
        BiFunction<String, Exception, EvalException> createEvalException = (msg, e) -> {
            EvalException ex = new EvalException(msg, e);

            List<String> list = extractMatchNode(m);

            if(list.size() > 0) {
                ex.setUnhanledList(list);
            }

            return ex;
        };


        while(m.find()) {

            String node = m.group();
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
                            this.pushOperator(operator);
                            return true;
                        })
                        .orElse(false)
                        || Optional.ofNullable(binaryOperatorHashMap.get(node))
                        .map(operator -> {
                            this.pushOperator(operator);
                            return true;
                        })
                        .orElse(false);

                if (isOperator) {
                    continue;
                }
            }catch (ArithmeticException e) {
                String msg = "operator " + node + " (position: " + m.start() + "): " + e.getMessage();
                throw createEvalException.apply(msg, e);
            }catch (EmptyStackException e) {
                String msg = "operator " + node + " (position: " + m.start() + "): " + " insufficient parameters\n";
                throw createEvalException.apply(msg, e);
            }catch (Exception e) {
                String msg = "operator " + node + " (position: " + m.start() + "): " + e.getMessage();
                throw createEvalException.apply(msg, e);
            }

            try{
                T num = toNumber.apply(node);
                this.pushOperand(num);
            }catch (NumberFormatException e) {
                String msg = node + " (position: " + m.start() + "): unknown operator or unsupported operand format" ;
                throw createEvalException.apply(msg, e);
            }catch (ArithmeticException e){
                String msg = "operand " + node + " (position: " + m.start() + "): " + e.getMessage();
                throw createEvalException.apply(msg, e);
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
     * <br>date 2018-11-28
     * @author lx
     * @param  key String 如"+", "-", "/", "*"
     * @param  operator BinaryOperator接口
     */
    public void registerOperator(String key, BinaryOperator<T> operator) {
        binaryOperatorHashMap.put(key, operator);
    }


    /**
     * 提取模式匹配的节点
     * <br>date 2018-11-30
     * @author lx
     * @param  m Matcher
     * @return nodes
     */
    public List<String> extractMatchNode(Matcher m) {

        List<String> list = new ArrayList<>();

        while (m.find()) {
            list.add(m.group());
        }

        return list;
    }

    /**
     * 设定显示精度
     * <br>date 2018-11-28
     * @author lx
     * @return Stack content
     */
    public void setDispalyFormat(Function<T, String> toText){
        this.toText = toText;
    }

    /**
     * 按设定的显示精度输出Stack
     * <br>date 2018-11-28
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
}

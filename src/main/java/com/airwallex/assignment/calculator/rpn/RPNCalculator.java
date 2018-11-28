package com.airwallex.assignment.calculator.rpn;

import com.airwallex.assignment.calculator.EvalException;
import com.airwallex.assignment.calculator.StackCalculator;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author lx acte@foxmail.com
 * @date 2018-11-22
 */

public class RPNCalculator<T extends Number> extends StackCalculator<T>  {

    private Map<String, Runnable > commandMap = new HashMap<>();
    private Map<String, UnaryOperator<T> > unaryOperatorMap = new HashMap<>();
    private Map<String, BinaryOperator<T> > binaryOperatorHashMap = new HashMap<>();

    private Function<String, T> toNumber;

    public RPNCalculator(Function<String, T> toNumber) {
        super();
        this.toNumber = toNumber;
    }

    @Override
    public void eval(String text) throws EvalException {

        Pattern pattern = Pattern.compile("\\S+");
        Matcher m = pattern.matcher(text);

        BiFunction<String, Exception, EvalException> createEvalException = (msg, e) -> {
            EvalException ex = new EvalException(msg, e);

            List<String> list = new ArrayList<>();

            while (m.find()) {
                list.add(m.group());
            }
            if(list.size() > 0) {
                ex.setUnhanledList(list);
            }

            return ex;
        };

        while(m.find()) {

            String node = m.group();
            try {
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

    }


    public void registerCommand(String key, Runnable command) {
        commandMap.put(key, command);
    }

    public void registerOperator(String key, UnaryOperator<T> operator) {
        unaryOperatorMap.put(key, operator);
    }

    public void registerOperator(String key, BinaryOperator<T> operator) {
        binaryOperatorHashMap.put(key, operator);
    }

}

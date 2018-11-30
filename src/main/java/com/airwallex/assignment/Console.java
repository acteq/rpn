package com.airwallex.assignment;

import com.airwallex.assignment.calculator.Caretaker;
import com.airwallex.assignment.calculator.EvalException;
import com.airwallex.assignment.calculator.Momento;
import com.airwallex.assignment.calculator.rpn.RPNCalculator;
import com.airwallex.assignment.calculator.rpn.RPNCalculatorBuilder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


/**
 * 控制台应用程序
 * date 2018-11-25
 * @author lx
 * @version 0.0.1
 */

public class Console implements Caretaker {

    Stack<Momento> momentos = new Stack();

    private static final int STORED_PRECISION = 15;

    private static final int DISPLAY_PRECISION = 10;

    public static void main(String args[]) throws IOException {

        Console console = new Console();

        RPNCalculatorBuilder builder = new RPNCalculatorBuilder();
        RPNCalculator calculator = builder.buildCalculator(STORED_PRECISION);
        builder.buildArithmetic();

        calculator.setCaretaker(console);

        // register command clear, undo
        calculator.registerCommand("clear", () -> { calculator.clear(); console.momentos.clear(); });
        calculator.registerCommand("undo", () -> {
            //EmptyStackException
            try {
                Momento momento = console.momentos.pop();
                calculator.setMemento(momento);
            }catch (EmptyStackException e){
                //nothing to do
            }
        });

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        try{
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> unhanledList = null;
        while(str != null && ! str.isEmpty()){
            try {
                calculator.eval(str);
            }catch (EvalException e){
                System.out.println(e.getMessage());
                unhanledList = e.getUnhanledList();

            }catch (Exception e){
                System.out.println(e.getMessage());
            }finally {

                //displayed to 10 decimal places

                Stream<String> results = calculator.stream()
                        .map( val -> formatBigDecimal((BigDecimal) val, DISPLAY_PRECISION));

                System.out.printf("stack: %s\n", String.join(" ", results.collect(toList())));

                Optional.ofNullable(unhanledList)
                        .ifPresent( list -> {
                            System.out.println("(the " + String.join(",", list)
                                    + " were not pushed on to the stack due to the previous error)");
                        });
            }
            try{
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean add(Momento momento) {
        momentos.push(momento);
        return true;
    }

    /**
     * 把BigDecimal 按设定的精度转换成String
     * <br>date 2018-11-28
     * @author lx
     * @param num 要转换的把BigDecimal
     * @param precision 精度
     * @return String
     */
    public static String formatBigDecimal(BigDecimal num, int precision) {
        return Optional.ofNullable(num)
            .map(val-> {
                String numStr = num.stripTrailingZeros().toPlainString();

                String[] strs = numStr.split("\\.");

                if (strs.length > 1) {
                    strs[1] = strs[1].substring(0, Integer.min(precision, strs[1].length()));
                }
                return String.join(".", strs);

            }).orElse(null);
    }
}

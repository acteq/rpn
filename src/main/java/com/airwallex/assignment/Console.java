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
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;


/**
 * @author lx
 * @date 2018-11-25
 */

public class Console implements Caretaker {

    Stack<Momento> momentos = new Stack();

    public static void main(String args[]) throws IOException {

        Console console = new Console();

        RPNCalculatorBuilder builder = new RPNCalculatorBuilder();
        RPNCalculator calculator = builder.buildCalculator(15);
        builder.buildArithmetic();

        calculator.setCaretaker(console);
        // register command clear, undo
        calculator.registerCommand("clear", () -> calculator.clear());
        calculator.registerCommand("undo", () -> {
            //EmptyStackException
            Momento momento = console.momentos.pop();
            calculator.setMemento(momento);
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
                System.out.print("stack:");
                //displayed to 10 decimal places
//                List<String> results = calculator.getResult().map(val-> ((BigDecimal)val).toPlainString()).collect(Collectors.toList());
                calculator.getResult().forEach( val -> System.out.printf(" %.10f", (BigDecimal)val));
                System.out.print("\n");

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
}

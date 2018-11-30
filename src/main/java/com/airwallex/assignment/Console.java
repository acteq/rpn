package com.airwallex.assignment;

import com.airwallex.assignment.calculator.Caretaker;
import com.airwallex.assignment.calculator.EvalException;
import com.airwallex.assignment.calculator.Momento;
import com.airwallex.assignment.calculator.rpn.RPNCalculator;
import com.airwallex.assignment.calculator.rpn.RPNCalculatorBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 控制台应用程序
 * date 2018-11-25
 * @author lx
 * @version 0.0.1
 */

public class Console implements Caretaker {

    private Stack<Momento> momentos = new Stack();

    public static void main(String args[])  {

        Console console = new Console();

        RPNCalculator calculator = new RPNCalculatorBuilder()
                .buildArithmetic(15)
                .setDisplayPrecision(10)
                .build();

        calculator.setCaretaker(console);

        // register command clear, undo
        calculator.registerCommand("clear", () -> { calculator.clear(); console.momentos.clear(); });
        calculator.registerCommand("undo", () -> {
            //EmptyStackException
            try {
                calculator.setMemento(console.momentos.pop());
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
                System.out.printf("%s\n", calculator.toString());
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

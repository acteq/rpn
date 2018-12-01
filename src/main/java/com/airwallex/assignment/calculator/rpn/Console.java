package com.airwallex.assignment.calculator.rpn;

import com.airwallex.assignment.calculator.*;
import com.airwallex.assignment.calculator.impl.Momento;

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

    private Stack<Momento> momentos = new Stack<>();

    public static void main(String args[])  {

        Console console = new Console();

        ConcreteCalculator calculator = new CalculatorBuilder(Parser::parse)
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

        String unhanled = null;
        while(str != null && ! str.isEmpty()){
            try {
                calculator.eval(str);
            }catch (EvalException e){
                System.out.println(e.getMessage());
                unhanled = e.getExtraMessage();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }finally {
                System.out.printf("%s\n", calculator.toString());
                if(unhanled != null)
                    System.out.println(unhanled);
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

package com.airwallex.assignment;

import com.airwallex.assignment.calculator.Caretaker;
import com.airwallex.assignment.calculator.EvalException;
import com.airwallex.assignment.calculator.Momento;
import com.airwallex.assignment.calculator.rpn.RPNCalculator;
import com.airwallex.assignment.calculator.rpn.RPNCalculatorBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lx
 * @date 2018-11-25
 */

public class Main {
    public static void main(String args[]) throws IOException {

        List<Momento> momentos = new ArrayList<>();

        RPNCalculatorBuilder builder = new RPNCalculatorBuilder();
        RPNCalculator calculator = builder.buildCalculator(15);
        builder.buildArithmetic();

        calculator.setCaretaker(momentos);
        momentos.ad.add(null);

        calculator.registerCommand("clear", val -> calculator.clear());
        calculator.registerCommand("undo", val -> {
            Momento momento = momentos.pop();
            calculator.setMemento(momento);
        });

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        while(! str.isEmpty()){

            try {
                calculator.eval(str);
            }catch (EvalException e){
                System.out.printf("", e.getMessage());
            }finally {
                System.out.print("stack:");
                //displayed to 10 decimal places
                calculator.getResult().forEach( val -> System.out.printf(" %f", val));
                // unpushed ....
            }

            str = br.readLine();
        }
    }
}

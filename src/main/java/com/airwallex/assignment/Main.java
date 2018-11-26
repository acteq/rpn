package com.airwallex.assignment;

import com.airwallex.assignment.calculator.EvalException;
import com.airwallex.assignment.calculator.rpn.RPNCalculator;
import com.airwallex.assignment.calculator.rpn.RPNCalculatorBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author lx
 * @date 2018-11-25
 */

public class Main {
    public static void main(String args[]) throws IOException {
        RPNCalculatorBuilder builder = new RPNCalculatorBuilder();
        RPNCalculator rpnCalculator = builder.buildCalculator(15);
        builder.buildArithmetic();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        while(! str.isEmpty()){

            try {
                rpnCalculator.eval(str);
            }catch (EvalException e){
                System.out.printf("", e.getMessage());
            }finally {
                System.out.print("stack:");
                //displayed to 10 decimal places
                rpnCalculator.getResult().forEach( val -> System.out.printf(" %f", val));
                // unpushed ....
            }

            str = br.readLine();
        }
    }
}

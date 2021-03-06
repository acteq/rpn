package com.airwallex.assignment.calculator;

import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * parse text to a list of tuple
 * @author lx
 */

public class Parser {

    private Parser(){}

    public static List<Tuple<String, Integer>> parse(String text) {
        Pattern pattern = Pattern.compile("\\S+");
        Matcher m = pattern.matcher(text);

        List<Tuple<String, Integer>> list = new ArrayList<>();
        while(m.find()) {
            list.add(new Tuple<>(m.group(), m.start()));
        }

        return list;
    }
}

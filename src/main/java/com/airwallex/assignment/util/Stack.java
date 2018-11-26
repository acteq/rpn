package com.airwallex.assignment.util;

import java.util.stream.Stream;

/**
 * @author lx
 * @datT 2018-11-22
 */

public interface Stack<T> {
    T push(T item) ;

    T pop();

    T peek() ;

    int search(T item);

    boolean empty() ;

    Stream<T> stream();
}

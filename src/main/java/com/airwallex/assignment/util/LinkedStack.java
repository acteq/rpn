package com.airwallex.assignment.util;


import java.util.LinkedList;
import java.util.stream.Stream;


public class LinkedStack<T> implements Stack<T> {

    private LinkedList<T> linked ;

    public LinkedStack() {
        this.linked = new LinkedList<T>();
    }

    public T push(T item) {

        this.linked.addFirst(item);
        return item;
    }

    public T pop() {

        if (this.linked.isEmpty()) {
            return null;
        }
        return this.linked.removeFirst();
    }

    public T peek() {
        return this.linked.peek();
    }

    public int search(T item) {
        int i = this.linked.indexOf(item);
        return i + 1;
    }

    public boolean empty() {
        return this.linked.isEmpty();
    }

    public Object clone() {
        //shallow clone
        return this.linked.clone();
    }

    public Stream<T> stream() {
        return this.linked.stream();
    }
}
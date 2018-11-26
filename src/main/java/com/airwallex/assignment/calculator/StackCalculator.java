package com.airwallex.assignment.calculator;

import com.airwallex.assignment.node.Operand;
import com.airwallex.assignment.node.Operator;
import com.airwallex.assignment.visitor.NodeVisitor;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * @author lx
 * @date 2018-11-24
 */

public abstract class StackCalculator<T extends Number>  implements Calculator<T>, NodeVisitor {

    private LinkedList<T> linked;


    private HashMap<String, UnaryOperator<T> > unaryOperatorMap = new HashMap<>();
    private HashMap<String, BinaryOperator<T> > binaryOperatorHashMap = new HashMap<>();
    private Map<String, Consumer<String > > commandMap = new HashMap<>();

    private Function<String, T> operandConverter;

    public StackCalculator( ) {

        linked  = new LinkedList<T>();
    }

    @Override
    public void clear() {
        linked.clear();
    }

    @Override
    public void undo() {

    }

    @Override
    public Stream<T> getResult() {
        return linked.stream();
    }

    public void setOperandConverter(Function<String, T> convert) {
        operandConverter = convert;
    }

    @Override
    public void visitOperand(Operand operand) {
        try {
            push(operandConverter.apply(operand.getText()));
        } catch (Exception e) {

        }
    }

    @Override
    public void visitOperator(Operator operator) {
        String text = operator.getText();
        UnaryOperator<T> command = unaryOperatorMap.get(text);

        if(command != null) {
            T num1 = pop();
            command.apply(num1);

        }else{
            BinaryOperator<T> operator1 = binaryOperatorHashMap.get(text);
            if(operator1 != null) {
                T num1 = pop();
                T num2 = pop();
                operator1.apply(num1, num2);
            }
        }
    }

    public void registerOperator(String key, UnaryOperator<T> operator) {
        if(binaryOperatorHashMap.containsKey(key)) {
            binaryOperatorHashMap.remove(key);
        }
        unaryOperatorMap.put(key, operator);
    }

    public void registerOperator(String key, BinaryOperator<T> operator) {
        if(unaryOperatorMap.containsKey(key)){
            unaryOperatorMap.remove(key);
        }
        binaryOperatorHashMap.put(key, operator);
    }

    public void registerCommand(String key, Consumer<String > command) {
        if(binaryOperatorHashMap.containsKey(key)) {
            binaryOperatorHashMap.remove(key);
        }
        commandMap.put(key, command);
    }

    private class Memento {

        private List<T> state = new ArrayList<>();

        List<T> getState(){
            return state;
        }

        void setState(List<T> state){
            this.state = state;
        }
    }

    public Memento createMemento() {

        Memento memento = new Memento();
        List<T> state = new ArrayList<>();

//        memento.setState();
        return memento;
    }

    public void setMemento(Memento memento) {

        List<T> state = memento.getState();
        pop();

        state.stream().forEach(this::push);

    }

    private T push(T item) {
        this.linked.addFirst(item);
        return item;
    }

    private T pop() {

        if (this.linked.isEmpty()) {
            return null;
        }
        return this.linked.removeFirst();
    }

}

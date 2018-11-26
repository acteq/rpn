package com.airwallex.assignment.node;

import com.airwallex.assignment.visitor.NodeVisitor;

/**
 * @author lx
 * @date 2018-11-23
 */

public class Operand implements Node {

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visitOperand(this);
    }

    private String text;

    public Operand(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}

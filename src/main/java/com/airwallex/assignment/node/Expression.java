package com.airwallex.assignment.node;

import com.airwallex.assignment.visitor.NodeVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lx
 * @date 2018-11-23
 */

public class Expression implements Node {

    private List<Node> list = new ArrayList();

    @Override
    public void accept(NodeVisitor visitor) {
        list.stream().forEach( node -> node.accept(visitor));
    }

    public Expression(String text) {
        String[] tokens = text.split(" ");
    }
}

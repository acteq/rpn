package com.airwallex.assignment.node;

import com.airwallex.assignment.visitor.NodeVisitor;

public interface Node {
    void accept(NodeVisitor visitor);
}

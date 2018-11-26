package com.airwallex.assignment.visitor;


import com.airwallex.assignment.node.Operand;
import com.airwallex.assignment.node.Operator;

public interface NodeVisitor {
    void visitOperand(Operand operand);
    void visitOperator(Operator operator);
}

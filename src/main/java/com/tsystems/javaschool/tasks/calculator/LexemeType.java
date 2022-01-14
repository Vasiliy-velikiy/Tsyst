package com.tsystems.javaschool.tasks.calculator;
/**
 * this enum contains BRACKETS, numerical operations, number such as: 5, 6, 4.535 */
public enum LexemeType {

    LEFT_BRACKET, RIGHT_BRACKET,
    OP_PLUS,OP_MINUS,OP_MUL,PO_DIV,
    NUMBER,
    EOF;     //end of string
}

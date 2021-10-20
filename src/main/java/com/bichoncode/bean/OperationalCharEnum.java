package com.bichoncode.bean;

public enum OperationalCharEnum {

    PlUS("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    LEFT_BRACKETS("("),
    RIGHT_BRACKETS(")");
    // 操作字符
    private String valueChar;

    OperationalCharEnum(String valueChar) {
        this.valueChar = valueChar;
    }

    public String getValueChar() {
        return valueChar;
    }

    public void setValueChar(String valueChar) {
        this.valueChar = valueChar;
    }
}

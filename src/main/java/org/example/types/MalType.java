package org.example.types;

public abstract class MalType {

    public enum Type {
        SYMBOL, DIGIT, LIST, FUNCTION
    }

    public abstract Type getType();

    public abstract String getValue();
}

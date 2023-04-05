package org.example.types;

import static org.example.types.MalType.Type.DIGIT;

public class MalDigit extends MalType {
    public MalDigit(String token) {
        super();
        this.value = token;
    }

    private final String value;

    @Override
    public Type getType() {
        return DIGIT;
    }

    @Override
    public String getValue() {
        return value;
    }
}

package org.example.types;

import static org.example.types.MalType.Type.SYMBOL;

public class MalSymbol extends MalType {
    public MalSymbol(String token) {
        super();
        this.value = token;
    }

    private final String value;

    @Override
    public Type getType() {
        return SYMBOL;
    }

    @Override
    public String getValue() {
        return value;
    }
}

package org.example.types;

import static org.example.types.MalType.Type.LIST;

import java.util.ArrayList;

public class MalList extends MalType {
    private final ArrayList<MalType> tokens;

    public MalList() {
        this.tokens = new ArrayList<>();
    }

    public ArrayList<MalType> getTokens() {
        return tokens;
    }

    public void addToken(MalType token) {
        tokens.add(token);
    }

    @Override
    public Type getType() {
        return LIST;
    }

    @Override
    public String getValue() {
        var strings = new ArrayList<String>();
        for (MalType t : tokens) {
            strings.add(t.getValue());
        }
        if (tokens.size() > 0) {
            return "(" + String.join(" ", strings) + ")";
        } else {
            return "()";
        }
    }
}

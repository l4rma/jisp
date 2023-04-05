package org.example.types;

import static org.example.types.MalType.Type.FUNCTION;

import java.util.function.BiFunction;

public class MalFunction extends MalType {

    BiFunction<Integer, Integer, Integer> lambda;

    public MalFunction(BiFunction<Integer, Integer, Integer> lambda) {
        this.lambda = lambda;
    }

    public BiFunction<Integer, Integer, Integer> getLambda() {
        return lambda;
    }

    @Override
    public Type getType() {
        return FUNCTION;
    }

    @Override
    public String getValue() {
        return "fn";
    }
}

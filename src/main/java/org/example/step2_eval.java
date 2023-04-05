package org.example;

import static org.example.types.MalType.Type.FUNCTION;
import static org.example.types.MalType.Type.LIST;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BiFunction;
import org.example.types.MalContinue;
import org.example.types.MalDigit;
import org.example.types.MalException;
import org.example.types.MalFunction;
import org.example.types.MalList;
import org.example.types.MalType;

public class step2_eval {
    Reader r = new Reader();
    Printer p = new Printer();


    private MalType Read(String str) throws MalException {
        return r.readString(str);
    }

    private MalType eval(MalType ast, HashMap<String, BiFunction<Integer, Integer, Integer>> replEnv) throws MalException {
        if (ast.getType() == LIST) {
            MalList list = (MalList) ast;
            if (list.getTokens().size() < 1) {
                return ast;
            }
            list = (MalList) evalAst(ast, replEnv);
            var tokens = list.getTokens();
            if (list.getTokens().get(0).getType() != FUNCTION) {
                return list;
            }
            var fn = (MalFunction) list.getTokens().get(0);
            var a = Integer.parseInt(tokens.get(1).getValue());
            var b = Integer.parseInt(tokens.get(2).getValue());
            var lambda = fn.getLambda();
            return new MalDigit(lambda.apply(a, b).toString());
        }

        return evalAst(ast, replEnv);
    }

    private MalType evalAst(MalType ast, HashMap<String, BiFunction<Integer, Integer, Integer>> env) throws MalException {
        switch (ast.getType()) {
            case SYMBOL:
                for (String symbol : env.keySet()) {
                    if (symbol.equals(ast.getValue())) {
                        return new MalFunction(env.get(symbol));
                    }
                }
                throw new MalException("Unknown symbol");
            case LIST:
                var newList = new MalList();
                MalList list = (MalList) ast;
                for (MalType member : list.getTokens()) {
                    newList.addToken(eval(member, env));
                }
                return newList;
            default:
                return ast;
        }
    }

    private String Print(MalType exp) {
        return p.printString(exp);
    }

    public String Rep(String input, HashMap<String, BiFunction<Integer, Integer, Integer>> env) throws MalException {
        var read = Read(input);
        var eval = eval(read, env);
        return Print(eval);
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var lexer = new step2_eval();
        HashMap<String, BiFunction<Integer, Integer, Integer>> replEnv = new HashMap<>();
        replEnv.put("+", Integer::sum);
        replEnv.put("-", (a, b) -> a - b);
        replEnv.put("*", (a, b) -> a * b);
        replEnv.put("/", (a, b) -> a / b);
        replEnv.put("%", (a, b) -> a % b);


        while (true) {
            System.out.print("user> ");
            var input = scanner.nextLine();
            if ("exit".equals(input)) {
                break;
            }
            try {
                System.out.println(lexer.Rep(input, replEnv));
            } catch (MalContinue e) {
                System.out.print("");
            } catch (MalException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
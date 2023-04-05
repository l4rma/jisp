package org.example;

import java.util.Scanner;
import org.example.types.MalException;
import org.example.types.MalType;

public class step1_read_print {
    Reader r = new Reader();
    Printer p = new Printer();

    private MalType Read(String str) throws MalException {
        return r.readString(str);
    }

    private MalType Eval(MalType ast) {
        return ast;
    }

    private String Print(MalType exp) {
        return p.printString(exp);
    }

    public String Rep(String input) throws MalException {
        var read = Read(input);
        var eval = Eval(read);
        return Print(eval);
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var lexer = new step1_read_print();

        while (true) {
            System.out.print("user> ");
            var input = scanner.nextLine();
            if ("exit".equals(input)) {
                break;
            }
            try {
                System.out.println(lexer.Rep(input));
            } catch (MalException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

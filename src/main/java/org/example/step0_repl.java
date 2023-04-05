package org.example;

import java.util.Scanner;

public class step0_repl {
    private String Read(String input) {
        return input;
    }

    private String Eval(String input) {
        return input;
    }

    private String Print(String input) {
        return input;
    }

    public String Rep(String input) {
        var read = Read(input);
        var eval = Eval(read);
        return Print(eval);
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var lexer = new step0_repl();

        while (true) {
            System.out.print("user> ");
            var input = scanner.nextLine();
            if ("exit".equals(input)) {
                break;
            }
            System.out.println(lexer.Rep(input));
        }
    }
}

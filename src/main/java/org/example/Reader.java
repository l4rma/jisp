package org.example;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.types.MalContinue;
import org.example.types.MalDigit;
import org.example.types.MalException;
import org.example.types.MalList;
import org.example.types.MalSymbol;
import org.example.types.MalType;

public class Reader {
    public static class ReaderObject {
        private final ArrayList<String> tokens;
        private int pos;

        public ReaderObject(ArrayList<String> tokens) {
            this.tokens = tokens;
            this.pos = 0;
        }

        private String next() {
            return this.tokens.get(this.pos++);
        }

        private String peek() {
            if (this.pos > this.tokens.size() - 1) {
                return null;
            }
            return this.tokens.get(this.pos);
        }
    }

    public MalType readString(String input) throws MalException {
        var tokens = tokenize(input);
        var reader = new ReaderObject(tokens);
        return readForm(reader);
    }

    private MalType readForm(ReaderObject reader) throws MalException {
        var currentToken = reader.peek();
        if (currentToken == null) {
            throw new MalContinue();
        }

        switch (currentToken.charAt(0)) {
            case '(':
                return readList(reader);
            case ')':
                throw new MalException("Unexpected ')'");
            default:
                return readAtom(reader);
        }
    }

    private MalType readAtom(ReaderObject reader) throws MalException {
        var currentToken = reader.next();
        if (Character.isDigit(currentToken.charAt(0))) {
            return new MalDigit(currentToken);
        } else if (Character.isLetter(currentToken.charAt(0)) || currentToken.substring(0, 1).matches("[+\\-*/%]")) {
            return new MalSymbol(currentToken);
        } else {
            throw new MalException("Unknown token");
        }
    }

    private MalList readList(ReaderObject reader) throws MalException {
        var list = new MalList();
        var currentToken = reader.next();
        if (currentToken.charAt(0) != '(') {
            System.out.println("WTF!?!?!?!");
        }

        while ((currentToken = reader.peek()) != null && currentToken.charAt(0) != ')') {
            list.addToken(readForm(reader));
        }

        if (currentToken == null) {
            throw new MalException("Expected ), got EOF");
        }
        reader.next();

        return list;
    }

    public ArrayList<String> tokenize(String input) {
        var tokens = new ArrayList<String>();
        Pattern pattern = Pattern.compile("[\\s ,]*(~@|[\\[\\]{}()'`~@]|\"(?:\\\\.|[^\\\\\"])*\"?|;.*|[^\\s \\[\\]{}()'\"`~@,;]*)");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String token = matcher.group(1);
            if (token != null && !"".equals(token) && !(token.charAt(0) == ';')) {
                tokens.add(token);
            }
        }
        return tokens;
    }

}

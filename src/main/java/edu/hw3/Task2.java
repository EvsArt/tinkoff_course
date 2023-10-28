package edu.hw3;

import java.util.ArrayList;
import java.util.List;

public final class Task2 {

    private static final char OPENBRACKET = '(';
    private static final char CLOSEBRACKET = ')';

    private Task2() {
    }

    public static List<String> clusterize(String input) {

        ArrayList<String> result = new ArrayList<>();
        int unclosedBrackets = 0;
        ArrayList<Character> claster = new ArrayList<>();
        char nextChar;

        for (int i = 0; i < input.length(); i++) {

            nextChar = input.charAt(i);
            if (nextChar == OPENBRACKET) {
                unclosedBrackets++;
            } else if (nextChar == CLOSEBRACKET) {
                unclosedBrackets--;
            }
            claster.add(nextChar);

            if (unclosedBrackets < 0) {   // Situation like ")("
                claster.clear();
                unclosedBrackets = 0;
            }

            if (unclosedBrackets == 0 && !claster.isEmpty()) {
                result.add(charactersListToString(claster));
                claster = new ArrayList<>();
            }

        }

        return result;

    }

    private static String charactersListToString(List<Character> list) {
        StringBuilder builder = new StringBuilder();
        for (Character str : list) {
            builder.append(str);
        }
        return builder.toString();
    }

}

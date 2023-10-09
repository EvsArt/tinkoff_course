package edu.hw1;

public final class Task4 {

    private Task4() {
    }

    public static String fixString(String unfixedString) {

        char[] str = unfixedString.toCharArray();

        char tmp;
        for (int i = 0; i < str.length - 1; i += 2) {
            tmp = str[i + 1];
            str[i + 1] = str[i];
            str[i] = tmp;
        }

        return String.valueOf(str);

    }

}

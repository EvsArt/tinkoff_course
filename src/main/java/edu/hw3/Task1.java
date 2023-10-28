package edu.hw3;

public final class Task1 {

    private Task1() {
    }

    private static final int ALPHABETSTARTCODE = 'a';
    private static final int ALPHABETSIZE = 26;

    public static String atbash(String input) {

        char[] result = new char[input.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = getMirrorChar(input.charAt(i));
        }

        return String.valueOf(result);

    }

    protected static char getMirrorChar(char ch) {

        char charCopy = ch;
        boolean isUpperCase = Character.isUpperCase(charCopy);
        charCopy = Character.toLowerCase(charCopy);

        if (charCopy < ALPHABETSTARTCODE || charCopy > ALPHABETSTARTCODE + ALPHABETSIZE) {
            return ch;
        }

        int charPosInAlph = charCopy - ALPHABETSTARTCODE;
        char mirrorChar = (char) (ALPHABETSTARTCODE + ALPHABETSIZE - charPosInAlph - 1);

        if (isUpperCase) {
            mirrorChar = Character.toUpperCase(mirrorChar);
        }

        return mirrorChar;
    }

}

package edu.hw1;

import java.util.Arrays;

public final class Task6 {

    private Task6() {
    }

    private static final String KAPREKARA_CONST = "6174";

    @SuppressWarnings("MagicNumber")
    public static int countK(int num) {
        if (num <= 1000 || num > 9999) {
            return -1;
        }

        String numStr = String.valueOf(num);
        boolean allNumsSimilar = true;
        char firstChar = numStr.charAt(0);
        for (char curChar : numStr.toCharArray()) {
            if (curChar != firstChar) {
                allNumsSimilar = false;
                break;
            }
        }
        if (allNumsSimilar) {
            return -1;
        }

        return recursiveGetCount(numStr, 0);

    }

    private static int recursiveGetCount(String numStr, int startCount) {

        if (numStr.equals(KAPREKARA_CONST)) {
            return startCount;
        }

        return recursiveGetCount(kaprekaraFunc(numStr), startCount + 1);

    }

    public static String kaprekaraFunc(String numStr) {
        char[] numCharArr = numStr.toCharArray();
        Arrays.sort(numCharArr);
        int smallerNum = Integer.parseInt(String.valueOf(numCharArr));
        char tmp;
        for (int i = 0; i < numCharArr.length / 2; i++) {       // reverse numCharArr
            tmp = numCharArr[i];
            numCharArr[i] = numCharArr[numCharArr.length - i - 1];
            numCharArr[numCharArr.length - i - 1] = tmp;
        }
        int biggerNum = Integer.parseInt(String.valueOf(numCharArr));

        return String.valueOf(biggerNum - smallerNum);

    }

}

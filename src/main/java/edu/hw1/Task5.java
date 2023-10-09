package edu.hw1;

public final class Task5 {

    private Task5() {
    }

    @SuppressWarnings("MagicNumber")
    public static boolean isPalindromeDescendant(int num) {

        int numCopy = num;
        if (numCopy < 0) {
            numCopy = -numCopy;
        }

        while (numCopy / 10 != 0) {
            if (isPalindrome(numCopy)) {
                return true;
            }
            numCopy = getNumChild(numCopy);
        }

        return false;
    }

    @SuppressWarnings("MagicNumber")
    public static boolean isPalindrome(int num) {

        int numCopy = num;
        if (numCopy < 0) {
            numCopy = -numCopy;
        }
        int tmp = numCopy;

        int numLength = 0;
        do {
            numCopy /= 10;
            numLength++;
        } while (numCopy > 0);

        numCopy = tmp;

        for (int i = 0; i < numLength / 2; i++) {
            int num1 = numCopy / (int) Math.pow(10, numLength - i - 1) % 10;
            int num2 = numCopy % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
            if (num1 != num2) {
                return false;
            }
        }
        return true;

    }

    @SuppressWarnings("MagicNumber")
    public static int getNumChild(int num) {

        int in = num;
        if (in < 0) {
            in = -in;
        }
        int tmp;
        int res = 0;
        int nextInd = 0;
        int tmpSum;

        do {
            tmp = in % 100;
            tmpSum = tmp / 10 + tmp % 10;
            res += tmpSum * Math.pow(10, nextInd);

            nextInd++;
            if (tmpSum / 10 != 0) {
                nextInd++;
            }
            in /= 100;

        } while (in > 0);

        return res;

    }

}

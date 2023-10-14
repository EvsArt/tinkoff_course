package edu.hw1;

public final class Task2 {

    private Task2() {
    }

    @SuppressWarnings("MagicNumber")
    public static int countDigits(int num) {

        int res = 0;

        int numCopy = num;
        if (numCopy < 0) {
            numCopy = -numCopy;
        }

        do {
            numCopy /= 10;
            res++;
        } while (numCopy > 0);

        return res;

    }

}

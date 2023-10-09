package edu.hw1;

public final class Task7 {

    private Task7() {
    }

    public static int rotateLeft(int n, int shift) {
        if (n <= 0) {
            return -1;
        }
        int numCopy = n;
        int bitLen = (int) (Math.log(n) / Math.log(2)) + 1;

        for (int i = 0; i < shift; i++) {
            boolean leftBit = numCopy >= Math.pow(2, bitLen - 1);
            numCopy = numCopy << 1;
            numCopy %= Math.pow(2, bitLen); // Сохранение исходного количества битов
            if (leftBit) {
                numCopy++;   // Заполнение правого бита
            }
        }
        return numCopy;
    }

    public static int rotateRight(int n, int shift) {
        if (n <= 0) {
            return -1;
        }

        int numCopy = n;
        int bitLen = (int) (Math.log(n) / Math.log(2)) + 1;

        for (int i = 0; i < shift; i++) {
            boolean rightBit = numCopy % 2 == 1;
            numCopy = numCopy >> 1;
            if (rightBit) {
                numCopy += Math.pow(2, bitLen - 1);   // Заполнение правого бита
            }
        }

        return numCopy;
    }

}

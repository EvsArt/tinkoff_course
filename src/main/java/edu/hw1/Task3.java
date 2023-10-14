package edu.hw1;

public final class Task3 {

    private Task3() {
    }

    public static boolean isNestable(int[] mas1, int[] mas2) {

        if (mas1.length == 0 || mas2.length == 0) {
            return true;
        }

        int min1 = mas1[0];
        int max1 = mas1[0];
        int min2 = mas2[0];
        int max2 = mas2[0];

        for (int i : mas1) {
            if (i < min1) {
                min1 = i;
            }
            if (i > max1) {
                max1 = i;
            }
        }

        for (int i : mas2) {
            if (i < min2) {
                min2 = i;
            }
            if (i > max2) {
                max2 = i;
            }
        }

        return min1 > min2 && max1 < max2;

    }

}

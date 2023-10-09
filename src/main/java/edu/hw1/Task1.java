package edu.hw1;

public final class Task1 {

    private Task1() {
    }

    @SuppressWarnings("MagicNumber")
    public static int minutesToSeconds(String time) {
        int minutes = Integer.parseInt(time.split(":")[0]);
        int seconds = Integer.parseInt(time.split(":")[1]);

        if (minutes < 0 || seconds < 0 || seconds >= 60) {
            return -1;
        }
        return minutes * 60 + seconds;

    }

}

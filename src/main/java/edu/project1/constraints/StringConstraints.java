package edu.project1.constraints;

public final class StringConstraints {

    private StringConstraints() {
    }

    public static String startEveryOutputLine = ">";
    public static String startEveryInputLine = "<";

    public static String winMsg = "You win!";
    public static String lossMsg = "You lost!";
    public static String successGuessMsg = "Hit!";
    public static String failedGuessMsg = "Missed!";
    public static String wrongInputMsg = "Wrong input! Only one character allowed!";
    public static String uselessInput = "Useless! This letter has already been guessed!";

    public static String guessALetter = "Guess a letter:";

    public static String stringMistakesCount(int mistakes, int maxMistakes) {
        return String.format("Mistakes: %d of %d", mistakes, maxMistakes);
    }

    public static String stringWordState(char[] wordState) {
        return String.format("The word: %s", String.valueOf(wordState));
    }
}

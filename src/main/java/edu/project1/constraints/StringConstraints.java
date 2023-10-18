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
    public static String goodbye = "Goodbye!";

    public static String guessALetter = "Guess a letter:";
    public static String stringForExit = "exit";
    public static String howToExit = "Print \"" + stringForExit + "\" for exit";
    public static String yes = "yes";
    public static String no = "no";
    public static String suggestionToPlayOneMoreGame =
        "Do you want to play one more game? (print \"" + yes + "\" or \"" + no + "\")";
    public static String incorrectInputYesOrNo = "Incorrect input! Please print \"yes\" or \"no\"";

    public static String stringMistakesCount(int mistakes, int maxMistakes) {
        return String.format("Mistakes: %d of %d", mistakes, maxMistakes);
    }

    public static String stringWordState(char[] wordState) {
        return String.format("The word: %s", String.valueOf(wordState));
    }
}

package edu.project1.pojo;

import edu.project1.constraints.Constraints;
import edu.project1.response.AttemptResult;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Word {

    private final int firstLetterIndex = 0;
    private final char nonguessLetterChar = Constraints.NONGUESSLEERCHAR;

    private final String word;
    private final char[] wordState;   // If client guessed "l" in "Hello", it will be "**ll*"
    private final Set<Character> nonGuessedChars;
    private final Set<Character> guessedChars;

    public Word(String word) {
        this.word = word;

        wordState = new char[word.length()];
        Arrays.fill(wordState, nonguessLetterChar);

        nonGuessedChars = new HashSet<>(word.length());
        for (char ch : word.toLowerCase().toCharArray()) {
            nonGuessedChars.add(ch);
        }

        guessedChars = new HashSet<>(word.length());
    }

    public GuessResult guessCharacter(char userChar) {

        AttemptResult attemptResult;
        char ch = Character.toLowerCase(userChar);

        if (guessedChars.contains(ch)) {
            attemptResult = AttemptResult.USELESSINPUT;
            return new GuessResult(attemptResult, Arrays.copyOf(wordState, word.length()));
        }

        if (nonGuessedChars.contains(ch)) {

            openGuessedLetters(ch);

            nonGuessedChars.remove(ch);
            guessedChars.add(ch);

            if (nonGuessedChars.isEmpty()) {
                attemptResult = AttemptResult.WINNING;
            } else {
                attemptResult = AttemptResult.SUCCESSFULGUESS;
            }

            return new GuessResult(attemptResult, Arrays.copyOf(wordState, word.length()));
        }

        attemptResult = AttemptResult.FAILEDGUESS;
        return new GuessResult(attemptResult, Arrays.copyOf(wordState, word.length()));

    }

    public String getWord() {
        return word;
    }

    private void openGuessedLetters(char ch) {

        if (Character.toLowerCase(word.charAt(firstLetterIndex)) == ch) {
            wordState[firstLetterIndex] = Character.toUpperCase(ch);
        }
        for (int i = firstLetterIndex + 1; i < word.length(); i++) {
            if (Character.toLowerCase(word.charAt(i)) == ch) {
                wordState[i] = ch;
            }
        }

    }

    public record GuessResult(AttemptResult attemptResult, char[] wordState) {
    }

}

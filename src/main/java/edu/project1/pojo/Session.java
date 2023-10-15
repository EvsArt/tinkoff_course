package edu.project1.pojo;

import edu.project1.constraints.StringConstraints;
import edu.project1.response.AttemptResult;
import edu.project1.utils.IOUtils;

public class Session {

    private final Word answer;
    private final int maxMistakes;
    private char[] wordState;   // If client guessed "l" in "Hello", it will be "**ll*"
    private int curMistakesNum = 0;

    public Session(Word answer, int maxMistakes) {
        this.answer = answer;
        this.maxMistakes = maxMistakes;
    }

    public void startGame() {
        AttemptResult attemptResult;
        do {
            attemptResult = nextAttempt();
        }
        while (attemptResult != AttemptResult.LOSS
            && attemptResult != AttemptResult.WINNING
            && attemptResult != AttemptResult.EXIT);
    }

    private AttemptResult nextAttempt() {
        AttemptResult attemptResult;

        IOUtils.consoleLineOutput();
        IOUtils.consoleLineOutput(StringConstraints.guessALetter);
        String input = IOUtils.consoleLineInput();

        if (input.equals(StringConstraints.stringForExit)) {
            attemptResult = AttemptResult.EXIT;
            return attemptResult;
        }

        if (input.length() != 1 || !Character.isAlphabetic(input.charAt(0))) {
            attemptResult = AttemptResult.WRONGINPUT;
            attemptResult.doResultActivity();
            return attemptResult;
        }
        char letter = input.charAt(0);

        attemptResult = tryToGuessWord(letter);
        IOUtils.consoleLineOutput(StringConstraints.stringMistakesCount(curMistakesNum, maxMistakes));
        IOUtils.consoleLineOutput(StringConstraints.stringWordState(wordState));
        attemptResult.doResultActivity();

        return attemptResult;

    }

    protected AttemptResult tryToGuessWord(char clientAnswer) {

        Word.GuessResult result = answer.guessCharacter(clientAnswer);
        wordState = result.wordState();

        if (result.attemptResult().equals(AttemptResult.FAILEDGUESS)) {
            curMistakesNum++;
            if (curMistakesNum == maxMistakes) {
                return AttemptResult.LOSS;
            } else {
                return AttemptResult.FAILEDGUESS;
            }
        }

        return result.attemptResult();

    }

}

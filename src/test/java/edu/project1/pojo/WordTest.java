package edu.project1.pojo;

import edu.project1.constraints.Constraints;
import edu.project1.response.AttemptResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class WordTest {

    char nglc = Constraints.NONGUESSLEERCHAR;

    @Test
    @DisplayName("Successful guessing letter")
    void successfulGuessCharacterAndWin() {
        Word word = new Word("Test");
        char ch1 = 't';
        char ch2 = 'E';
        char ch3 = 's';

        Word.GuessResult result1 = word.guessCharacter(ch1);
        AttemptResult hitAttemptResult1 = result1.attemptResult();
        char[] wordState1 = result1.wordState();

        Word.GuessResult result2 = word.guessCharacter(ch2);
        AttemptResult hitAttemptResult2 = result2.attemptResult();
        char[] wordState2 = result2.wordState();

        Word.GuessResult result3 = word.guessCharacter(ch3);
        AttemptResult hitAttemptResult3 = result3.attemptResult();
        char[] wordState3 = result3.wordState();

        assertThat(hitAttemptResult1).isEqualTo(AttemptResult.SUCCESSFULGUESS);
        assertThat(wordState1).containsExactly('T', nglc, nglc, 't');

        assertThat(hitAttemptResult2).isEqualTo(AttemptResult.SUCCESSFULGUESS);
        assertThat(wordState2).containsExactly('T', 'e', nglc, 't');

        assertThat(hitAttemptResult3).isEqualTo(AttemptResult.WINNING);
        assertThat(wordState3).containsExactly('T', 'e', 's', 't');
    }

    @Test
    @DisplayName("Failed guessing letter")
    void failedGuessCharacter() {
        Word word = new Word("Test");

        char ch1 = 't';
        char ch2 = 'i';

        word.guessCharacter(ch1);   // Success (Prev test)
        Word.GuessResult result = word.guessCharacter(ch2);

        AttemptResult failedAttemptResult = result.attemptResult();
        char[] wordState = result.wordState();

        assertThat(failedAttemptResult).isEqualTo(AttemptResult.FAILEDGUESS);
        assertThat(wordState).containsExactly('T', nglc, nglc, 't');

    }

    @Test
    @DisplayName("Useless guessing letter")
    void useLessGuess() {
        Word word = new Word("Test");

        char ch1 = 't';
        char ch2 = 't';

        word.guessCharacter(ch1);
        Word.GuessResult result = word.guessCharacter(ch2);

        AttemptResult failedAttemptResult = result.attemptResult();
        char[] wordState = result.wordState();

        assertThat(failedAttemptResult).isEqualTo(AttemptResult.USELESSINPUT);
        assertThat(wordState).containsExactly('T', nglc, nglc, 't');

    }

}

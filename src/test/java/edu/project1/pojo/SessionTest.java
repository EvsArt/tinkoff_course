package edu.project1.pojo;

import edu.project1.constraints.Constraints;
import edu.project1.response.AttemptResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Testing main functions")
class SessionTest {

    int maxMistakes = Constraints.MAXMISTAKES;
    Session session = new Session(new Word("Test"), maxMistakes);

    @Test
    @DisplayName("Try to win")
    void tryToGetWin() {

        char ch1 = 't';
        char ch2 = 'S';
        char ch3 = 'e';

        AttemptResult result1 = session.tryToGuessWord(ch1);
        AttemptResult result2 = session.tryToGuessWord(ch2);
        AttemptResult result3 = session.tryToGuessWord(ch2);
        AttemptResult result4 = session.tryToGuessWord(ch3);

        assertThat(result1).isEqualTo(AttemptResult.SUCCESSFULGUESS);
        assertThat(result2).isEqualTo(AttemptResult.SUCCESSFULGUESS);
        assertThat(result3).isEqualTo(AttemptResult.USELESSINPUT);
        assertThat(result4).isEqualTo(AttemptResult.WINNING);

    }

    @Test
    @DisplayName("Try to lose")
    void tryToGetLoss() {

        char ch1 = 't';
        char ch2 = 'i';

        AttemptResult result1 = session.tryToGuessWord(ch1);
        AttemptResult result2 = null;
        for(int i = 0; i < maxMistakes; i++){
            result2 = session.tryToGuessWord(ch2);
        }

        assertThat(result1).isEqualTo(AttemptResult.SUCCESSFULGUESS);
        assertThat(result2).isEqualTo(AttemptResult.LOSS);

    }
}

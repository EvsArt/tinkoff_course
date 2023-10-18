package edu.project1.application;

import edu.project1.constraints.Constraints;
import edu.project1.constraints.StringConstraints;
import edu.project1.dictionary.DefaultDictionary;
import edu.project1.dictionary.Dictionary;
import edu.project1.pojo.Session;
import edu.project1.pojo.Word;
import edu.project1.utils.IOUtils;

public class ConsoleHangman implements Application {

    public void run() {

        Dictionary dictionary = new DefaultDictionary();
        int maxMistakes = Constraints.MAXMISTAKES;

        boolean oneMoreGame = true;
        while (oneMoreGame) {
            IOUtils.consoleLineOutput(StringConstraints.howToExit);
            Word answer = dictionary.getRandomWord();
            Session session = new Session(answer, maxMistakes);

            session.startGame();

            oneMoreGame = oneMoreGameSuggestion();
        }

    }

    private boolean oneMoreGameSuggestion() {

        while (true) {
            IOUtils.consoleLineOutput(StringConstraints.suggestionToPlayOneMoreGame);
            String yesOrNo = IOUtils.consoleLineInput();
            if (yesOrNo.equals(StringConstraints.no)) {
                IOUtils.consoleLineOutput(StringConstraints.goodbye);
                return false;
            } else if (yesOrNo.equals(StringConstraints.yes)) {
                return true;
            }
            IOUtils.consoleLineOutput(StringConstraints.incorrectInputYesOrNo);
        }
    }

}

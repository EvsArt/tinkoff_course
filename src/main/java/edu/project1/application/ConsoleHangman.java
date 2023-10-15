package edu.project1.application;

import edu.project1.constraints.Constraints;
import edu.project1.dictionary.DefaultDictionary;
import edu.project1.dictionary.Dictionary;
import edu.project1.pojo.Session;
import edu.project1.pojo.Word;

public class ConsoleHangman implements Application {

    public void run() {

        Dictionary dictionary = new DefaultDictionary();
        Word answer = dictionary.getRandomWord();
        int maxMistakes = Constraints.MAXMISTAKES;

        Session session = new Session(answer, maxMistakes);

        session.startGame();

    }

}

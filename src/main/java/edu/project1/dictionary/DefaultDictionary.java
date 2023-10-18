package edu.project1.dictionary;

import edu.project1.pojo.Word;
import java.util.List;
import java.util.Random;

public class DefaultDictionary implements Dictionary {

    private final Random random = new Random();
    private final List<String> words = List.of(
        "Hello",
        "Friday",
        "Lemon",
        "Tinkoff",
        "Forest"
    );

    @Override
    public Word getRandomWord() {
        return new Word(words.get(
            Math.abs(random.nextInt())
                % (words.size())
        ));
    }
}

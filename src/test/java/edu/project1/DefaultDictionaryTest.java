package edu.project1;

import edu.project1.dictionary.DefaultDictionary;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("DictionaryTest")
class DefaultDictionaryTest {

    DefaultDictionary dictionary = new DefaultDictionary();

    @Test
    @DisplayName("CorrectWordsInDictionary")
    void getRandomWord() {

        int iterations = 200;

        for(int i = 0; i < iterations; i++){
            String word = dictionary.getRandomWord().getWord();
            assertThat(word)
                .isNotNull()
                .isNotBlank()
                .isNotEmpty()
                .isAlphabetic();
        }

    }
}

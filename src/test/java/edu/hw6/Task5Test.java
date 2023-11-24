package edu.hw6;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task5Test {

    Task5.HackerNews hw = new Task5.HackerNews();

    @Test
    public void hackerNewsTopStories() {

        int topLen = hw.hackerNewsTopStories().length;

        assertThat(topLen).isPositive();

    }

    @Test
    public void news() {

        String title = hw.news(38248705);
        String wrongTitle = hw.news(-1);

        String expTitle = "Why companies are racing to build the biggest bug farm";
        String expWrongTitle = "";

        assertEquals(title, expTitle);
        assertEquals(wrongTitle, expWrongTitle);

    }

}

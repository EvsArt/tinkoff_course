package edu.hw6;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

        assertThat(title).isEqualTo("Why companies are racing to build the biggest bug farm");
        assertThat(wrongTitle).isEqualTo("");

    }

}

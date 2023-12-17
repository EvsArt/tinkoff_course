package edu.hw10.task1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomGeneratorTest {

    @Test
    void getRandomValueOfInt() {
        // given
        RandomGenerator generator = new RandomGenerator();
        int min = 1;
        int max = 6;
        // when
        int res = generator.getRandomValueOf(Integer.class, String.valueOf(min), String.valueOf(max));
        // then
        assertThat(res).isBetween(min, max);
    }

    @Test
    void getRandomValueOfIntWithNulls() {
        // given
        RandomGenerator generator = new RandomGenerator();
        // when
        int res = generator.getRandomValueOf(Integer.class, null, null);
        // then
        assertThat(res).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Test
    void getRandomValueOfDouble() {
        // given
        RandomGenerator generator = new RandomGenerator();
        double min = 1.5;
        double max = 7.5;
        // when
        double res = generator.getRandomValueOf(Double.class, String.valueOf(min), String.valueOf(max));
        // then
        assertTrue(res >= min && res <= max);
    }

    @Test
    void getRandomValueOfString() {
        // given
        RandomGenerator generator = new RandomGenerator();
        String min = "aa";
        String max = "aaaa";
        // when
        String res = generator.getRandomValueOf(String.class, min, max);
        // then
        assertThat(res.length()).isBetween(min.length(), max.length());
    }
}

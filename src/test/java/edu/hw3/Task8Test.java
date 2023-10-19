package edu.hw3;

import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task8Test {

    @Test
    void testStandardValues() {
        Task8.BackwardIterator<Integer> iter = new Task8.BackwardIterator<>(List.of(1, 2, 3));

        boolean HN1 = iter.hasNext();
        int val1 = iter.next();
        boolean HN2 = iter.hasNext();
        int val2 = iter.next();
        boolean HN3 = iter.hasNext();
        int val3 = iter.next();
        boolean HN4 = iter.hasNext();

        assertThat(HN1).isTrue();
        assertThat(val1).isEqualTo(3);
        assertThat(HN2).isTrue();
        assertThat(val2).isEqualTo(2);
        assertThat(HN3).isTrue();
        assertThat(val3).isEqualTo(1);
        assertThat(HN4).isFalse();
        assertThrows(NoSuchElementException.class, iter::next);

    }

}

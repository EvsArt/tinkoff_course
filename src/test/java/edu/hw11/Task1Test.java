package edu.hw11;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task1Test {

    @Test
    void tosString() {
        // given
        Task1 task1 = new Task1();
        // when
        String res = task1.getString();
        // then
        assertEquals("Hello, ByteBuddy!", res);
    }
}

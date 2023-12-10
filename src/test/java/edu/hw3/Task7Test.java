package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Дерево и null")
class Task7Test {

    @Test
    @DisplayName("ProofOfWorking")
    void defaultTest() {
        // given
        Task7.tree.put("OneKey", "OneValue");
        Task7.tree.put(null, "test");
        Task7.tree.put("TwoKey", "TwoValue");
        // when
        boolean res = Task7.tree.containsKey(null);
        // then
        assertTrue(res);
    }

}

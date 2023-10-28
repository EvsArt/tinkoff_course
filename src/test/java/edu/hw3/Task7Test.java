package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Дерево и null")
class Task7Test {

    @Test
    @DisplayName("ProofOfWorking")
    void defaultTest() {
        Task7.tree.put("OneKey", "OneValue");
        Task7.tree.put(null, "test");
        Task7.tree.put("TwoKey", "TwoValue");

        assertThat(Task7.tree.containsKey(null)).isTrue();
    }

}

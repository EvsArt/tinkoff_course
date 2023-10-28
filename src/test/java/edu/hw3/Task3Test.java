package edu.hw3;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Частота слов")
class Task3Test {

    static class FreqDictArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                Arguments.of(List.of("a", "bb", "a", "bb"), Map.of("bb", 2, "a", 2)),
                Arguments.of(List.of("код", "код", "код", "bug"), Map.of("код", 3, "bug", 1)),
                Arguments.of(List.of(1, 1, 2, 2), Map.of(1, 2, 2, 2)),
                Arguments.of(List.of(), Map.of())
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(FreqDictArgumentsProvider.class) <T> void freqDict(
        List<T> testInput,
        Map<T, Integer> expectedResult
    ) {

        Map<T, Integer> res = Task3.freqDict(testInput);

        assertThat(res).isEqualTo(expectedResult);
    }
}

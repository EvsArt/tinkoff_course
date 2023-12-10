package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Кластеризация скобок")
class Task2Test {

    static class DefaultClusterizeArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            // given
            return Stream.of(
                Arguments.of("()()()", List.of("()", "()", "()")),
                Arguments.of("((()))", List.of("((()))")),
                Arguments.of("((()))(())()()(()())", List.of("((()))", "(())", "()", "()", "(()())")),
                Arguments.of("((())())(()(()()))", List.of("((())())", "(()(()()))"))
            );
        }
    }

    @ParameterizedTest
    @DisplayName("Correct input")
    @ArgumentsSource(DefaultClusterizeArgumentsProvider.class)
    void clusterize(String testString, List<String> expectedRes) {
        // when
        List<String> res = Task2.clusterize(testString);
        // then
        assertThat(res).isEqualTo(expectedRes);

    }

    static class ClusterizeWithLettersArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            // given
            return Stream.of(
                Arguments.of("a((b())(cc))(()d((e)()))", List.of("a", "((b())(cc))", "(()d((e)()))"))
            );
        }
    }

    @ParameterizedTest
    @DisplayName("Test clustering with letters between brackets")
    @ArgumentsSource(ClusterizeWithLettersArgumentsProvider.class)
    void clusterizeWithLetters(String testString, List<String> expectedRes) {
        // when
        List<String> res = Task2.clusterize(testString);
        // then
        assertThat(res).isEqualTo(expectedRes);

    }

    static class ClusterizeWithWrongOrderBracketsArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            // given
            return Stream.of(
                Arguments.of(")()()", List.of("()", "()")),
                Arguments.of("()(()", List.of("()")),
                Arguments.of("())()", List.of("()", "()")),
                Arguments.of("a((b())(cc))((()d((e)()))", List.of("a", "((b())(cc))")),
                Arguments.of("a((b())(cc))(()d((e()()))", List.of("a", "((b())(cc))"))
            );
        }
    }

    @ParameterizedTest
    @DisplayName("Test clustering with wrong brackets order")
    @ArgumentsSource(ClusterizeWithWrongOrderBracketsArgumentsProvider.class)
    void wrongOrderBrackets(String testString, List<String> expectedRes) {
        // when
        List<String> res = Task2.clusterize(testString);
        // then
        assertThat(res).isEqualTo(expectedRes);

    }

}

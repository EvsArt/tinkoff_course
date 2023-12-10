package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Шифр Атбаш")
class Task1Test {

    @ParameterizedTest
    @DisplayName("Get mirror char")
    // given
    @CsvSource(value = {
        "a, z",
        "b, y",
        "z, a",
        "A, Z",
        "Y, B",
        "2, 2",
        "!, !",
        "я, я"
    })
    void getMirrorChar(char testChar, char expectedChar) {
        // when
        char res = Task1.getMirrorChar(testChar);
        // then
        assertThat(res).isEqualTo(expectedChar);
    }

    static class AtbashArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            // given
            return Stream.of(
                Arguments.of("Hello world!", "Svool dliow!"),
                Arguments.of(
                    "Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler",
                    "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi"
                )
            );
        }
    }

    @ParameterizedTest
    @DisplayName("Main function")
    @ArgumentsSource(AtbashArgumentsProvider.class)
    void atbash(String testString, String expectedResult) {
        // when
        String res = Task1.atbash(testString);
        // then
        assertThat(res).isEqualTo(expectedResult);
    }

}

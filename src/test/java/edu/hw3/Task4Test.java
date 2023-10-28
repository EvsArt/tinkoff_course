package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Римские цифры")
class Task4Test {

    static class NormalRomanDigitsArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                Arguments.of(3999, "MMMCMXCIX"),
                Arguments.of(1000, "M"),
                Arguments.of(900, "CM"),
                Arguments.of(899, "DCCCXCIX"),
                Arguments.of(400, "CD"),
                Arguments.of(399, "CCCXCIX"),
                Arguments.of(91, "XCI"),
                Arguments.of(90, "XC"),
                Arguments.of(89, "LXXXIX"),
                Arguments.of(41, "XLI"),
                Arguments.of(40, "XL"),
                Arguments.of(39, "XXXIX"),
                Arguments.of(20, "XX"),
                Arguments.of(19, "XIX"),
                Arguments.of(18, "XVIII"),
                Arguments.of(17, "XVII"),
                Arguments.of(16, "XVI"),
                Arguments.of(15, "XV"),
                Arguments.of(14, "XIV"),
                Arguments.of(13, "XIII"),
                Arguments.of(12, "XII"),
                Arguments.of(11, "XI"),
                Arguments.of(10, "X"),
                Arguments.of(9, "IX"),
                Arguments.of(8, "VIII"),
                Arguments.of(7, "VII"),
                Arguments.of(6, "VI"),
                Arguments.of(5, "V"),
                Arguments.of(4, "IV"),
                Arguments.of(3, "III"),
                Arguments.of(2, "II"),
                Arguments.of(1, "I")
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(NormalRomanDigitsArgumentsProvider.class)
    void defaultConvertToRoman(int input, String expectedResult) {
        String res = Task4.convertToRoman(input);

        assertThat(res).isEqualTo(expectedResult);
    }

    static class WrongRomanDigitsArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                Arguments.of(4000, Task4.WRONGINPUT),
                Arguments.of(0, Task4.WRONGINPUT)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(WrongRomanDigitsArgumentsProvider.class)
    void wrongConvertToRoman(int input, String expectedResult) {
        String res = Task4.convertToRoman(input);

        assertThat(res).isEqualTo(expectedResult);
    }

}

package lotto.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import lotto.constant.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;

class MoneyValidatorTest {

    @Nested
    @DisplayName("정상 입력")
    class ValidInput {

        @ParameterizedTest(name = "정상 입력: \"{0}\"")
        @ValueSource(strings = {
                "1000", "8000", "12000",
                "001000", "0008000",
                "   8000   ", "   001000   "
        })
        void valid_numbers_are_parsed(String input) {
            int value = MoneyValidator.validate(input);
            String trimmed = input.trim();
            int expected = Integer.parseInt(trimmed);
            assertThat(value).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("형식 오류")
    class FormatErrors {

        @ParameterizedTest(name = "비어있는 입력: \"{0}\"")
        @ValueSource(strings = {"", " ", "   "})
        void empty_input(String input) {
            assertThatThrownBy(() -> MoneyValidator.validate(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(Constants.ERROR_PREFIX);
        }

        @ParameterizedTest(name = "숫자가 아닌 문자 포함: \"{0}\"")
        @ValueSource(strings = {
                "abc", "1a00", "1000원", "8.000",
                "-1000", "+1000", "1_000", "8,000",
                "1-000", "1/000", "1000L"
        })
        void contains_non_digit(String input) {
            assertThatThrownBy(() -> MoneyValidator.validate(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(Constants.ERROR_PREFIX);
        }

        @ParameterizedTest(name = "내부 공백: \"{0}\"")
        @ValueSource(strings = {
                "1 000", " 1 000 ", "80 00"
        })
        void contains_space_inside(String input) {
            assertThatThrownBy(() -> MoneyValidator.validate(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(Constants.ERROR_PREFIX);
        }
    }

    @Nested
    @DisplayName("값 제약 조건")
    class ValueConstraints {

        @ParameterizedTest(name = "0 또는 음수: \"{0}\"")
        @ValueSource(strings = {"0", "0000"})
        void zero_or_negative(String input) {
            assertThatThrownBy(() -> MoneyValidator.validate(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(Constants.ERROR_PREFIX);
        }

        @ParameterizedTest(name = "1000 단위 아님: \"{0}\"")
        @ValueSource(strings = {"1500", "999", "123", "2500"})
        void not_thousand_unit(String input) {
            assertThatThrownBy(() -> MoneyValidator.validate(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(Constants.ERROR_PREFIX);
        }

        @ParameterizedTest(name = "Integer 범위 초과: \"{0}\"")
        @MethodSource("lotto.validator.MoneyValidatorTest#overflowSamples")
        void overflow(String input) {
            assertThatThrownBy(() -> MoneyValidator.validate(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(Constants.ERROR_PREFIX);
        }
    }

    static Stream<String> overflowSamples() {
        return Stream.of(
                "2147483648",
                "3000000000"
        );
    }
}
package lotto.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BonusNumberParserTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @Nested
    @DisplayName("정상 입력")
    class Success {

        @ParameterizedTest(name = "앞뒤 공백 허용: \"{0}\"")
        @ValueSource(strings = { "7", " 7", "7 ", "   7   ", "\t7", "7\t", " \n 45 \t " })
        void leading_or_trailing_whitespace_allowed(String raw) {
            int n = BonusNumberParser.parse(raw);
            assertThat(n).isEqualTo(Integer.parseInt(raw.trim()));
        }
    }

    @Nested
    @DisplayName("형식 오류")
    class FormatErrors {

        @ParameterizedTest(name = "숫자 사이 공백(내부 공백) 금지: \"{0}\"")
        @ValueSource(strings = { "1 0", "0 1", "0\t1", "0\n1", "4 5" })
        void inner_whitespace(String raw) {
            assertThatThrownBy(() -> BonusNumberParser.parse(raw))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(ERROR_MESSAGE);
        }

        @ParameterizedTest(name = "숫자가 아닌 문자 포함: \"{0}\"")
        @ValueSource(strings = { "7a", "a7", "7원", "7,0", "-7", "+7", "7.0" })
        void non_digit(String raw) {
            assertThatThrownBy(() -> BonusNumberParser.parse(raw))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(ERROR_MESSAGE);
        }

        @ParameterizedTest(name = "비어있는 입력: \"{0}\"")
        @ValueSource(strings = { "", " ", "   ", "\t", "\n" })
        void empty(String raw) {
            assertThatThrownBy(() -> BonusNumberParser.parse(raw))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(ERROR_MESSAGE);
        }

        @Test
        void overflow() {
            assertThatThrownBy(() -> BonusNumberParser.parse("2147483648"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(ERROR_MESSAGE);
        }
    }
}
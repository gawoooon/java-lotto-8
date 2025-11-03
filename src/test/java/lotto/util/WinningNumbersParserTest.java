package lotto.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WinningNumbersParserTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @Nested
    @DisplayName("정상 케이스")
    class Success {

        @Test
        @DisplayName("쉼표로 구분된 6개 숫자를 파싱한다")
        void parse_six_numbers() {
            List<Integer> nums = WinningNumbersParser.parse("1,2,3,4,5,6");
            assertThat(nums).containsExactly(1,2,3,4,5,6);
        }

        @Test
        @DisplayName("앞뒤 공백은 허용한다")
        void trim_is_allowed() {
            List<Integer> nums = WinningNumbersParser.parse("   1,2,3,4,5,6   ");
            assertThat(nums).containsExactly(1,2,3,4,5,6);
        }

        @Test
        @DisplayName("숫자 순서(오름/내림/뒤섞임)는 상관 없다")
        void order_does_not_matter() {
            List<Integer> nums = WinningNumbersParser.parse("6,1,5,2,4,3");
            assertThat(nums).containsExactly(6,1,5,2,4,3);
        }
    }

    @Nested
    @DisplayName("형식 오류")
    class FormatErrors {

        @ParameterizedTest(name = "요소 사이 공백 금지: \"{0}\"")
        @ValueSource(strings = { "1, 2,3,4,5,6", "1,2,3, 4,5,6", "1,2,3,4,5, 6" })
        void inner_space_not_allowed(String input) {
            assertThatThrownBy(() -> WinningNumbersParser.parse(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(ERROR_MESSAGE);
        }

        @ParameterizedTest(name = "숫자 외 문자 금지: \"{0}\"")
        @ValueSource(strings = { "1,a,3,4,5,6", "1,2,3,4,5,6a", "1,2,3,4,5,#" })
        void non_digit_is_not_allowed(String input) {
            assertThatThrownBy(() -> WinningNumbersParser.parse(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(ERROR_MESSAGE);
        }

        @ParameterizedTest(name = "쉼표 개수/배치 오류: \"{0}\"")
        @ValueSource(strings = { "1,2,3,4,5", "1,2,3,4,5,6,7", ",1,2,3,4,5,6", "1,2,3,4,5,6,", "1,,2,3,4,5,6" })
        void wrong_separator_count_or_position(String input) {
            assertThatThrownBy(() -> WinningNumbersParser.parse(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(ERROR_MESSAGE);
        }

        @ParameterizedTest(name = "비어있는 입력: \"{0}\"")
        @ValueSource(strings = { "", " ", "   " })
        void empty_input(String input) {
            assertThatThrownBy(() -> WinningNumbersParser.parse(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(ERROR_MESSAGE);
        }
    }
}

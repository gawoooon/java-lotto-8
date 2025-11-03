package lotto.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WinningNumbersValidatorTest {

    @Test
    @DisplayName("정상: 6개, 1~45 범위, 중복 없음이면 통과")
    void valid_numbers() {
        assertThatCode(() -> WinningNumbersValidator.validate(List.of(6, 1, 5, 2, 4, 3)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("개수 오류: 6개가 아니면 예외")
    void size_must_be_six() {
        assertThatThrownBy(() -> WinningNumbersValidator.validate(List.of(1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    @DisplayName("범위 오류: 1~45 밖의 숫자 포함 시 예외")
    void out_of_range_is_error() {
        assertThatThrownBy(() -> WinningNumbersValidator.validate(List.of(0, 2, 3, 4, 5, 6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");

        assertThatThrownBy(() -> WinningNumbersValidator.validate(List.of(1, 2, 3, 4, 5, 46)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    @DisplayName("중복 오류: 같은 숫자가 2개 이상이면 예외")
    void duplicate_is_error() {
        assertThatThrownBy(() -> WinningNumbersValidator.validate(List.of(1, 1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
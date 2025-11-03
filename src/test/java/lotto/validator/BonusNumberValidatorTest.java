package lotto.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import lotto.constant.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BonusNumberValidatorTest {

    @Test
    @DisplayName("정상: 1~45 범위, 당첨 6개와 중복 없음")
    void ok() {
        assertThatCode(() ->
                BonusNumberValidator.validate(7, List.of(1, 2, 3, 4, 5, 6))
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("범위 오류: 0 또는 46 이상")
    void out_of_range() {
        assertThatThrownBy(() ->
                BonusNumberValidator.validate(0, List.of(1, 2, 3, 4, 5, 6))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith(Constants.ERROR_PREFIX);

        assertThatThrownBy(() ->
                BonusNumberValidator.validate(46, List.of(1, 2, 3, 4, 5, 6))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith(Constants.ERROR_PREFIX);
    }

    @Test
    @DisplayName("중복 오류: 당첨 번호와 보너스가 같으면 예외")
    void duplicate_with_winning() {
        assertThatThrownBy(() ->
                BonusNumberValidator.validate(6, List.of(1, 2, 3, 4, 5, 6))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith(Constants.ERROR_PREFIX);
    }
}
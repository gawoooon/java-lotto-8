package lotto.validator;

import lotto.constant.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyValidatorTest {

    @Nested
    @DisplayName("정상 값 검증")
    class ValidValues {

        @ParameterizedTest(name = "1000 단위 정상 입력: {0}")
        @ValueSource(ints = {1000, 8000, 12000, 2000000})
        void valid_money(int value) {
            MoneyValidator.validate(value);
        }
    }

    @Nested
    @DisplayName("비즈니스 제약 검증")
    class BusinessConstraints {

        @ParameterizedTest(name = "0 이하 금액: {0}")
        @ValueSource(ints = {0, -1, -1000})
        void zero_or_negative(int value) {
            assertThatThrownBy(() -> MoneyValidator.validate(value))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(Constants.ERROR_PREFIX);
        }

        @ParameterizedTest(name = "1000 단위가 아닌 금액: {0}")
        @ValueSource(ints = {1500, 999, 123, 2500, 3001})
        void not_thousand_unit(int value) {
            assertThatThrownBy(() -> MoneyValidator.validate(value))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(Constants.ERROR_PREFIX);
        }
    }
}
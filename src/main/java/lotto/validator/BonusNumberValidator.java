package lotto.validator;

import java.util.Collection;
import lotto.constant.ErrorMessage;

public final class BonusNumberValidator {

    private BonusNumberValidator() {}

    public static void validate(int bonus, Collection<Integer> winningNumbers) {
        if (bonus < 1 || bonus > 45) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_NUMBER_RANGE.getMessage());
        }
        if (winningNumbers != null && winningNumbers.contains(bonus)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BONUS_DUPLICATE.getMessage());
        }
    }
}
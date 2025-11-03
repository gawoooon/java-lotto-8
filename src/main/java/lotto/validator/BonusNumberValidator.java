package lotto.validator;

import java.util.Collection;
import lotto.constant.Constants;
import lotto.constant.ErrorMessage;

public final class BonusNumberValidator {

    private BonusNumberValidator() {}

    public static void validate(int bonus, Collection<Integer> winningNumbers) {
        if (bonus < Constants.LOTTO_MIN_NUMBER || bonus > Constants.LOTTO_MAX_NUMBER) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_NUMBER_RANGE.getMessage());
        }
        if (winningNumbers != null && winningNumbers.contains(bonus)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BONUS_DUPLICATE.getMessage());
        }
    }
}
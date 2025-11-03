package lotto.validator;

import java.util.List;
import lotto.constant.Constants;
import lotto.constant.ErrorMessage;

public final class WinningNumbersValidator {

    private WinningNumbersValidator() {}

    public static void validate(List<Integer> numbers) {
        if (numbers == null || numbers.size() != Constants.WINNING_NUMBER_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_WINNING_NUMBER_COUNT.getMessage());
        }
        if (numbers.stream().distinct().count() != Constants.WINNING_NUMBER_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_DUPLICATE.getMessage());
        }
        if (numbers.stream().anyMatch(n -> n < Constants.LOTTO_MIN_NUMBER || n > Constants.LOTTO_MAX_NUMBER)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_NUMBER_RANGE.getMessage());
        }
    }
}
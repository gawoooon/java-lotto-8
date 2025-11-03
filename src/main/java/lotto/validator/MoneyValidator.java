package lotto.validator;

import lotto.constant.ErrorMessage;

public final class MoneyValidator {

    private MoneyValidator() {}

    public static int validate(int value) {
        ensurePositive(value);
        ensureThousandUnit(value);

        return value;
    }

    private static void ensurePositive(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NEGATIVE_OR_ZERO.getMessage());
        }
    }

    private static void ensureThousandUnit(int value) {
        if (value % 1000 != 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_THOUSAND_UNIT.getMessage());
        }
    }
}
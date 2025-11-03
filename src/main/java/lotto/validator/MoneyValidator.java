package lotto.validator;

import lotto.constant.ErrorMessage;

public final class MoneyValidator {

    private MoneyValidator() {}

    public static int validate(String raw) {
        String input = trim(raw);
        ensureNotEmpty(input);
        ensureNoInnerWhitespace(input);
        ensureNumericOnly(input);

        int value = parseIntWithinRange(input);

        ensurePositive(value);
        ensureThousandUnit(value);

        return value;
    }

    private static String trim(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_EMPTY_INPUT.getMessage());
        }
        return raw.trim();
    }

    private static void ensureNotEmpty(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_EMPTY_INPUT.getMessage());
        }
    }

    private static void ensureNoInnerWhitespace(String input) {
        if (input.chars().anyMatch(Character::isWhitespace)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INNER_WHITESPACE.getMessage());
        }
    }

    private static void ensureNumericOnly(String input) {
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
        }
    }

    private static int parseIntWithinRange(String input) {
        try {
            long value = Long.parseLong(input);
            if (value > Integer.MAX_VALUE) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_RANGE.getMessage());
            }
            return (int) value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
        }
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
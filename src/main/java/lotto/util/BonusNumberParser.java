package lotto.util;

import lotto.constant.ErrorMessage;

public final class BonusNumberParser {

    private BonusNumberParser() {}

    public static int parse(String raw) {
        String input = trim(raw);
        ensureNotEmpty(input);
        ensureNoInnerWhitespace(input);
        ensureDigitsOnly(input);
        return parseIntWithinRange(input);
    }

    private static String trim(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_EMPTY_INPUT.getMessage());
        }
        return raw.trim();
    }

    private static void ensureNotEmpty(String s) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_EMPTY_INPUT.getMessage());
        }
    }

    private static void ensureNoInnerWhitespace(String s) {
        if (s.chars().anyMatch(Character::isWhitespace)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INNER_WHITESPACE.getMessage());
        }
    }

    private static void ensureDigitsOnly(String s) {
        if (!s.matches("\\d+")) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
        }
    }

    private static int parseIntWithinRange(String s) {
        long value = parseLongStrictly(s);
        validateRange(value);
        return (int) value;
    }

    private static long parseLongStrictly(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
        }
    }

    private static void validateRange(long value) {
        if (value > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_RANGE.getMessage());
        }
    }
}
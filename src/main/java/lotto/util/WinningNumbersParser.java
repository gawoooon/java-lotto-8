package lotto.util;

import java.util.ArrayList;
import java.util.List;
import lotto.constant.ErrorMessage;

public final class WinningNumbersParser {

    private WinningNumbersParser() {
    }

    public static List<Integer> parse(String raw) {
        String input = trim(raw);
        ensureNotEmpty(input);
        ensureNoInnerWhitespace(input);

        String[] tokens = input.split(",", -1);
        ensureSixTokens(tokens);
        ensureNoEmptyToken(tokens);

        List<Integer> numbers = new ArrayList<>(6);
        for (String t : tokens) {
            ensureDigitsOnly(t);
            numbers.add(Integer.parseInt(t));
        }
        return numbers;
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

    private static void ensureSixTokens(String[] tokens) {
        if (tokens.length != 6) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_WINNING_NUMBER_COUNT.getMessage());
        }
    }

    private static void ensureNoEmptyToken(String[] tokens) {
        for (String t : tokens) {
            if (t.isEmpty()) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_WINNING_NUMBER_FORMAT.getMessage());
            }
        }
    }

    private static void ensureDigitsOnly(String token) {
        if (!token.matches("\\d+")) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_WINNING_NUMBER_FORMAT.getMessage());
        }
    }
}

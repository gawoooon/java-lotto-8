package lotto.constant;

public enum ErrorMessage {

    INVALID_NUMBER_FORMAT("숫자만 입력할 수 있습니다."),
    INVALID_INNER_WHITESPACE("값 안에 공백이 포함될 수 없습니다."),
    INVALID_EMPTY_INPUT("입력이 비어있습니다."),
    INVALID_NEGATIVE_OR_ZERO("값은 0보다 커야 합니다."),
    INVALID_THOUSAND_UNIT("구입 금액은 1000 단위여야 합니다."),
    INVALID_RANGE("값이 허용 범위를 벗어났습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = "[ERROR] " + message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(Object value) {
        return message + " " + value;
    }
}
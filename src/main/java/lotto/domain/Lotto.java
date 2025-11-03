package lotto.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lotto.constant.ErrorMessage;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        List<Integer> copy = new ArrayList<>(numbers);
        Collections.sort(copy);
        this.numbers = Collections.unmodifiableList(copy);
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_SIZE.getMessage());
        }

        if (numbers.stream().distinct().count() != 6) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_DUPLICATE.getMessage());
        }

        boolean invalidRange = numbers.stream().anyMatch(n -> n < 1 || n > 45);
        if (invalidRange) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_NUMBER_RANGE.getMessage());
        }
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    @Override
    public String toString() {
        return numbers.toString();
    }
}

package lotto.model.vo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {

    private final static String NUMBERS_AMOUNT_ERROR_MESSAGE = "로또 번호는 6개여야 합니다.";
    private final static String NOT_ALLOW_DUPLICATE_NUMBER_MESSAGE = "중복된 번호를 사용할 수 없습니다.";
    private final static String NUMBER_OUT_OF_RANGE_MESSAGE = "로또 번호는 1 이상 45 이하여야 합니다.";
    private final static String ERROR_TAG = "[ERROR]";
    private final static String SPACE = " ";
    private final static int NUMBERS_AMOUNT = 6;
    private final static int LOTTO_MIN_NUMBER = 1;
    private final static int LOTTO_MAX_NUMBER = 45;

    private final List<Integer> numbers;

    private Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    public static Lotto from(List<Integer> numbers) {
        return new Lotto(numbers);
    }

    public boolean contains(int number) {
        return this.numbers.contains(number);
    }

    public List<Integer> getNumbers() {
        return List.copyOf(numbers);
    }

    private void validate(List<Integer> numbers) {
        validateSize(numbers);
        validateDuplicate(numbers);
        numbers.forEach(this::validateNumberOutOfRange);
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers == null || numbers.size() != NUMBERS_AMOUNT) {
            throw new IllegalArgumentException(ERROR_TAG + SPACE + NUMBERS_AMOUNT_ERROR_MESSAGE);
        }
    }

    private void validateDuplicate(List<Integer> numbers) {
        Set<Integer> set = new HashSet<>(numbers);
        if (set.size() != NUMBERS_AMOUNT) {
            throw new IllegalArgumentException(ERROR_TAG + SPACE + NOT_ALLOW_DUPLICATE_NUMBER_MESSAGE);
        }
    }

    private void validateNumberOutOfRange(int number) {
        if (number < LOTTO_MIN_NUMBER || number > LOTTO_MAX_NUMBER) {
            throw new IllegalArgumentException(ERROR_TAG + SPACE + NUMBER_OUT_OF_RANGE_MESSAGE);
        }
    }
}

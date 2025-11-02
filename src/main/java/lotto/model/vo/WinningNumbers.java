package lotto.model.vo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WinningNumbers {

    private final static Map<Integer, Integer> RANK_TABLE
            = Map.of(6, 1, 5, 3, 4, 4, 3, 5);
    private final static String NUMBER_OUT_OF_RANGE_MESSAGE = "로또 번호는 1 이상 45 이하여야 합니다.";
    private final static String NOT_ALLOW_SAME_NUMBER_MESSAGE = "중복된 당첨 번호를 사용할 수 없습니다.";
    private final static String NOT_ALLOW_SAME_NUMBERS_BONUS_MESSAGE = "당첨 번호와 동일한 보너스 번호를 사용할 수 없습니다.";
    private final static String NUMBERS_AMOUNT_ERROR_MESSAGE = "로또 번호는 6개여야 합니다.";
    private final static String ERROR_TAG = "[ERROR]";
    private final static String SPACE = " ";
    private final static int NUMBERS_AMOUNT = 6;
    private final static int LOTTO_MIN_NUMBER = 1;
    private final static int LOTTO_MAX_NUMBER = 45;
    private final static int NO_RANK = 0;
    private final static int SECOND_RANK = 2;
    private final static int FIVE = 5;

    private final List<Integer> numbers;
    private final int bonus;

    public WinningNumbers(List<Integer> numbers, int bonus) {
        validateDrawing(numbers);
        this.numbers = numbers;
        validateBonus(bonus);
        this.bonus = bonus;
    }

    public static WinningNumbers of(List<Integer> winNumbers, int bonusNumber) {
        return new WinningNumbers(winNumbers, bonusNumber);
    }

    public int calculateRank(Lotto lotto) {
        int matchNumbers = getMatchNumbers(lotto);
        boolean containsBonus = containsBonus(lotto);

        if (matchNumbers == FIVE && containsBonus) {
            return SECOND_RANK;
        }

        Integer rank = RANK_TABLE.get(matchNumbers);

        if (rank == null) {
            return NO_RANK;
        }

        return rank;
    }

    private boolean containsBonus(Lotto lotto) {
        return lotto.contains(bonus);
    }

    private int getMatchNumbers(Lotto lotto) {
        int count = 0;
        for (Integer number : numbers) {
            if (lotto.contains(number)) {
                count++;
            }
        }
        return count;
    }

    private void validateDrawing(List<Integer> numbers) {
        validateNumberSize(numbers);
        validateDuplicate(numbers);
        numbers.forEach(this::validateNumberOutOfRange);

    }

    private void validateNumberSize(List<Integer> numbers) {
        if (numbers != null && numbers.size() != NUMBERS_AMOUNT) {
            throw new IllegalArgumentException(ERROR_TAG + SPACE + NUMBERS_AMOUNT_ERROR_MESSAGE);
        }

    }

    private void validateDuplicate(List<Integer> numbers) {
        Set<Integer> set = new HashSet<>(numbers);
        if (set.size() != NUMBERS_AMOUNT) {
            throw new IllegalArgumentException(ERROR_TAG + SPACE + NOT_ALLOW_SAME_NUMBER_MESSAGE);
        }
    }

    private void validateNumberOutOfRange(int number) {
        if (number < LOTTO_MIN_NUMBER || number > LOTTO_MAX_NUMBER) {
            throw new IllegalArgumentException(ERROR_TAG + SPACE + NUMBER_OUT_OF_RANGE_MESSAGE);
        }
    }

    private void validateBonus(int bonus) {
        validateNumberOutOfRange(bonus);
        validateDuplicateBonus(bonus);
    }

    private void validateDuplicateBonus(int bonus) {
        if (numbers.contains(bonus)) {
            throw new IllegalArgumentException(
                    ERROR_TAG + SPACE + NOT_ALLOW_SAME_NUMBERS_BONUS_MESSAGE);
        }
    }
}

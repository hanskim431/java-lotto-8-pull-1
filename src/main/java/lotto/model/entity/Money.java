package lotto.model.entity;

public class Money {

    private final static String NOT_POSITIVE_NUMBER_MESSAGE = "로또 구입 금액은 양수여야합니다.";
    private final static String NOT_MULTIPLE_OF_PRICE_MESSAGE = "로또 구입 금액은 1000원 단위여야합니다.";
    private final static String ERROR_TAG = "[ERROR]";
    private final static String SPACE = " ";
    private final static int ZERO = 0;
    private final static int LOTTO_PRICE = 1000;

    private final int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    public static Money from(int amount) {
        validate(amount);
        return new Money(amount);
    }

    public int calculateLottoAmount() {
        return amount / LOTTO_PRICE;
    }

    private static void validate(int amount) {
        validatePositive(amount);
        validateMultipleOfPrice(amount);
    }

    private static void validatePositive(int amount) {
        if (amount <= ZERO) {
            throw new IllegalArgumentException(ERROR_TAG + SPACE + NOT_POSITIVE_NUMBER_MESSAGE);
        }
    }

    private static void validateMultipleOfPrice(int amount) {
        if (amount % LOTTO_PRICE != ZERO) {
            throw new IllegalArgumentException(ERROR_TAG + SPACE + NOT_MULTIPLE_OF_PRICE_MESSAGE);
        }
    }
}

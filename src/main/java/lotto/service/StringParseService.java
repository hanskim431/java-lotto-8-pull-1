package lotto.service;

import java.util.ArrayList;
import java.util.List;

public class StringParseService {

    private final static String ERROR_TAG = "[ERROR]";
    private final static String EMPTY_STRING_ERROR_MESSAGE = "빈 문자열은 허용되지 않습니다.";
    private final static String MONEY_NUMBER_FORMAT_ERROR_MESSAGE = "금액은 정수만 입력할 수 있습니다.";
    private final static String WIN_NUMBER_FORMAT_ERROR_MESSAGE = "당첨 번호는 쉼표로 구분된 정수만 입력할 수 있습니다.";
    private final static String BONUS_NUMBER_FORMAT_ERROR_MESSAGE = "보너스 번호는 정수만 입력할 수 있습니다.";

    private final static String SPACE = " ";
    private final static String COMMA = ",";

    private final static int SPLIT_LIMIT = -1;

    private StringParseService() {
    }

    public static int parseIntBuyMoney(String userInputBuyMoney) {
        validateNullOrEmpty(userInputBuyMoney);
        try {
            return Integer.parseInt(userInputBuyMoney);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_TAG + SPACE + MONEY_NUMBER_FORMAT_ERROR_MESSAGE);
        }
    }

    public static List<Integer> parseIntListInputWinNumbers(String userInputWinNumbers) {
        validateNullOrEmpty(userInputWinNumbers);
        List<Integer> winNumbers = new ArrayList<>();
        String[] split = userInputWinNumbers.split(COMMA, SPLIT_LIMIT);
        for (String string : split) {
            try {
                winNumbers.add(Integer.parseInt(string));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(ERROR_TAG + SPACE + WIN_NUMBER_FORMAT_ERROR_MESSAGE);
            }
        }
        return winNumbers;
    }

    public static int parseIntBonusNumber(String bonusNumber) {
        validateNullOrEmpty(bonusNumber);
        try {
            return Integer.parseInt(bonusNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_TAG + SPACE + BONUS_NUMBER_FORMAT_ERROR_MESSAGE);
        }
    }

    private static void validateNullOrEmpty(String userInputWinNumbers) {
        if (userInputWinNumbers == null || userInputWinNumbers.isEmpty()) {
            throw new IllegalArgumentException(ERROR_TAG + SPACE + EMPTY_STRING_ERROR_MESSAGE);
        }
    }
}

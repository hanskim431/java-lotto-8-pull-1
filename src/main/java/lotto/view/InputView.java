package lotto.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private final static String REQUEST_MONEY_MESSAGE = " 구입금액을 입력해 주세요.";
    private final static String REQUEST_WIN_NUMBERS_MESSAGE = " 당첨 번호를 입력해 주세요.";
    private final static String REQUEST_BONUS_NUMBER_MESSAGE = " 보너스 번호를 입력해 주세요.";

    public static String getUserInputBuyMoney() {
        System.out.println(REQUEST_MONEY_MESSAGE);
        return Console.readLine();
    }
    public static String getUserInputWinNumbers() {
        System.out.println(REQUEST_WIN_NUMBERS_MESSAGE);
        return Console.readLine();
    }
    public static String getUserInputBonusNumber() {
        System.out.println(REQUEST_BONUS_NUMBER_MESSAGE);
        return Console.readLine();
    }
}

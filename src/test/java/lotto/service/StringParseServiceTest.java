package lotto.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StringParseServiceTest {

    private final static String MONEY_NUMBER_FORMAT_ERROR_MESSAGE = "[ERROR] 금액은 정수만 입력할 수 있습니다.";
    private final static String WIN_NUMBER_FORMAT_ERROR_MESSAGE = "[ERROR] 당첨 번호는 쉼표로 구분된 정수만 입력할 수 있습니다.";

    @ParameterizedTest
    @ValueSource(strings = {"한글", "english", "❌", "1.1234", " ", "", "\n", "123\t", "123 "})
    @DisplayName("예외: 정수외에 다른 값이 들어오면 예외를 반환합니다.")
    void shouldThrowException_WhenBuyMoneyIsNotInteger(String value) {
        assertThrows(IllegalArgumentException.class,
                () -> StringParseService.parseIntBuyMoney(value),
                MONEY_NUMBER_FORMAT_ERROR_MESSAGE);
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            value,         number
            1,                1
            2,                2
            100,              100
            1234567890,       1234567890
            """)
    @DisplayName("통과: 정수만 들어있는 문자열을 정수로 변환합니다.")
    void shouldParseBuyMoneyCorrectly(String value, int number) {
        assertEquals(number, StringParseService.parseIntBuyMoney(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "1,,",
            "1,2,3,-",
            "1,한글",
            "1,english",
            "1,❌",
            "123\t"
    })
    @DisplayName("예외: 정수와 쉼표외에 다른 값이 들어오면 예외를 반환합니다.")
    void shouldThrowNumberFormatException_WhenNotInputIntegerStringListWithComma(String value) {
        assertThrows(
                IllegalArgumentException.class,
                () -> StringParseService.parseIntListInputWinNumbers(value),
                WIN_NUMBER_FORMAT_ERROR_MESSAGE);
    }

    @ParameterizedTest
    @MethodSource("provideSuccessListData")
    @DisplayName("통과: 쉼표로 구분된 정수 문자열을 정수 리스트로 변환합니다.")
    void shouldReturnIntegerList_WhenInputIntegerStringListWithComma(String value, List<Integer> list) {
        List<Integer> integers = StringParseService.parseIntListInputWinNumbers(value);
        assertEquals(integers.size(), list.size());
        assertTrue(list.containsAll(integers));
    }

    private static Stream<Arguments> provideSuccessListData() {
        return Stream.of(
                Arguments.of("1", List.of(1)),
                Arguments.of("1,2,3", List.of(1, 2, 3)),
                Arguments.of("100,200,300,400", List.of(100, 200, 300, 400))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"한글", "english", "❌", "1.1234", " ", "", "\n", "123\t", "123 "})
    @DisplayName("예외: 정수외에 다른 값이 들어오면 예외를 반환합니다.")
    void shouldThrowException_WhenBonusNumberIsNotInteger(String value) {
        assertThrows(IllegalArgumentException.class,
                () -> StringParseService.parseIntBonusNumber(value),
                MONEY_NUMBER_FORMAT_ERROR_MESSAGE);
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            value,         number
            1,                1
            2,                2
            100,              100
            1234567890,       1234567890
            """)
    @DisplayName("통과: 정수만 들어있는 문자열을 정수로 변환합니다.")
    void shouldParseBonusNumberCorrectly(String value, int number) {
        assertEquals(number, StringParseService.parseIntBonusNumber(value));
    }

}
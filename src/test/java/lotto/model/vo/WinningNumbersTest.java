package lotto.model.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class WinningNumbersTest {

    private Lotto lotto;

    @BeforeEach
    void setup() {
        lotto = Lotto.from(new ArrayList<>(List.of(1, 2, 3, 4, 5, 6)));
    }

    @ParameterizedTest
    @MethodSource("provideOutOfRangeNumberTestCases")
    @DisplayName("예외: 허용 범위 밖의 당첨 번호를 사용시 예외를 반환합니다.")
    void shouldThrowException_WhenInsertOutOfRangeNumber(List<Integer> numbers, int bonus) {
        assertThrows(IllegalArgumentException.class,
                () -> WinningNumbers.of(numbers, bonus),
                "[ERROR] 로또 번호는 1 이상 45 이하여야 합니다."
        );
    }

    public static Stream<Arguments> provideOutOfRangeNumberTestCases() {
        return Stream.of(
                Arguments.of(List.of(-1, 2, 3, 4, 5, 6), 45),
                Arguments.of(List.of(0, 2, 3, 4, 5, 6), 45),
                Arguments.of(List.of(1, 2, 3, 4, 5, 46), 45)
        );
    }

    @ParameterizedTest
    @MethodSource("provideOutOfRangeBonusTestCases")
    @DisplayName("예외: 허용 범위 밖의 보너스 번호를 사용시 예외를 반환합니다.")
    void shouldThrowException_WhenInsertOutOfRangeBonus(List<Integer> numbers, int bonus) {
        assertThrows(IllegalArgumentException.class,
                () -> WinningNumbers.of(numbers, bonus),
                "[ERROR] 로또 번호는 1 이상 45 이하여야 합니다."
        );
    }

    public static Stream<Arguments> provideOutOfRangeBonusTestCases() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 3, 4, 5, 6), -1),
                Arguments.of(List.of(1, 2, 3, 4, 5, 6), 0),
                Arguments.of(List.of(1, 2, 3, 4, 5, 6), 46)
        );
    }

    @Test
    @DisplayName("예외: 중복된 당첨 번호를 사용시 예외를 반환합니다.")
    void shouldThrowException_WhenInsertDuplicateNumber() {
        assertThrows(IllegalArgumentException.class,
                () -> WinningNumbers.of(List.of(1, 1, 3, 4, 5, 6), 45),
                "[ERROR] 중복된 당첨 번호를 사용할 수 없습니다."
        );
    }

    @Test
    @DisplayName("예외: 중복된 보너스 번호를 사용시 예외를 반환합니다.")
    void shouldThrowException_WhenInsertDuplicateBonus() {
        assertThrows(IllegalArgumentException.class,
                () -> WinningNumbers.of(List.of(1, 2, 3, 4, 5, 6), 1),
                "[ERROR] 당첨 번호와 동일한 보너스 번호를 사용할 수 없습니다."
        );
    }

    @ParameterizedTest
    @MethodSource("provideWrongNumberAmountTestCases")
    @DisplayName("예외: 6개가 아닌 로또 번호로 객체 생성 시 예외를 반환합니다.")
    void shouldThrowException_WhenInsertWrongNumberAmount(List<Integer> numbers, int bonus) {
        assertThrows(IllegalArgumentException.class,
                () -> WinningNumbers.of(numbers, bonus),
                "[ERROR] 로또 번호는 6개여야 합니다."
        );
    }

    public static Stream<Arguments> provideWrongNumberAmountTestCases() {
        return Stream.of(
                Arguments.of(List.of(1), 45),
                Arguments.of(List.of(1, 2, 3, 4, 5, 6, 7), 45)
        );
    }

    @ParameterizedTest(name = "{0}개 일치{1} -> {2}등")
    @MethodSource("provideSuccessRankTestCases")
    @DisplayName("통과: 로또 번호 일치 개수에 따른 등수를 계산합니다.")
    void shouldCalculateRank(int matchCount, String bonusInfo, int expectedRank,
                             List<Integer> numbers, int bonus) {
        WinningNumbers winning = WinningNumbers.of(numbers, bonus);

        int rank = winning.calculateRank(lotto);

        assertEquals(expectedRank, rank);
    }

    private static Stream<Arguments> provideSuccessRankTestCases() {
        return Stream.of(
                Arguments.of(6, "", 1, List.of(1, 2, 3, 4, 5, 6), 45),
                Arguments.of(5, "+보너스", 2, List.of(1, 2, 3, 4, 5, 44), 6),
                Arguments.of(5, "", 3, List.of(1, 2, 3, 4, 5, 44), 45),
                Arguments.of(4, "", 4, List.of(1, 2, 3, 4, 43, 44), 45),
                Arguments.of(3, "", 5, List.of(1, 2, 3, 42, 43, 44), 45),
                Arguments.of(2, "", 0, List.of(1, 2, 41, 42, 43, 44), 45),
                Arguments.of(1, "", 0, List.of(1, 40, 41, 42, 43, 44), 45),
                Arguments.of(0, "", 0, List.of(39, 40, 41, 42, 43, 44), 45)
        );
    }
}
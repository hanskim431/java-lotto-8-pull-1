package lotto.model.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LottoTest {

    @Test
    @DisplayName("예외: 로또 번호가 6개 보다 적을 때 에외를 발생한다.")
    void shouldThrowException_WhenLackNumberAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> Lotto.from(List.of(1, 2, 3, 4, 5)),
                "[ERROR] 로또 번호는 6개여야 합니다.");
    }

    @Test
    @DisplayName("예외: 로또 번호가 6개 보다 많을 때 에외를 발생한다.")
    void shouldThrowException_WhenSurplusNumberAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> Lotto.from(List.of(1, 2, 3, 4, 5, 6, 7)),
                "[ERROR] 로또 번호는 6개여야 합니다.");
    }

    @Test
    @DisplayName("예외: 로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    void shouldThrowException_WhenNumberDuplicate() {
        assertThrows(IllegalArgumentException.class,
                () -> Lotto.from(List.of(1, 2, 3, 4, 5, 5)),
                "[ERROR] 중복된 번호를 사용할 수 없습니다."
        );
    }

    @ParameterizedTest
    @MethodSource("provideNumberOutOfRangeTestCase")
    @DisplayName("예외: 로또 번호에 허용 범위를 넘은 숫자가 있으면 예외가 발생한다.")
    void shouldThrowException_WhenNumberOutOfRange(List<Integer> numbers) {
        assertThrows(IllegalArgumentException.class,
                () -> Lotto.from(numbers),
                "[ERROR] 로또 번호는 1 이상 45 이하여야 합니다."
        );
    }

    public static Stream<Arguments> provideNumberOutOfRangeTestCase() {
        return Stream.of(
                Arguments.of(List.of(-1, 2, 3, 4, 5, 6)),
                Arguments.of(List.of(0, 2, 3, 4, 5, 6)),
                Arguments.of(List.of(46, 2, 3, 4, 5, 6))
        );
    }

    @Test
    @DisplayName("통과: 로또 번호에 조회 번호가 있으면 true를 반환한다.")
    void shouldReturnTrue_WhenNumberContains() {
        Lotto lotto = Lotto.from(new ArrayList<>(List.of(1, 2, 3, 4, 5, 6)));
        assertTrue(lotto.contains(1));
    }

    @Test
    @DisplayName("통과: 로또 번호에 조회 번호가 없으면 false를 반환한다.")
    void shouldReturnFalse_WhenNumberNotContains() {
        Lotto lotto = Lotto.from(new ArrayList<>(List.of(1, 2, 3, 4, 5, 6)));
        assertFalse(lotto.contains(1000));
    }

    @Test
    @DisplayName("통과: 로또 번호에 조회 번호가 없으면 false를 반환한다.")
    void shouldReturnNumbers_WhenCallGetNumbers() {
        Lotto lotto = Lotto.from(new ArrayList<>(List.of(1, 2, 3, 4, 5, 6)));
        assertFalse(lotto.contains(1000));
    }
}

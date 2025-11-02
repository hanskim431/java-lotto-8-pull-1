package lotto.model.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class MoneyTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("예외: 구입 금액에 양수가 들어오지 않으면 예외를 반환한다.")
    void shouldThrowException_WhenNotPositive(int money) {
        Exception exception = assertThrowsExactly(IllegalArgumentException.class,
                () -> Money.from(money));
        assertEquals("[ERROR] 로또 구입 금액은 양수여야합니다.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1001, 2001})
    @DisplayName("예외: 구입 금액에 1000원 단위가 들어오지 않으면 예외를 반환한다.")
    void shouldThrowException_WhenNotMultipleOfThousand(int money) {
        Exception exception = assertThrowsExactly(IllegalArgumentException.class,
                () -> Money.from(money));
        assertEquals("[ERROR] 로또 구입 금액은 1000원 단위여야합니다.", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideCalculateLottoAmountTestCase")
    @DisplayName("통과: 구입 금액으로 살 수 있는 로또 수량을 반환한다.")
    void shouldReturnLottoAmount(int money, int amount) {
        assertEquals(amount, Money.from(money).calculateLottoAmount());
    }

    public static Stream<Arguments> provideCalculateLottoAmountTestCase() {
        return Stream.of(
                Arguments.of(1000, 1),
                Arguments.of(2000, 2),
                Arguments.of(30000, 30)
        );
    }
}
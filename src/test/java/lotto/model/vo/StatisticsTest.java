package lotto.model.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {

    private static final String ERROR_TAG = "[ERROR]";
    private static final String NONE_GAME_NOT_ALLOW = "게임 횟수가 0 인 경우는 통계를 계산할 수 없습니다";

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            rank1, rank2, rank3, rank4, rank5, lose
            1,     0,     0,     0,     0,     0
            0,     1,     0,     0,     0,     0
            0,     0,     1,     0,     0,     0
            0,     0,     0,     1,     0,     0
            0,     0,     0,     0,     1,     0
            0,     0,     0,     0,     0,     1
            """)
    @DisplayName("통과: 각 등급별 당첨 개수가 rankAmount에 올바르게 매핑됩니다.")
    void shouldMapRankAmountCorrectly(int rank1, int rank2, int rank3, int rank4, int rank5, int lose) {
        Statistics statistics = Statistics.of(rank1, rank2, rank3, rank4, rank5, lose);
        Map<Integer, Integer> rankAmount = statistics.getRankAmount();

        assertEquals(rank1, rankAmount.get(1));
        assertEquals(rank2, rankAmount.get(2));
        assertEquals(rank3, rankAmount.get(3));
        assertEquals(rank4, rankAmount.get(4));
        assertEquals(rank5, rankAmount.get(5));
        assertEquals(lose, rankAmount.get(6));
    }

    @ParameterizedTest
    @MethodSource("provideEarnRateTestData")
    @DisplayName("통과: 수익률(earnRate)이 올바르게 계산됩니다.")
    void shouldCalculateEarnRateCorrectly(int rank1, int rank2, int rank3, int rank4, int rank5, int lose, double expectedEarnRate) {
        Statistics statistics = Statistics.of(rank1, rank2, rank3, rank4, rank5, lose);
        assertEquals(expectedEarnRate, statistics.getEarnRate(), 0.001);
    }

    private static Stream<Arguments> provideEarnRateTestData() {
        return Stream.of(
                // 1등 1개: (2,000,000,000) / (1 * 1000) * 100 = 200,000,000
                Arguments.of(1, 0, 0, 0, 0, 0, 200_000_000),
                // 2등 1개: (30,000,000) / (1 * 1000) * 100 = 3,000,000
                Arguments.of(0, 1, 0, 0, 0, 0, 3_000_000),
                // 3등 1개: (1,500,000) / (1 * 1000) * 100 = 150,000
                Arguments.of(0, 0, 1, 0, 0, 0, 150_000),
                // 4등 1개: (50,000) / (1 * 1000) * 100 = 5,000
                Arguments.of(0, 0, 0, 1, 0, 0, 5_000),
                // 5등 1개: (5,000) / (1 * 1000) * 100 = 500
                Arguments.of(0, 0, 0, 0, 1, 0, 500),
                // 꽝 1개: (0) / (1 * 1000) * 100 = 0
                Arguments.of(0, 0, 0, 0, 0, 1, 0),
                // 혼합: 5등 1개 + 꽝 7개 = (5,000) / (8 * 1000) * 100 = 62.5
                Arguments.of(0, 0, 0, 0, 1, 7, 62.5),
                // 혼합: 3등 2개 + 꽝 3개 = (1,500,000 * 2) / (5 * 1000) * 100 = 60,000
                Arguments.of(0, 0, 2, 0, 0, 3, 60_000)
        );
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0, 0, 0, 0, 0, 0
            """)
    @DisplayName("예외: 게임 횟수(모든 등급의 합)가 0이면 예외를 반환합니다.")
    void shouldThrowException_WhenGameAmountIsZero(int rank1, int rank2, int rank3, int rank4, int rank5, int lose) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Statistics.of(rank1, rank2, rank3, rank4, rank5, lose)
        );
        assertTrue(exception.getMessage().contains(ERROR_TAG));
        assertTrue(exception.getMessage().contains(NONE_GAME_NOT_ALLOW));
    }

}
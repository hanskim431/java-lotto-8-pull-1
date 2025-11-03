package lotto.model.dto;

import lotto.model.vo.Statistics;

import java.util.Map;

public class StatisticsResponseDto {

    private final static String NOW_ALLOW_NULL_DATA = "[ERROR] 빈 데이터는 허용되지 않습니다.";

    private final Map<Integer, Integer> rankPrizeTable;

    private final Map<Integer, Integer> rankAmount;

    private StatisticsResponseDto(
            Map<Integer, Integer> rankPrizeTable, Map<Integer, Integer> rankAmount) {
        validateNull(rankPrizeTable);
        this.rankPrizeTable = Map.copyOf(rankPrizeTable);
        validateNull(rankAmount);
        this.rankAmount = Map.copyOf(rankAmount);
    }

    public static StatisticsResponseDto of(Statistics statistics) {
        return new StatisticsResponseDto(
                statistics.getRankPrizeTable(), statistics.getRankAmount());
    }

    public Map<Integer, Integer> getRankPrizeTable() {
        return Map.copyOf(rankPrizeTable);
    }

    public Map<Integer, Integer> getRankAmount() {
        return Map.copyOf(rankAmount);
    }

    private void validateNull(Map<Integer, Integer> map) {
        if (map == null) {
            throw new IllegalArgumentException(NOW_ALLOW_NULL_DATA);
        }
    }
}

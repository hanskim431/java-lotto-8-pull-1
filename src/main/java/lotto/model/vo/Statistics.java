package lotto.model.vo;

import java.util.Map;

public class Statistics {

    private final static int ZERO = 0;
    private final static String ERROR_TAG = "[ERROR]";
    private final static String SPACE = " ";
    private final static String NONE_GAME_NOT_ALLOW = "게임 횟수가 0 인 경우는 통계를 계산할 수 없습니다";

    private final static int RANK1 = 1;
    private final static int RANK2 = 2;
    private final static int RANK3 = 3;
    private final static int RANK4 = 4;
    private final static int RANK5 = 5;
    private final static int LOSE = 6;

    private final static Map<Integer, Integer> RANK_PRIZE_TABLE
            = Map.of(
            RANK1, 2_000_000_000,
            RANK2, 30_000_000,
            RANK3, 1_500_000,
            RANK4, 50_000,
            RANK5, 5_000,
            LOSE, 0
    );

    private final Map<Integer, Integer> rankAmount;

    private final double earnRate;

    private Statistics(Map<Integer, Integer> rankAmount) {
        this.rankAmount = rankAmount;
        earnRate = calculateEarnRate();
    }

    public static Statistics of(int rank1, int rank2, int rank3, int rank4, int rank5, int lose) {
        Map<Integer, Integer> rankRecord = Map.of(
                RANK1, rank1,
                RANK2, rank2,
                RANK3, rank3,
                RANK4, rank4,
                RANK5, rank5,
                LOSE, lose
        );

        validateNoGame(rankRecord);

        return new Statistics(rankRecord);
    }

    public Map<Integer, Integer> getRankPrizeTable() {
        return Map.copyOf(RANK_PRIZE_TABLE);
    }

    public Map<Integer, Integer> getRankAmount() {
        return Map.copyOf(Map.copyOf(rankAmount));
    }

    public double getEarnRate() {
        return this.earnRate;
    }

    private double calculateEarnRate() {
        int gameAmount = rankAmount.values().stream().mapToInt(Integer::intValue).sum();
        int earn = rankAmount.entrySet().stream()
            .mapToInt(entry -> entry.getValue() * RANK_PRIZE_TABLE.get(entry.getKey()))
            .sum();
        int investAmount = gameAmount * 1000;
        return ((double) earn) / investAmount * 100;
    }

    private static void validateNoGame(Map<Integer, Integer> rankAmount) {
        int gameAmount = rankAmount.values().stream().mapToInt(Integer::intValue).sum();
        if (gameAmount == ZERO) {
            throw new IllegalArgumentException(ERROR_TAG + SPACE + NONE_GAME_NOT_ALLOW);
        }
    }
}

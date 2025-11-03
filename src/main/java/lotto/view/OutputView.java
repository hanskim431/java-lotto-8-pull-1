package lotto.view;

import lotto.model.dto.LottoResponseDto;
import lotto.model.dto.StatisticsResponseDto;
import lotto.model.vo.Lottos;

import java.util.ArrayList;
import java.util.Map;

public class OutputView {

    private final static String PURCHASE_MESSAGE = "개를 구매했습니다.";
    private final static String STATISTICS_MESSAGE = "당첨 통계";
    private final static String SEPARATOR = "---";
    private final static String RANK1_DESCRIPTION = "6개 일치";
    private final static String RANK2_DESCRIPTION = "5개 일치, 보너스 볼 일치";
    private final static String RANK3_DESCRIPTION = "5개 일치";
    private final static String RANK4_DESCRIPTION = "4개 일치";
    private final static String RANK5_DESCRIPTION = "3개 일치";
    private final static String RANK_PRIZE_FORMAT = "%s (%,d원) - %d개";
    private final static String EARN_RATE_FORMAT = "총 수익률은 %.1f%%입니다.";

    public static void printPurchaseMessage(int count) {
        System.out.println(count + PURCHASE_MESSAGE);
    }

    public static void printLottos(Lottos lottos) {
        for (var lotto : lottos) {
            LottoResponseDto lottoDto = LottoResponseDto.from(lotto);
            ArrayList<Integer> numbers = new ArrayList<>(lottoDto.getNumbers());
            numbers.sort(null);
            System.out.println(numbers);
        }
    }

    public static void printStatistics(StatisticsResponseDto statisticsDto, double earnRate) {
        System.out.println(STATISTICS_MESSAGE);
        System.out.println(SEPARATOR);

        var rankAmount = statisticsDto.getRankAmount();
        var rankPrizeTable = statisticsDto.getRankPrizeTable();

        printRankLine(1, RANK1_DESCRIPTION, rankAmount, rankPrizeTable);
        printRankLine(2, RANK2_DESCRIPTION, rankAmount, rankPrizeTable);
        printRankLine(3, RANK3_DESCRIPTION, rankAmount, rankPrizeTable);
        printRankLine(4, RANK4_DESCRIPTION, rankAmount, rankPrizeTable);
        printRankLine(5, RANK5_DESCRIPTION, rankAmount, rankPrizeTable);

        System.out.println(String.format(EARN_RATE_FORMAT, earnRate));
    }

    private static void printRankLine(int rank, String rankDescription,
                                       Map<Integer, Integer> rankAmount,
                                       Map<Integer, Integer> rankPrizeTable) {
        int count = rankAmount.get(rank);
        int prize = rankPrizeTable.get(rank);
        System.out.println(String.format(RANK_PRIZE_FORMAT, rankDescription, prize, count));
    }
}

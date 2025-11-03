package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.model.entity.Money;
import lotto.model.vo.Lotto;
import lotto.model.vo.Lottos;
import lotto.model.vo.Statistics;
import lotto.model.vo.WinningNumbers;

import java.util.List;

public class LottoService {

    private final static int NUMBERS_AMOUNT = 6;
    private final static int LOTTO_MIN_NUMBER = 1;
    private final static int LOTTO_MAX_NUMBER = 45;
    private final static int RANK_ARRAY_SIZE = 7;
    private final static int RANK1 = 1;
    private final static int RANK2 = 2;
    private final static int RANK3 = 3;
    private final static int RANK4 = 4;
    private final static int RANK5 = 5;
    private final static int LOSE = 0;

    public LottoService() {
    }

    public Lottos purchaseLottos(Money money) {
        int lottoAmount = money.calculateLottoAmount();
        Lottos lottos = Lottos.newInstance();
        for (int i = 0; i < lottoAmount; i++) {
            lottos.add(this.generateLotto());
        }
        return lottos;
    }

    public Statistics calculateStatistics(Lottos lottos, WinningNumbers winningNumbers) {
        int[] rankCount = new int[RANK_ARRAY_SIZE];

        for (Lotto lotto : lottos) {
            int rank = winningNumbers.calculateRank(lotto);
            rankCount[rank]++;
        }

        return Statistics.of(rankCount[RANK1], rankCount[RANK2], rankCount[RANK3],
                rankCount[RANK4], rankCount[RANK5], rankCount[LOSE]);
    }

    private Lotto generateLotto() {
        List<Integer> lottoNumbers = Randoms.pickUniqueNumbersInRange(
                        LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER, NUMBERS_AMOUNT);
        return Lotto.from(lottoNumbers);
    }
}

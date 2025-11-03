package lotto.controller;

import lotto.model.dto.StatisticsResponseDto;
import lotto.model.entity.Money;
import lotto.model.vo.Lottos;
import lotto.model.vo.Statistics;
import lotto.model.vo.WinningNumbers;
import lotto.service.LottoService;
import lotto.service.StringParseService;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class LottoController {

    private final LottoService lottoService;

    public LottoController(LottoService lottoService) {
        this.lottoService = lottoService;
    }

    public void run() {
        Lottos lottos = purchaseLottos();
        OutputView.printLottos(lottos);

        WinningNumbers winningNumbers = inputWinningNumbers();
        Statistics statistics = lottoService.calculateStatistics(lottos, winningNumbers);

        printStatistics(statistics);
    }

    private Lottos purchaseLottos() {
        try {
            String inputMoney = InputView.getUserInputBuyMoney();
            int money = StringParseService.parseIntBuyMoney(inputMoney);
            Money buyMoney = Money.from(money);
            Lottos lottos = lottoService.purchaseLottos(buyMoney);
            OutputView.printPurchaseMessage(lottos.size());
            return lottos;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return purchaseLottos();
        }
    }

    private WinningNumbers inputWinningNumbers() {
        try {
            String inputWinNumbers = InputView.getUserInputWinNumbers();
            List<Integer> winNumbers = StringParseService.parseIntListInputWinNumbers(inputWinNumbers);

            String inputBonusNumber = InputView.getUserInputBonusNumber();
            int bonusNumber = StringParseService.parseIntBonusNumber(inputBonusNumber);

            return WinningNumbers.of(winNumbers, bonusNumber);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputWinningNumbers();
        }
    }

    private void printStatistics(Statistics statistics) {
        StatisticsResponseDto statisticsDto = StatisticsResponseDto.of(statistics);
        OutputView.printStatistics(statisticsDto, statistics.getEarnRate());
    }
}

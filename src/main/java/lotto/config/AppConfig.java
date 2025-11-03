package lotto.config;

import lotto.controller.LottoController;
import lotto.service.LottoService;

public class AppConfig {

    private static final AppConfig instance = new AppConfig();

    private final LottoService lottoService;
    private final LottoController lottoController;

    private AppConfig() {
        this.lottoService = new LottoService();
        this.lottoController = new LottoController(getLottoService());
    }

    public static AppConfig getInstance() {
        return instance;
    }

    public LottoController getLottoController() {
        return lottoController;
    }

    public LottoService getLottoService() {
        return lottoService;
    }
}

package lotto.controller;

import java.util.List;
import java.util.Map;

import lotto.constant.Rank;
import lotto.domain.Lotto;
import lotto.dto.LottoResultDTO;
import lotto.dto.LottoStatisticsDTO;
import lotto.util.NumbericInputParser;
import lotto.util.WinningNumbersParser;
import lotto.service.LottoService;
import lotto.validator.BonusNumberValidator;
import lotto.validator.MoneyValidator;
import lotto.validator.WinningNumbersValidator;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final LottoService lottoService = new LottoService();

    public void run() {
        int money = readMoney();
        List<Lotto> lottos = publishLottos(money);
        List<Integer> winningNumbers = readWinningNumbers();
        int bonusNumber = readBonusNumber(winningNumbers);

        LottoStatisticsDTO dto = calculateStatistics(lottos, winningNumbers, bonusNumber, money);
        outputView.printStatistics(dto);
    }

    private int readMoney() {
        while (true) {
            try {
                String moneyInput = inputView.readMoney();
                int money = NumbericInputParser.parse(moneyInput);
                return MoneyValidator.validate(money);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List<Lotto> publishLottos(int money) {
        List<Lotto> lottos = lottoService.createLottos(money);
        outputView.printRandomLottos(lottos);
        return lottos;
    }

    private List<Integer> readWinningNumbers() {
        while (true) {
            try {
                String input = inputView.readWinningNumbers();
                List<Integer> numbers = WinningNumbersParser.parse(input);
                WinningNumbersValidator.validate(numbers);
                return numbers;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int readBonusNumber(List<Integer> winningNumbers) {
        while (true) {
            try {
                String input = inputView.readBonusNumber();
                int bonus = NumbericInputParser.parse(input);
                BonusNumberValidator.validate(bonus, winningNumbers);
                return bonus;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private LottoStatisticsDTO calculateStatistics(
            List<Lotto> lottos,
            List<Integer> winningNumbers,
            int bonusNumber,
            int money
    ) {
        List<LottoResultDTO> results = lottos.stream()
                .map(lotto -> lottoService.compareWithWinningNumbers(lotto, winningNumbers, bonusNumber))
                .toList();

        Map<Rank, Integer> rankCount = lottoService.countRankResults(results);
        double profitRate = lottoService.calculateProfitRate(rankCount, money);

        return new LottoStatisticsDTO(rankCount, profitRate);
    }
}
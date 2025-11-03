package lotto.view;

import java.util.List;
import java.util.Map;
import lotto.domain.Lotto;
import lotto.constant.Rank;
import lotto.dto.LottoStatisticsDTO;

public class OutputView {

    private static final String PURCHASE_MESSAGE = "%d개를 구매했습니다.\n";
    private static final String STATISTICS_HEADER = "당첨 통계\n---";
    private static final String PROFIT_RATE_FORMAT = "총 수익률은 %.1f%%입니다.\n";

    private static final List<Rank> PRINT_ORDER = List.of(
            Rank.FIFTH, Rank.FOURTH, Rank.THIRD, Rank.SECOND, Rank.FIRST
    );

    private static final Map<Rank, String> RANK_MESSAGES = Map.of(
            Rank.FIFTH, "3개 일치 (5,000원)",
            Rank.FOURTH, "4개 일치 (50,000원)",
            Rank.THIRD, "5개 일치 (1,500,000원)",
            Rank.SECOND, "5개 일치, 보너스 볼 일치 (30,000,000원)",
            Rank.FIRST, "6개 일치 (2,000,000,000원)"
    );

    public void printRandomLottos(List<Lotto> lottos) {
        printBlankLine();
        printPurchaseMessage(lottos.size());
        lottos.forEach(lotto -> System.out.println(lotto));
        printBlankLine();
    }

    public void printStatistics(LottoStatisticsDTO dto) {
        System.out.println(STATISTICS_HEADER);
        PRINT_ORDER.forEach(rank ->
                System.out.println(RANK_MESSAGES.get(rank) + " - " + dto.getCount(rank) + "개")
        );
        System.out.printf(PROFIT_RATE_FORMAT, dto.getProfitRate());
    }

    private void printPurchaseMessage(int count) {
        System.out.printf(PURCHASE_MESSAGE, count);
    }

    private void printBlankLine() {
        System.out.println();
    }
}
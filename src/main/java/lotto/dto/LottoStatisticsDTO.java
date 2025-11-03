package lotto.dto;

import java.util.Map;
import lotto.constant.Rank;

public class LottoStatisticsDTO {
    private final Map<Rank, Integer> rankCount;
    private final double profitRate;

    public LottoStatisticsDTO(Map<Rank, Integer> rankCount, double profitRate) {
        this.rankCount = rankCount;
        this.profitRate = profitRate;
    }

    public int getCount(Rank rank) {
        return rankCount.getOrDefault(rank, 0);
    }

    public double getProfitRate() {
        return profitRate;
    }
}

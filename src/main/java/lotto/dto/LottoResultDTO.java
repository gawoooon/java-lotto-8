package lotto.dto;

import lotto.constant.Rank;

public class LottoResultDTO {
    private final int matchCount;
    private final boolean bonusMatched;

    public LottoResultDTO(int matchCount, boolean bonusMatched) {
        this.matchCount = matchCount;
        this.bonusMatched = bonusMatched;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public boolean isBonusMatched() {
        return bonusMatched;
    }

    public Rank toRank() {
        return Rank.from(matchCount, bonusMatched);
    }
}

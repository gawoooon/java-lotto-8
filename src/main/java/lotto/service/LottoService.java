package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import lotto.constant.Constants;
import lotto.constant.Rank;
import lotto.domain.Lotto;

import java.util.ArrayList;
import java.util.List;
import lotto.dto.LottoResultDTO;

public class LottoService {

    public List<Lotto> createLottos(int purchaseAmount) {
        int count = purchaseAmount / Constants.LOTTO_PRICE;
        List<Lotto> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(Constants.LOTTO_MIN_NUMBER,
                    Constants.LOTTO_MAX_NUMBER, Constants.LOTTO_NUMBER_COUNT);
            result.add(new Lotto(numbers));
        }

        return result;
    }

    public LottoResultDTO compareWithWinningNumbers(Lotto lotto, List<Integer> winningNumbers, int bonusNumber) {
        int matchCount = (int) lotto.getNumbers().stream()
                .filter(winningNumbers::contains)
                .count();

        boolean bonusMatched = lotto.getNumbers().contains(bonusNumber);

        return new LottoResultDTO(matchCount, bonusMatched);
    }

    public Map<Rank, Integer> countRankResults(List<LottoResultDTO> results) {
        Map<Rank, Integer> resultCount = new EnumMap<>(Rank.class);

        for (Rank rank : Rank.values()) {
            resultCount.put(rank, 0);
        }

        for (LottoResultDTO dto : results) {
            Rank rank = dto.toRank();
            resultCount.put(rank, resultCount.get(rank) + 1);
        }

        return resultCount;
    }

    public double calculateProfitRate(Map<Rank, Integer> rankCount, int purchaseAmount) {
        long totalPrize = 0;

        for (Map.Entry<Rank, Integer> entry : rankCount.entrySet()) {
            Rank rank = entry.getKey();
            int count = entry.getValue();
            totalPrize += (long) rank.getPrize() * count;
        }

        double rate = (double) totalPrize / purchaseAmount * 100;
        return Math.round(rate * 10) / 10.0;
    }
}

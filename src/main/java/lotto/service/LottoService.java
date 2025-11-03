package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.Collections;
import lotto.constant.Rank;
import lotto.domain.Lotto;

import java.util.ArrayList;
import java.util.List;
import lotto.dto.LottoResultDTO;

public class LottoService {

    public List<Lotto> createLottos(int purchaseAmount) {
        int count = purchaseAmount / 1000;
        List<Lotto> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
            Collections.sort(numbers);
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
    
    public double calculateProfitRate(List<Rank> ranks, int purchaseAmount) {
        long totalPrize = ranks.stream()
                .mapToLong(Rank::getPrize)
                .sum();

        double rate = ((double) totalPrize / purchaseAmount) * 100;
        return Math.round(rate * 10) / 10.0;
    }
}

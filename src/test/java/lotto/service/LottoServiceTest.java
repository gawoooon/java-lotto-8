package lotto.service;

import lotto.constant.Rank;
import lotto.domain.Lotto;
import lotto.dto.LottoResultDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoServiceTest {
    private final LottoService lottoService = new LottoService();

    @Nested
    @DisplayName("createLottos")
    class createLottosTest {
        @Test
        @DisplayName("입력 금액에 따라 로또 N장이 발행된다.")
        void createLottos_count_matches_money() {
            List<Lotto> lottos = lottoService.createLottos(5000);

            assertThat(lottos).hasSize(5);
        }

        @Test
        @DisplayName("각 로또는 6개의 번호를 오름차순으로 포함한다.")
        void each_lotto_has_sorted_6_numbers() {
            List<Lotto> lottos = lottoService.createLottos(1000);

            List<Integer> numbers = lottos.get(0).getNumbers();
            assertThat(numbers).hasSize(6);
            assertThat(numbers).isSorted();
            assertThat(numbers).doesNotHaveDuplicates();
            assertThat(numbers).allMatch(n -> n >= 1 && n <= 45);
        }
    }

    @Nested
    @DisplayName("당첨 결과 비교 테스트")
    class CompareResults {

        private final List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        private final int bonusNumber = 7;

        @Test
        @DisplayName("6개 번호가 모두 일치하면 1등")
        void matchAllSix() {
            Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
            LottoResultDTO result = lottoService.compareWithWinningNumbers(lotto, winningNumbers, bonusNumber);

            assertThat(result.getMatchCount()).isEqualTo(6);
            assertThat(result.isBonusMatched()).isFalse();
            assertThat(result.toRank()).isEqualTo(Rank.FIRST);
        }

        @Test
        @DisplayName("5개 번호 + 보너스 번호 일치하면 2등")
        void matchFivePlusBonus() {
            Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 7));
            LottoResultDTO result = lottoService.compareWithWinningNumbers(lotto, winningNumbers, bonusNumber);

            assertThat(result.getMatchCount()).isEqualTo(5);
            assertThat(result.isBonusMatched()).isTrue();
            assertThat(result.toRank()).isEqualTo(Rank.SECOND);
        }

        @Test
        @DisplayName("5개 번호만 일치하면 3등")
        void matchFiveOnly() {
            Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 45));
            LottoResultDTO result = lottoService.compareWithWinningNumbers(lotto, winningNumbers, bonusNumber);

            assertThat(result.getMatchCount()).isEqualTo(5);
            assertThat(result.isBonusMatched()).isFalse();
            assertThat(result.toRank()).isEqualTo(Rank.THIRD);
        }

        @Test
        @DisplayName("4개 번호 일치하면 4등")
        void matchFour() {
            Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 40, 41));
            LottoResultDTO result = lottoService.compareWithWinningNumbers(lotto, winningNumbers, bonusNumber);

            assertThat(result.getMatchCount()).isEqualTo(4);
            assertThat(result.toRank()).isEqualTo(Rank.FOURTH);
        }

        @Test
        @DisplayName("3개 번호 일치하면 5등")
        void matchThree() {
            Lotto lotto = new Lotto(List.of(1, 2, 3, 40, 41, 42));
            LottoResultDTO result = lottoService.compareWithWinningNumbers(lotto, winningNumbers, bonusNumber);

            assertThat(result.getMatchCount()).isEqualTo(3);
            assertThat(result.toRank()).isEqualTo(Rank.FIFTH);
        }

        @Test
        @DisplayName("2개 이하 일치하면 꽝(NONE)")
        void matchTwoOrLess() {
            Lotto lotto = new Lotto(List.of(1, 2, 40, 41, 42, 43));
            LottoResultDTO result = lottoService.compareWithWinningNumbers(lotto, winningNumbers, bonusNumber);

            assertThat(result.getMatchCount()).isEqualTo(2);
            assertThat(result.toRank()).isEqualTo(Rank.NONE);
        }

        @Test
        @DisplayName("보너스 번호만 일치해도 등수에 영향 없음")
        void onlyBonusMatched() {
            Lotto lotto = new Lotto(List.of(7, 8, 9, 10, 11, 12));
            LottoResultDTO result = lottoService.compareWithWinningNumbers(lotto, winningNumbers, bonusNumber);

            assertThat(result.getMatchCount()).isEqualTo(0);
            assertThat(result.isBonusMatched()).isTrue();
            assertThat(result.toRank()).isEqualTo(Rank.NONE);
        }
    }

    @Nested
    @DisplayName("수익률 계산 테스트")
    class ProfitRate {

        @Test
        @DisplayName("모두 꽝이면 수익률 0.0%")
        void allNone() {
            List<Rank> ranks = List.of(Rank.NONE, Rank.NONE, Rank.NONE);
            double rate = lottoService.calculateProfitRate(ranks, 3000);
            assertThat(rate).isEqualTo(0.0);
        }

        @Test
        @DisplayName("5등 1개면 수익률 166.7% (5,000 / 3,000)")
        void oneFifth() {
            List<Rank> ranks = List.of(Rank.FIFTH, Rank.NONE, Rank.NONE);
            double rate = lottoService.calculateProfitRate(ranks, 3000);
            assertThat(rate).isEqualTo(166.7);
        }

        @Test
        @DisplayName("1등 1개면 수익률 20000000000.0% (2,000,000,000 / 10,000)")
        void oneFirst() {
            List<Rank> ranks = List.of(Rank.FIRST);
            double rate = lottoService.calculateProfitRate(ranks, 10_000);
            assertThat(rate).isEqualTo(20_000_00000.0 / 100); // 20000000.0%
        }

        @Test
        @DisplayName("5등 1개, 4등 1개, 3등 1개, 구입금액 8000 → 19437.5%")
        void mixedRanks() {
            List<Rank> ranks = List.of(Rank.FIFTH, Rank.FOURTH, Rank.THIRD);
            double rate = lottoService.calculateProfitRate(ranks, 8000);
            assertThat(rate).isEqualTo(19_437.5);
        }

        @Test
        @DisplayName("소수점 둘째 자리에서 반올림하여 첫째 자리까지 표시")
        void roundingToOneDecimal() {
            List<Rank> ranks = List.of(Rank.FIFTH);
            double rate = lottoService.calculateProfitRate(ranks, 3001);
            assertThat(rate).isEqualTo(166.6);
        }
    }
}
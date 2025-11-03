package lotto.service;

import lotto.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoServiceTest {

    @Test
    @DisplayName("입력 금액에 따라 로또 N장이 발행된다.")
    void generateLottos_count_matches_money() {
        LottoService service = new LottoService();

        List<Lotto> lottos = service.generateLottos(5000);

        assertThat(lottos).hasSize(5);
    }

    @Test
    @DisplayName("각 로또는 6개의 번호를 오름차순으로 포함한다.")
    void each_lotto_has_sorted_6_numbers() {
        LottoService service = new LottoService();
        List<Lotto> lottos = service.generateLottos(1000);

        List<Integer> numbers = lottos.get(0).getNumbers();
        assertThat(numbers).hasSize(6);
        assertThat(numbers).isSorted();
        assertThat(numbers).doesNotHaveDuplicates();
        assertThat(numbers).allMatch(n -> n >= 1 && n <= 45);
    }
}
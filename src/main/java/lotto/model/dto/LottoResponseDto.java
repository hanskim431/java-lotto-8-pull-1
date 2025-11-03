package lotto.model.dto;

import lotto.model.vo.Lotto;

import java.util.List;

public class LottoResponseDto {

    private final static String NOW_ALLOW_NULL_DATA = "[ERROR] 빈 데이터는 허용되지 않습니다.";

    private final List<Integer> numbers;

    private LottoResponseDto(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public static LottoResponseDto from(Lotto lotto) {
        validate(lotto);
        List<Integer> lottoNumbers = lotto.getNumbers();
        return new LottoResponseDto(lottoNumbers);
    }

    public List<Integer> getNumbers() {
        return List.copyOf(numbers);
    }

    public int size() {
        return numbers.size();
    }

    private static void validate(Lotto lotto) {
        validateNull(lotto);
    }

    private static void validateNull(Lotto lotto) {
        if (lotto == null) {
            throw new IllegalArgumentException(NOW_ALLOW_NULL_DATA);
        }
    }
}

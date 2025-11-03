package lotto.model.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Lottos implements Iterable<Lotto> {

    private final static String ERROR_TAG= "[ERROR]";
    private final static String SPACE= " ";
    private final static String NOT_ALLOW_NULL_LOTTO = "null인 로또를 삽입할 수 없습니다.";

    private final List<Lotto> lottos;

    private Lottos() {
        this.lottos = new ArrayList<>();
    }

    private Lottos(List<Lotto> lottos) {
        this();
        this.lottos.addAll(lottos);
    }

    public static Lottos newInstance() {
        return new Lottos();
    }

    public static Lottos from(List<Lotto> lottos) {
        lottos.forEach(Lottos::valitate);
        return new Lottos(lottos);
    }

    public boolean add(Lotto lotto) {
        return lottos.add(lotto);
    }

    public boolean addAll(List<Lotto> lottos) {
        lottos.forEach(Lottos::valitate);
        return lottos.addAll(Collections.unmodifiableList(lottos));
    }

    public int size() {
        return lottos.size();
    }

    @Override
    public Iterator<Lotto> iterator() {
        return lottos.iterator();
    }

    private static void valitate(Lotto lotto) {
        if (lotto == null) {
            throw new IllegalArgumentException(ERROR_TAG + SPACE + NOT_ALLOW_NULL_LOTTO);
        }
    }
}

package fr.sii.rxjava.operator.grouping;

import fr.sii.rxjava.util.T2;
import rx.Observable;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.lang.Integer.MAX_VALUE;
import static rx.Observable.range;

public class GroupBy {

    public static void main(String... args) {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5);

        Observable<String> grouped = ints1
                .groupBy(i -> i % 2)
                .flatMap(groupObs -> groupObs
                        .zipWith(range(1, MAX_VALUE), T2::t2)
                        .map(v -> v._2 + " ieme " + (groupObs.getKey() == 0 ? "" : "im") + "pair : " + v._1));

        createObserver(10, 350, "Observer", GREEN, grouped);
    }
}

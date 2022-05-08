package fr.sii.rxjava.operator.combining;

import rx.Observable;

import static fr.sii.rxjava.operator.combining.Join.randomOf;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.*;
import static rx.Observable.merge;

public class GroupJoin {

    public static void main(String... args) {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5);

        Observable<Integer> ints2 = createColdIntsObservable(200, 10, "Ints 2", ORANGE, 10, 15);

        Observable<String> groupJoined =
                merge(ints1.groupJoin(ints2,
                        l -> createColdIntsObservable(10, 350, "End", randomOf(RED), l, l),
                        r -> createColdIntsObservable(200, 350, "End", randomOf(ORANGE), r, r),
                        (l, rObs) -> rObs.scan(l + " / ", (acc, r) -> acc + r + " "))
                );

        createObserver(400, 10, "Observer", GREEN, groupJoined);
    }
}

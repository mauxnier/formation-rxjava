package fr.sii.rxjava.operator.combining;

import rx.Observable;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.*;
import static rx.Observable.combineLatest;

public class CombineLatest {
    public static void main(String... args) {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5);

        Observable<Integer> ints2 = createColdIntsObservable(200, 10, "Ints 2", ORANGE, 10, 15);

        createObserver(10, 350, "Observer", GREEN, combineLatest(ints1, ints2, (a, b) -> a + " / " + b));
    }
}

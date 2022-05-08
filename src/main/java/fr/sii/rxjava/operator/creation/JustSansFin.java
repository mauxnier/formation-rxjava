package fr.sii.rxjava.operator.creation;

import rx.Observable;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static rx.Observable.never;

public class JustSansFin {
    public static void main(String... args) {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5)
                .concatWith(never());

        createObserver(10, 350, "Observer", GREEN, ints1);
    }
}

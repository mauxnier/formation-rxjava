package fr.sii.rxjava.operator.reduction;

import rx.Observable;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;

public class Reduce {

    public static void main(String... args) throws InterruptedException {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5);

        createObserver(10, 350, "Observer 1", GREEN, ints1.reduce(1000, (acc, v) -> acc+v));

        // Clickez sur onCompleted pour voir le résultat.
    }
}

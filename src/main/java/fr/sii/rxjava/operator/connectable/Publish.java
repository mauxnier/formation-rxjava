package fr.sii.rxjava.operator.connectable;

import rx.observables.ConnectableObservable;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.*;

public class Publish {

    public static void main(String... args) throws InterruptedException {
        ConnectableObservable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5)
                .publish();

        createObserver(10, 350, "Observer 1", GREEN, ints1);

        createObserver(450, 350, "Observer 2", YELLOW, ints1);

        Thread.sleep(5_000);

        ints1.connect();
    }
}

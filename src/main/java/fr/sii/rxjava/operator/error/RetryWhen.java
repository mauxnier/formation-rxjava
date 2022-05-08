package fr.sii.rxjava.operator.error;

import rx.Observable;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.util.concurrent.TimeUnit.SECONDS;

public class RetryWhen {

    public static void main(String... args) {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5);

        Observable<Integer> retryed = ints1
                .retryWhen(exObs -> exObs
                        .take(3)
                        .delay(1, SECONDS));

        createObserver(10, 350, "Observer", GREEN, retryed);
    }
}

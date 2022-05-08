package fr.sii.rxjava.operator.error;

import rx.Observable;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.range;
import static rx.Observable.timer;

public class RetryWhenNTimes {

    public static final int RETRY_COUNT = 3;

    public static void main(String... args) {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5);

        Observable<Integer> retryed = ints1
                .retryWhen(exObs -> exObs
                        .zipWith(range(1, RETRY_COUNT + 1), (n, i) -> i)
                        .doOnNext(i -> System.out.println("Attente de " + i + " secondes."))
                        .flatMap(i -> timer(i, SECONDS)));

        createObserver(10, 350, "Observer", GREEN, retryed);
    }
}

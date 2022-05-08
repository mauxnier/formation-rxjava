package fr.sii.rxjava.operator.transformation;

import rx.Observable;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.interval;

public class FlatMap2Equivalent {

    public static void main(String... args) throws InterruptedException {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5);

        Observable<String> mapped = ints1
                .flatMap(i -> interval(1, SECONDS)
                        .take(5)
                        .map(t -> i + " / " + t)
                        .map(s -> i + " (" + s + ")"));

        createObserver(10, 350, "Observer 1", GREEN, mapped);
    }
}

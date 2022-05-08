package fr.sii.rxjava.operator.error;

import rx.Observable;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.*;
import static rx.Observable.*;

public class OnErrorResumeNext {

    public static void main(String... args) {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5);

        Observable<Integer> throwables = createColdIntsObservable(200, 10, "Throwable", YELLOW, 666, 666)
                .flatMap(__ -> error(new Throwable()));

        Observable<Integer> exceptions = createColdIntsObservable(400, 10, "Exception", YELLOW, 666, 666)
                .map(__ -> {
                    throw new RuntimeException();
                });

        Observable<Integer> merged = merge(ints1, throwables, exceptions);

        createObserver(10, 350, "Observer", GREEN, merged.onErrorResumeNext(just(100, 1000, 10000)));
    }
}

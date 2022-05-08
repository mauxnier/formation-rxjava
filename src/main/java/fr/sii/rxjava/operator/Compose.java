package fr.sii.rxjava.operator;

import rx.Observable;
import rx.Observable.Transformer;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.*;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.zip;

public class Compose {

    public static void main(String... args) throws InterruptedException {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5);

        Observable<Integer> ints2 = createColdIntsObservable(200, 10, "Ints 2", ORANGE, 10, 15);

        Observable<String> str1 = ints1.compose(factorisation());
        Observable<String> str2 = ints2.compose(factorisation());

        createObserver(10, 350, "Observer 1", GREEN, zip(str1, str2, (a, b) -> a + " / " + b));
    }

    static Transformer<Integer, String> factorisation() {
        return o -> o.delay(1, SECONDS)
                .map(v -> 1000 + v)
                .map(v -> "V-" + v);
    }
}

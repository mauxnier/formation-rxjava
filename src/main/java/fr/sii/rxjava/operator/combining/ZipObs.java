package fr.sii.rxjava.operator.combining;

import rx.Observable;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.lang.System.out;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.interval;
import static rx.Observable.zip;

public class ZipObs {
    public static void main(String... args) {
        Observable<Observable<String>> intObs = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5)
                .map(i -> interval(1, SECONDS)
                        .doOnSubscribe(() -> out.println("Subscribe au 'sous-Observable' de " + i))
                        .take(10)
                        .map(t -> i + "/" + t));

        Observable<String> zipped = zip(intObs, params -> "params: " + asList(params));

        // Selectionnez des valeurs, puis Completed.
        createObserver(10, 350, "Observer", GREEN, zipped);
    }
}

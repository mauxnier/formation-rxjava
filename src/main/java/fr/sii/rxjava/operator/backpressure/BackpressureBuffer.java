package fr.sii.rxjava.operator.backpressure;

import rx.Observable;

import java.util.concurrent.atomic.AtomicLong;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.interval;
import static rx.Observable.range;

public class BackpressureBuffer {
    public static void main(String... args) {
        Observable<Integer> burstInts = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5)
                .flatMap(i -> range(i, 100));

        AtomicLong bufferSize = new AtomicLong();

        Observable<Long> speedLongs = interval(50, MILLISECONDS)
                .doOnNext(__ -> bufferSize.incrementAndGet())
                .onBackpressureBuffer()
                .doOnNext(__ -> bufferSize.decrementAndGet());

        createObserver(10, 350, "Observer", GREEN, burstInts.zipWith(speedLongs, (a, b) -> a + " / " + b));

        createObserver(350, 10, "Observer", YELLOW, interval(1, SECONDS).map(__ -> "Buffer size : " + bufferSize));
    }
}

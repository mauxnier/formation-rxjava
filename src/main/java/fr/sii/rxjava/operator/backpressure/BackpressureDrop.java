package fr.sii.rxjava.operator.backpressure;

import fr.sii.rxjava.util.T2;
import rx.Observable;

import java.util.concurrent.atomic.AtomicLong;

import static fr.sii.rxjava.util.T2.t2;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.interval;
import static rx.Observable.range;

public class BackpressureDrop {

    static final String OK = "ok";
    static final String KO = "KO!!!!!!!!!!!!";

    public static void main(String... args) {
        Observable<Integer> burstInts = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5)
                .flatMap(i -> range(i, 100));

        AtomicLong droppedCount = new AtomicLong();

        Observable<Long> speedLongs = interval(50, MILLISECONDS)
                .doOnNext(__ -> droppedCount.incrementAndGet())
                .onBackpressureDrop()
                .doOnNext(__ -> droppedCount.decrementAndGet());

        Observable<T2<Integer, T2<String, Long>>> zipped = burstInts.zipWith(speedLongs
                        .scan(t2(OK, -1L), (acc, v) -> t2(acc._2 == v - 1 ? "ok" : KO + ", new: " + v + ", prev: " + acc._2 + ", dropped: " + (v - acc._2 - 1), v))
                        .skip(1)
                , (a, b) -> t2(a, b));

        createObserver(10, 350, "Observer", GREEN, zipped
//                .filter(t -> t._2._1.startsWith(KO))
        );

        createObserver(350, 10, "Observer", YELLOW, interval(1, SECONDS).map(__ -> "Dropped count : " + droppedCount));
    }
}

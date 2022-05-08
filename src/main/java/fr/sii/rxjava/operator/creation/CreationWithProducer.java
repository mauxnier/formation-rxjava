package fr.sii.rxjava.operator.creation;

import rx.Observable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static rx.Observable.*;

public class CreationWithProducer {
    public static void main(String... args) {
        Observable<String> ints1 = create(pusher -> {
            AtomicInteger i = new AtomicInteger();

            pusher.setProducer(n -> {
                System.out.println("Le Producer doit produire : " + n + " valeurs.");

                LongStream.range(0, n).forEach(__ -> pusher.onNext("Produced-" + i.incrementAndGet()));
            });
        });

        Observable<Integer> ints2 = createColdIntsObservable(10, 10, "Ints 2", RED, 0, 5)
                .concatMap(i -> range(100 * i, 10));

        createObserver(10, 350, "Observer", GREEN, zip(ints1, ints2, (a, b) -> a + " / " + b));
    }
}

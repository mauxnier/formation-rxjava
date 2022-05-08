package fr.sii.rxjava.pres;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.defer;
import static rx.Observable.interval;

public class Connect {

    public static Observable<String> source() {
        AtomicInteger subscribeCounter = new AtomicInteger();
        return defer(() -> {
            int subscribeNb = subscribeCounter.incrementAndGet();
            return interval(1, SECONDS).
                    map(v -> "Sub-" + subscribeNb + "---value-" + (v + 1));
        });
    }


    public static Action1<String> received(final int observerNb) {
        return v -> System.out.println("Subscriber " + observerNb +
                " : received \"" + v + "\" at " + Instant.now());
    }

    public static void main(String[] args) {
        try {
            final ConnectableObservable<String> co = source().replay(3);
            System.out.println("-- subscribe 1 & 2");

            final Subscription subscription1 = co.subscribe(received(1));
            final Subscription subscription2 = co.subscribe(received(2));

            System.out.println("-- connect");
            co.connect();
            Thread.sleep(6_500);

            System.out.println("-- unsubscribe 1 & 2");
            subscription1.unsubscribe();
            subscription2.unsubscribe();
            Thread.sleep(5_000);

            System.out.println("-- subscribe 3");
            co.subscribe(received(3));
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

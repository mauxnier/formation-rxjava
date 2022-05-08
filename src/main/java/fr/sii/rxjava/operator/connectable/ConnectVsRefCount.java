package fr.sii.rxjava.operator.connectable;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.defer;
import static rx.Observable.interval;

public class ConnectVsRefCount {

    private static SimpleDateFormat DF = new SimpleDateFormat("ss.SSS");

    public static void main(String[] args) throws InterruptedException {
        withConnect();

        System.out.println("=========================================================");

        withRefCount();
    }

    private static void withConnect() throws InterruptedException {
        System.out.println("---------- REPLAY + CONNECT ------------");
        ConnectableObservable<String> co = source().replay(3);
        System.out.println("-- subscribe 1 & 2");

        Subscription subscription1 = co.subscribe(received(1));
        Subscription subscription2 = co.subscribe(received(2));

        System.out.println("-- connect");
        Subscription connectSubscription = co.connect();
        Thread.sleep(6_500);

        System.out.println("-- unsubscribe 1 & 2");
        subscription1.unsubscribe();
        subscription2.unsubscribe();
        Thread.sleep(5_000);

        System.out.println("-- subscribe 3");
        Subscription subscription3 = co.subscribe(received(3));
        Thread.sleep(3_000);

        System.out.println("-- Unsubscribe connectSubscription");
        connectSubscription.unsubscribe();
        Thread.sleep(3_000);

        System.out.println("-- unsubscribe 3");
        subscription3.unsubscribe();
    }

    private static void withRefCount() throws InterruptedException {
        System.out.println("--------- REPLAY + REFCOUNT ---------");

        Observable<String> rc = source().replay(3).refCount();

        System.out.println("-- subscribe 1 & 2");

        Subscription subscription1 = rc.subscribe(received(1));
        Subscription subscription2 = rc.subscribe(received(2));
        Thread.sleep(6_500);

        System.out.println("-- unsubscribe 1 & 2");
        subscription1.unsubscribe();
        subscription2.unsubscribe();
        Thread.sleep(5_000);

        System.out.println("-- subscribe 3");
        Subscription subscription3 = rc.subscribe(received(3));
        Thread.sleep(3_000);

        System.out.println("-- unsubscribe 3");
        subscription3.unsubscribe();
    }

    private static Action1<String> received(int observerNb) {
        return v -> System.out.println("Subscriber " + observerNb + " : received \"" + v + "\" at " + DF.format(new Date()));
    }

    private static Observable<String> source() {
        AtomicInteger subscribeCounter = new AtomicInteger();

        return defer(() -> {
            int subscribeNb = subscribeCounter.incrementAndGet();
            return interval(1, SECONDS)
                    .map(v -> "Sub-" + subscribeNb + "---value-" + (v + 1));
        });
    }
}
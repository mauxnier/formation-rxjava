package fr.sii.rxjava.operator.connectable;

import rx.Observable;

import java.util.concurrent.TimeUnit;

public class ReplayWithFunction {

    public static void main(String... args) throws InterruptedException {
        Observable<String> src = Observable.interval(1, TimeUnit.SECONDS)
                .take(5)
                .map(v -> System.currentTimeMillis() + " / " + v)
                .replay(obs -> obs.map(v -> "First slow use: " + v).concatWith(obs.map(v -> "Fast reuse: " + v)));

        src.subscribe(v -> System.out.println("Subscriber 1 : " + v));
        Thread.sleep(10000);

        src.subscribe(v -> System.out.println("Subscriber 2 : " + v));
        Thread.sleep(10000);
    }
}

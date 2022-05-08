package fr.sii.rxjava.exercice;

import rx.Observable;

import java.util.concurrent.TimeUnit;

public class Ex000_002_NestedSubscribe {

    /**
     * Essayer de comprendre
     **/
    public static void main(String[] args) throws InterruptedException {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(10)
                .subscribe(v1 -> {
                    Observable.interval(1, TimeUnit.SECONDS)
                            .take(10)
                            .subscribe(v2 -> System.out.println("v1 : " + v1 + " v2 : " + v2));
                });
        Thread.sleep(20_000);
    }

}

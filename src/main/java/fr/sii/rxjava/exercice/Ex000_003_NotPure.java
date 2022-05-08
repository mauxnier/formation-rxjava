package fr.sii.rxjava.exercice;

import rx.Observable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Ex000_003_NotPure {


    /**
     * Effet de bord
     **/
    public static void main(String[] args) throws InterruptedException {
        final AtomicInteger resultat = new AtomicInteger(0);
        final AtomicInteger increment = new AtomicInteger(0);

        // On ajoute à resultat la valeur de increment
        Observable.interval(1, TimeUnit.SECONDS)
                .take(10)
                .subscribe(v1 -> {
                    // La fonction n'est pas pure, on prend deux éléments extérieurs,
                    // il suffit qu'une autre personne touche à increment et le résultat
                    // sera totalement modifié
                    System.out.println(resultat.getAndAdd(increment.get()));
                });

        // On fait +1 à increment
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(100)
                .subscribe(v1 -> {
                    increment.incrementAndGet();
                });

        Thread.sleep(20_000);
    }

}

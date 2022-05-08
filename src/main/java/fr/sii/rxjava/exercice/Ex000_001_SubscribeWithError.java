package fr.sii.rxjava.exercice;

import rx.Observable;

public class Ex000_001_SubscribeWithError {

    /**
     * Afficher l'erreur dans la console (sans faire planter l'application)
     **/
    public static void main(String[] args) throws InterruptedException {
        Observable.error(new IllegalStateException("Wow !"))
                .subscribe(System.out::println, Throwable::printStackTrace);
        Thread.sleep(5_000);
    }

}

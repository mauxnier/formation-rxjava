package fr.sii.rxjava.exercice;

import io.reactivex.rxjava3.core.Observable;

public class Ex000_001_SubscribeWithError {

    /**
     * Afficher l'erreur dans la console (sans faire planter l'application)
     **/
    public static void main(String[] args) throws InterruptedException {
        Observable.error(new IllegalStateException("Wow !"));
        Thread.sleep(5_000);
    }

}

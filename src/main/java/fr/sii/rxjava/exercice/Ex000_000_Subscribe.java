package fr.sii.rxjava.exercice;

import io.reactivex.rxjava3.core.Observable;

public class Ex000_000_Subscribe {

    /**
     * Afficher la suite de nombres dans la console
     **/
    public static void main(String[] args) throws InterruptedException {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Thread.sleep(5_000);
    }

}

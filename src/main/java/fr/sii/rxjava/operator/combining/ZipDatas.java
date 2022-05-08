package fr.sii.rxjava.operator.combining;

import fr.sii.rxjava.util.rxplayground.Data;
import rx.Observable;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdDataIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.*;
import static rx.schedulers.Schedulers.computation;
import static rx.schedulers.Schedulers.io;
import static rx.schedulers.Schedulers.newThread;

public class ZipDatas {
    public static void main(String... args) {
        Observable<Data<Integer>> ints1 = createColdDataIntsObservable(10, 10, "Ints 1", RED, 0, 5)
                .observeOn(computation());

        Observable<Data<Integer>> ints2 = createColdDataIntsObservable(200, 10, "Ints 2", ORANGE, 10, 15)
                .observeOn(newThread());

        Observable<Data<String>> zipped = ints1
                .zipWith(ints2, (da, db) -> da.mapWith(db, (a, b) -> a + " // " + b));

        createObserver(10, 350, "Observer", GREEN, zipped);

        // Cliquez sur les sources dans differents sens, observez les threads de creation du resultat...
    }
}

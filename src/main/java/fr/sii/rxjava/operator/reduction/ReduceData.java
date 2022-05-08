package fr.sii.rxjava.operator.reduction;

import fr.sii.rxjava.util.rxplayground.Data;
import rx.Observable;

import static fr.sii.rxjava.util.rxplayground.Data.data;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdDataIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;

public class ReduceData {

    public static void main(String... args) throws InterruptedException {
        Observable<Data<Integer>> ints1 = createColdDataIntsObservable(10, 10, "Ints 1", RED, 0, 5);

        createObserver(10, 350, "Observer 1", GREEN, ints1.reduce(data(1000), (acc, v) -> acc.mapWith(v, Integer::sum)));

        // Clickez sur onCompleted pour voir le résultat.
    }
}

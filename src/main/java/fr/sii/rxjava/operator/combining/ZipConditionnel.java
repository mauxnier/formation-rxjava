package fr.sii.rxjava.operator.combining;

import fr.sii.rxjava.util.T2;
import rx.Observable;

import static fr.sii.rxjava.util.T2.t2;
import static fr.sii.rxjava.util.Utils.toImList;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static rx.Observable.from;
import static rx.Observable.just;

public class ZipConditionnel {
    public static void main(String... args) {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5);

        Observable<Character> toZipWithEven = from("Chars for even".chars().mapToObj(v -> (char) v).collect(toImList()));

        Observable<Character> toZipWithOdd = from("Odd chars".chars().mapToObj(v -> (char) v).collect(toImList()));

        // T2<Value, Continue>
        Observable<T2<String, Boolean>> zipped = ints1
                .groupBy(i -> i % 2)
                .flatMap(group -> group
                        .zipWith(group.getKey() == 0 ? toZipWithEven : toZipWithOdd, (i, c) -> t2(i + " / " + c, true))
                        .concatWith(just(t2("The End", false))))
                .takeWhile(t -> t._2);

        createObserver(10, 350, "Observer", GREEN, zipped);
    }
}

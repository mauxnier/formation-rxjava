package fr.sii.rxjava.operator.subjet;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.*;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static rx.schedulers.Schedulers.computation;
import static rx.schedulers.Schedulers.io;

/**
 * Cliquez rapidement dans les deux fenêtres.
 */
public class Subject {
    public static void main(String... args) {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5)
                .observeOn(io());

        Observable<Integer> ints2 = createColdIntsObservable(200, 10, "Ints 2", ORANGE, 10, 15)
                .observeOn(computation());

        PublishSubject subject = PublishSubject.create();

        ints1.subscribe(subject);
        ints2.subscribe(subject);

        createObserver(10, 350, "Observer", GREEN, subject.map(slowFuncWithThreadDisplay()));
    }

    public static Func1<Integer, Integer> slowFuncWithThreadDisplay() {
        return i -> {
            System.out.println("Debut de travail avec : " + i + ", sur " + currentThread().getName());
            try {
                sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("  Fin de travail avec : " + i + ", sur " + currentThread().getName());

            return i * 10;
        };
    }
}

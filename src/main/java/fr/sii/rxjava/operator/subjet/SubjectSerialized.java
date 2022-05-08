package fr.sii.rxjava.operator.subjet;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

import static fr.sii.rxjava.operator.subjet.Subject.slowFuncWithThreadDisplay;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.*;
import static rx.schedulers.Schedulers.computation;
import static rx.schedulers.Schedulers.io;

/**
 * Cliquez rapidement dans les deux fenêtres.
 */
public class SubjectSerialized {
    public static void main(String... args) {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5)
                .observeOn(io());

        Observable<Integer> ints2 = createColdIntsObservable(200, 10, "Ints 2", ORANGE, 10, 15)
                .observeOn(computation());

        SerializedSubject<Integer, Integer> subject = new SerializedSubject<>(PublishSubject.create());

        ints1.subscribe(subject);
        ints2.subscribe(subject);

        createObserver(10, 350, "Observer", GREEN, subject.map(slowFuncWithThreadDisplay()));
    }
}

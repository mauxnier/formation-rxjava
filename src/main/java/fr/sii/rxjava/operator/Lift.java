package fr.sii.rxjava.operator;

import rx.Observable;
import rx.Subscriber;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;

public class Lift {

    public static void main(String... args) throws InterruptedException {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5);

        Observable<String> lifted = ints1
                .lift(child ->
                        new Subscriber<Integer>(child) {
                            @Override
                            public void onCompleted() {
                                child.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                child.onError(e);
                            }

                            @Override
                            public void onNext(Integer i) {
                                child.onNext((i % 2 == 0 ? "Even" : "Odd") + " : " + i);
                            }
                        });

        createObserver(10, 350, "Observer 1", GREEN, lifted);
    }
}

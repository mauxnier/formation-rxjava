package fr.sii.rxjava.operator.scheduling;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;

public class ObserveOn {

    public static void main(String... args) {
        Executor myThreadExecutor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("my-thread").build());

        Scheduler myThreadScheduler = Schedulers.from(myThreadExecutor);

        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5)
                .observeOn(myThreadScheduler);

        createObserver(10, 350, "Observer", GREEN, ints1);
    }
}

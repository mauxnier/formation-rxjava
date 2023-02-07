package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Timed;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static fr.sii.rxjava.exercice.Ex210_DoubleKeyDuration.timeCmd;
import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainApp.startApp;
import static fr.sii.rxjava.util.Pt.pt;
import static io.reactivex.rxjava3.core.Observable.*;
import static java.lang.Math.max;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Ex700_ClickThenKey implements App, Consts {

    static final Pt TIME_PT = pt(100, 40);

    public static void main(String... args) {
        startApp(new Ex700_ClickThenKey());
    }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    static Command couleurARecopier(Couleur c) {
        return addLog("Couleur à recopier: " + c);
    }

    @Override
    public List<String> description() {
        return singletonList("Clickez puis recopiez le mot...");
    }

    private static Observable<Long> zeroThenAnErrorAfter5Seconds() {
        return just(0L)
                .concatWith(never())
                .timeout(5_001, MILLISECONDS, error(new TimeoutException()));
    }

    private Observable<Long> elapsedTime() {
        return interval(100, MILLISECONDS)
                .timeInterval()
                .map(Timed::time)
                .scan(Long::sum);
    }
}
package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Pt;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static fr.sii.rxjava.util.Cmd.addText;
import static fr.sii.rxjava.util.Cmd.uniq;
import static fr.sii.rxjava.util.MainFrame.App;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static fr.sii.rxjava.util.Pt.pt;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static rx.Observable.interval;

public class Ex210_DoubleKeyDuration implements App {

    static final Pt TIME_PT = pt(100, 100);

    public static void main(String... args) { startApp(new Ex210_DoubleKeyDuration()); }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

        return in.keys()
                .scan(false, (acc, key) -> !acc)
                .switchMap(active ->{
                    if(active){
                        return Observable.interval(40, MILLISECONDS)
                                .timeInterval(scheduler)
                                .scan(0l, (acc, v) -> acc + v.getIntervalInMilliseconds())
                                .map(time -> timeCmd(TIME_PT, time));
                    }
                    return Observable.empty();

                });

//        return in.keys()
//                .window(1, 2)
//                .concatMap(__ -> interval(40, MILLISECONDS)
//                        .timeInterval(scheduler)
//                        .scan(0L, (acc, ti) -> acc + ti.getIntervalInMilliseconds())
//                        .map(sumTI -> timeCmd(TIME_PT, sumTI))
//                        .takeUntil(in.keys()));
    }

    static Command timeCmd(Pt p, long sumTI) {return uniq("time", addText(p, format("%.1f s", sumTI / 1_000.0)));}

    @Override
    public List<String> description() {
        return singletonList("Frappez 2 fois sur le clavier...");
    }
}
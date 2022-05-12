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
        

        return in.mouseLeftClickCount()
                .withLatestFrom(in.mouseXY(), (c, p) -> p)
                .zipWith(fromArray(Couleur.values()).repeat(), T2::t2)
                .concatMap(p_couleur -> {
                    Couleur couleur = p_couleur._2;

                    return combineLatest(
                            elapsedTime(),
                            in.keys().scan("", (acc, c) -> c == BACKSPACE ? acc.substring(0, max(0, acc.length() - 1)) : acc + c),
                            zeroThenAnErrorAfter5Seconds(),
                            (t, txt, __) -> T2.t2(t, txt))
                            .takeUntil((T2 time_txt) -> time_txt._2.equals(couleur.name()))
                            .flatMap(time_txt -> just(
                                    uniq("time", timeCmd(TIME_PT, time_txt._1)),
                                    uniq("txt", Cmd.addText(100, 20, "Frappe: '" + time_txt._2 + "'")))
                                    .startWith(time_txt._2.equals(couleur.name()) ? just(GAGNE) : empty()))
                            .onErrorResumeNext(e -> just(PERDU))
                            .startWithArray(addPt(p_couleur._1, couleur.color), couleurARecopier(couleur));
                });
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
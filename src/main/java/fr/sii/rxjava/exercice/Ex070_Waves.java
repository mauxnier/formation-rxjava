package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.util.List;

import static fr.sii.rxjava.util.Cmd.addPt;
import static fr.sii.rxjava.util.Cmd.uniq;
import static fr.sii.rxjava.util.MainFrame.App;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static java.lang.Math.*;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static rx.Observable.*;

public class Ex070_Waves implements App {

    public static void main(String... args) { startApp(new Ex070_Waves()); }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

        return in.mouseLeftClickCount()
                .withLatestFrom(in.mouseXY(), (c, p) -> p)
                .buffer(2)
                .flatMap(pts -> wave(pts.get(0), pts.get(0).distance(pts.get(1))));
    }

    static Observable<Command> wave(Pt p, double amplitude) {
        String id = p.toString();
        int ptCount = 50;
        int freezeDelay = 50;
        int removeDelay = 10;
        int lifeTime = 5_000;

        return interval(40, MILLISECONDS)
                .take(lifeTime, MILLISECONDS)
                .map(v -> v / 5.0)
                .withLatestFrom(just(50)
                        .concatWith(interval(lifeTime - freezeDelay * (ptCount + 1), freezeDelay, MILLISECONDS)
                                .map(v -> (int) (ptCount - v))
                                .take(ptCount)), T2::t2)
                .flatMap(phase_count -> range(ptCount - phase_count._2, phase_count._2)
                        .map(t -> {
                            double a = t / 5.0 + phase_count._1;
                            return uniq(id + "-" + t, addPt(p.move(t * 10, pow(E, (t - ptCount) * 0.05) * amplitude * (cos(a) + sin(a)))));
                        }))
                .concatWith(interval(removeDelay, MILLISECONDS)
                        .take(ptCount)
                        .map(t -> Cmd.removeUniq(id + "-" + t)));
    }

    @Override
    public List<String> description() {
        return singletonList("Clickez 2 points...");
    }
}
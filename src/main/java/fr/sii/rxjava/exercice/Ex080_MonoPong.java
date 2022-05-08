package fr.sii.rxjava.exercice;

import com.google.common.collect.ImmutableList;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.MainFrame;
import fr.sii.rxjava.util.Pt;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.util.Collections;
import java.util.List;

import static fr.sii.rxjava.util.Cmd.addLine;
import static fr.sii.rxjava.util.Cmd.uniq;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static java.awt.Color.RED;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Ex080_MonoPong implements MainFrame.App {

    static final int LEN = 400;
    static final int MIN = 50;
    static final int MAX = MIN + LEN;
    static final int GAP = 5;

    static final int MIN_IN = MIN + GAP;
    static final int MAX_IN = MAX - GAP;

    static final int RACKET = 100;

    public static void main(String... args) { startApp(new Ex080_MonoPong()); }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

        return in.mouseXY()
                .map(Pt::x)
                .startWith(0)
                .flatMapIterable(x -> racketCmds(x))
                .startWith(
                        addLine(MIN, MIN, MAX, MIN, RED),
                        addLine(MAX, MIN, MAX, MAX, RED),
                        addLine(MAX, MAX, MIN, MAX, RED),
                        addLine(MIN, MAX, MIN, MIN, RED)
                );
    }

    static List<Command> racketCmds(Integer x) {
        int pos = position(x);
        int rev = reversePosition(x);

        return ImmutableList.of(
                uniq("top", addLine(pos, MIN_IN, pos + RACKET, MIN_IN)),
                uniq("bottom", addLine(rev, MAX_IN, rev + RACKET, MAX_IN)),
                uniq("left", addLine(MIN_IN, pos, MIN_IN, pos + RACKET)),
                uniq("right", addLine(MAX_IN, rev, MAX_IN, rev + RACKET)));
    }

    static int position(int x) {
        return min(MAX - GAP - RACKET, max(x, MIN + GAP));
    }

    static int reversePosition(int x) {
        return max(MIN + GAP, min(MAX - GAP - RACKET, MAX - GAP - x));
    }
}
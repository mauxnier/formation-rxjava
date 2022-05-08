package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.MainFrame;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;
import rx.observables.GroupedObservable;
import rx.schedulers.TimeInterval;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static fr.sii.rxjava.util.Cmd.addPt;
import static fr.sii.rxjava.util.Cmd.addText;
import static fr.sii.rxjava.util.Cmd.clear;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static java.awt.Color.BLUE;
import static java.awt.Color.RED;
import static java.lang.Integer.*;
import static java.util.Collections.singletonList;
import static rx.Observable.*;

public class Ex710_NumbersLikeMusicScore implements MainFrame.App {

    public static void main(String... args) { startApp(new Ex710_NumbersLikeMusicScore()); }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    static Command groupTxt(Integer key) {
        return addText(0, yTxt(key), "" + key);
    }

    static List<Command> dot(int key, GroupeInfo gi, ValInfo vi) {
        System.out.println(key + ", " + gi.index + ", " + gi.sumTI + ", " + vi.index + ", " + vi.ti + ", " + vi.sumTI);

        int y = y(key);
        int yTxt = yTxt(key);
        long time = gi.sumTI + vi.sumTI;
        long x = 20 + time / 20;
        boolean first = gi.index + vi.index <= 2;
        Command pt = addPt(x, y, first ? BLUE : RED);

        return first ? of(pt) : of(pt, addText(x - 50, yTxt, "" + (vi.index == 1 ? time : (long) (Long) vi.ti)));
    }

    static int yTxt(int key) {
        return y(key) + 5;
    }

    static int y(int key) {
        return 20 + key * 40;
    }

    @Override
    public List<String> description() {
        return singletonList("Tappez sur le pavé numérique... pas trop rapidement..., click droit pour tout éffacer.");
    }

    public static class GroupeInfo {
        final long sumTI;
        final GroupedObservable<Integer, Integer> groupObs;
        final int index;

        GroupeInfo() {
            this(Long.MIN_VALUE, 0);
        }

        public GroupeInfo(long sumTI, int index) {
            this(sumTI, null, index);
        }

        GroupeInfo(long sumTI, GroupedObservable<Integer, Integer> groupObs, int index) {
            this.sumTI = sumTI;
            this.groupObs = groupObs;
            this.index = index;
        }

        GroupeInfo accumulate(TimeInterval<GroupedObservable<Integer, Integer>> v) {
            return new GroupeInfo(sumTI == Long.MIN_VALUE ? 0 : sumTI + v.getIntervalInMilliseconds(), v.getValue(), index + 1);
        }
    }

    public static class ValInfo {
        final long sumTI;
        final long ti;
        final int index;

        ValInfo() {
            this(0, 0, 0);
        }

        ValInfo(long sumTI, long ti, int index) {
            this.sumTI = sumTI;
            this.ti = ti;
            this.index = index;
        }

        ValInfo accumulate(TimeInterval<Integer> v) {
            return new ValInfo(sumTI + v.getIntervalInMilliseconds(), v.getIntervalInMilliseconds(), index + 1);
        }
    }
}

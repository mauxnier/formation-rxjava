package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.observables.GroupedObservable;
import io.reactivex.rxjava3.schedulers.Timed;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Contract;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainApp.startApp;
import static io.reactivex.rxjava3.core.Observable.*;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Collections.singletonList;
import static javafx.scene.paint.Color.RED;

public class Ex710_NumbersLikeMusicScore implements App {

    public static void main(String... args) {
        startApp(new Ex710_NumbersLikeMusicScore());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

        return in.mouseRightClickCount()
                .zipWith(range(1, MAX_VALUE), (a, b) -> b)
                .startWithItem(0)
                .switchMap(rc -> in.keys()
                        .filter(c -> c >= '0' && c <= '9')
                        .map(String::valueOf)
                        .map(Integer::parseInt)
                        .groupBy(c -> c)
                        .timeInterval(scheduler)
                        .scan(new GroupeInfo(), GroupeInfo::accumulate)
                        .skip(1)
                        .flatMap(groupInfo -> {
                            GroupedObservable<Integer, Integer> groupObs = groupInfo.groupObs;
                            int key = groupObs.getKey();

                            return groupObs
                                    .timeInterval(scheduler)
                                    .scan(new ValInfo(), ValInfo::accumulate)
                                    .skip(1)
                                    .flatMapIterable(valInfo -> dot(key, groupInfo, valInfo))
                                    .startWithItem(groupTxt(key));
                        })
                        .startWith(rc == 0 ? empty() : just(clear()))
                );
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
        Command pt = addPt(x, y, first ? Color.BLUE : RED);

        return first ? of(pt) : of(pt, addText(x - 50, yTxt, "" + (vi.index == 1 ? time : (Long) vi.ti)));
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

        GroupeInfo accumulate(Timed<GroupedObservable<Integer, Integer>> v) {
            return new GroupeInfo(sumTI == Long.MIN_VALUE ? 0 : sumTI + v.time(), v.value(), index + 1);
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

        ValInfo accumulate(Timed<Integer> v) {
            return new ValInfo(sumTI + v.time(), v.time(), index + 1);
        }
    }
}

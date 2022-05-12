package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.List;

import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainApp.startApp;
import static fr.sii.rxjava.util.Pt.pt;
import static io.reactivex.rxjava3.core.Observable.merge;
import static io.reactivex.rxjava3.core.Observable.range;
import static java.util.Collections.singletonList;

public class Ex130_ClickCountBySlice implements App {

    static final int GROUP_SIZE = 50;
    static final int GROUP_COUNT = 10;

    public static void main(String... args) {
        startApp(new Ex130_ClickCountBySlice());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        

        return merge(
                range(0, GROUP_COUNT)
                        .map(Ex130_ClickCountBySlice::groupLine),

                in.keys()
                        .withLatestFrom(in.mouseXY(), (c, p) -> p)
                        .startWith(range(0, GROUP_COUNT).map(i -> pt((0.5 + i) * GROUP_SIZE, 0)))
                        .groupBy(p -> p.x / GROUP_SIZE)
                        .filter(groupPtsObs -> groupPtsObs.getKey() < GROUP_COUNT)
                        .flatMap(groupPtsObs -> groupPtsObs
                                .zipWith(range(0, Integer.MAX_VALUE), (p, i) -> i)
                                .map(sum -> groupText(groupPtsObs.getKey(), sum))));
    }

    static Command groupText(int groupId, int sum) {
        return uniq("group-id-" + groupId, addText(groupId * GROUP_SIZE + 10, 300, "" + sum));
    }

    static Command groupLine(int group) {
        return addLine((group + 1) * GROUP_SIZE, 0, (group + 1) * GROUP_SIZE, 300);
    }

    @Override
    public List<String> description() {
        return singletonList("Deplacez vous dans les colonnes et frappez sur le clavier...");
    }
}
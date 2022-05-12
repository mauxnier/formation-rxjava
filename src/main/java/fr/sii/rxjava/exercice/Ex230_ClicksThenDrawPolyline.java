package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.List;

import static fr.sii.rxjava.util.Cmd.addLine;
import static fr.sii.rxjava.util.Cmd.addPt;
import static fr.sii.rxjava.util.MainApp.startApp;
import static fr.sii.rxjava.util.T2.t2;
import static fr.sii.rxjava.util.Utils.imAppend;
import static fr.sii.rxjava.util.Utils.imListOf;
import static io.reactivex.rxjava3.core.Observable.fromIterable;
import static io.reactivex.rxjava3.core.Observable.merge;
import static java.util.Collections.singletonList;
import static javafx.scene.paint.Color.*;

public class Ex230_ClicksThenDrawPolyline implements App {

    public static void main(String... args) {
        startApp(new Ex230_ClicksThenDrawPolyline());
    }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        

        return Observable.just(t2(RED, ORANGE), t2(GREEN, MAGENTA), t2(BLUE, CYAN))
                .repeat()
                .concatMap(pt_lineColors -> in.mouseLeftClickCount()
                        .takeUntil(c -> c == 2)
                        .withLatestFrom(in.mouseXY(), T2::t2)
                        .scan(t2(0, imListOf(Pt.class)), (acc, clicks_p) -> clicks_p.mapB(p -> imAppend(acc._2, p)))
                        .skip(1)
                        .flatMap(clicks_pts -> merge(
                                fromIterable(clicks_pts._2)
                                        .lastElement()
                                        .toObservable()
                                        .map(p -> addPt(p, pt_lineColors._1)),
                                fromIterable(clicks_pts._2)
                                        .filter(__ -> clicks_pts._1 == 2)
                                        .buffer(2, 1)
                                        .filter(l -> l.size() == 2)
                                        .map(pts -> addLine(pts.get(0), pts.get(1), pt_lineColors._2)))));
    }

    @Override
    public List<String> description() {
        return singletonList("Clickez les points qui formeront une ligne brisée après double click...");
    }
}
package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Pt;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.T2;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.util.List;

import static fr.sii.rxjava.util.Cmd.addLine;
import static fr.sii.rxjava.util.Cmd.addPt;
import static fr.sii.rxjava.util.MainFrame.App;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static fr.sii.rxjava.util.T2.t2;
import static fr.sii.rxjava.util.Utils.imAppend;
import static fr.sii.rxjava.util.Utils.imListOf;
import static java.awt.Color.*;
import static java.util.Collections.singletonList;
import static rx.Observable.*;

public class Ex230_ClicksThenDrawPolyline implements App {

    public static void main(String... args) { startApp(new Ex230_ClicksThenDrawPolyline()); }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

        return just(t2(RED, ORANGE), t2(GREEN, MAGENTA), t2(BLUE, CYAN))
                .repeat()
                .concatMap(pt_lineColors -> in.mouseLeftClickCount()
                        .takeUntil(c -> c == 2)
                        .withLatestFrom(in.mouseXY(), T2::t2)
                        .scan(t2(0, imListOf(Pt.class)), (acc, clicks_p) -> clicks_p.mapB(p -> imAppend(acc._2, p)))
                        .skip(1)
                        .<Command>flatMap(clicks_pts -> merge(
                                from(clicks_pts._2)
                                        .last()
                                        .map(p -> addPt(p, pt_lineColors._1)),
                                from(clicks_pts._2)
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
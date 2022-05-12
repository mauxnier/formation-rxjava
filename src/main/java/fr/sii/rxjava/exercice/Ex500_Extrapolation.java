package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.List;

import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainApp.startApp;
import static io.reactivex.rxjava3.core.Observable.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static javafx.scene.paint.Color.ORANGE;

public class Ex500_Extrapolation implements App {

    public static void main(String... args) {
        startApp(new Ex500_Extrapolation());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        

        return merge(
                in.mouseLeftClickCount()
                        .withLatestFrom(in.mouseXY(), (clicks, pos) -> pos)
                        .distinctUntilChanged()
                        .buffer(2, 1)
                        .zipWith(fromArray(Couleur.values()).repeat(), T2::t2)
                        .switchMap(pts_color -> {
                            List<Pt> pts = pts_color._1;
                            Pt p1 = pts.get(0);
                            Pt p2 = pts.get(1);
                            Color color = pts_color._2.color;

                            return interval(250, MILLISECONDS)
                                    .map(t -> p1.extrapolate(p2, 20 + t * 10))
                                    .map(p -> addPt(p, color))
                                    .startWith(just(addLine(p1, p2, color), addPt(p1, ORANGE), addPt(p2, ORANGE)));
                        }),

                in.mouseRightClickCount().flatMap(e -> just(clear())));
    }

    @Override
    public List<String> description() {
        return Collections.singletonList("Clickez 2 points...");
    }
}
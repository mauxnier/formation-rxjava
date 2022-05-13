package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Contract;

import java.util.List;

import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainApp.startApp;
import static io.reactivex.rxjava3.core.Observable.*;
import static java.lang.Math.*;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Ex260_ColoredSnow implements App {

    static final int RAYS_COUNT = 19;

    public static void main(String... args) {
        startApp(new Ex260_ColoredSnow());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        

        return in.mouseLeftClickCount()
                .withLatestFrom(in.mouseXY(), (c, p) -> p)
                .zipWith(fromArray(Couleur.values()).repeat(), T2::t2)
                .flatMap(p_couleur -> {
                    String id = snowFlakeId(p_couleur._1);
                    return interval(40, MILLISECONDS)
                            .takeWhile(t -> t <= 255L)
                            .flatMapSingle(t -> {
                                Pt center = p_couleur._1.move(0, 2 * t);

                                return range(0, RAYS_COUNT)
                                        .map(i -> snowFlake(center, i, t))
                                        .map(p -> addLine(center, p, fade(p_couleur._2.color, t)))
                                        .toList()
                                        .map(lines -> uniq(id, group(lines)));
                            })
                            .concatWith(just(removeUniq(id)));
                });
    }

    static String snowFlakeId(Pt p) {
        return "snow-flake-" + p;
    }

    static Pt snowFlake(Pt p, int i, long t) {
        double d = 50 + 25 * sin(t / 10.0);
        double a = i * 2 * PI / ((double) RAYS_COUNT);
        return p.move(d * cos(a), d * sin(a));
    }

    static Color fade(Color color, double t) {
        return color.deriveColor(1,1,1,1-(t /255));
    }

    @Override
    public List<String> description() {
        return singletonList("Clickez...");
    }
}
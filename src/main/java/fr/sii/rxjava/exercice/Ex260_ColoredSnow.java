package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Pt;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.T2;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.awt.*;
import java.util.List;

import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainFrame.App;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static java.lang.Math.*;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static rx.Observable.*;

public class Ex260_ColoredSnow implements App {

    static final int RAYS_COUNT = 19;

    public static void main(String... args) { startApp(new Ex260_ColoredSnow()); }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

        return in.mouseLeftClickCount()
                .withLatestFrom(in.mouseXY(), (c, p) -> p)
                .zipWith(from(Couleur.values()).repeat(), T2::t2)
                .flatMap(p_couleur -> {
                    String id = snowFlakeId(p_couleur._1);

                    return interval(40, MILLISECONDS)
                            .takeWhile(t -> t <= 255L)
                            .flatMap(t -> {
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

    static String snowFlakeId(Pt p) {return "snow-flake-" + p;}

    static Pt snowFlake(Pt p, int i, long t) {
        double d = 50 + 25 * sin(t / 10.0);
        double a = i * 2 * PI / ((double) RAYS_COUNT);
        return p.move(d * cos(a), d * sin(a));
    }

    static Color fade(Color color, double t) {
        double r = (255 - color.getRed()) * (t / 255.0) + color.getRed();
        double g = (255 - color.getGreen()) * (t / 255.0) + color.getGreen();
        double b = (255 - color.getBlue()) * (t / 255.0) + color.getBlue();
        return new Color((int) r, (int) g, (int) b);
    }

    @Override
    public List<String> description() {
        return singletonList("Clickez...");
    }
}
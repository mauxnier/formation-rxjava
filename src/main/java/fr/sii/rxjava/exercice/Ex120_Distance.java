package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.List;

import static fr.sii.rxjava.util.MainApp.startApp;
import static io.reactivex.rxjava3.core.Observable.merge;

public class Ex120_Distance implements App {

    public static void main(String... args) {
        startApp(new Ex120_Distance());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        
        return merge(
                new Ex060_ClickPolyLine().commands(in, services, scheduler),

                in.mouseLeftClickCount()
                        .withLatestFrom(in.mouseXY(), (c, p) -> p)
                        .buffer(2, 1)
                        .scan(0.0, (acc, pts) -> acc + pts.get(0).distance(pts.get(1)))
                        .map(d -> "Distance: " + d)
                        .map(Cmd::addLog));
    }
}
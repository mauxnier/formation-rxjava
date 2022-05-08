package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Cmd;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.MainFrame;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import static fr.sii.rxjava.util.MainFrame.startApp;
import static rx.Observable.merge;

public class Ex120_Distance implements MainFrame.App {

    public static void main(String... args) { startApp(new Ex120_Distance()); }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

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
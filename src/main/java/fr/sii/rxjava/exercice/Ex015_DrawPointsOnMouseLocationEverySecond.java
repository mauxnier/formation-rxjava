package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.MainFrame;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static fr.sii.rxjava.util.Cmd.addText;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Ex015_DrawPointsOnMouseLocationEverySecond implements MainFrame.App {

    public static void main(String... args) { startApp(new Ex015_DrawPointsOnMouseLocationEverySecond()); }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

        return Observable.interval(1, SECONDS)
                .withLatestFrom(in.mouseXY(), (i,m) -> m)
                .map(p -> addText(p, "x:" + p.x + ", y:" + p.y));

//        return in.mouseXY()
//                .sample(1, SECONDS)
//                .map(p -> addText(p, "x:" + p.x + ", y:" + p.y));
    }
}
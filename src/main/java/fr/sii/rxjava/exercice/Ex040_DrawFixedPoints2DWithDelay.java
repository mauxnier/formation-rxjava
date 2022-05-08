package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Cmd;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import static fr.sii.rxjava.exercice.Ex006_DrawFixedPoints2D.PTS_BY_SIDE;
import static fr.sii.rxjava.exercice.Ex006_DrawFixedPoints2D.onePt;
import static fr.sii.rxjava.util.MainFrame.App;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.just;
import static rx.Observable.range;

public class Ex040_DrawFixedPoints2DWithDelay implements App {

    public static final int DURATION_MS = 2000;

    public static void main(String... args) { startApp(new Ex040_DrawFixedPoints2DWithDelay()); }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }
}
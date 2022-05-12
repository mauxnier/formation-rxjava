package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.Cmd;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import static fr.sii.rxjava.util.MainApp.startApp;
import static fr.sii.rxjava.util.Pt.pt;
import static io.reactivex.rxjava3.core.Observable.interval;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Ex004_DrawFixedPoints1DAnimated implements App {

    public static void main(String... args) {
        startApp(new Ex004_DrawFixedPoints1DAnimated());
    }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

        return interval(250, MILLISECONDS)
                .take(20)
                .map(x -> pt(100 + 20 * x, 100))
                .map(Cmd::addPt);
    }
}
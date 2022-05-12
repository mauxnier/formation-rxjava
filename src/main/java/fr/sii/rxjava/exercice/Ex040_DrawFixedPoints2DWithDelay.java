package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.Cmd;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import static fr.sii.rxjava.exercice.Ex006_DrawFixedPoints2D.PTS_BY_SIDE;
import static fr.sii.rxjava.exercice.Ex006_DrawFixedPoints2D.onePt;
import static fr.sii.rxjava.util.MainApp.startApp;
import static io.reactivex.rxjava3.core.Observable.just;
import static io.reactivex.rxjava3.core.Observable.range;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Ex040_DrawFixedPoints2DWithDelay implements App {

    public static final int DURATION_MS = 2000;

    public static void main(String... args) {
        startApp(new Ex040_DrawFixedPoints2DWithDelay());
    }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

        return range(0, PTS_BY_SIDE)
                .flatMap(x -> range(0, PTS_BY_SIDE)
                        .flatMap(y -> services.randoms()
                                .take(1)
                                .flatMap(r -> just(y).delay((long) (r * DURATION_MS), MILLISECONDS)))
                        .map(y -> onePt(x, y)))
                .map(Cmd::addPt)
                .concatWith(Observable.<Command>empty().delay(3, SECONDS));
    }
}
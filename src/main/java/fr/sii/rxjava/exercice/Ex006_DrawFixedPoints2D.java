package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import static fr.sii.rxjava.util.MainApp.startApp;
import static fr.sii.rxjava.util.Pt.pt;
import static io.reactivex.rxjava3.core.Observable.range;

public class Ex006_DrawFixedPoints2D implements App {

    static final int PTS_BY_SIDE = 30;

    public static void main(String... args) {
        startApp(new Ex006_DrawFixedPoints2D());
    }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        

        return range(0, PTS_BY_SIDE)
                .flatMap(x -> range(0, PTS_BY_SIDE)
                        .map(y -> onePt(x, y)))
                .map(Cmd::addPt);
    }

    static Pt onePt(int x, int y) {
        return pt(10 + 20 * x, 10 + 20 * y);
    }
}
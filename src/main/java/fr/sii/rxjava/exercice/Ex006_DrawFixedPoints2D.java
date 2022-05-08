package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Cmd;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Pt;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import static fr.sii.rxjava.util.MainFrame.App;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static fr.sii.rxjava.util.Pt.pt;
import static rx.Observable.range;

public class Ex006_DrawFixedPoints2D implements App {

    static final int PTS_BY_SIDE = 30;

    public static void main(String... args) { startApp(new Ex006_DrawFixedPoints2D()); }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

        return range(0, PTS_BY_SIDE)
                .flatMap(x -> range(0, PTS_BY_SIDE)
                        .map(y -> onePt(x, y)))
                .map(Cmd::addPt);
    }

    static Pt onePt(int x, int y) {return pt(10 + 20 * x, 10 + 20 * y);}
}
package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Cmd;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import static fr.sii.rxjava.util.MainFrame.App;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static fr.sii.rxjava.util.Pt.pt;
import static rx.Observable.just;

public class Ex000_DrawFixedPoints implements App {

    public static void main(String... args) { startApp(new Ex000_DrawFixedPoints()); }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return just(pt(100, 100), pt(100, 200))
                .map(Cmd::addPt);
    }
}
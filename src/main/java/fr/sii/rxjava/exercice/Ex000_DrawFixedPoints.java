package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import static fr.sii.rxjava.util.Pt.pt;


public class Ex000_DrawFixedPoints implements App {

    public static void main(String... args) { MainApp.startApp(new Ex000_DrawFixedPoints()); }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.just(pt(100, 100), pt(100, 200))
                .map(Cmd::addPt);
    }
}
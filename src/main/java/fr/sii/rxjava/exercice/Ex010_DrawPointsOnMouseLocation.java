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

public class Ex010_DrawPointsOnMouseLocation implements App {

    public static void main(String... args) { startApp(new Ex010_DrawPointsOnMouseLocation()); }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }
}
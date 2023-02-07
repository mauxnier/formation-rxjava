package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import static fr.sii.rxjava.util.Cmd.addText;
import static fr.sii.rxjava.util.MainApp.startApp;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Ex015_DrawPointsOnMouseLocationEverySecond implements App {

    public static void main(String... args) {
        startApp(new Ex015_DrawPointsOnMouseLocationEverySecond());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }
}
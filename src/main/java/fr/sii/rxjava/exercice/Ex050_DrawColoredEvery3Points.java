package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.Cmd;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.List;

import static fr.sii.rxjava.util.MainApp.startApp;
import static io.reactivex.rxjava3.core.Observable.fromArray;
import static io.reactivex.rxjava3.core.Observable.just;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;


public class Ex050_DrawColoredEvery3Points implements App {

    public static void main(String... args) {
        startApp(new Ex050_DrawColoredEvery3Points());
    }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    @Override
    public List<String> description() {
        return singletonList("Bougez la souris, les couleurs changent tous les 3 points (1 point / 100 ms)");
    }
}
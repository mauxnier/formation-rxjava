package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.List;

import static fr.sii.rxjava.util.MainApp.startApp;

public class Ex020_IsAllMoviesAreAfter70s implements App, Consts {

    public static void main(String... args) {
        startApp(new Ex020_IsAllMoviesAreAfter70s());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    @Override
    public List<String> description() {
        return Collections.singletonList("Tous les films sont ils sortis après 1970 ?");
    }
}
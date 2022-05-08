package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.MainFrame.App;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.util.Collections;
import java.util.List;

import static fr.sii.rxjava.util.MainFrame.startApp;

public class Ex020_IsAllMoviesAreAfter70s implements App, Consts {

    public static void main(String... args) { startApp(new Ex020_IsAllMoviesAreAfter70s()); }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return services.movies().allMovies()
                .all(m -> m.year >= 1970)
                .map(r -> r ? YES : NO);
    }

    @Override
    public List<String> description() {
        return Collections.singletonList("Tous les films sont ils sortis après 1970 ?");
    }
}
package fr.sii.rxjava.exercice;

import fr.sii.rxjava.data.Movie;
import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.Cmd;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.List;

import static fr.sii.rxjava.data.Actor.Sex.f;
import static fr.sii.rxjava.util.MainApp.startApp;
import static java.util.Collections.singletonList;

public class Ex021_ListOfMoviesWithActresses implements App, Consts {

    public static void main(String... args) {
        startApp(new Ex021_ListOfMoviesWithActresses());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    @Override
    public List<String> description() {
        return singletonList("Listez dans l'ordre alphabetique les films avec actrices");
    }
}
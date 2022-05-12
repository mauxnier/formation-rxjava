package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.List;

import static fr.sii.rxjava.util.Cmd.addLog;
import static fr.sii.rxjava.util.MainApp.startApp;
import static io.reactivex.rxjava3.core.Observable.fromIterable;
import static java.util.Collections.singletonList;

public class Ex100_SplitWords implements App {

    public static void main(String... args) {
        startApp(new Ex100_SplitWords());
    }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        

        Observable<Character> keysShared = in.keys().share();

        return keysShared
                .filter(c -> c != ' ')
                .buffer(keysShared.filter(c -> c == ' '))
                .flatMapSingle(cs -> fromIterable(cs).reduce("", (acc, v) -> acc + v))
                .map(s -> addLog("Mot: '" + s + "'"));
    }

    @Override
    public List<String> description() {
        return singletonList("Tapez des mots séparés par des espaces...");
    }
}
package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.util.List;

import static fr.sii.rxjava.util.Cmd.addLog;
import static fr.sii.rxjava.util.MainFrame.App;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static java.util.Collections.singletonList;
import static rx.Observable.from;

public class Ex100_SplitWords implements App {

    public static void main(String... args) { startApp(new Ex100_SplitWords()); }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    @Override
    public List<String> description() {
        return singletonList("Tapez des mots séparés par des espaces...");
    }
}
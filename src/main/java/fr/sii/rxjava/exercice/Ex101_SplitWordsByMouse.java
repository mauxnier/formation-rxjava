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
import static java.util.Collections.singletonList;

public class Ex101_SplitWordsByMouse implements App {

    public static void main(String... args) {
        startApp(new Ex101_SplitWordsByMouse());
    }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        

        return in.keys()
                .buffer(in.mouseLeftClickCount())
                .flatMapSingle(b -> services.reverse(b).reduce("", (acc, v) -> acc + v))
                .map(str -> addLog("Mot: '" + str + "'"));
    }

    @Override
    public List<String> description() {
        return singletonList("Tapez quelques lettres, puis cliquez...");
    }
}
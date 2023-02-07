package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Pt;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.List;

import static fr.sii.rxjava.util.Cmd.addText;
import static fr.sii.rxjava.util.Cmd.uniq;
import static fr.sii.rxjava.util.MainApp.startApp;
import static fr.sii.rxjava.util.Pt.pt;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Ex210_DoubleKeyDuration implements App {

    static final Pt TIME_PT = pt(100, 100);

    public static void main(String... args) {
        startApp(new Ex210_DoubleKeyDuration());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    static Command timeCmd(Pt p, long sumTI) {
        return uniq("time", addText(p, format("%.1f s", sumTI / 1_000.0)));
    }

    @Override
    public List<String> description() {
        return singletonList("Frappez 2 fois sur le clavier...");
    }
}
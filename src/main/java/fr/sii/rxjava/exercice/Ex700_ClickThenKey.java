package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.TimeInterval;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static fr.sii.rxjava.exercice.Ex210_DoubleKeyDuration.timeCmd;
import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainFrame.App;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static fr.sii.rxjava.util.Pt.pt;
import static java.lang.Math.max;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static rx.Observable.*;

public class Ex700_ClickThenKey implements App, Consts {

    static final Pt TIME_PT = pt(100, 40);

    public static void main(String... args) { startApp(new Ex700_ClickThenKey()); }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    static Command couleurARecopier(Couleur c) {
        return addLog("Couleur à recopier: " + c);
    }

    @Override
    public List<String> description() {
        return singletonList("Clickez puis recopiez le mot...");
    }
}
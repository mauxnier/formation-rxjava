package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Pt;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.T2;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.util.List;

import static fr.sii.rxjava.util.Cmd.addLine;
import static fr.sii.rxjava.util.Cmd.addPt;
import static fr.sii.rxjava.util.MainFrame.App;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static fr.sii.rxjava.util.T2.t2;
import static fr.sii.rxjava.util.Utils.imAppend;
import static fr.sii.rxjava.util.Utils.imListOf;
import static java.awt.Color.*;
import static java.util.Collections.singletonList;
import static rx.Observable.*;

public class Ex230_ClicksThenDrawPolyline implements App {

    public static void main(String... args) { startApp(new Ex230_ClicksThenDrawPolyline()); }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    @Override
    public List<String> description() {
        return singletonList("Clickez les points qui formeront une ligne brisée après double click...");
    }
}
package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.List;

import static fr.sii.rxjava.util.Cmd.addLine;
import static fr.sii.rxjava.util.Cmd.addPt;
import static fr.sii.rxjava.util.MainApp.startApp;
import static fr.sii.rxjava.util.T2.t2;
import static fr.sii.rxjava.util.Utils.imAppend;
import static fr.sii.rxjava.util.Utils.imListOf;
import static io.reactivex.rxjava3.core.Observable.fromIterable;
import static io.reactivex.rxjava3.core.Observable.merge;
import static java.util.Collections.singletonList;
import static javafx.scene.paint.Color.*;

public class Ex230_ClicksThenDrawPolyline implements App {

    public static void main(String... args) {
        startApp(new Ex230_ClicksThenDrawPolyline());
    }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    @Override
    public List<String> description() {
        return singletonList("Clickez les points qui formeront une ligne brisée après double click...");
    }
}
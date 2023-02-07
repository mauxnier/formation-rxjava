package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.List;

import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainApp.startApp;
import static io.reactivex.rxjava3.core.Observable.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static javafx.scene.paint.Color.ORANGE;

public class Ex500_Extrapolation implements App {

    public static void main(String... args) {
        startApp(new Ex500_Extrapolation());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    @Override
    public List<String> description() {
        return Collections.singletonList("Clickez 2 points...");
    }
}
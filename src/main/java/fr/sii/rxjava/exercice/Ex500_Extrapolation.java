package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static java.awt.Color.ORANGE;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static rx.Observable.*;

public class Ex500_Extrapolation implements MainFrame.App {

    public static void main(String... args) { startApp(new Ex500_Extrapolation()); }

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
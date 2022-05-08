package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.MainFrame;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.util.List;

import static fr.sii.rxjava.util.MainFrame.startApp;
import static java.util.Collections.singletonList;

public class Ex1000_FlappyBird implements MainFrame.App, Consts {

    private static final int H = 784;
    private static final int W = 755;

    public static void main(String... args) {
        startApp(new Ex1000_FlappyBird());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    @Override
    public List<String> description() {
        return singletonList("Espace pour commencer...");
    }
}

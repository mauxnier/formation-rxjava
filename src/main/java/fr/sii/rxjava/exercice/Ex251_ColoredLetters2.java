package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.List;

import static fr.sii.rxjava.exercice.Ex250_ColoredLetters.endTyping;
import static fr.sii.rxjava.exercice.Ex250_ColoredLetters.startTyping;
import static fr.sii.rxjava.util.Cmd.addText;
import static fr.sii.rxjava.util.Cmd.uniq;
import static fr.sii.rxjava.util.MainApp.startApp;
import static io.reactivex.rxjava3.core.Observable.*;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Ex251_ColoredLetters2 implements App {

    public static void main(String... args) {
        startApp(new Ex251_ColoredLetters2());
    }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    @Override
    public List<String> description() {
        return singletonList("Clickez puis tapez, attendre un peu pour terminer la saisie...");
    }
}
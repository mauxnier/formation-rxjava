package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainApp.startApp;
import static io.reactivex.rxjava3.core.Observable.fromArray;
import static io.reactivex.rxjava3.core.Observable.just;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Ex250_ColoredLetters implements App {

    public static void main(String... args) {
        startApp(new Ex250_ColoredLetters());
    }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    static Command endTyping(Couleur couleur) {
        return addLog("Fin de saisie en " + couleur.name().toUpperCase() + ", clickez pour une autre saisie");
    }

    static Command startTyping(Couleur couleur) {
        return addLog("Commencez à taper en " + couleur.name().toUpperCase() + "...");
    }

    @Override
    public List<String> description() {
        return singletonList("Clickez puis tapez, attendre un peu pour terminer la saisie...");
    }
}
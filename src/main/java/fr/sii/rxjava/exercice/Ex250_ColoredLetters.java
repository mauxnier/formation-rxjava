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

import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainFrame.App;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.from;
import static rx.Observable.just;

public class Ex250_ColoredLetters implements App {

    public static void main(String... args) { startApp(new Ex250_ColoredLetters()); }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    static Command endTyping(Couleur couleur) {return addLog("Fin de saisie en " + couleur.name().toUpperCase() + ", clickez pour une autre saisie");}

    static Command startTyping(Couleur couleur) {return addLog("Commencez à taper en " + couleur.name().toUpperCase() + "...");}

    @Override
    public List<String> description() {
        return singletonList("Clickez puis tapez, attendre un peu pour terminer la saisie...");
    }
}
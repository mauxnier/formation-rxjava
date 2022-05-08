package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Cmd;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.util.List;

import static fr.sii.rxjava.util.MainFrame.App;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static java.util.Arrays.asList;
import static rx.Observable.amb;

public class Ex052_LettersOrFigures implements App {

    public static void main(String... args) { startApp(new Ex052_LettersOrFigures()); }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    @Override
    public List<String> description() {
        return asList("Tapez une lettre ou un chiffre... ", "Puis n'accepter que cette catégorie (lettre ou chiffre)");
    }
}
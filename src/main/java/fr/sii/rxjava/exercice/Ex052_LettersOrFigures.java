package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.Cmd;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.List;

import static fr.sii.rxjava.util.MainApp.startApp;
import static io.reactivex.rxjava3.core.Observable.ambArray;
import static java.util.Arrays.asList;

public class Ex052_LettersOrFigures implements App {

    public static void main(String... args) {
        startApp(new Ex052_LettersOrFigures());
    }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        

        return ambArray(
                in.keys().filter(Character::isLetter),
                in.keys().filter(Character::isDigit))
                .map(Cmd::addLog);
    }

    @Override
    public List<String> description() {
        return asList("Tapez une lettre ou un chiffre... ", "Puis n'accepter que cette catégorie (lettre ou chiffre)");
    }
}
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
import static io.reactivex.rxjava3.core.Observable.merge;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;


public class Ex150_DrawPointBetweenLettersAndFigures implements App {

    public static void main(String... args) {
        startApp(new Ex150_DrawPointBetweenLettersAndFigures());
    }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

        return merge(in.mouseXY()
                .sample(100, MILLISECONDS)
                .window(in.keys().filter(Character::isLetter), character -> in.keys().filter(Character::isDigit)))
                .map(Cmd::addPt);
    }

    @Override
    public List<String> description() {
        return asList("Bougez la souris...", "Une lettre pour commencer à dessiner", "Un chiffre pour arreter de dessiner");
    }
}
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
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static rx.Observable.*;

public class Ex050_DrawColoredEvery3Points implements App {

    public static void main(String... args) { startApp(new Ex050_DrawColoredEvery3Points()); }

    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

        return
                in.mouseXY()
                        .sample(100, MILLISECONDS).zipWith(
                from(Couleur.values())
                        .map(Couleur::color)
                        .concatMap(c -> just(c).repeat(3))
                        .repeat(),
                Cmd::addPt);
    }

    @Override
    public List<String> description() {
        return singletonList("Bougez la souris, les couleurs changent tous les 3 points (1 point / 100 ms)");
    }
}
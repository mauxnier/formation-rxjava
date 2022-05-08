package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.MainFrame.App;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static java.awt.Color.MAGENTA;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.just;
import static rx.Observable.merge;

public class Ex017_DrawPointsWithClear implements App {

    public static void main(String... args) { startApp(new Ex017_DrawPointsWithClear()); }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return merge(
                in.mouseRightClickCount()
                        .map(e -> clear()),

                in.mouseXY()
                        .throttleLast(1, SECONDS)
                        .flatMap(p -> just(
                                addLine(p, p.move(50, 20)),
                                addPt(p),
                                addPt(p.move(50, 20)),
                                addText(p, p.x + "," + p.y),
                                addLog("Mouse position: " + p))),

                in.mouseXY()
                        .map(p -> uniq("cursor", group(
                                addText(300, 20, "Cursor: " + p.x + "," + p.y, MAGENTA),
                                addPt(p.x, 15, Couleur.values()[(p.x / 50) % Couleur.values().length].color)))),

                in.keys()
                        .map(c -> addLog("Typed: " + c)));
    }
}
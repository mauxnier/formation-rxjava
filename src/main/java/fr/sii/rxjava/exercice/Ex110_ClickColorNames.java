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

import static com.google.common.base.Preconditions.checkState;
import static fr.sii.rxjava.util.Cmd.addPt;
import static fr.sii.rxjava.util.Cmd.addText;
import static fr.sii.rxjava.util.MainFrame.App;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static java.util.Collections.singletonList;
import static rx.Observable.from;
import static rx.Observable.range;

public class Ex110_ClickColorNames implements App {

    public static void main(String... args) { startApp(new Ex110_ClickColorNames()); }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    static Command addLetter(Couleur couleur, Pt p, Character c, Integer index) {
        checkState(index >= 1);
        return addText(p.move(15 * index, -3 * index), "" + c, couleur.color);
    }

    @Override
    public List<String> description() {
        return singletonList("Clickez pour faire afficher le nom des couleurs...");
    }
}
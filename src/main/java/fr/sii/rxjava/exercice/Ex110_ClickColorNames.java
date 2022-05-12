package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static fr.sii.rxjava.util.Cmd.addPt;
import static fr.sii.rxjava.util.Cmd.addText;
import static fr.sii.rxjava.util.MainApp.startApp;
import static io.reactivex.rxjava3.core.Observable.fromArray;
import static io.reactivex.rxjava3.core.Observable.range;
import static java.util.Collections.singletonList;

public class Ex110_ClickColorNames implements App {

    public static void main(String... args) {
        startApp(new Ex110_ClickColorNames());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        

        return in.mouseLeftClickCount()
                .withLatestFrom(in.mouseXY(), (c, p) -> p)
                .zipWith(fromArray(Couleur.values()).repeat(), T2::t2)
                .flatMap(p_couleur -> {
                    Couleur couleur = p_couleur._2;
                    Pt p = p_couleur._1;

                    return services.stringCapitalise(couleur.name())
                            .zipWith(range(1, 100), (chaR, i) -> addLetter(couleur, p, chaR, i))
                            .startWithItem(addPt(p, couleur.color));
                });
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
package fr.sii.rxjava.exercice;

import fr.sii.rxjava.data.Director;
import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.T2;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import static fr.sii.rxjava.data.Actor.byNameThenFirstName;
import static fr.sii.rxjava.data.Director.directorbyNameThenFirstName;
import static fr.sii.rxjava.exercice.Ex410_ActorsWithCommonDirector.commonDescription;
import static fr.sii.rxjava.exercice.Ex410_ActorsWithCommonDirector.directorAndActorsFormater;
import static fr.sii.rxjava.util.Cmd.addLog;
import static fr.sii.rxjava.util.MainApp.startApp;
import static fr.sii.rxjava.util.T2.t2;
import static io.reactivex.rxjava3.core.Observable.zip;
import static java.util.Comparator.comparing;

public class Ex410_ActorsWithCommonDirectorV2 implements App, Consts {

    static final Comparator<T2<Director, ?>> cmpT2 = comparing(T2::_1, directorbyNameThenFirstName);

    public static void main(String... args) {
        startApp(new Ex410_ActorsWithCommonDirectorV2());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    @Override
    public List<String> description() {
        return commonDescription();
    }
}
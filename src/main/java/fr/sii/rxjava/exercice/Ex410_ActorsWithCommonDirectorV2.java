package fr.sii.rxjava.exercice;

import fr.sii.rxjava.data.Director;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.MainFrame.App;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.T2;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import static fr.sii.rxjava.data.Actor.byNameThenFirstName;
import static fr.sii.rxjava.data.Director.directorbyNameThenFirstName;
import static fr.sii.rxjava.exercice.Ex410_ActorsWithCommonDirector.commonDescription;
import static fr.sii.rxjava.exercice.Ex410_ActorsWithCommonDirector.directorAndActorsFormater;
import static fr.sii.rxjava.util.Cmd.addLog;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static fr.sii.rxjava.util.T2.t2;
import static java.util.Comparator.comparing;
import static rx.Observable.zip;

public class Ex410_ActorsWithCommonDirectorV2 implements App, Consts {

    static final Comparator<T2<Director, ?>> cmpT2 = comparing(T2::_1, directorbyNameThenFirstName);

    public static void main(String... args) { startApp(new Ex410_ActorsWithCommonDirectorV2()); }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return services.movies().allMovies()
                .flatMap(m -> zip(
                        services.movies().getMovieDirector(m.title),
                        services.movies().getMovieActors(m.title).toList(), T2::t2))
                .groupBy(T2::_1, T2::_2)
                .flatMap(actorsObs -> actorsObs
                        .collect(() -> new TreeSet<>(byNameThenFirstName), TreeSet::addAll)
                        .map(sortedActors -> t2(actorsObs.getKey(), sortedActors)))
                .toSortedList(cmpT2::compare)
                .flatMap(Observable::from)
                .map(dir_actors -> addLog(directorAndActorsFormater(dir_actors._1, dir_actors._2)));
    }

    @Override
    public List<String> description() {
        return commonDescription();
    }
}
package fr.sii.rxjava.exercice;

import com.google.common.base.Joiner;
import fr.sii.rxjava.data.Director;
import fr.sii.rxjava.data.Movie;
import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.Comparator;
import java.util.List;

import static fr.sii.rxjava.data.Director.directorbyNameThenFirstName;
import static fr.sii.rxjava.data.Movie.CHRONO;
import static fr.sii.rxjava.util.MainApp.startApp;
import static fr.sii.rxjava.util.T2.t2;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;

public class Ex400_ListOfMoviesByDirector implements App, Consts {

    static final Comparator<T2<Director, List<Movie>>> cmpListSize = comparing(t -> t._2.size());
    static final Comparator<T2<Director, List<Movie>>> cmpT2 = cmpListSize.reversed().thenComparing(t -> t._1, directorbyNameThenFirstName);

    public static void main(String... args) {
        startApp(new Ex400_ListOfMoviesByDirector());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        

        return services.movies().allMovies()
                .flatMap(m -> services.movies().getMovieDirector(m.title).map(d -> t2(d, m)))
                .groupBy(T2::_1, T2::_2)
                .flatMapSingle(movieObs -> movieObs
                        .toSortedList(CHRONO::compare)
                        .map(l -> t2(movieObs.getKey(), l)))
                .toSortedList(cmpT2::compare)
                .flatMapObservable(Observable::fromIterable)
                .map(Ex400_ListOfMoviesByDirector::directorAndMoviesFormater)
                .map(Cmd::addLog);
    }

    static String directorAndMoviesFormater(T2<Director, List<Movie>> dirAndMovies) {
        String s = dirAndMovies._1 + " (" + dirAndMovies._2.size() + " movies) : " + Joiner.on(", ").join(dirAndMovies._2);
        System.out.println(s);
        return s;
    }

    @Override
    public List<String> description() {
        return asList("Listez les films (dans l'ordre chronologique) par realisateur",
                "Realisateurs triés par nombre de films, puis par nom, puis par prenom");
    }
}
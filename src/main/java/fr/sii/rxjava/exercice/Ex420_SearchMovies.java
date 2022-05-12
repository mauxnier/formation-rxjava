package fr.sii.rxjava.exercice;

import fr.sii.rxjava.data.Director;
import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;

import java.util.Comparator;
import java.util.List;

import static fr.sii.rxjava.data.Director.directorbyNameThenFirstName;
import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainApp.startApp;
import static fr.sii.rxjava.util.Pt.pt;
import static fr.sii.rxjava.util.Utils.imListOf;
import static io.reactivex.rxjava3.core.Observable.fromIterable;
import static io.reactivex.rxjava3.core.Observable.range;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.max;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class Ex420_SearchMovies implements App, Consts {

    static final Comparator<T2<Director, ?>> cmpT2 = comparing(T2::_1, directorbyNameThenFirstName);

    static final String QUERY_ID = "queryId";
    static final Pt QUERY_PT = pt(10, 20);
    static final String QUERY_PREFIX = "Search: ";

    static final Pt ACTOR_PT = pt(10, 40);
    static final Pt DIRECTOR_PT = pt(250, 40);
    static final Pt MOVIE_PT = pt(500, 40);

    static final List<Command> INIT_CMD = asList(
            queryCmd(""),
            addText(ACTOR_PT, "Actors:", RED),
            addText(DIRECTOR_PT, "Directors:", RED),
            addText(MOVIE_PT, "Movies:", RED));

    public static void main(String... args) {
        startApp(new Ex420_SearchMovies());
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        // return Observable.never();

        Observable<String> query = in.keys()
                .map(Character::toLowerCase)
                .scan("", (acc, c) -> c == BACKSPACE ? acc.substring(0, max(0, acc.length() - 1)) : acc + c)
                .skip(1)
                .share();

        return Observable.mergeArray(
                fromIterable(INIT_CMD),

                query.map(Ex420_SearchMovies::queryCmd),

                query.compose(displayDatasAtPt(services.movies().allActors(), ACTOR_PT)),
                query.compose(displayDatasAtPt(services.movies().allDirectors(), DIRECTOR_PT)),
                query.compose(displayDatasAtPt(services.movies().allMovies(), MOVIE_PT)));
    }

    static Command queryCmd(String q) {
        return uniq(QUERY_ID, addText(QUERY_PT, QUERY_PREFIX + q, BLUE));
    }

    static <T> ObservableTransformer<String, Command> displayDatasAtPt(Observable<T> src, Pt p) {
        // return Observable.never();

        return queryObs -> queryObs
                .debounce(500, MILLISECONDS)
                .distinctUntilChanged()
                .switchMap(debounced -> src
                        .filter(a -> a.toString().toLowerCase().contains(debounced))
                        .zipWith(range(1, MAX_VALUE), (a, i) -> resultCmd(p, a, i))
                        .scan(imListOf(Command.class), Utils::imAppend)
                        .map(cmds -> resultGroupCmd(p, cmds)));
    }

    static Command resultGroupCmd(Pt p, List<Command> cmds) {
        return uniq("" + p, group(cmds));
    }

    static <T> Command resultCmd(Pt p, T value, int index) {
        return addText(p.move(0, index * 20), index + " - " + value);
    }

    @Override
    public List<String> description() {
        return asList("Cherche dans la base des films (gerer le backspace)", "Refactorez en utilisant compose(...)");
    }
}
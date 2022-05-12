package fr.sii.rxjava.util;

import com.google.common.collect.Lists;
import fr.sii.rxjava.data.MovieServices;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.Contract;

import static com.google.common.base.Preconditions.checkState;
import static fr.sii.rxjava.util.Utils.slow;
import static fr.sii.rxjava.util.Utils.toImList;

public interface Services {

    /**
     * @return des valeurs aléatoires [0; 1]
     */
    Observable<Double> randoms();

    Observable<String> words();

    @Contract(pure = true)
    default Observable<Character> stringCapitalise(String s) {
        checkState(s.length() > 0);

        return Observable.fromIterable(s.chars().mapToObj(v -> (char) v).collect(toImList()))
                .subscribeOn(Schedulers.io())
                .skip(1)
                .compose(slow())
                .startWith(Observable.just(s.charAt(0)).map(Character::toUpperCase));
    }

    @Contract(pure = true)
    default Observable<Character> chars(String s) {
        return Observable.fromIterable(s.chars().mapToObj(v -> (char) v).collect(toImList()))
                .subscribeOn(Schedulers.io())
                .compose(slow());
    }

    @Contract(pure = true)
    default <T> Observable<T> reverse(Iterable<T> values) {
        return Observable.fromIterable(values)
                .subscribeOn(Schedulers.io())
                .toList()
                .flatMapObservable(l -> Observable.fromIterable(Lists.reverse(l)))
                .compose(slow());
    }

    Observable<Character> randomChars();

    default MovieServices movies() {
        return new MovieServices();
    }
}

package fr.sii.rxjava.util;

import com.google.common.collect.Lists;
import fr.sii.rxjava.data.MovieServices;
import org.jetbrains.annotations.Contract;
import rx.Observable;

import static com.google.common.base.Preconditions.checkState;
import static fr.sii.rxjava.util.Utils.slow;
import static fr.sii.rxjava.util.Utils.toImList;
import static rx.Observable.from;
import static rx.Observable.just;
import static rx.schedulers.Schedulers.io;

public interface Services {

    /**
     * @return des valeurs aléatoires [0; 1]
     */
    Observable<Double> randoms();

    Observable<String> words();

    @Contract(pure = true)
    default Observable<Character> stringCapitalise(String s) {
        checkState(s.length() > 0);

        return from(s.chars().mapToObj(v -> (char) v).collect(toImList()))
                .subscribeOn(io())
                .skip(1)
                .map(value -> new Character((char) value))
                .compose(slow())
                .startWith(just(s.charAt(0)).map(Character::toUpperCase));
    }

    @Contract(pure = true)
    default Observable<Character> chars(String s) {
        return from(s.chars().mapToObj(v -> (char) v).collect(toImList()))
                .subscribeOn(io())
                .map(value -> new Character((char) value))
                .compose(slow());
    }

    @Contract(pure = true)
    default <T> Observable<T> reverse(Iterable<T> values) {
        return from(values)
                .subscribeOn(io())
                .toList()
                .flatMapIterable(Lists::reverse)
                .compose(slow());
    }

    Observable<Character> randomChars();

    default MovieServices movies() {return new MovieServices();}
}

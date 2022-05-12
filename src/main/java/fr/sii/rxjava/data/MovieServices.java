package fr.sii.rxjava.data;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.Contract;

import static fr.sii.rxjava.data.Datas.*;
import static fr.sii.rxjava.util.Utils.dataSlow;

public class MovieServices {
    @Contract(pure = true)
    public Observable<Actor> getMovieActors(String movieTitle) {
        return Observable.fromIterable(MOVIE_ACTORS.get(movieTitle))
                .subscribeOn(Schedulers.io())
                .compose(dataSlow());
    }

    @Contract(pure = true)
    public Observable<Actor> allActors() {
        return Observable.fromIterable(ACTORS)
                .subscribeOn(Schedulers.io())
                .compose(dataSlow());
    }

    @Contract(pure = true)
    public Observable<Director> allDirectors() {
        return Observable.fromIterable(DIRECTORS)
                .subscribeOn(Schedulers.io())
                .compose(dataSlow());
    }

    @Contract(pure = true)
    public Observable<Movie> allMovies() {
        return Observable.fromIterable(MOVIES)
                .subscribeOn(Schedulers.io())
                .compose(dataSlow());
    }

    @Contract(pure = true)
    public Observable<Movie> getMovie(String title) {
        return Observable.fromIterable(MOVIES)
                .subscribeOn(Schedulers.io())
                .filter(m -> m.title.equalsIgnoreCase(title))
                .firstElement()
                .toObservable()
                .compose(dataSlow());
    }

    @Contract(pure = true)
    public Observable<Director> getMovieDirector(String movieTitle) {
        return Observable.just(MOVIE_DIRECTOR.get(movieTitle))
                .subscribeOn(Schedulers.io())
                .compose(dataSlow());
    }
}

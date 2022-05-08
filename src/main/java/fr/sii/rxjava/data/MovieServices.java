package fr.sii.rxjava.data;

import org.jetbrains.annotations.Contract;
import rx.Observable;

import static fr.sii.rxjava.data.Datas.*;
import static fr.sii.rxjava.util.Utils.dataSlow;
import static rx.Observable.from;
import static rx.Observable.just;
import static rx.schedulers.Schedulers.io;

public class MovieServices {
    @Contract(pure = true)
    public Observable<Actor> getMovieActors(String movieTitle) {
        return from(MOVIE_ACTORS.get(movieTitle))
                .subscribeOn(io())
                .compose(dataSlow());
    }

    @Contract(pure = true)
    public Observable<Actor> allActors() {
        return from(ACTORS)
                .subscribeOn(io())
                .compose(dataSlow());
    }

    @Contract(pure = true)
    public Observable<Director> allDirectors() {
        return from(DIRECTORS)
                .subscribeOn(io())
                .compose(dataSlow());
    }

    @Contract(pure = true)
    public Observable<Movie> allMovies() {
        return from(MOVIES)
                .subscribeOn(io())
                .compose(dataSlow());
    }

    @Contract(pure = true)
    public Observable<Movie> getMovie(String title) {
        return from(MOVIES)
                .subscribeOn(io())
                .filter(m -> m.title.equalsIgnoreCase(title))
                .first()
                .compose(dataSlow());
    }

    @Contract(pure = true)
    public Observable<Director> getMovieDirector(String movieTitle) {
        return just(MOVIE_DIRECTOR.get(movieTitle))
                .subscribeOn(io())
                .compose(dataSlow());
    }
}

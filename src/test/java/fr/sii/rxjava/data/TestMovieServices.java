package fr.sii.rxjava.data;

import java.util.List;

import static fr.sii.rxjava.data.Datas.*;

public class TestMovieServices {

    public static List<Actor> allActors() { return ACTORS; }

    public static List<Director> allDirectors() { return DIRECTORS; }

    public static List<Movie> allMovies() { return MOVIES; }

    public static List<Actor> getMovieActors(String movieTitle) { return MOVIE_ACTORS.get(movieTitle); }

    public static Director getMovieDirector(String movieTitle) { return MOVIE_DIRECTOR.get(movieTitle); }
}

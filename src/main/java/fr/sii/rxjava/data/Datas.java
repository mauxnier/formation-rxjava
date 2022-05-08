package fr.sii.rxjava.data;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.util.*;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.ImmutableMap.copyOf;
import static fr.sii.rxjava.data.Actor.Sex.f;
import static fr.sii.rxjava.data.Actor.actor;
import static fr.sii.rxjava.data.Director.director;
import static fr.sii.rxjava.data.Movie.movie;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

public class Datas {

    private static final Set<Movie> MUT_MOVIES = new HashSet<>();
    private static final Set<Director> MUT_DIRECTORS = new HashSet<>();
    private static final Set<Actor> MUT_ACTORS = new HashSet<>();
    private static final Map<String, Director> MUT_MOVIE_DIRECTOR = new HashMap<>();
    private static final Map<String, List<Actor>> MUT_MOVIE_ACTOR = new HashMap<>();

    static final ImmutableList<Movie> MOVIES;
    static final ImmutableList<Director> DIRECTORS;
    static final ImmutableList<Actor> ACTORS;
    static final ImmutableMap<String, Director> MOVIE_DIRECTOR;
    static final ImmutableMap<String, List<Actor>> MOVIE_ACTORS;

    static {
        add(movie("ocean's twelve", 2004),
                director("steven", "soderbergh"),
                actor("brad", "pitt"),
                actor("catherine", "zeta-jones", f),
                actor("george", "clooney"),
                actor("julia", "roberts", f),
                actor("andy", "garcia"),
                actor("matt", "damon"));

        add(movie("the monuments men", 2014),
                director("george", "clooney"),
                act("damon"),
                act("clooney"),
                actor("bill", "murray"),
                actor("jean", "dujardin"),
                actor("cate", "blanchett", f),
                actor("john", "goodman"));

        add(movie("the big lebowski", 1998),
                director("joel", "coen"),
                act("goodman"),
                actor("steve", "buscemi"),
                actor("jeff", "bridges"));

        add(movie("fargo", 1996),
                dir("coen"),
                act("buscemi"));

        add(movie("no country for old men", 2007),
                dir("coen"),
                actor("tommy", "lee jones"),
                actor("javier", "bardem"),
                actor("woody", "harrelson"));

        add(movie("minority report", 2002),
                director("steven", "spielberg"),
                actor("tom", "cruise"));

        add(movie("die hard", 1988),
                director("john", "mctiernan"),
                actor("bruce", "willis"));

        add(movie("die hard: with _1 vengeance", 1995),
                dir("mctiernan"),
                act("willis"));

        add(movie("die hard 2", 1990),
                director("renny", "harlin"),
                act("willis"));

        add(movie("oblivion", 2013),
                director("joseph", "kosinski"),
                act("cruise"),
                actor("morgan", "freeman"));

        add(movie("seven", 1995),
                director("david", "fincher"),
                act("freeman"),
                act("pitt"));

        add(movie("fight club", 1999),
                dir("fincher"),
                actor("edward", "norton"),
                act("pitt"));

        add(movie("the curious case of benjamin button", 2008),
                dir("fincher"),
                act("blanchett"),
                act("pitt"));

        add(movie("babel", 2008),
                director("alejandro", "inarritu"),
                act("blanchett"),
                act("pitt"));

        add(movie("birdman", 2014),
                dir("inarritu"),
                act("norton"),
                actor("michael", "keaton"),
                actor("emma", "stone", f),
                actor("noami", "watts", f));

        add(movie("biutiful", 2010),
                dir("inarritu"),
                act("bardem"));

        add(movie("erin brockovich", 2000),
                dir("soderbergh"),
                act("roberts"),
                actor("aaron", "eckhart"));

        add(movie("saving private ryan", 1998),
                dir("spielberg"),
                act("damon"),
                actor("tom", "hanks"));

        add(movie("the terminal", 2004),
                dir("spielberg"),
                act("hanks"),
                act("zeta-jones"));

        add(movie("bridge of spies", 2015),
                dir("spielberg"),
                act("hanks"));

        // Private Mutable -> Public Immutable.
        MOVIES = copyOf(MUT_MOVIES);
        DIRECTORS = copyOf(MUT_DIRECTORS);
        ACTORS = copyOf(MUT_ACTORS);
        MOVIE_ACTORS = copyOf(MUT_MOVIE_ACTOR);
        MOVIE_DIRECTOR = copyOf(MUT_MOVIE_DIRECTOR);
    }

    private static Actor act(String name) {
        return MUT_ACTORS.stream().filter(a -> a.name.equalsIgnoreCase(name)).findFirst().get();
    }

    private static Director dir(String name) {
        return MUT_DIRECTORS.stream().filter(a -> a.name.equalsIgnoreCase(name)).findFirst().get();
    }

    private static void add(Movie movie, Director director, Actor... actors) {
        MUT_MOVIES.add(movie);
        MUT_DIRECTORS.add(director);
        stream(actors).forEach(MUT_ACTORS::add);
        MUT_MOVIE_DIRECTOR.put(movie.title, director);
        MUT_MOVIE_ACTOR.put(movie.title, asList(actors));
    }

    public static void main(String... args) throws IOException {
        System.out.println(Joiner.on("\n").withKeyValueSeparator("=").join(MOVIE_DIRECTOR));
        System.out.println("----------");
        System.out.println(Joiner.on("\n").withKeyValueSeparator("=").join(MOVIE_ACTORS));
    }
}

package fr.sii.rxjava.exercice;

import fr.sii.rxjava.data.Movie;
import fr.sii.rxjava.util.Cmd;
import org.junit.jupiter.api.Test;

import static fr.sii.rxjava.data.Actor.Sex.f;
import static fr.sii.rxjava.data.TestMovieServices.allMovies;
import static fr.sii.rxjava.data.TestMovieServices.getMovieActors;
import static java.util.concurrent.TimeUnit.MINUTES;

public class Ex021_ListOfMoviesWithActressesTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex021_ListOfMoviesWithActresses.class);

        inputPusher.advanceTimeBy(1, MINUTES);

        assertStepValues(allMovies().stream()
                .filter(m -> getMovieActors(m.title).stream().anyMatch(a -> a.sex == f))
                .map(Movie::title)
                .sorted()
                .map(Cmd::addLog));
        assertCompleted();
    }
}
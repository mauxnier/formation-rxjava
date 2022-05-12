package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Cmd;
import fr.sii.rxjava.util.T2;
import org.junit.jupiter.api.Test;

import static fr.sii.rxjava.data.Movie.CHRONO;
import static fr.sii.rxjava.data.TestMovieServices.allMovies;
import static fr.sii.rxjava.data.TestMovieServices.getMovieDirector;
import static fr.sii.rxjava.exercice.Ex400_ListOfMoviesByDirector.cmpT2;
import static fr.sii.rxjava.exercice.Ex400_ListOfMoviesByDirector.directorAndMoviesFormater;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class Ex400_ListOfMoviesByDirectorTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex400_ListOfMoviesByDirector.class);

        inputPusher.advanceTimeBy(1, MINUTES);

        assertStepValues(allMovies().stream()
                .collect(groupingBy(m -> getMovieDirector(m.title)))
                .entrySet().stream()
                .map(T2::fromEntry)
                .sorted(cmpT2)
                .map(t -> t.mapB(l -> l.stream().sorted(CHRONO).collect(toList())))
                .map(t -> directorAndMoviesFormater(t))
                .map(Cmd::addLog));
        assertCompleted();
    }
}
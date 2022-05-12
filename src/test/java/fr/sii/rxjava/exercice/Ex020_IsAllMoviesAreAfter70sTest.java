package fr.sii.rxjava.exercice;

import org.junit.jupiter.api.Test;

import static fr.sii.rxjava.data.TestMovieServices.allMovies;
import static java.util.concurrent.TimeUnit.MINUTES;

public class Ex020_IsAllMoviesAreAfter70sTest extends BaseTest implements Consts {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex020_IsAllMoviesAreAfter70s.class);

        inputPusher.advanceTimeBy(1, MINUTES);

        assertStepValues(allMovies().stream().allMatch(m -> m.year >= 1970) ? YES : NO);
        assertCompleted();
    }
}
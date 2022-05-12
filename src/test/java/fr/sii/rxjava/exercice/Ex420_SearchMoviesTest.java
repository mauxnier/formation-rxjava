package fr.sii.rxjava.exercice;

import org.junit.Ignore;
import org.junit.Test;

import static fr.sii.rxjava.data.TestMovieServices.allActors;
import static fr.sii.rxjava.exercice.Ex420_SearchMovies.*;
import static fr.sii.rxjava.util.Utils.imList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Ex420_SearchMoviesTest extends BaseTest {

    static final String DUJARDIN = "dujardin";

    @Test
    @Ignore
    public void testCommands() throws Exception {
        testSetupFor(Ex420_SearchMovies.class);

        {
            assertStepValues(INIT_CMD);
        }

        {
            inputPusher.pushKey("d")
                    .advanceTimeBy(400, MILLISECONDS)
                    .pushKey("u")
                    .advanceTimeBy(400, MILLISECONDS)
                    .pushKey("jardin");

            assertStepValues(
                    range(1, DUJARDIN.length() + 1)
                            .mapToObj(i -> DUJARDIN.substring(0, i))
                            .map(q -> queryCmd(q))
                            .collect(toList()));
        }

        { // Après recherche.
            inputPusher.advanceTimeBy(1, MINUTES);

            assertStepValues(
                    imList(resultGroupCmd(ACTOR_PT, imList()),
                            resultGroupCmd(DIRECTOR_PT, imList()),
                            resultGroupCmd(MOVIE_PT, imList()),
                            resultGroupCmd(ACTOR_PT, allActors().stream()
                                    .filter(a -> a.toString().toLowerCase().contains(DUJARDIN))
                                    .map(a -> resultCmd(ACTOR_PT, a, 1))
                                    .collect(toList()))));

            testSubscriber.assertNotComplete();
        }
    }
}
package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Cmd;
import fr.sii.rxjava.util.InputPusher;
import fr.sii.rxjava.util.T2;
import org.junit.Test;

import static fr.sii.rxjava.data.Actor.byNameThenFirstName;
import static fr.sii.rxjava.data.TestMovieServices.*;
import static fr.sii.rxjava.exercice.Ex410_ActorsWithCommonDirector.cmpT2;
import static fr.sii.rxjava.exercice.Ex410_ActorsWithCommonDirector.directorAndActorsFormater;
import static fr.sii.rxjava.util.T2.t2;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class Ex410_ActorsWithCommonDirectorTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex410_ActorsWithCommonDirector.class);

        check(this, inputPusher);
    }

  static  void check(BaseTest test ,InputPusher inputPusher) {
        inputPusher.advanceTimeBy(1, MINUTES);

       test. assertStepValues(allMovies().stream()
                .map(m -> t2(getMovieDirector(m.title), getMovieActors(m.title)))
                .collect(groupingBy(T2::_1))
                .entrySet().stream()
                .map(T2::fromEntry)
                .map(t -> t.mapB(l -> l.stream().flatMap(v -> v._2.stream()).distinct().sorted(byNameThenFirstName).collect(toList())))
                .sorted(cmpT2)
                .map(dirAndActs -> directorAndActorsFormater(dirAndActs._1, dirAndActs._2))
                .map(Cmd::addLog));
       test. assertCompleted();
    }
}
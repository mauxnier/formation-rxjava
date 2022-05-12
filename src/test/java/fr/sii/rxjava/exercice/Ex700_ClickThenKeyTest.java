package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.cmds.AddLogCmd;
import fr.sii.rxjava.util.cmds.UniqCmd;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static fr.sii.rxjava.exercice.Couleur.rouge;
import static fr.sii.rxjava.exercice.Ex700_ClickThenKey.*;
import static io.reactivex.rxjava3.core.Observable.fromIterable;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

public class Ex700_ClickThenKeyTest extends BaseTest {

    @Test
    @Ignore
    public void testPerdu() throws Exception {
        testSetupFor(Ex700_ClickThenKey.class);

        inputPusher.pushMouseXY(10, 10)
                .pushMouseLeftClick(1)
                .advanceTimeBy(10, SECONDS);

        asList("time", "txt").forEach(id ->
                assertEquals(50, fromIterable(testSubscriber.values())
                        .ofType(UniqCmd.class)
                        .filter(c -> id.equals(c.id))
                        .count()
                        .blockingGet().intValue()));

        List<AddLogCmd> logs = fromIterable(testSubscriber.values())
                .ofType(AddLogCmd.class)
                .toList()
                .blockingGet();

        System.out.println(logs);

        assertEquals(asList(couleurARecopier(rouge), PERDU), logs);
    }

    @Test
    @Ignore
    public void testGagne() throws Exception {
        testSetupFor(Ex700_ClickThenKey.class);

        inputPusher.pushMouseXY(10, 10)
                .pushMouseLeftClick(1)
                .pushKey("rouge")
                .advanceTimeBy(1, SECONDS);

        List<AddLogCmd> logs = fromIterable(testSubscriber.values())
                .ofType(AddLogCmd.class)
                .toList()
                .blockingGet();

        assertEquals(asList(couleurARecopier(rouge), GAGNE), logs);
    }
}

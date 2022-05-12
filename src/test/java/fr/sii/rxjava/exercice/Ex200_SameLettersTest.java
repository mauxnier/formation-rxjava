package fr.sii.rxjava.exercice;

import org.junit.Test;

import static fr.sii.rxjava.util.Cmd.addLog;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Ex200_SameLettersTest extends BaseTest implements Consts {

    @Test
    public void testPerdu() throws Exception {
        testSetupFor(Ex200_SameLetters.class, testServices -> testServices.setRandomChars("gagnant"));

        inputPusher.advanceTimeBy(100, MILLISECONDS)
                .pushKey("g").advanceTimeBy(1, SECONDS)
                .pushKey("a").advanceTimeBy(1, SECONDS)
                .pushKey("g").advanceTimeBy(1, SECONDS)
                .pushKey("x").advanceTimeBy(1, SECONDS);

        assertStepValues(addLog("g"), addLog("a"), addLog("g"), PERDU);
        testSubscriber.assertNotComplete();
    }

    @Test
    public void testGagne() throws Exception {
        testSetupFor(Ex200_SameLetters.class, testServices -> testServices.setRandomChars("gagnant"));

        inputPusher.advanceTimeBy(100, MILLISECONDS)
                .pushKey("g").advanceTimeBy(1, SECONDS)
                .pushKey("a").advanceTimeBy(1, SECONDS)
                .pushKey("g").advanceTimeBy(1, SECONDS)
                .pushKey("n").advanceTimeBy(1, SECONDS)
                .pushKey("a").advanceTimeBy(1, SECONDS);

        assertStepValues(addLog("g"), addLog("a"), addLog("g"), addLog("n"), addLog("a"), GAGNE);
        testSubscriber.assertNotComplete();
    }
}
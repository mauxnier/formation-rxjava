package fr.sii.rxjava.exercice;

import org.junit.jupiter.api.Test;

import static fr.sii.rxjava.util.Cmd.addLog;

public class Ex052_LettersOrFiguresTest extends BaseTest {

    @Test
    public void testLetters() throws Exception {
        testSetupFor(Ex052_LettersOrFigures.class);

        inputPusher.pushKey("a1b2c3");

        assertStepValues(addLog("a"), addLog("b"), addLog("c"));
        testSubscriber.assertNotComplete();
    }

    @Test
    public void testFigers() throws Exception {
        testSetupFor(Ex052_LettersOrFigures.class);

        inputPusher.pushKey("1a2b3c");

        assertStepValues(addLog("1"), addLog("2"), addLog("3"));
        testSubscriber.assertNotComplete();
    }
}
package fr.sii.rxjava.exercice;

import org.junit.Test;

import static fr.sii.rxjava.util.Cmd.addLog;

public class Ex100_SplitWordsTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex100_SplitWords.class);

        inputPusher.pushKey("abc def ");

        assertStepValues(addLog("Mot: 'abc'"), addLog("Mot: 'def'"));
        testSubscriber.assertNotComplete();
    }
}
package fr.sii.rxjava.exercice;

import org.junit.Test;

import static fr.sii.rxjava.util.Cmd.addLog;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Ex101_SplitWordsByMouseTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex101_SplitWordsByMouse.class);

        inputPusher
                .pushMouseLeftClick(1)
                .pushKey("abc")
                .pushMouseLeftClick(1)
                .pushKey("123")
                .pushMouseLeftClick(1)
                .advanceTimeBy(10, SECONDS);

        assertStepValues(addLog("Mot: ''"), addLog("Mot: 'cba'"), addLog("Mot: '321'"));
        testSubscriber.assertNotComplete();
    }
}
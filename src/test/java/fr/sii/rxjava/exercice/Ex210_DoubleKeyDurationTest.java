package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.cmds.Command;
import org.junit.Test;

import static fr.sii.rxjava.exercice.Ex210_DoubleKeyDuration.TIME_PT;
import static fr.sii.rxjava.exercice.Ex210_DoubleKeyDuration.timeCmd;
import static io.reactivex.rxjava3.core.Observable.fromIterable;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.Assert.assertEquals;

public class Ex210_DoubleKeyDurationTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex210_DoubleKeyDuration.class);

        verify(1000);
        verify(500);
        verify(2000);
    }

    void verify(int delayTime) {
        inputPusher.pushKey("a")
                .advanceTimeBy(delayTime, MILLISECONDS)
                .pushKey("b");

        Command lastTimeCmd = fromIterable(testSubscriber.values())
                .lastElement().blockingGet();

        System.out.println(lastTimeCmd);

        assertEquals(timeCmd(TIME_PT, delayTime), lastTimeCmd);
    }
}
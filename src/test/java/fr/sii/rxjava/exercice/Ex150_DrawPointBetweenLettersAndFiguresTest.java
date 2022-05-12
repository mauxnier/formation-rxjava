package fr.sii.rxjava.exercice;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static fr.sii.rxjava.util.Cmd.addPt;

public class Ex150_DrawPointBetweenLettersAndFiguresTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex150_DrawPointBetweenLettersAndFigures.class);

        inputPusher
                .pushMouseXY(10, 10).advanceTimeBy(100, TimeUnit.MILLISECONDS)
                .pushMouseXY(11, 10).advanceTimeBy(100, TimeUnit.MILLISECONDS)
                .pushKey("a")
                .pushMouseXY(10, 20).advanceTimeBy(100, TimeUnit.MILLISECONDS)
                .pushMouseXY(11, 20).advanceTimeBy(100, TimeUnit.MILLISECONDS)
                .pushMouseXY(12, 20).advanceTimeBy(100, TimeUnit.MILLISECONDS)
                .pushKey("0")
                .pushMouseXY(11, 30).advanceTimeBy(100, TimeUnit.MILLISECONDS)
                .pushKey("b")
                .pushMouseXY(10, 40).advanceTimeBy(100, TimeUnit.MILLISECONDS)
                .pushMouseXY(11, 40).advanceTimeBy(100, TimeUnit.MILLISECONDS)
                .pushMouseXY(12, 40).advanceTimeBy(100, TimeUnit.MILLISECONDS)
                .pushKey("1")
                .pushMouseXY(10, 50).advanceTimeBy(100, TimeUnit.MILLISECONDS);

        assertStepValues(addPt(10, 20), addPt(11, 20), addPt(12, 20), addPt(10, 40), addPt(11, 40), addPt(12, 40));
        testSubscriber.assertNotComplete();
    }
}
package fr.sii.rxjava.exercice;

import org.junit.Test;

import static fr.sii.rxjava.util.Cmd.addPt;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Ex050_DrawColoredEvery3PointsTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex050_DrawColoredEvery3Points.class);

        inputPusher
                .pushMouseXY(10, 10).advanceTimeBy(100, MILLISECONDS)
                .pushMouseXY(11, 10).advanceTimeBy(100, MILLISECONDS)
                .pushMouseXY(12, 10).advanceTimeBy(100, MILLISECONDS)
                .pushMouseXY(13, 10).advanceTimeBy(100, MILLISECONDS)
                .pushMouseXY(14, 10).advanceTimeBy(100, MILLISECONDS)
                .pushMouseXY(15, 10).advanceTimeBy(100, MILLISECONDS)
                .pushMouseXY(16, 10).advanceTimeBy(100, MILLISECONDS);

        assertStepValues(
                addPt(10, 10, Couleur.values()[0].color),
                addPt(11, 10, Couleur.values()[0].color),
                addPt(12, 10, Couleur.values()[0].color),
                addPt(13, 10, Couleur.values()[1].color),
                addPt(14, 10, Couleur.values()[1].color),
                addPt(15, 10, Couleur.values()[1].color),
                addPt(16, 10, Couleur.values()[2].color));
        testSubscriber.assertNoTerminalEvent();
    }
}
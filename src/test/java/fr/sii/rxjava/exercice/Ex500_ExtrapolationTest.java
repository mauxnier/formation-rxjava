package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Pt;
import org.junit.Ignore;
import org.junit.Test;

import static fr.sii.rxjava.util.Cmd.addLine;
import static fr.sii.rxjava.util.Cmd.addPt;
import static fr.sii.rxjava.util.Pt.pt;
import static java.util.concurrent.TimeUnit.SECONDS;
import static javafx.scene.paint.Color.ORANGE;

public class Ex500_ExtrapolationTest extends BaseTest {

    @Test
    @Ignore
    public void testCommands() throws Exception {
        testSetupFor(Ex500_Extrapolation.class);

        Pt p1 = pt(10, 10);
        Pt p2 = pt(20, 10);
        Pt p3 = pt(20, 30);

        inputPusher
                .pushMouseXY(p1).pushMouseLeftClick(1)
                .pushMouseXY(p2).pushMouseLeftClick(1)
                .advanceTimeBy(1, SECONDS)
                .pushMouseXY(p3).pushMouseLeftClick(1)
                .advanceTimeBy(1, SECONDS);

        Couleur c1 = Couleur.values()[0];
        Couleur c2 = Couleur.values()[1];

        assertStepValues(
                addLine(p1, p2, c1.color),
                addPt(p1, ORANGE),
                addPt(p2, ORANGE),
                addPt(p1.extrapolate(p2, 20), c1.color),
                addPt(p1.extrapolate(p2, 30), c1.color),
                addPt(p1.extrapolate(p2, 40), c1.color),
                addPt(p1.extrapolate(p2, 50), c1.color),
                addLine(p2, p3, c2.color),
                addPt(p2, ORANGE),
                addPt(p3, ORANGE),
                addPt(p2.extrapolate(p3, 20), c2.color),
                addPt(p2.extrapolate(p3, 30), c2.color),
                addPt(p2.extrapolate(p3, 40), c2.color),
                addPt(p2.extrapolate(p3, 50), c2.color));
        testSubscriber.assertNotComplete();
    }
}
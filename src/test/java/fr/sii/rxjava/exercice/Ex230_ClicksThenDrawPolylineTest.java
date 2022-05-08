package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Pt;
import org.junit.Test;

import static fr.sii.rxjava.util.Cmd.addLine;
import static fr.sii.rxjava.util.Cmd.addPt;
import static fr.sii.rxjava.util.Pt.pt;
import static java.awt.Color.*;

public class Ex230_ClicksThenDrawPolylineTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex230_ClicksThenDrawPolyline.class);

        Pt p1 = pt(10, 10);
        Pt p2 = pt(20, 10);
        Pt p3 = pt(20, 30);

        Pt p4 = pt(10, 110);
        Pt p5 = pt(20, 110);
        Pt p6 = pt(20, 130);

        inputPusher
                .pushMouseXY(p1).pushMouseLeftClick(1)
                .pushMouseXY(p2).pushMouseLeftClick(1)
                .pushMouseXY(p3).pushMouseLeftClick(2) // Fin de première polyline.
                .pushMouseXY(p4).pushMouseLeftClick(1)
                .pushMouseXY(p5).pushMouseLeftClick(1)
                .pushMouseXY(p6).pushMouseLeftClick(2);

        assertStepValues(
                addPt(p1, RED),
                addPt(p2, RED),
                addPt(p3, RED),
                addLine(p1, p2, ORANGE),
                addLine(p2, p3, ORANGE),
                addPt(p4, GREEN),
                addPt(p5, GREEN),
                addPt(p6, GREEN),
                addLine(p4, p5, MAGENTA),
                addLine(p5, p6, MAGENTA));

        testSubscriber.assertNoTerminalEvent();
    }
}
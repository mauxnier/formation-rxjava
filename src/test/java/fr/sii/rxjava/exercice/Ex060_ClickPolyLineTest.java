package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Pt;
import org.junit.Test;

import static fr.sii.rxjava.util.Cmd.addLine;
import static fr.sii.rxjava.util.Pt.pt;

public class Ex060_ClickPolyLineTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex060_ClickPolyLine.class);

        Pt p1 = pt(10, 100);
        Pt p2 = pt(20, 200);
        Pt p3 = pt(30, 300);

        inputPusher.pushMouseXY(p1)
                .pushMouseLeftClick(1)
                .pushMouseXY(p2)
                .pushMouseLeftClick(1)
                .pushMouseXY(p3)
                .pushMouseLeftClick(1);

        assertStepValues(addLine(p1, p2), addLine(p2, p3));
        testSubscriber.assertNoTerminalEvent();
    }
}
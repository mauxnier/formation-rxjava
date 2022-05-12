package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Pt;
import org.junit.jupiter.api.Test;

import static fr.sii.rxjava.util.Cmd.addLine;
import static fr.sii.rxjava.util.Cmd.addLog;
import static fr.sii.rxjava.util.Pt.pt;

public class Ex120_DistanceTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex120_Distance.class);

        Pt p1 = pt(10, 10);
        Pt p2 = pt(20, 10);
        Pt p3 = pt(20, 30);

        inputPusher.pushMouseXY(p1)
                .pushMouseLeftClick(1)
                .pushMouseXY(p2)
                .pushMouseLeftClick(1)
                .pushMouseXY(p3)
                .pushMouseLeftClick(1);

        assertStepValues(
                addLog("Distance: 0.0"),
                addLine(p1, p2),
                addLog("Distance: " + p1.distance(p2)),
                addLine(p2, p3),
                addLog("Distance: " + (p1.distance(p2) + p2.distance(p3))));
        testSubscriber.assertNotComplete();
    }
}
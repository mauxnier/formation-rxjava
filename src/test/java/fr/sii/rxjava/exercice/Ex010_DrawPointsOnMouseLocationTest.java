package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Pt;
import org.junit.Test;

import static fr.sii.rxjava.util.Cmd.addPt;
import static fr.sii.rxjava.util.Pt.pt;

public class Ex010_DrawPointsOnMouseLocationTest extends BaseTest {

    @Test
    public void testApp() throws Exception {
        testSetupFor(Ex010_DrawPointsOnMouseLocation.class);

        Pt p = pt(100, 200);
        inputPusher.pushMouseXY(p);

        assertStepValues(addPt(p));
        testSubscriber.assertNotComplete();
    }
}
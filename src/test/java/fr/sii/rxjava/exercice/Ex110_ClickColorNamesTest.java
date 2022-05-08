package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Pt;
import org.junit.Test;

import static fr.sii.rxjava.exercice.Ex110_ClickColorNames.addLetter;
import static fr.sii.rxjava.util.Cmd.addPt;
import static fr.sii.rxjava.util.Pt.pt;
import static java.lang.Character.toUpperCase;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Ex110_ClickColorNamesTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex110_ClickColorNames.class);

        Pt p1 = pt(100, 100);
        Pt p2 = pt(200, 200);
        Pt p3 = pt(300, 300);

        inputPusher
                .pushMouseXY(p1).pushMouseLeftClick(1).advanceTimeBy(100, MILLISECONDS)
                .pushMouseXY(p2).pushMouseLeftClick(1).advanceTimeBy(100, MILLISECONDS)
                .pushMouseXY(p3).pushMouseLeftClick(1)
                .advanceTimeBy(10, SECONDS);

        Couleur c1 = Couleur.values()[0];
        Couleur c2 = Couleur.values()[1];
        Couleur c3 = Couleur.values()[2];

        int i1 = 0;
        int i2 = 0;
        int i3 = 0;

        assertStepValues(
                addPt(p1, c1.color),
                addLetter(c1, p1, toUpperCase(c1.name().charAt(i1++)), i1),
                addPt(p2, c2.color),
                addLetter(c2, p2, toUpperCase(c2.name().charAt(i2++)), i2),
                addPt(p3, c3.color),
                addLetter(c3, p3, toUpperCase(c3.name().charAt(i3++)), i3),
                addLetter(c1, p1, c1.name().charAt(i1++), i1),
                addLetter(c2, p2, c2.name().charAt(i2++), i2),
                addLetter(c3, p3, c3.name().charAt(i3++), i3),
                addLetter(c1, p1, c1.name().charAt(i1++), i1),
                addLetter(c2, p2, c2.name().charAt(i2++), i2),
                addLetter(c3, p3, c3.name().charAt(i3++), i3),
                addLetter(c1, p1, c1.name().charAt(i1++), i1),
                addLetter(c2, p2, c2.name().charAt(i2++), i2),
                addLetter(c3, p3, c3.name().charAt(i3++), i3),
                addLetter(c1, p1, c1.name().charAt(i1++), i1));
        testSubscriber.assertNoTerminalEvent();
    }
}
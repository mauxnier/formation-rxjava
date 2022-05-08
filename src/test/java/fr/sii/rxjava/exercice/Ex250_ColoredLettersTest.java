package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Pt;
import org.junit.Test;

import static fr.sii.rxjava.exercice.Ex250_ColoredLetters.endTyping;
import static fr.sii.rxjava.exercice.Ex250_ColoredLetters.startTyping;
import static fr.sii.rxjava.util.Cmd.addText;
import static fr.sii.rxjava.util.Cmd.uniq;
import static fr.sii.rxjava.util.Pt.pt;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Ex250_ColoredLettersTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex250_ColoredLetters.class);

        verify(this);
    }

    static void verify(BaseTest test) {
        Pt p1 = pt(10, 10);
        Pt p2 = pt(20, 10);

        test.inputPusher
                .pushMouseXY(p1)
                .pushMouseLeftClick(1)
                .pushKey("abc")
                .advanceTimeBy(10, SECONDS)
                .pushMouseXY(p2)
                .pushMouseLeftClick(1)
                .pushKey("def")
                .advanceTimeBy(10, SECONDS);

        Couleur c1 = Couleur.values()[0];
        Couleur c2 = Couleur.values()[1];

        test.assertStepValues(
                startTyping(c1),
                uniq(p1.toString(), addText(p1, "a", c1.color)),
                uniq(p1.toString(), addText(p1, "ab", c1.color)),
                uniq(p1.toString(), addText(p1, "abc", c1.color)),
                endTyping(c1),
                startTyping(c2),
                uniq(p2.toString(), addText(p2, "d", c2.color)),
                uniq(p2.toString(), addText(p2, "de", c2.color)),
                uniq(p2.toString(), addText(p2, "def", c2.color)),
                endTyping(c2));
        test.testSubscriber.assertNoTerminalEvent();
    }
}
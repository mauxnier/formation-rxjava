package fr.sii.rxjava.exercice;

import com.google.common.collect.ImmutableList;
import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.MainFrame;
import fr.sii.rxjava.util.Pt;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.awt.*;
import java.util.List;

import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static fr.sii.rxjava.util.Pt.pt;
import static fr.sii.rxjava.util.Utils.imList;
import static java.awt.Color.*;
import static java.lang.Math.*;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.joining;
import static rx.Observable.*;

public class Ex720_Pendu implements MainFrame.App, Consts {

    static final String LETTER_ID = "letter";

    static final Pt LETTER_PT = pt(100, 150);
    static final int STEP = 25;

    static final ImmutableList<Observable<Command>> BODY_PARTS = imList(gallows(), head(), body(), leftArm(), rightArm(), leftLeg(), rightLeg());

    public static void main(String... args) { startApp(new Ex720_Pendu()); }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    @Override
    public List<String> description() {
        return singletonList("Cliquez pour commencer...");
    }

    static String discover(String mot, String chars) { return mot.chars().mapToObj(c -> (chars.indexOf(c) == -1 ? '_' : (char) c) + " ").collect(joining()); }

    static Observable<Command> text(String mot, String keys) {return just(uniq(LETTER_ID, addText(LETTER_PT, discover(mot, keys))));}

    static Observable<Command> gallows() {
        return merge(
                line("gallows-base", pt(400, 400), pt(500, 400)),
                line("gallows-vertical", pt(450, 400), pt(450, 10)),
                line("gallows-high", pt(450, 10), pt(280, 10)),
                line("gallows-reinforcement", pt(450, 60), pt(400, 10)),
                line("gallows-rope", pt(300, 10), pt(300, 50)));
    }

    static Observable<Command> body() { return line("body", pt(300, 110), pt(300, 230)); }

    static Observable<Command> leftArm() { return member("left-arm", pt(300, 130), pt(270, 210)); }

    static Observable<Command> rightArm() { return member("right-arm", pt(300, 130), pt(330, 210)); }

    static Observable<Command> leftLeg() { return member("left-leg", pt(300, 230), pt(290, 310)); }

    static Observable<Command> rightLeg() { return member("right-leg", pt(300, 230), pt(310, 310)); }

    static Observable<Command> head() {
        return merge(
                circle(pt(300, 80), 30),
                line("mouth", pt(290, 95), pt(310, 95)))
                .concatWith(just(addPt(290, 65, BLUE), addPt(310, 65, BLUE)))
                .concatWith(delayedPt(300, 80, RED));
    }

    static Observable<Command> member(String id, Pt a, Pt b) {return concat(line(id, a, b), delayedPt(b.x, b.y, BLACK));}

    static Observable<Command> delayedPt(int x, int y, Color c) { return just(addPt(x, y, c)).delay(500, MILLISECONDS); }

    static Observable<Command> circle(Pt center, int radius) {
        return interval(40, MILLISECONDS)
                .take(STEP + 1)
                .map(t -> center.move(radius * cos(t * 2 * PI / ((double) STEP)), radius * sin(t * 2 * PI / ((double) STEP))))
                .buffer(2, 1)
                .filter(pts -> pts.size() == 2)
                .map(pts -> addLine(pts.get(0), pts.get(1)));
    }

    static Observable<Command> line(String id, Pt a, Pt b) {
        return interval(40, MILLISECONDS)
                .take(STEP + 1)
                .map(t -> a.interpolate(b, a.distance(b) * t / ((double) STEP)))
                .map(p -> uniq(id, addLine(a, p)));
    }
}

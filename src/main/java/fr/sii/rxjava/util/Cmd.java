package fr.sii.rxjava.util;

import fr.sii.rxjava.util.cmds.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Contract;

import java.util.Collection;

import static fr.sii.rxjava.util.Pt.pt;
import static java.util.Arrays.asList;
import static javafx.scene.paint.Color.*;

public interface Cmd {

    // === POINT ===
    @Contract(pure = true)
    static Command addPt(Pt p) {
        return addPt(p.x, p.y, RED);
    }

    @Contract(pure = true)
    static Command addPt(Pt p, Color color) {
        return addPt(p.x, p.y, color);
    }

    @Contract(pure = true)
    static Command addPt(double x, double y) {
        return addPt(pt(x, y));
    }

    @Contract(pure = true)
    static Command addPt(double x, double y, Color color) {
        return addPt(x, y, 5, color);
    }

    @Contract(pure = true)
    static Command addPt(double x, double y, int radius, javafx.scene.paint.Color color) {
        return new AddPtCmd(x, y, radius, color, g -> {
            g.setFill(color);
            g.fillOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);
        });
    }

    // === TEXT ===
    @Contract(pure = true)
    static Command addText(Pt p, String text) {
        return addText(p, text, BLACK);
    }

    @Contract(pure = true)
    static Command addText(Pt p, String text, Color color) {
        return addText(p.x, p.y, text, color);
    }

    @Contract(pure = true)
    static Command addText(double x, double y, String text) {
        return addText(x, y, text, BLACK);
    }

    @Contract(pure = true)
    static Command addText(double x, double y, String text, Color color) {
        return new AddTextCmd(x, y, text, color, g -> {
            g.setFill(color);
            g.fillText(text, (int) x, (int) y);
        });
    }

    // === LINE ===
    @Contract(pure = true)
    static Command addLine(Pt p1, Pt p2) {
        return addLine(p1, p2, BLUE);
    }

    @Contract(pure = true)
    static Command addLine(Pt p1, Pt p2, Color color) {
        return addLine(p1.x, p1.y, p2.x, p2.y, color);
    }

    @Contract(pure = true)
    static Command addLine(int x1, int y1, int x2, int y2) {
        return addLine(pt(x1, y1), pt(x2, y2));
    }

    @Contract(pure = true)
    static Command addLine(int x1, int y1, int x2, int y2, Color color) {
        return new AddLineCmd(x1, y1, x2, y2, color, g -> {
            g.setStroke(color);
            g.strokeLine(x1, y1, x2, y2);
        });
    }

    // === LOG ===
    @Contract(pure = true)
    static Command addLog(Object value) {
        return addLog(value.toString(), GRAY);
    }

    @Contract(pure = true)
    static Command addLog(String msg, Color color) {
        return new AddLogCmd(msg, color, new AddLogCmd.LogDrawing(40, msg, color));
    }

    // === CLEAR ===
    @Contract(pure = true)
    static Command clear() {
        return ClearCmd.INSTANCE;
    }

    // === GROUP ===
    @Contract(pure = true)
    static Command group(Command... cmds) {
        return group(asList(cmds));
    }

    @Contract(pure = true)
    static Command group(Collection<Command> cmds) {
        return new GroupCmd(cmds);
    }

    // === UNIQ ===
    @Contract(pure = true)
    static Command removeUniq(String id) {
        return new RemoveUniqCmd(id);
    }

    @Contract(pure = true)
    static Command uniq(String id, Command delegateCmd) {
        return new UniqCmd(id, delegateCmd);
    }

    // === NoOp ===
    @Contract(pure = true)
    static Command noOp() {
        return new NoOpCmd();
    }
}

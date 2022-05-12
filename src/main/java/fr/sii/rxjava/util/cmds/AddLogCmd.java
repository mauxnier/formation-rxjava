package fr.sii.rxjava.util.cmds;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;

import static fr.sii.rxjava.util.Utils.*;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.partitioningBy;

public final class AddLogCmd extends BaseCmd {

    public final String msg;
    public final Color color;

    public AddLogCmd(String msg, Color color, Drawing drawing) {
        super(drawing);
        this.msg = requireNonNull(msg);
        this.color = requireNonNull(color);
    }

    @Override
    public Drawings apply(Drawings drawings, Drawing newDrawing) {
        Map<Boolean, java.util.List<Drawing>> logs = drawings.values().stream().collect(partitioningBy(LogDrawing.class::isInstance));

        return new Drawings(
                imAppend(
                        imConcat(
                                logs.get(false),
                                logs.get(true).stream()
                                        .map(LogDrawing.class::cast)
                                        .map(logCmd -> newDrawing instanceof LogDrawing ? logCmd.moveUp() : logCmd)
                                        .filter(a -> a.y > 0)
                                        .collect(toImList())),
                        newDrawing));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddLogCmd addLogCmd = (AddLogCmd) o;

        if (!msg.equals(addLogCmd.msg)) {
            return false;
        }
        return color.equals(addLogCmd.color);
    }

    @Override
    public int hashCode() {
        int result = msg.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AddLogCmd{msg='" + msg + "', color=" + colorHex(color) + "}";
    }

    public final static class LogDrawing implements Drawing {
        final long y;
        final String msg;
        final Color color;

        public LogDrawing(long y, String msg, Color color) {
            this.y = y;
            this.msg = msg;
            this.color = color;
        }

        LogDrawing moveUp() {
            return new LogDrawing(y - 1, msg, color);
        }

        @Override
        public void accept(GraphicsContext g) {
            g.setFill(color);
            g.fillText(msg, 3, 17 * y);
        }
    }
}

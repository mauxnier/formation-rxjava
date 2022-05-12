package fr.sii.rxjava.util.cmds;

import javafx.scene.canvas.GraphicsContext;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public final class UniqCmd extends BaseCmd {
    public final String id;
    public final Drawing uniqDrawing;
    private final Command cmd;

    public UniqCmd(String id, Command cmd) {
        super(new UniqDrawing(id, cmd));
        this.id = requireNonNull(id);
        this.uniqDrawing = requireNonNull(cmd.getDrawing());
        this.cmd = cmd;
    }

    public Command getCmd() { return cmd; }

    @Override
    public Drawings apply(Drawings drawings, Drawing newDrawing) {
        Predicate<Drawing> notUniqDrawingOrWithDifferentName = a -> !(a instanceof UniqDrawing)
                || !(newDrawing instanceof UniqDrawing)
                || (a instanceof UniqDrawing && !((UniqDrawing) a).id.equals(((UniqDrawing) newDrawing).id));

        return drawings.filterAndAppend(notUniqDrawingOrWithDifferentName, newDrawing);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        UniqCmd uniqCmd = (UniqCmd) o;

        if (!id.equals(uniqCmd.id)) { return false; }
        return cmd.equals(uniqCmd.cmd);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + cmd.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UniqCmd{id='" + id + '\'' + ", cmds=" + cmd + '}';
    }

    static class UniqDrawing implements Drawing {
        public final String id;
        private final Drawing drawing;

        public UniqDrawing(String id, Command delegateCmd) {
            this.id = id;
            drawing = delegateCmd.getDrawing();
        }

        @Override
        public void accept(GraphicsContext g) {
            drawing.accept(g);
        }
    }
}

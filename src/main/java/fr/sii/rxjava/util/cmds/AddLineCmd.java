package fr.sii.rxjava.util.cmds;

import java.awt.*;

import static java.util.Objects.requireNonNull;

public final class AddLineCmd extends BaseCmd {

    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;
    public final Color color;

    public AddLineCmd(int x1, int y1, int x2, int y2, Color color, Drawing drawing) {
        super(drawing);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = requireNonNull(color);
    }

    public Color getColor() { return color; }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        AddLineCmd addLineCmd = (AddLineCmd) o;

        if (x1 != addLineCmd.x1) { return false; }
        if (y1 != addLineCmd.y1) { return false; }
        if (x2 != addLineCmd.x2) { return false; }
        if (y2 != addLineCmd.y2) { return false; }
        return color.equals(addLineCmd.color);
    }

    @Override
    public int hashCode() {
        int result = x1;
        result = 31 * result + y1;
        result = 31 * result + x2;
        result = 31 * result + y2;
        result = 31 * result + color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AddLineCmd{x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + ", color=" + colorHex(color) + '}';
    }
}

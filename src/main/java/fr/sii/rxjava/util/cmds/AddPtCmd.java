package fr.sii.rxjava.util.cmds;

import javafx.scene.paint.Color;

import static java.util.Objects.requireNonNull;

public final class AddPtCmd extends BaseCmd {
    public final double x;
    public final double y;
    public final int radius;
    public final Color color;

    public AddPtCmd(double x, double y, int radius, Color color, Drawing drawing) {
        super(drawing);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = requireNonNull(color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddPtCmd addPtCmd = (AddPtCmd) o;

        if (Double.compare(addPtCmd.x, x) != 0) {
            return false;
        }
        if (Double.compare(addPtCmd.y, y) != 0) {
            return false;
        }
        if (radius != addPtCmd.radius) {
            return false;
        }
        return color.equals(addPtCmd.color);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + radius;
        result = 31 * result + color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AddPtCmd{x=" + x + ", y=" + y + ", radius=" + radius + ", color=" + colorHex(color) + '}';
    }
}

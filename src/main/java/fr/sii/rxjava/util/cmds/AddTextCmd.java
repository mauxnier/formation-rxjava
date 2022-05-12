package fr.sii.rxjava.util.cmds;

import javafx.scene.paint.Color;

import static java.util.Objects.requireNonNull;

public final class AddTextCmd extends BaseCmd {

    public final double x;
    public final double y;
    public final String text;
    public final Color color;

    public AddTextCmd(double x, double y, String text, Color color, Drawing drawing) {
        super(drawing);
        this.x = x;
        this.y = y;
        this.text = requireNonNull(text);
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

        AddTextCmd addTextCmd = (AddTextCmd) o;

        if (Double.compare(addTextCmd.x, x) != 0) {
            return false;
        }
        if (Double.compare(addTextCmd.y, y) != 0) {
            return false;
        }
        if (!text.equals(addTextCmd.text)) {
            return false;
        }
        return color.equals(addTextCmd.color);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + text.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AddTextCmd{x=" + x + ", y=" + y + ", addText='" + text + '\'' + ", color=" + colorHex(color) + '}';
    }
}

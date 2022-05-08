package fr.sii.rxjava.util.cmds;

import fr.sii.rxjava.util.cmds.UniqCmd.UniqDrawing;

import static java.util.Objects.requireNonNull;

public final class RemoveUniqCmd extends BaseCmd {
    public final String id;

    public RemoveUniqCmd(String id) {
        super(EMPTY_DRAWING);
        this.id = requireNonNull(id);
    }

    @Override
    public Drawings call(Drawings drawings, Drawing drawing) {
        return drawings.filter(d -> !(d instanceof UniqDrawing) || !((UniqDrawing) d).id.equals(id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RemoveUniqCmd that = (RemoveUniqCmd) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "RemoveUniqCmd{id='" + id + '}';
    }
}

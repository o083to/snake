package o083to.model.frog;

import o083to.model.Cell;
import o083to.model.Player;

public abstract class Frog extends Player {

    protected Cell body;

    protected Frog(Cell position, int delay) {
        super(delay);
        body = position;
    }

    public Cell getBody() {
        return body;
    }

    public abstract int getLengthFactor();

    public abstract int getScoreFactor();

    @Override
    protected void move() {

    }
}

package o083to.model.frog;

import o083to.model.Cell;

public class GreenFrog extends Frog {

    private static final int LENGTH_FACTOR = 1;
    private static final int SCORE_FACTOR = 1;

    public GreenFrog(Cell position, int delay) {
        super(position, delay);
    }

    @Override
    public int getLengthFactor() {
        return LENGTH_FACTOR;
    }

    @Override
    public int getScoreFactor() {
        return SCORE_FACTOR;
    }
}

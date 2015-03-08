package o083to.model.frog;

import o083to.Game;
import o083to.model.Cell;

public class GreenFrog extends Frog {

    private static final int LENGTH_FACTOR = 1;
    private static final int SCORE_FACTOR = 1;

    public GreenFrog(Game game, Cell position, int delay) {
        super(game, position, delay);
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

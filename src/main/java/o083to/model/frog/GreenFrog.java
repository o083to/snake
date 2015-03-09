package o083to.model.frog;

import o083to.Game;
import o083to.model.board.Cell;

public class GreenFrog extends Frog {

    public GreenFrog(Game game, Cell position, int delay) {
        super(game, position, delay);
    }

    @Override
    public int getLengthFactor() {
        return 1;
    }

    @Override
    public int getScoreFactor() {
        return 1;
    }

    @Override
    public boolean isPoisonous() {
        return false;
    }
}

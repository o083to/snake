package o083to.model.frog;

import o083to.Game;
import o083to.model.Cell;

public class RedFrog extends Frog {

    public RedFrog(Game game, Cell position, int delay) {
        super(game, position, delay);
    }

    @Override
    public int getLengthFactor() {
        return -1;
    }

    @Override
    public int getScoreFactor() {
        return 2;
    }

    @Override
    public boolean isPoisonous() {
        return false;
    }
}

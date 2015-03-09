package o083to.model.frog;

import o083to.Game;
import o083to.model.board.Cell;

public class BlueFrog extends Frog {

    public BlueFrog(Game game, Cell position, int delay) {
        super(game, position, delay);
    }

    @Override
    public int getLengthFactor() {
        return 0;
    }

    @Override
    public int getScoreFactor() {
        return 0;
    }

    @Override
    public boolean isPoisonous() {
        return true;
    }
}
